// class name : org.elasticsearch.action.support.replication.ReplicationOperation
// method name: execute()
// side       : left
// region     : lines 98-119 (affected lines: [98, 99, 101, 108, 109, 110, 113, 119])
class ReplicationOperation {
    void execute__() {
    public RestStatus status() {
        return getShardInfo().status();
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        shardId = ShardId.readShardId(in);
        type = in.readString();
        id = in.readString();
        version = in.readZLong();
        seqNo = in.readZLong();
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        shardId.writeTo(out);
        out.writeString(type);
        out.writeString(id);
        out.writeZLong(version);
        out.writeZLong(seqNo);
    }
}
