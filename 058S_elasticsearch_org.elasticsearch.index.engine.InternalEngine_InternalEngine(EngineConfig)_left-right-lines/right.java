// class name : org.elasticsearch.index.engine.InternalEngine
// method name: InternalEngine(EngineConfig)
// side       : right
// region     : lines 134-164 (affected lines: [134, 161, 162, 163, 164])
class InternalEngine {
    void InternalEngine_EngineConfig_() {
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
            .field(Fields._VERSION, version)
            .field("forced_refresh", forcedRefresh);
        shardInfo.toXContent(builder, params);
        return builder;
    }
}
    }
}
