// class name : org.elasticsearch.action.support.replication.ReplicationOperationTests
// method name: testReplication()
// side       : base
// region     : lines 103-126 (affected lines: [103, 106, 108, 116, 125, 126])
class ReplicationOperationTests {
    void testReplication__() {
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        shardId.writeTo(out);
        out.writeString(type);
        out.writeString(id);
        out.writeZLong(version);
    }

    static final class Fields {
        static final String _INDEX = "_index";
        static final String _TYPE = "_type";
        static final String _ID = "_id";
        static final String _VERSION = "_version";
    }

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
