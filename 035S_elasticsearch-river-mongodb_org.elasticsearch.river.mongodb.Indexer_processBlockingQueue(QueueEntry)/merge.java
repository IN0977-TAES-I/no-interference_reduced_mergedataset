class Indexer {
    ESLogger logger;
    MongoDBRiverDefinition definition;
    Client client;
    ScriptService scriptService;

    @SuppressWarnings({ "unchecked" })
    private Timestamp<?> processBlockingQueue(QueueEntry entry) {
        Operation operation = entry.getOperation();
        if (entry.getData().get(MongoDBRiver.MONGODB_ID_FIELD) == null
                && (operation == Operation.INSERT || operation == Operation.UPDATE || operation == Operation.DELETE)) {
            logger.warn("Cannot get object id. Skip the current item: [{}]", entry.getData());
            return null;
        }

        Timestamp<?> lastTimestamp = entry.getOplogTimestamp();
        String type;
        if (definition.isImportAllCollections()) {
            type = entry.getCollection();
        } else {
            type = definition.getTypeName();
        }
        if (MongoDBRiver.OPLOG_COMMAND_OPERATION.equals(operation)) {
            try {
                updateBulkRequest(entry.getData(), null, operation, definition.getIndexName(), type, null, null);
            } catch (IOException ioEx) {
                logger.error("Update bulk failed.", ioEx);
            }
            return lastTimestamp;
        }

        String objectId = "";
        if (entry.getData().get(MongoDBRiver.MONGODB_ID_FIELD) != null) {
            objectId = entry.getData().get(MongoDBRiver.MONGODB_ID_FIELD).toString();
        }

        if (entry.isAttachment()) {
            try {
                updateBulkRequest(entry.getData(), objectId, operation, definition.getIndexName(), type, null, null);
            } catch (IOException ioEx) {
                logger.error("Update bulk failed.", ioEx);
            }
            return lastTimestamp;
        }

        if (hasScript() && definition.isAdvancedTransformation()) {
            return applyAdvancedTransformation(entry, type);
        }

        if (logger.isTraceEnabled()) {
            logger.trace("updateBulkRequest for id: [{}], operation: [{}]", objectId, operation);
        }

        if (!definition.getIncludeCollection().isEmpty()) {
            logger.trace("About to include collection. set attribute {} / {} ", definition.getIncludeCollection(),
                    definition.getMongoCollection());
            entry.getData().put(definition.getIncludeCollection(), definition.getMongoCollection());
        }

        Map<String, Object> ctx = new HashMap();
        Map<String, Object> data = entry.getData().toMap();
        if (hasScript()) {
            if (ctx != null) {
                ctx.put("document", entry.getData());
                ctx.put("operation", operation.getValue());
                if (!objectId.isEmpty()) {
                    ctx.put("id", objectId);
                }
                if (logger.isTraceEnabled()) {
                    logger.trace("Script to be executed: {} - {}", definition.getScriptType(), definition.getScript());
                    logger.trace("Context before script executed: {}", ctx);
                }
                try {
                    ExecutableScript executableScript = scriptService.executable(definition.getScriptType(), definition.getScript(),
                            ScriptService.ScriptType.INLINE, ImmutableMap.of("logger", logger));
                    executableScript.setNextVar("ctx", ctx);
                    executableScript.run();
                    ctx = (Map<String, Object>) executableScript.unwrap(ctx);
                } catch (Exception e) {
                    logger.warn("failed to script process {}, ignoring", e, ctx);
                    MongoDBRiverHelper.setRiverStatus(client, definition.getRiverName(), Status.SCRIPT_IMPORT_FAILED);
                }
                if (logger.isTraceEnabled()) {
                    logger.trace("Context after script executed: {}", ctx);
                }
                if (isDocumentIgnored(ctx)) {
                    logger.trace("From script ignore document id: {}", objectId);
                    return lastTimestamp;
                }
                if (isDocumentDeleted(ctx)) {
                    ctx.put("operation", MongoDBRiver.OPLOG_DELETE_OPERATION);
                }
                if (ctx.containsKey("document")) {
                    data = (Map<String, Object>) ctx.get("document");
                    logger.trace("From script document: {}", data);
                }
                operation = extractOperation(ctx);
                logger.trace("From script operation: {} -> {}", ctx.get("operation").toString(), operation);
            }
        }

        try {
            String index = extractIndex(ctx);
            type = extractType(ctx, type);
            String parent = extractParent(ctx);
            String routing = extractRouting(ctx);
            objectId = extractObjectId(ctx, objectId);
            updateBulkRequest(new BasicDBObject(data), objectId, operation, index, type, routing, parent);
        } catch (IOException e) {
            logger.warn("failed to parse {}", e, entry.getData());
        }
        return lastTimestamp;
    }
}
