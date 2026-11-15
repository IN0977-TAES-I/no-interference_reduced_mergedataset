class InternalEngine {
    long lastDeleteVersionPruneTimeMSec;
    ElasticsearchConcurrentMergeScheduler mergeScheduler;
    IndexWriter indexWriter;
    SearcherFactory searcherFactory;
    SearcherManager searcherManager;
    LiveVersionMap versionMap;
    IndexThrottle throttle;
    EngineConfig.OpenMode openMode;
    AtomicBoolean allowCommits;

    public InternalEngine(EngineConfig engineConfig) throws EngineException {
        super(engineConfig);
        openMode = engineConfig.getOpenMode();
        this.versionMap = new LiveVersionMap();
        store.incRef();
        IndexWriter writer = null;
        Translog translog = null;
        SearcherManager manager = null;
        EngineMergeScheduler scheduler = null;
        boolean success = false;
        try {
            this.lastDeleteVersionPruneTimeMSec = engineConfig.getThreadPool().estimatedTimeInMillis();
            mergeScheduler = scheduler = new EngineMergeScheduler(engineConfig.getShardId(), engineConfig.getIndexSettings());
            throttle = new IndexThrottle();
            this.searcherFactory = new SearchFactory(logger, isClosed, engineConfig);
            try {
                writer = createWriter(openMode == EngineConfig.OpenMode.CREATE_INDEX_AND_TRANSLOG);
                indexWriter = writer;
                translog = openTranslog(engineConfig, writer);
                assert translog.getGeneration() != null;
            } catch (IOException | TranslogCorruptedException e) {
                throw new EngineCreationFailureException(shardId, "failed to create engine", e);
            } catch (AssertionError e) {
                if (ExceptionsHelper.stackTrace(e).contains("org.apache.lucene.index.IndexWriter.filesExist")) {
                    throw new EngineCreationFailureException(shardId, "failed to create engine", e);
                } else {
                    throw e;
                }
            }

            this.translog = translog;
            manager = createSearcherManager();
            this.searcherManager = manager;
            this.versionMap.setManager(searcherManager);
            allowCommits.compareAndSet(true, openMode != EngineConfig.OpenMode.OPEN_INDEX_AND_TRANSLOG);
            success = true;
        } finally {
            if (success == false) {
                IOUtils.closeWhileHandlingException(writer, translog, manager, scheduler);
                versionMap.clear();
                if (isClosed.get() == false) {
                    store.decRef();
                }
            }
        }
        logger.trace("created new InternalEngine");
    }
}
