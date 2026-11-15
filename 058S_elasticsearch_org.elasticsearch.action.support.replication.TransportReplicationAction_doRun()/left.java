class TransportReplicationAction {
    String transportReplicaAction;
    ReplicaRequest request;
    TransportChannel channel;
    ReplicationTask task;

    @Override
    protected void doRun() throws Exception {
        setPhase(task, "replica");
        final ReplicaResponse response;
        assert request.shardId() != null : "request shardId must be set";
        try (ShardReference replica = getReplicaShardReference(request.shardId(), request.primaryTerm())) {
            shardOperationOnReplica(request);
            response = new ReplicaResponse(replica.routingEntry().allocationId().getId(), replica.getLocalCheckpoint());
            if (logger.isTraceEnabled()) {
                logger.trace("action [{}] completed on shard [{}] for request [{}]", transportReplicaAction, request.shardId(),
                    request);
            }
        }
        setPhase(task, "finished");
        channel.sendResponse(response);
    }
}
