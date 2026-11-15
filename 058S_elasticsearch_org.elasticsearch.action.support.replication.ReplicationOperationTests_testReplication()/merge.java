class ReplicationOperationTests {
    public void testReplication() throws Exception {
        final String index = "test";
        final ShardId shardId = new ShardId(index, "_na_", 0);

        ClusterState state = stateWithActivePrimary(index, true, randomInt(5));
        final long primaryTerm = state.getMetaData().index(index).primaryTerm(0);
        final IndexShardRoutingTable indexShardRoutingTable = state.getRoutingTable().shardRoutingTable(shardId);
        ShardRouting primaryShard = indexShardRoutingTable.primaryShard();
        if (primaryShard.relocating() && randomBoolean()) {
            state = ClusterState.builder(state)
                .nodes(DiscoveryNodes.builder(state.nodes()).localNodeId(primaryShard.relocatingNodeId())).build();
            primaryShard = primaryShard.buildTargetRelocatingShard();
        }

        final Set<ShardRouting> expectedReplicas = getExpectedReplicas(shardId, state);

        final Map<ShardRouting, Throwable> expectedFailures = new HashMap<>();
        final Set<ShardRouting> expectedFailedShards = new HashSet<>();
        for (ShardRouting replica : expectedReplicas) {
            if (randomBoolean()) {
                Throwable t;
                boolean criticalFailure = randomBoolean();
                if (criticalFailure) {
                    t = new CorruptIndexException("simulated", (String) null);
                } else {
                    t = new IndexShardNotStartedException(shardId, IndexShardState.RECOVERING);
                }
                logger.debug("--> simulating failure on {} with [{}]", replica, t.getClass().getSimpleName());
                expectedFailures.put(replica, t);
                if (criticalFailure) {
                    expectedFailedShards.add(replica);
                }
            }
        }

        Request request = new Request(shardId);
        PlainActionFuture<TestPrimary.Result> listener = new PlainActionFuture<>();
        final ClusterState finalState = state;
        final TestReplicaProxy replicasProxy = new TestReplicaProxy(expectedFailures);
        final TestPrimary primary = new TestPrimary(primaryShard, primaryTerm);
        final TestReplicationOperation op = new TestReplicationOperation(request,
            primary, listener, replicasProxy, () -> finalState);
        op.execute();

        assertThat(request.primaryTerm(), equalTo(primaryTerm));
        assertThat("request was not processed on primary", request.processedOnPrimary.get(), equalTo(true));
        assertThat(request.processedOnReplicas, equalTo(expectedReplicas));
        assertThat(replicasProxy.failedReplicas, equalTo(expectedFailedShards));
        assertTrue("listener is not marked as done", listener.isDone());
        ShardInfo shardInfo = listener.actionGet().getShardInfo();
        assertThat(shardInfo.getFailed(), equalTo(expectedFailedShards.size()));
        assertThat(shardInfo.getFailures(), arrayWithSize(expectedFailedShards.size()));
        assertThat(shardInfo.getSuccessful(), equalTo(1 + expectedReplicas.size() - expectedFailures.size()));
        final List<ShardRouting> unassignedShards =
            indexShardRoutingTable.shardsWithState(ShardRoutingState.UNASSIGNED);
        final int totalShards = 1 + expectedReplicas.size() + unassignedShards.size();
        assertThat(shardInfo.getTotal(), equalTo(totalShards));

        assertThat(primary.knownLocalCheckpoints.remove(primaryShard.allocationId().getId()), equalTo(primary.localCheckpoint));
        assertThat(primary.knownLocalCheckpoints, equalTo(replicasProxy.generatedLocalCheckpoints));
    }
}
