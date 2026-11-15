class DocWriteResponse {
    ShardId shardId;
    String id;
    String type;
    long version;

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        shardId.writeTo(out);
        out.writeString(type);
        out.writeString(id);
        out.writeZLong(version);
    }
}
