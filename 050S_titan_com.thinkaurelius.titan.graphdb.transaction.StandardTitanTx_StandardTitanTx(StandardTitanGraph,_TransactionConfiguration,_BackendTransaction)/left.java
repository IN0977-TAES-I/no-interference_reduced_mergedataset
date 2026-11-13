// class name : com.thinkaurelius.titan.graphdb.transaction.StandardTitanTx
// method name: StandardTitanTx(StandardTitanGraph, TransactionConfiguration, BackendTransaction)
// side       : left
// region     : lines 182-199 (affected lines: [182, 183, 197, 198, 199])
class StandardTitanTx {
    void StandardTitanTx_StandardTitanGraph__TransactionConfiguration__BackendTransaction_() {
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

    /*
     * ------------------------------------ Utility Access Verification methods ------------------------------------
     */

    private void verifyWriteAccess(TitanVertex... vertices) {
    }
}
