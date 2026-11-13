// class name : org.elasticsearch.common.settings.IndexScopedSettings
// method name: BUILT_IN_INDEX_SETTINGS
// side       : base
// region     : lines 119-121 (affected lines: [119, 120, 121])
class IndexScopedSettings {
    void BUILT_IN_INDEX_SETTINGS() {
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        ReplicationResponse.ShardInfo shardInfo = getShardInfo();
        builder.field(Fields._INDEX, shardId.getIndexName())
    }
}
