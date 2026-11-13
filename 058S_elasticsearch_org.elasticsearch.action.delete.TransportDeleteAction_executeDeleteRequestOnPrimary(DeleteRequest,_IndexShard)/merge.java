// class name : org.elasticsearch.action.delete.TransportDeleteAction
// method name: executeDeleteRequestOnPrimary(DeleteRequest, IndexShard)
// side       : merge
// region     : lines 136-140 (affected lines: [136, 140])
class TransportDeleteAction {
    void executeDeleteRequestOnPrimary_DeleteRequest__IndexShard_() {
        shardId.writeTo(out);
        out.writeString(type);
        out.writeString(id);
        out.writeZLong(version);
        out.writeZLong(seqNo);
    }
}
