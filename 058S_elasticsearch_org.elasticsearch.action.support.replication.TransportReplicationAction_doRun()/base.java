class TransportReplicationAction {
    String transportReplicaAction;
    ReplicaRequest request;
    TransportChannel channel;
    ReplicationTask task;

    @Override
    protected void doRun() throws Exception {
        setPhase(task, "replica");
        assert request.shardId() != null : "request shardId must be set";
        try (Releasable ignored = acquireReplicaOperationLock(request.shardId(), request.primaryTerm())) {
            shardOperationOnReplica(request);
            if (logger.isTraceEnabled()) {
                logger.trace("action [{}] completed on shard [{}] for request [{}]", transportReplicaAction, request.shardId(),
                    request);
            }
        }
        setPhase(task, "finished");
        channel.sendResponse(TransportResponse.Empty.INSTANCE);
    }
}
