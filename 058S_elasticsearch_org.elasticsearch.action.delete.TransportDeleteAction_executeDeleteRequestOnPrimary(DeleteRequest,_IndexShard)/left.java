// class name : org.elasticsearch.action.delete.TransportDeleteAction
// method name: executeDeleteRequestOnPrimary(DeleteRequest, IndexShard)
// side       : left
// region     : lines 136-140 (affected lines: [136, 140])
class TransportDeleteAction {
    void executeDeleteRequestOnPrimary_DeleteRequest__IndexShard_() {
                .field(Fields._ID, id)
                .field(Fields._VERSION, version);
        shardInfo.toXContent(builder, params);
        //nocommit: i'm not sure we want to expose it in the api but it will be handy for debugging while we work...
        builder.field(Fields._SHARD_ID, shardId.id());
    }
}
