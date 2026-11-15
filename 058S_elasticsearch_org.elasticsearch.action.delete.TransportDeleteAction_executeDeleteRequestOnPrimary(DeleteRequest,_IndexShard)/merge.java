class TransportDeleteAction {
    public static WriteResult<DeleteResponse> executeDeleteRequestOnPrimary(DeleteRequest request, IndexShard indexShard) {
        Engine.Delete delete = indexShard.prepareDeleteOnPrimary(request.type(), request.id(), request.version(), request.versionType());
        indexShard.delete(delete);
        request.versionType(delete.versionType().versionTypeForReplicationAndRecovery());
        request.version(delete.version());
        request.seqNo(delete.seqNo());

        assert request.versionType().validateVersionForWrites(request.version());
        DeleteResponse response = new DeleteResponse(indexShard.shardId(), request.type(), request.id(), delete.seqNo(), delete.version(), delete.found());
        return new WriteResult<>(response, delete.getTranslogLocation());
    }
}
