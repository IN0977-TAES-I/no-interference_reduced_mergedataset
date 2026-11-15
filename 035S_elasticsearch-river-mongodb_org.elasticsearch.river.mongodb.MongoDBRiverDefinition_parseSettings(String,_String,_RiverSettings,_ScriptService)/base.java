class MongoDBRiverDefinition {
    static ESLogger logger;
    static String DEFAULT_DB_HOST;
    static int DEFAULT_DB_PORT;
    static int DEFAULT_BULK_ACTIONS;
    static TimeValue DEFAULT_FLUSH_INTERVAL;
    static ByteSizeValue DEFAULT_BULK_SIZE;
    static String DB_FIELD;
    static String SERVERS_FIELD;
    static String HOST_FIELD;
    static String PORT_FIELD;
    static String OPTIONS_FIELD;
    static String SECONDARY_READ_PREFERENCE_FIELD;
    static String CONNECTION_TIMEOUT;
    static String SOCKET_TIMEOUT;
    static String SSL_CONNECTION_FIELD;
    static String SSL_VERIFY_CERT_FIELD;
    static String DROP_COLLECTION_FIELD;
    static String EXCLUDE_FIELDS_FIELD;
    static String INCLUDE_FIELDS_FIELD;
    static String INCLUDE_COLLECTION_FIELD;
    static String INITIAL_TIMESTAMP_FIELD;
    static String INITIAL_TIMESTAMP_SCRIPT_TYPE_FIELD;
    static String INITIAL_TIMESTAMP_SCRIPT_FIELD;
    static String ADVANCED_TRANSFORMATION_FIELD;
    static String SKIP_INITIAL_IMPORT_FIELD;
    static String PARENT_TYPES_FIELD;
    static String STORE_STATISTICS_FIELD;
    static String IMPORT_ALL_COLLECTIONS_FIELD;
    static String DISABLE_INDEX_REFRESH_FIELD;
    static String FILTER_FIELD;
    static String CREDENTIALS_FIELD;
    static String USER_FIELD;
    static String PASSWORD_FIELD;
    static String SCRIPT_FIELD;
    static String SCRIPT_TYPE_FIELD;
    static String COLLECTION_FIELD;
    static String GRIDFS_FIELD;
    static String INDEX_OBJECT;
    static String NAME_FIELD;
    static String TYPE_FIELD;
    static String LOCAL_DB_FIELD;
    static String ADMIN_DB_FIELD;
    static String THROTTLE_SIZE_FIELD;
    static String BULK_SIZE_FIELD;
    static String BULK_TIMEOUT_FIELD;
    static String CONCURRENT_BULK_REQUESTS_FIELD;
    static String BULK_FIELD;
    static String ACTIONS_FIELD;
    static String SIZE_FIELD;
    static String CONCURRENT_REQUESTS_FIELD;
    static String FLUSH_INTERVAL_FIELD;

    @SuppressWarnings("unchecked")
    public synchronized static MongoDBRiverDefinition parseSettings(String riverName, String riverIndexName, RiverSettings settings,
            ScriptService scriptService) {

        logger.info("Parse river settings for {}", riverName);
        Preconditions.checkNotNull(riverName, "No riverName specified");
        Preconditions.checkNotNull(riverIndexName, "No riverIndexName specified");
        Preconditions.checkNotNull(settings, "No settings specified");

        Builder builder = new Builder();
        builder.riverName(riverName);
        builder.riverIndexName(riverIndexName);

        List<ServerAddress> mongoServers = new ArrayList<ServerAddress>();
        String mongoHost;
        int mongoPort;

        if (settings.settings().containsKey(MongoDBRiver.TYPE)) {
            Map<String, Object> mongoSettings = (Map<String, Object>) settings.settings().get(MongoDBRiver.TYPE);
            if (mongoSettings.containsKey(SERVERS_FIELD)) {
                Object mongoServersSettings = mongoSettings.get(SERVERS_FIELD);
                logger.trace("mongoServersSettings: " + mongoServersSettings);
                boolean array = XContentMapValues.isArray(mongoServersSettings);

                if (array) {
                    ArrayList<Map<String, Object>> feeds = (ArrayList<Map<String, Object>>) mongoServersSettings;
                    for (Map<String, Object> feed : feeds) {
                        mongoHost = XContentMapValues.nodeStringValue(feed.get(HOST_FIELD), null);
                        mongoPort = XContentMapValues.nodeIntegerValue(feed.get(PORT_FIELD), DEFAULT_DB_PORT);
                        logger.info("Server: " + mongoHost + " - " + mongoPort);
                        try {
                            mongoServers.add(new ServerAddress(mongoHost, mongoPort));
                        } catch (UnknownHostException uhEx) {
                            logger.warn("Cannot add mongo server {}:{}", uhEx, mongoHost, mongoPort);
                        }
                    }
                }
            } else {
                mongoHost = XContentMapValues.nodeStringValue(mongoSettings.get(HOST_FIELD), DEFAULT_DB_HOST);
                mongoPort = XContentMapValues.nodeIntegerValue(mongoSettings.get(PORT_FIELD), DEFAULT_DB_PORT);
                try {
                    mongoServers.add(new ServerAddress(mongoHost, mongoPort));
                } catch (UnknownHostException uhEx) {
                    logger.warn("Cannot add mongo server {}:{}", uhEx, mongoHost, mongoPort);
                }
            }
            builder.mongoServers(mongoServers);

            MongoClientOptions.Builder mongoClientOptionsBuilder = MongoClientOptions.builder().autoConnectRetry(true)
                    .socketKeepAlive(true);

            if (mongoSettings.containsKey(OPTIONS_FIELD)) {
                Map<String, Object> mongoOptionsSettings = (Map<String, Object>) mongoSettings.get(OPTIONS_FIELD);
                logger.trace("mongoOptionsSettings: " + mongoOptionsSettings);
                builder.mongoSecondaryReadPreference(XContentMapValues.nodeBooleanValue(
                        mongoOptionsSettings.get(SECONDARY_READ_PREFERENCE_FIELD), false));
                builder.connectTimeout(XContentMapValues.nodeIntegerValue(mongoOptionsSettings.get(CONNECTION_TIMEOUT), 0));
                builder.socketTimeout(XContentMapValues.nodeIntegerValue(mongoOptionsSettings.get(SOCKET_TIMEOUT), 0));
                builder.dropCollection(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(DROP_COLLECTION_FIELD), false));
                builder.mongoUseSSL(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(SSL_CONNECTION_FIELD), false));
                builder.mongoSSLVerifyCertificate(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(SSL_VERIFY_CERT_FIELD), true));
                builder.advancedTransformation(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(ADVANCED_TRANSFORMATION_FIELD),
                        false));
                builder.skipInitialImport(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(SKIP_INITIAL_IMPORT_FIELD), false));

                mongoClientOptionsBuilder.connectTimeout(builder.connectTimeout).socketTimeout(builder.socketTimeout);

                if (builder.mongoSecondaryReadPreference) {
                    mongoClientOptionsBuilder.readPreference(ReadPreference.secondaryPreferred());
                }

                if (builder.mongoUseSSL) {
                    mongoClientOptionsBuilder.socketFactory(getSSLSocketFactory());
                }

                if (mongoOptionsSettings.containsKey(PARENT_TYPES_FIELD)) {
                    Set<String> parentTypes = new HashSet<String>();
                    Object parentTypesSettings = mongoOptionsSettings.get(PARENT_TYPES_FIELD);
                    logger.debug("parentTypesSettings: " + parentTypesSettings);
                    boolean array = XContentMapValues.isArray(parentTypesSettings);

                    if (array) {
                        ArrayList<String> fields = (ArrayList<String>) parentTypesSettings;
                        for (String field : fields) {
                            logger.debug("Field: " + field);
                            parentTypes.add(field);
                        }
                    }

                    builder.parentTypes(parentTypes);
                }

                if (mongoOptionsSettings.containsKey(STORE_STATISTICS_FIELD)) {
                    Object storeStatistics = mongoOptionsSettings.get(STORE_STATISTICS_FIELD);
                    boolean object = XContentMapValues.isObject(storeStatistics);
                    if (object) {
                        Map<String, Object> storeStatisticsSettings = (Map<String, Object>) storeStatistics;
                        builder.storeStatistics(true);
                        builder.statisticsIndexName(XContentMapValues.nodeStringValue(storeStatisticsSettings.get(INDEX_OBJECT), riverName
                                + "-stats"));
                        builder.statisticsTypeName(XContentMapValues.nodeStringValue(storeStatisticsSettings.get(TYPE_FIELD), "stats"));
                    } else {
                        builder.storeStatistics(XContentMapValues.nodeBooleanValue(storeStatistics, false));
                        if (builder.storeStatistics) {
                            builder.statisticsIndexName(riverName + "-stats");
                            builder.statisticsTypeName("stats");
                        }
                    }
                }
                builder.importAllCollections(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(IMPORT_ALL_COLLECTIONS_FIELD),
                        false));
                builder.disableIndexRefresh(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(DISABLE_INDEX_REFRESH_FIELD), false));
                builder.includeCollection(XContentMapValues.nodeStringValue(mongoOptionsSettings.get(INCLUDE_COLLECTION_FIELD), ""));

                if (mongoOptionsSettings.containsKey(INCLUDE_FIELDS_FIELD)) {
                    Set<String> includeFields = new HashSet<String>();
                    Object includeFieldsSettings = mongoOptionsSettings.get(INCLUDE_FIELDS_FIELD);
                    logger.debug("includeFieldsSettings: " + includeFieldsSettings);
                    boolean array = XContentMapValues.isArray(includeFieldsSettings);

                    if (array) {
                        ArrayList<String> fields = (ArrayList<String>) includeFieldsSettings;
                        for (String field : fields) {
                            logger.debug("Field: " + field);
                            includeFields.add(field);
                        }
                    }

                    if (!includeFields.contains(MongoDBRiver.MONGODB_ID_FIELD)) {
                        includeFields.add(MongoDBRiver.MONGODB_ID_FIELD);
                    }
                    builder.includeFields(includeFields);
                } else if (mongoOptionsSettings.containsKey(EXCLUDE_FIELDS_FIELD)) {
                    Set<String> excludeFields = new HashSet<String>();
                    Object excludeFieldsSettings = mongoOptionsSettings.get(EXCLUDE_FIELDS_FIELD);
                    logger.debug("excludeFieldsSettings: " + excludeFieldsSettings);
                    boolean array = XContentMapValues.isArray(excludeFieldsSettings);

                    if (array) {
                        ArrayList<String> fields = (ArrayList<String>) excludeFieldsSettings;
                        for (String field : fields) {
                            logger.debug("Field: " + field);
                            excludeFields.add(field);
                        }
                    }

                    builder.excludeFields(excludeFields);
                }

                if (mongoOptionsSettings.containsKey(INITIAL_TIMESTAMP_FIELD)) {
                    BSONTimestamp timeStamp = null;
                    try {
                        Map<String, Object> initalTimestampSettings = (Map<String, Object>) mongoOptionsSettings
                                .get(INITIAL_TIMESTAMP_FIELD);
                        String scriptType = "js";
                        if (initalTimestampSettings.containsKey(INITIAL_TIMESTAMP_SCRIPT_TYPE_FIELD)) {
                            scriptType = initalTimestampSettings.get(INITIAL_TIMESTAMP_SCRIPT_TYPE_FIELD).toString();
                        }
                        if (initalTimestampSettings.containsKey(INITIAL_TIMESTAMP_SCRIPT_FIELD)) {

                            ExecutableScript scriptExecutable = scriptService.executable(scriptType,
                                    initalTimestampSettings.get(INITIAL_TIMESTAMP_SCRIPT_FIELD).toString(), Maps.newHashMap());
                            Object ctx = scriptExecutable.run();
                            logger.trace("initialTimestamp script returned: {}", ctx);
                            if (ctx != null) {
                                long timestamp = Long.parseLong(ctx.toString());
                                timeStamp = new BSONTimestamp((int) (new Date(timestamp).getTime() / 1000), 1);
                            }
                        }
                    } catch (Throwable t) {
                        logger.warn("Could set initial timestamp", t, new Object());
                    } finally {
                        builder.initialTimestamp(timeStamp);
                    }
                }
            }
            builder.mongoClientOptions(mongoClientOptionsBuilder.build());

            if (mongoSettings.containsKey(CREDENTIALS_FIELD)) {
                String dbCredential;
                String mau = "";
                String map = "";
                String mlu = "";
                String mlp = "";
                Object mongoCredentialsSettings = mongoSettings.get(CREDENTIALS_FIELD);
                boolean array = XContentMapValues.isArray(mongoCredentialsSettings);

                if (array) {
                    ArrayList<Map<String, Object>> credentials = (ArrayList<Map<String, Object>>) mongoCredentialsSettings;
                    for (Map<String, Object> credential : credentials) {
                        dbCredential = XContentMapValues.nodeStringValue(credential.get(DB_FIELD), null);
                        if (ADMIN_DB_FIELD.equals(dbCredential)) {
                            mau = XContentMapValues.nodeStringValue(credential.get(USER_FIELD), null);
                            map = XContentMapValues.nodeStringValue(credential.get(PASSWORD_FIELD), null);
                        } else if (LOCAL_DB_FIELD.equals(dbCredential)) {
                            mlu = XContentMapValues.nodeStringValue(credential.get(USER_FIELD), null);
                            mlp = XContentMapValues.nodeStringValue(credential.get(PASSWORD_FIELD), null);
                        }
                    }
                }
                builder.mongoAdminUser(mau);
                builder.mongoAdminPassword(map);
                builder.mongoLocalUser(mlu);
                builder.mongoLocalPassword(mlp);
            }

            builder.mongoDb(XContentMapValues.nodeStringValue(mongoSettings.get(DB_FIELD), riverName));
            builder.mongoCollection(XContentMapValues.nodeStringValue(mongoSettings.get(COLLECTION_FIELD), riverName));
            builder.mongoGridFS(XContentMapValues.nodeBooleanValue(mongoSettings.get(GRIDFS_FIELD), false));
            if (mongoSettings.containsKey(FILTER_FIELD)) {
                String filter = XContentMapValues.nodeStringValue(mongoSettings.get(FILTER_FIELD), "");
                filter = removePrefix("o.", filter);
                builder.mongoCollectionFilter(convertToBasicDBObject(filter));
                builder.mongoOplogFilter(convertToBasicDBObject(removePrefix("o.", filter)));
            }

            if (mongoSettings.containsKey(SCRIPT_FIELD)) {
                String scriptType = "js";
                builder.script(mongoSettings.get(SCRIPT_FIELD).toString());
                if (mongoSettings.containsKey("scriptType")) {
                    scriptType = mongoSettings.get("scriptType").toString();
                } else if (mongoSettings.containsKey(SCRIPT_TYPE_FIELD)) {
                    scriptType = mongoSettings.get(SCRIPT_TYPE_FIELD).toString();
                }
                builder.scriptType(scriptType);
            }
        } else {
            mongoHost = DEFAULT_DB_HOST;
            mongoPort = DEFAULT_DB_PORT;
            try {
                mongoServers.add(new ServerAddress(mongoHost, mongoPort));
                builder.mongoServers(mongoServers);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            builder.mongoDb(riverName);
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
                bulkBuilder.flushInterval(XContentMapValues.nodeTimeValue(bulkSettings.get(FLUSH_INTERVAL_FIELD), DEFAULT_FLUSH_INTERVAL));
                builder.throttleSize(XContentMapValues.nodeIntegerValue(indexSettings.get(THROTTLE_SIZE_FIELD), bulkActions * 5));
            } else {
                int bulkActions = XContentMapValues.nodeIntegerValue(indexSettings.get(BULK_SIZE_FIELD), DEFAULT_BULK_ACTIONS);
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
}
