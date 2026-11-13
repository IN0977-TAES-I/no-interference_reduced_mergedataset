// class name : org.elasticsearch.action.DocWriteResponse
// method name: toXContent(XContentBuilder, Params)
// side       : merge
// region     : lines 160-166 (affected lines: [160, 163, 164, 165, 166])
class DocWriteResponse {
    void toXContent_XContentBuilder__Params_() {
            .field("forced_refresh", forcedRefresh);
        shardInfo.toXContent(builder, params);
        //nocommit: i'm not sure we want to expose it in the api but it will be handy for debugging while we work...
        builder.field(Fields._SHARD_ID, shardId.id());
        if (getSeqNo() >= 0) {
            builder.field(Fields._SEQ_NO, getSeqNo());
        }
    }
}
