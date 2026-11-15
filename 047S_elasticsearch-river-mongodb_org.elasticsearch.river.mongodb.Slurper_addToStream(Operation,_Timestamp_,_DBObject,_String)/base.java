class Slurper {
    static ESLogger logger;
    MongoDBRiverDefinition definition;
    SharedContext context;
    DB slurpedDb;

    private void addToStream(final Operation operation, final Timestamp<?> currentTimestamp, final DBObject data, final String collection)
            throws InterruptedException {
        if (logger.isTraceEnabled()) {
            logger.trace("addToStream - operation [{}], currentTimestamp [{}], data [{}], collection [{}]", operation, currentTimestamp,
                    data, collection);
        }

        if (operation == Operation.DROP_DATABASE) {
            if (definition.isImportAllCollections()) {
                for (String name : slurpedDb.getCollectionNames()) {
                    context.getStream().put(new MongoDBRiver.QueueEntry(currentTimestamp, Operation.DROP_COLLECTION, data, name));
                }
            } else {
                context.getStream().put(new MongoDBRiver.QueueEntry(currentTimestamp, Operation.DROP_COLLECTION, data, collection));
            }
        } else {
            context.getStream().put(new MongoDBRiver.QueueEntry(currentTimestamp, operation, data, collection));
        }
    }
}
