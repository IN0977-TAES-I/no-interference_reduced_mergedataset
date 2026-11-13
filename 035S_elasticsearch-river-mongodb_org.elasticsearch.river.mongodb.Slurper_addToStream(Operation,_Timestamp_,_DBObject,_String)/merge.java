// class name : org.elasticsearch.river.mongodb.Slurper
// method name: addToStream(Operation, Timestamp<?>, DBObject, String)
// side       : merge
// region     : lines 736-752 (affected lines: [736, 737, 738, 739, 740, 741, 742, 743, 747, 748, 751, 752])
class Slurper {
    void addToStream_Operation__Timestamp_____DBObject__String_() {
            builder.mongoCollection(riverName);
        }

        if (settings.settings().containsKey(INDEX_OBJECT)) {
            Map<String, Object> indexSettings = (Map<String, Object>) settings.settings().get(INDEX_OBJECT);
            builder.indexName(XContentMapValues.nodeStringValue(indexSettings.get(NAME_FIELD), builder.mongoDb));
            builder.typeName(XContentMapValues.nodeStringValue(indexSettings.get(TYPE_FIELD), builder.mongoDb));

            Bulk.Builder bulkBuilder = new Bulk.Builder();
            if (indexSettings.containsKey(BULK_FIELD)) {
                Map<String, Object> bulkSettings = (Map<String, Object>) indexSettings.get(BULK_FIELD);
                int bulkActions = XContentMapValues.nodeIntegerValue(bulkSettings.get(ACTIONS_FIELD), DEFAULT_BULK_ACTIONS);
                bulkBuilder.bulkActions(bulkActions);
                String size = XContentMapValues.nodeStringValue(bulkSettings.get(SIZE_FIELD), DEFAULT_BULK_SIZE.toString());
                bulkBuilder.bulkSize(ByteSizeValue.parseBytesSizeValue(size));
                bulkBuilder.concurrentRequests(XContentMapValues.nodeIntegerValue(bulkSettings.get(CONCURRENT_REQUESTS_FIELD),
                        EsExecutors.boundedNumberOfProcessors(ImmutableSettings.EMPTY)));
    }
}
