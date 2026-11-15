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
    Primary<Request, ReplicaRequest, PrimaryResultT> primary;
    PrimaryResultT primaryResult;

    void execute() throws Exception {
        final String writeConsistencyFailure = checkWriteConsistency ? checkWriteConsistency() : null;
        final ShardRouting primaryRouting = primary.routingEntry();
        final ShardId primaryId = primaryRouting.shardId();
        if (writeConsistencyFailure != null) {
            finishAsFailed(new UnavailableShardsException(primaryId,
                "{} Timeout: [{}], request: [{}]", writeConsistencyFailure, request.timeout(), request));
            return;
        }

        totalShards.incrementAndGet();
        pendingShards.incrementAndGet();
        primaryResult = primary.perform(request);
        primary.updateLocalCheckpointForShard(primaryRouting.allocationId().getId(), primary.localCheckpoint());
        final ReplicaRequest replicaRequest = primaryResult.replicaRequest();
        assert replicaRequest.primaryTerm() > 0 : "replicaRequest doesn't have a primary term";
        if (logger.isTraceEnabled()) {
            logger.trace("[{}] op [{}] completed on primary for request [{}]", primaryId, opType, request);
        }
        final List<ShardRouting> shards = getShards(primaryId, clusterStateSupplier.get());
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

        successfulShards.incrementAndGet();
        decPendingAndFinishIfNeeded();
    }
}
