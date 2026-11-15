class TransportReplicationAction {
    ReplicaRequest request;
    ReplicationTask task;

    @Override
    protected void doRun() throws Exception {
        setPhase(task, "replica");
        assert request.shardId() != null : "request shardId must be set";
        ReplicaResult result;
        try (Releasable ignored = acquireReplicaOperationLock(request.shardId(), request.primaryTerm())) {
            result = shardOperationOnReplica(request);
        }
        result.respond(new ResponseListener());
    }
}
