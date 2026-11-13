// class name : org.elasticsearch.common.settings.ClusterSettings
// method name: BUILT_IN_CLUSTER_SETTINGS
// side       : merge
// region     : lines 254-265 (affected lines: [254, 255, 256, 257, 258, 259, 264, 265])
class ClusterSettings {
    void BUILT_IN_CLUSTER_SETTINGS() {
        assertThat("Cluster must be a single node cluster", health.getNumberOfDataNodes(), equalTo(1));
        IndicesService instanceFromNode = getInstanceFromNode(IndicesService.class);
        return instanceFromNode.indexServiceSafe(index);
    }

    /**
     * Create a new search context.
     */
    protected static SearchContext createSearchContext(IndexService indexService) {
        BigArrays bigArrays = indexService.getIndexServices().getBigArrays();
        ThreadPool threadPool = indexService.getIndexServices().getThreadPool();
        PageCacheRecycler pageCacheRecycler = node().injector().getInstance(PageCacheRecycler.class);
    }
}
