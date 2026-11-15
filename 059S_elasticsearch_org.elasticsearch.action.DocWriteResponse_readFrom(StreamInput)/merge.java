class DocWriteResponse {
    ShardId shardId;
    String id;
    String type;
    long version;
    long seqNo;
    boolean forcedRefresh;

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        shardId = ShardId.readShardId(in);
        type = in.readString();
        id = in.readString();
        version = in.readZLong();
        seqNo = in.readZLong();
        forcedRefresh = in.readBoolean();
    }
}
