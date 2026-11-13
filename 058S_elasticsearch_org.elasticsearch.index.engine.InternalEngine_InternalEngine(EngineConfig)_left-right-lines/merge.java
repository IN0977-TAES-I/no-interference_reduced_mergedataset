// class name : org.elasticsearch.index.engine.InternalEngine
// method name: InternalEngine(EngineConfig)
// side       : merge
// region     : lines 134-164 (affected lines: [134, 161, 162, 163, 164])
class InternalEngine {
    void InternalEngine_EngineConfig_() {
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        shardId.writeTo(out);
        out.writeString(type);
        out.writeString(id);
        out.writeZLong(version);
        out.writeZLong(seqNo);
        out.writeBoolean(forcedRefresh);
    }

    static final class Fields {
        static final String _INDEX = "_index";
        static final String _TYPE = "_type";
        static final String _ID = "_id";
        static final String _VERSION = "_version";
        static final String _SHARD_ID = "_shard_id";
        static final String _SEQ_NO = "_seq_no";
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        ReplicationResponse.ShardInfo shardInfo = getShardInfo();
        builder.field(Fields._INDEX, shardId.getIndexName())
            .field(Fields._TYPE, type)
            .field(Fields._ID, id)
            .field(Fields._VERSION, version)
            .field("forced_refresh", forcedRefresh);
        shardInfo.toXContent(builder, params);
        //nocommit: i'm not sure we want to expose it in the api but it will be handy for debugging while we work...
        builder.field(Fields._SHARD_ID, shardId.id());
        if (getSeqNo() >= 0) {
    }
}
