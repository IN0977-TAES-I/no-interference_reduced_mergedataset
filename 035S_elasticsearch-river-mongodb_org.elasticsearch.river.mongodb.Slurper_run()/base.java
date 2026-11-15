class Slurper {
    static ESLogger logger;
    MongoDBRiverDefinition definition;
    SharedContext context;
    Client client;
    Mongo mongo;
    DB slurpedDb;
    AtomicLong totalDocuments;

    @Override
    public void run() {
        while (context.getStatus() == Status.RUNNING) {
            try {
                if (!assignCollections()) {
                    break;
                }

                Timestamp<?> startTimestamp = null;
                if (!definition.isSkipInitialImport()) {
                    if (!riverHasIndexedFromOplog() && definition.getInitialTimestamp() == null) {
                        if (!isIndexEmpty()) {
                            MongoDBRiverHelper.setRiverStatus(client, definition.getRiverName(), Status.INITIAL_IMPORT_FAILED);
                            break;
                        }
                        if (definition.isImportAllCollections()) {
                            for (String name : slurpedDb.getCollectionNames()) {
                                DBCollection collection = slurpedDb.getCollection(name);
                                startTimestamp = doInitialImport(collection);
                            }
                        } else {
                            DBCollection collection = slurpedDb.getCollection(definition.getMongoCollection());
                            startTimestamp = doInitialImport(collection);
                        }
                    }
                } else {
                    logger.info("Skip initial import from collection {}", definition.getMongoCollection());
                }

                DBCursor cursor = null;
                try {
                    cursor = oplogCursor(startTimestamp);
                    if (cursor == null) {
                        cursor = processFullOplog();
                    }
                    while (cursor.hasNext()) {
                        DBObject item = cursor.next();
                        Object applied = item.get("a");
                        if (applied != null && !applied.equals(Boolean.TRUE)) {
                            logger.debug("Encountered oplog entry with a:false, ts:" + item.get("ts"));
                            break;
                        }
                        startTimestamp = processOplogEntry(item, startTimestamp);
                    }
                    logger.debug("Before waiting for 500 ms");
                    Thread.sleep(500);
                } catch (MongoException.CursorNotFound e) {
                    logger.info("Cursor {} has been closed. About to open a new cusor.", cursor.getCursorId());
                    logger.debug("Total document inserted [{}]", totalDocuments.get());
                } catch (SlurperException sEx) {
                    logger.warn("Exception in slurper", sEx);
                    break;
                } catch (Exception ex) {
                    logger.warn("Exception while looping in cursor", ex);
                    Thread.currentThread().interrupt();
                    break;
                } finally {
                    if (cursor != null) {
                        logger.trace("Closing oplog cursor");
                        cursor.close();
                    }
                }
            } catch (MongoInterruptedException mIEx) {
                logger.warn("Mongo driver has been interrupted", mIEx);
                if (mongo != null) {
                    mongo.close();
                    mongo = null;
                }
                Thread.currentThread().interrupt();
                break;
            } catch (MongoException e) {
                logger.error("Mongo gave an exception", e);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException iEx) {
                }
            } catch (NoSuchElementException e) {
                logger.warn("A mongoDB cursor bug ?", e);
            } catch (InterruptedException e) {
                logger.info("river-mongodb slurper interrupted");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
