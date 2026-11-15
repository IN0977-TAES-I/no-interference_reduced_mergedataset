class StandardTitanTx {
    static Map<Long, InternalRelation> EMPTY_DELETED_RELATIONS;
    static ConcurrentMap<UniqueLockApplication, Lock> UNINITIALIZED_LOCKS;
    IDInspector idInspector;
    AttributeHandling attributeHandler;
    EdgeSerializer edgeSerializer;
    IndexSerializer indexSerializer;
    VertexCache vertexCache;
    AddedRelationsContainer addedRelations;
    Map<Long, InternalRelation> deletedRelations;
    Cache<IndexQuery, List<Object>> indexCache;
    IndexCache newVertexIndexEntries;
    ConcurrentMap<UniqueLockApplication, Lock> uniqueLocks;
    Map<String, Long> typeCache;
    AtomicLong temporaryID;
    boolean isOpen;

    public StandardTitanTx(StandardTitanGraph graph, TransactionConfiguration config, BackendTransaction txHandle) {
        Preconditions.checkNotNull(graph);
        Preconditions.checkArgument(graph.isOpen());
        Preconditions.checkNotNull(config);
        Preconditions.checkNotNull(txHandle);
        this.graph = graph;
        this.config = config;
        this.idInspector = graph.getIDInspector();
        this.attributeHandler = graph.getAttributeHandling();
        this.txHandle = txHandle;
        this.edgeSerializer = graph.getEdgeSerializer();
        this.indexSerializer = graph.getIndexSerializer();

        temporaryID = new AtomicLong(-1);

        int concurrencyLevel;
        if (config.isSingleThreaded()) {
            addedRelations = new SimpleBufferAddedRelations();
            concurrencyLevel = 1;
            typeCache = new HashMap<String, Long>();
            newVertexIndexEntries = new SimpleIndexCache();
        } else {
            addedRelations = new ConcurrentBufferAddedRelations();
            concurrencyLevel = 4;
            typeCache = new ConcurrentHashMap<String, Long>();
            newVertexIndexEntries = new ConcurrentIndexCache();
        }
        vertexCache = new LRUVertexCache(config.getVertexCacheSize(), concurrencyLevel);
        indexCache = CacheBuilder.newBuilder().weigher(new Weigher<IndexQuery, List<Object>>() {
            @Override
            public int weigh(IndexQuery q, List<Object> r) {
                return 2 + r.size();
            }
        }).concurrencyLevel(concurrencyLevel).maximumWeight(config.getIndexCacheWeight()).build();

        uniqueLocks = UNINITIALIZED_LOCKS;
        deletedRelations = EMPTY_DELETED_RELATIONS;

        this.isOpen = true;
    }
}
