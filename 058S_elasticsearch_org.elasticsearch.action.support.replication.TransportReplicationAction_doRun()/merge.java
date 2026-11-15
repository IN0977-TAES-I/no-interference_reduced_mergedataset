class TransportReplicationAction {
    ReplicaRequest request;
    ReplicationTask task;

    @Override
    protected void doRun() throws Exception {
        setPhase(task, "replica");
        final ReplicaResponse response;
        assert request.shardId() != null : "request shardId must be set";
        ReplicaResult result;
        try (ShardReference replica = getReplicaShardReference(request.shardId(), request.primaryTerm())) {
            result = shardOperationOnReplica(request);
            response = new ReplicaResponse(replica.routingEntry().allocationId().getId(), replica.getLocalCheckpoint());
        }
        result.respond(new ResponseListener(response));
    }
}
