// class name : org.elasticsearch.river.mongodb.Slurper
// method name: run()
// side       : left
// region     : lines 105-148 (affected lines: [105, 145, 148])
class Slurper {
    void run__() {

    // river
    private final String riverName;
    private final String riverIndexName;

    // mongodb.servers
    private final List<ServerAddress> mongoServers = new ArrayList<ServerAddress>();
    // mongodb
    private final String mongoDb;
    private final String mongoCollection;
    private final boolean mongoGridFS;
    private final BasicDBObject mongoOplogFilter;
    private final BasicDBObject mongoCollectionFilter;
    // mongodb.credentials
    private final String mongoAdminUser;
    private final String mongoAdminPassword;
    private final String mongoLocalUser;
    private final String mongoLocalPassword;

    // mongodb.options
    private final MongoClientOptions mongoClientOptions;
    private final int connectTimeout;
    private final int socketTimeout;
    private final boolean mongoSecondaryReadPreference;
    private final boolean mongoUseSSL;
    private final boolean mongoSSLVerifyCertificate;
    private final boolean dropCollection;
    private final Boolean isMongos;
    private final Set<String> excludeFields;
    private final Set<String> includeFields;
    private final String includeCollection;
    private final Timestamp<?> initialTimestamp;
    private final String script;
    private final String scriptType;
    private final boolean advancedTransformation;
    private final boolean skipInitialImport;
    private final Set<String> parentTypes;
    private final boolean storeStatistics;
    private final String statisticsIndexName;
    private final String statisticsTypeName;
    private final boolean importAllCollections;
    private final boolean disableIndexRefresh;
    // index
    private final String indexName;
    }
}
