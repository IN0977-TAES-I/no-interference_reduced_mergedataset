class DocWriteResponse {
    ShardId shardId;
    String id;
    String type;
    long version;

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        ReplicationResponse.ShardInfo shardInfo = getShardInfo();
        builder.field(Fields._INDEX, shardId.getIndexName())
            .field(Fields._TYPE, type)
            .field(Fields._ID, id)
            .field(Fields._VERSION, version);
        shardInfo.toXContent(builder, params);
        return builder;
    }
}
