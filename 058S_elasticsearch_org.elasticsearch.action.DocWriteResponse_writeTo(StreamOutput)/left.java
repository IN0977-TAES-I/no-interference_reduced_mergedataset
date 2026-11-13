// class name : org.elasticsearch.action.DocWriteResponse
// method name: writeTo(StreamOutput)
// side       : left
// region     : lines 140-141 (affected lines: [140, 141])
class DocWriteResponse {
    void writeTo_StreamOutput_() {
        builder.field(Fields._SHARD_ID, shardId.id());
        if (getSeqNo() >= 0) {
    }
}
