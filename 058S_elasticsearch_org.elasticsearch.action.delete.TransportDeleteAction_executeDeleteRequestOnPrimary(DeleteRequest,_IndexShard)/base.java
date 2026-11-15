class TransportDeleteAction {
    public static WriteResult<DeleteResponse> executeDeleteRequestOnPrimary(DeleteRequest request, IndexShard indexShard) {
        Engine.Delete delete = indexShard.prepareDeleteOnPrimary(request.type(), request.id(), request.version(), request.versionType());
        indexShard.delete(delete);
        request.versionType(delete.versionType().versionTypeForReplicationAndRecovery());
        request.version(delete.version());

        assert request.versionType().validateVersionForWrites(request.version());
        return new WriteResult<>(
            new DeleteResponse(indexShard.shardId(), request.type(), request.id(), delete.version(), delete.found()),
            delete.getTranslogLocation());
    }
}
