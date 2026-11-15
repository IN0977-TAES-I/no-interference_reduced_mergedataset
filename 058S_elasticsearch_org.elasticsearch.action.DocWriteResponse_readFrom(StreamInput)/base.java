class DocWriteResponse {
    ShardId shardId;
    String id;
    String type;
    long version;

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        shardId = ShardId.readShardId(in);
        type = in.readString();
        id = in.readString();
        version = in.readZLong();
    }
}
