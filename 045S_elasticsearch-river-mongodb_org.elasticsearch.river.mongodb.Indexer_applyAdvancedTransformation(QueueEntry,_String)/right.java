class Indexer {
    ESLogger logger;
    MongoDBRiverDefinition definition;
    Client client;
    ScriptService scriptService;

    @SuppressWarnings("unchecked")
    private Timestamp<?> applyAdvancedTransformation(QueueEntry entry, String type) {

        Timestamp<?> lastTimestamp = entry.getOplogTimestamp();
        Operation operation = entry.getOperation();
        String objectId = "";
        if (entry.getData().get(MongoDBRiver.MONGODB_ID_FIELD) != null) {
            objectId = entry.getData().get(MongoDBRiver.MONGODB_ID_FIELD).toString();
        }
        if (logger.isTraceEnabled()) {
            logger.trace("applyAdvancedTransformation for id: [{}], operation: [{}]", objectId, operation);
        }

        if (!definition.getIncludeCollection().isEmpty()) {
            logger.trace("About to include collection. set attribute {} / {} ", definition.getIncludeCollection(),
                    definition.getMongoCollection());
            entry.getData().put(definition.getIncludeCollection(), definition.getMongoCollection());
        }
        Map<String, Object> ctx = null;
        try {
            ctx = XContentFactory.xContent(XContentType.JSON).createParser("{}").mapAndClose();
        } catch (Exception e) {
        }

        List<Object> documents = new ArrayList<Object>();
        Map<String, Object> document = new HashMap<String, Object>();

        if (hasScript()) {
            if (ctx != null && documents != null) {

                document.put("data", entry.getData().toMap());
                if (!objectId.isEmpty()) {
                    document.put("id", objectId);
                }
                document.put("_index", definition.getIndexName());
                document.put("_type", type);
                document.put("operation", operation.getValue());

                documents.add(document);

                ctx.put("documents", documents);
                try {
                    ExecutableScript executableScript = scriptService.executable(definition.getScriptType(), definition.getScript(),
                            ImmutableMap.of("logger", logger));
                    if (logger.isTraceEnabled()) {
                        logger.trace("Script to be executed: {} - {}", definition.getScriptType(), definition.getScript());
                        logger.trace("Context before script executed: {}", ctx);
                    }
                    executableScript.setNextVar("ctx", ctx);
                    executableScript.run();
                    ctx = (Map<String, Object>) executableScript.unwrap(ctx);
                } catch (Exception e) {
                    logger.error("failed to script process {}, ignoring", e, ctx);
                    MongoDBRiverHelper.setRiverStatus(client, definition.getRiverName(), Status.SCRIPT_IMPORT_FAILED);
                }
                if (logger.isTraceEnabled()) {
                    logger.trace("Context after script executed: {}", ctx);
                }
                if (ctx.containsKey("documents") && ctx.get("documents") instanceof List<?>) {
                    documents = (List<Object>) ctx.get("documents");
                    for (Object object : documents) {
                        if (object instanceof Map<?, ?>) {
                            Map<String, Object> item = (Map<String, Object>) object;
                            if (logger.isTraceEnabled()) {
                                logger.trace("item: {}", item);
                            }
                            if (isDocumentDeleted(item)) {
                                item.put("operation", MongoDBRiver.OPLOG_DELETE_OPERATION);
                            }

                            String index = extractIndex(item);
                            type = extractType(item, type);
                            String parent = extractParent(item);
                            String routing = extractRouting(item);
                            operation = extractOperation(item);
                            boolean ignore = isDocumentIgnored(item);
                            Map<String, Object> data = (Map<String, Object>) item.get("data");
                            objectId = extractObjectId(data, objectId);
                            if (logger.isTraceEnabled()) {
                                logger.trace(
                                        "#### - Id: {} - operation: {} - ignore: {} - index: {} - type: {} - routing: {} - parent: {}",
                                        objectId, operation, ignore, index, type, routing, parent);
                            }
                            if (ignore) {
                                continue;
                            }
                            try {
                                updateBulkRequest(new BasicDBObject(data), objectId, operation, index, type, routing, parent);
                            } catch (IOException ioEx) {
                                logger.error("Update bulk failed.", ioEx);
                            }
                        }
                    }
                }
            }
        }

        return lastTimestamp;
    }
}
