// class name : org.elasticsearch.river.mongodb.Indexer
// method name: processBlockingQueue(QueueEntry)
// side       : left
// region     : lines 161-201 (affected lines: [161, 176, 189, 198, 201])
class Indexer {
    void processBlockingQueue_QueueEntry_() {
        private List<ServerAddress> mongoServers = new ArrayList<ServerAddress>();
        // mongodb
        private String mongoDb;
        private String mongoCollection;
        private boolean mongoGridFS;
        private BasicDBObject mongoOplogFilter;// = new BasicDBObject();
        private BasicDBObject mongoCollectionFilter = new BasicDBObject();
        // mongodb.credentials
        private String mongoAdminUser = "";
        private String mongoAdminPassword = "";
        private String mongoLocalUser = "";
        private String mongoLocalPassword = "";
        // mongodb.options
        private MongoClientOptions mongoClientOptions = null;
        private int connectTimeout = 0;
        private int socketTimeout = 0;
        private boolean mongoSecondaryReadPreference = false;
        private boolean mongoUseSSL = false;
        private boolean mongoSSLVerifyCertificate = false;
        private boolean dropCollection = false;
        private Boolean isMongos = null;
        private Set<String> excludeFields = null;
        private Set<String> includeFields = null;
        private String includeCollection = "";
        private Timestamp<?> initialTimestamp = null;
        private String script = null;
        private String scriptType = null;
        private boolean advancedTransformation = false;
        private boolean skipInitialImport;
        private Set<String> parentTypes = null;
        private boolean storeStatistics;
        private String statisticsIndexName;
        private String statisticsTypeName;
        private boolean importAllCollections;
        private boolean disableIndexRefresh;

        // index
        private String indexName;
        private String typeName;
        private int throttleSize;

    }
}
