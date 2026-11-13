// class name : org.elasticsearch.index.engine.InternalEngine
// method name: InternalEngine(EngineConfig)
// side       : left
// region     : lines 134-164 (affected lines: [134, 161, 162, 163, 164])
class InternalEngine {
    void InternalEngine_EngineConfig_() {
        builder.field(Fields._INDEX, shardId.getIndexName())
                .field(Fields._TYPE, type)
                .field(Fields._ID, id)
                .field(Fields._VERSION, version);
        shardInfo.toXContent(builder, params);
        //nocommit: i'm not sure we want to expose it in the api but it will be handy for debugging while we work...
        builder.field(Fields._SHARD_ID, shardId.id());
        if (getSeqNo() >= 0) {
            builder.field(Fields._SEQ_NO, getSeqNo());
        }
        return builder;
    }
}
    }
}
