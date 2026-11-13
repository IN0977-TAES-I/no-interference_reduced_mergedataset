// class name : org.elasticsearch.action.support.replication.ReplicationOperation
// method name: execute()
// side       : base
// region     : lines 98-119 (affected lines: [98, 99, 101, 108, 109, 110, 113, 119])
class ReplicationOperation {
    void execute__() {
        id = in.readString();
        version = in.readZLong();
    }

    @Override
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
    }
}
