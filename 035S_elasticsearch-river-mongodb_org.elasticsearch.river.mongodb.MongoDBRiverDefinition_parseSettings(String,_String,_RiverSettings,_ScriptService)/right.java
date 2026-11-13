// class name : org.elasticsearch.river.mongodb.MongoDBRiverDefinition
// method name: parseSettings(String, String, RiverSettings, ScriptService)
// side       : right
// region     : lines 475-655 (affected lines: [475, 500, 526, 534, 535, 536, 538, 539, 540, 541, 561, 567, 602, 620, 646, 655])
class MongoDBRiverDefinition {
    void parseSettings_String__String__RiverSettings__ScriptService_() {
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
                        logger.trace("Server: " + mongoHost + " - " + mongoPort);
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

            // MongoDB options
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
                    logger.trace("parentTypesSettings: " + parentTypesSettings);
                    boolean array = XContentMapValues.isArray(parentTypesSettings);

                    if (array) {
                        ArrayList<String> fields = (ArrayList<String>) parentTypesSettings;
                        for (String field : fields) {
                            logger.trace("Field: " + field);
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
                // builder.storeStatistics(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(STORE_STATISTICS_FIELD),
                // false));
                builder.importAllCollections(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(IMPORT_ALL_COLLECTIONS_FIELD),
                        false));
                builder.disableIndexRefresh(XContentMapValues.nodeBooleanValue(mongoOptionsSettings.get(DISABLE_INDEX_REFRESH_FIELD), false));
                builder.includeCollection(XContentMapValues.nodeStringValue(mongoOptionsSettings.get(INCLUDE_COLLECTION_FIELD), ""));

                if (mongoOptionsSettings.containsKey(INCLUDE_FIELDS_FIELD)) {
                    Set<String> includeFields = new HashSet<String>();
                    Object includeFieldsSettings = mongoOptionsSettings.get(INCLUDE_FIELDS_FIELD);
                    logger.trace("includeFieldsSettings: " + includeFieldsSettings);
                    boolean array = XContentMapValues.isArray(includeFieldsSettings);

                    if (array) {
                        ArrayList<String> fields = (ArrayList<String>) includeFieldsSettings;
                        for (String field : fields) {
                            logger.trace("Field: " + field);
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
                    logger.trace("excludeFieldsSettings: " + excludeFieldsSettings);
                    boolean array = XContentMapValues.isArray(excludeFieldsSettings);

                    if (array) {
                        ArrayList<String> fields = (ArrayList<String>) excludeFieldsSettings;
                        for (String field : fields) {
                            logger.trace("Field: " + field);
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
                        logger.error("Could not set initial timestamp", t);
                    } finally {
                        builder.initialTimestamp(timeStamp);
                    }
                }
            }
            builder.mongoClientOptions(mongoClientOptionsBuilder.build());

            // Credentials
            if (mongoSettings.containsKey(CREDENTIALS_FIELD)) {
                String dbCredential;
                String mau = "";
                String map = "";
                String mlu = "";
                String mlp = "";
                // String mdu = "";
                // String mdp = "";
                Object mongoCredentialsSettings = mongoSettings.get(CREDENTIALS_FIELD);
                boolean array = XContentMapValues.isArray(mongoCredentialsSettings);

                if (array) {
                    ArrayList<Map<String, Object>> credentials = (ArrayList<Map<String, Object>>) mongoCredentialsSettings;
    }
}
