// class name : org.elasticsearch.action.DocWriteResponse
// method name: writeTo(StreamOutput)
// side       : merge
// region     : lines 140-141 (affected lines: [140, 141])
class DocWriteResponse {
    void writeTo_StreamOutput_() {
        out.writeZLong(seqNo);
        out.writeBoolean(forcedRefresh);
    }
}
