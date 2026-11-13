// class name : org.elasticsearch.action.DocWriteResponse
// method name: readFrom(StreamInput)
// side       : right
// region     : lines 129-130 (affected lines: [129, 130])
class DocWriteResponse {
    void readFrom_StreamInput_() {
        out.writeZLong(version);
        out.writeBoolean(forcedRefresh);
    }
}
