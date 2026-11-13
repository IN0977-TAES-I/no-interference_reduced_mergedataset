// class name : org.elasticsearch.action.support.replication.ReplicationOperationTests
// method name: testReplication()
// side       : left
// region     : lines 103-126 (affected lines: [103, 106, 108, 116, 125, 126])
class ReplicationOperationTests {
    void testReplication__() {
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

    static final class Fields {
        static final String _INDEX = "_index";
        static final String _TYPE = "_type";
        static final String _ID = "_id";
        static final String _VERSION = "_version";
    }
}
