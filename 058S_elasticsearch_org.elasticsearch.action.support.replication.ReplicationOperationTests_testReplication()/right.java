// class name : org.elasticsearch.action.support.replication.ReplicationOperationTests
// method name: testReplication()
// side       : right
// region     : lines 103-126 (affected lines: [103, 106, 108, 116, 125, 126])
class ReplicationOperationTests {
    void testReplication__() {
    public void setForcedRefresh(boolean forcedRefresh) {
        this.forcedRefresh = forcedRefresh;
    }

    /** returns the rest status for this response (based on {@link ShardInfo#status()} */
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
        forcedRefresh = in.readBoolean();
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        shardId.writeTo(out);
    }
}
