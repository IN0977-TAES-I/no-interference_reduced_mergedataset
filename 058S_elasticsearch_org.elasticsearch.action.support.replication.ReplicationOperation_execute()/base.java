class ReplicationOperation {
    ESLogger logger;
    Request request;
    Supplier<ClusterState> clusterStateSupplier;
    String opType;
    AtomicInteger totalShards;
    AtomicInteger pendingShards;
    AtomicInteger successfulShards;
    boolean executeOnReplicas;
    boolean checkWriteConsistency;
    Primary<Request, ReplicaRequest, Response> primary;
    Response finalResponse;

    void execute() throws Exception {
        final String writeConsistencyFailure = checkWriteConsistency ? checkWriteConsistency() : null;
        final ShardId shardId = primary.routingEntry().shardId();
        if (writeConsistencyFailure != null) {
            finishAsFailed(new UnavailableShardsException(shardId,
                "{} Timeout: [{}], request: [{}]", writeConsistencyFailure, request.timeout(), request));
            return;
        }

        totalShards.incrementAndGet();
        pendingShards.incrementAndGet();
        Tuple<Response, ReplicaRequest> primaryResponse = primary.perform(request);
        successfulShards.incrementAndGet();
        finalResponse = primaryResponse.v1();
        ReplicaRequest replicaRequest = primaryResponse.v2();
        assert replicaRequest.primaryTerm() > 0 : "replicaRequest doesn't have a primary term";
        if (logger.isTraceEnabled()) {
            logger.trace("[{}] op [{}] completed on primary for request [{}]", shardId, opType, request);
        }
        List<ShardRouting> shards = getShards(shardId, clusterStateSupplier.get());
        final String localNodeId = primary.routingEntry().currentNodeId();
        for (final ShardRouting shard : shards) {
            if (executeOnReplicas == false || shard.unassigned()) {
                if (shard.primary() == false) {
                    totalShards.incrementAndGet();
                }
                continue;
            }

            if (shard.currentNodeId().equals(localNodeId) == false) {
                performOnReplica(shard, replicaRequest);
            }

            if (shard.relocating() && shard.relocatingNodeId().equals(localNodeId) == false) {
                performOnReplica(shard.buildTargetRelocatingShard(), replicaRequest);
            }
        }

        decPendingAndFinishIfNeeded();
    }
}
