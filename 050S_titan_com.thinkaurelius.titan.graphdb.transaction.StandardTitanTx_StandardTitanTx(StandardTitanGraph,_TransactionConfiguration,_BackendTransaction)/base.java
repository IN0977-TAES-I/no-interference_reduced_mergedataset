// class name : com.thinkaurelius.titan.graphdb.transaction.StandardTitanTx
// method name: StandardTitanTx(StandardTitanGraph, TransactionConfiguration, BackendTransaction)
// side       : base
// region     : lines 182-199 (affected lines: [182, 183, 197, 198, 199])
class StandardTitanTx {
    void StandardTitanTx_StandardTitanGraph__TransactionConfiguration__BackendTransaction_() {
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

    private final void verifyWriteAccess(TitanVertex... vertices) {
        if (config.isReadOnly())
            throw new UnsupportedOperationException("Cannot create new entities in read-only transaction");
        verifyAccess(vertices);
    }
}
