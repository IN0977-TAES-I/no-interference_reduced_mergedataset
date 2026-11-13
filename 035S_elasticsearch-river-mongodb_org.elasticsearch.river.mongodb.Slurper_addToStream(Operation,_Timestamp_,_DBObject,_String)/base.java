// class name : org.elasticsearch.river.mongodb.Slurper
// method name: addToStream(Operation, Timestamp<?>, DBObject, String)
// side       : base
// region     : lines 736-752 (affected lines: [736, 737, 738, 739, 740, 741, 742, 743, 747, 748, 751, 752])
class Slurper {
    void addToStream_Operation__Timestamp_____DBObject__String_() {
                bulkBuilder.bulkActions(bulkActions);
                bulkBuilder.bulkSize(DEFAULT_BULK_SIZE);
                bulkBuilder.flushInterval(XContentMapValues.nodeTimeValue(indexSettings.get(BULK_TIMEOUT_FIELD), DEFAULT_FLUSH_INTERVAL));
                bulkBuilder.concurrentRequests(XContentMapValues.nodeIntegerValue(indexSettings.get(CONCURRENT_BULK_REQUESTS_FIELD),
                        EsExecutors.boundedNumberOfProcessors(ImmutableSettings.EMPTY)));
                builder.throttleSize(XContentMapValues.nodeIntegerValue(indexSettings.get(THROTTLE_SIZE_FIELD), bulkActions * 5));
            }
            builder.bulk(bulkBuilder.build());
        } else {
            builder.indexName(builder.mongoDb);
            builder.typeName(builder.mongoDb);
            builder.bulk(new Bulk.Builder().build());
        }
        return builder.build();
    }

    private static SocketFactory getSSLSocketFactory() {
    }
}
