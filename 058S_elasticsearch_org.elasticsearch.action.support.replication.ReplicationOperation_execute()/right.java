// class name : org.elasticsearch.action.support.replication.ReplicationOperation
// method name: execute()
// side       : right
// region     : lines 98-119 (affected lines: [98, 99, 101, 108, 109, 110, 113, 119])
class ReplicationOperation {
    void execute__() {
    public boolean forcedRefresh() {
        return forcedRefresh;
    }

    @Override
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
    }
}
