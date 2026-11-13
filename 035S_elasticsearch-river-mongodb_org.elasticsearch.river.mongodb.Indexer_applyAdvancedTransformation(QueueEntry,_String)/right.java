// class name : org.elasticsearch.river.mongodb.Indexer
// method name: applyAdvancedTransformation(QueueEntry, String)
// side       : right
// region     : lines 287-358 (affected lines: [287, 288, 321, 331, 358])
class Indexer {
    void applyAdvancedTransformation_QueueEntry__String_() {
        }

        public Builder dropCollection(boolean dropCollection) {
            this.dropCollection = dropCollection;
            return this;
        }

        public Builder excludeFields(Set<String> excludeFields) {
            this.excludeFields = excludeFields;
            return this;
        }

        public Builder includeFields(Set<String> includeFields) {
            this.includeFields = includeFields;
            return this;
        }

        public Builder includeCollection(String includeCollection) {
            this.includeCollection = includeCollection;
            return this;
        }

        public Builder disableIndexRefresh(boolean disableIndexRefresh) {
            this.disableIndexRefresh = disableIndexRefresh;
            return this;
        }

        public Builder initialTimestamp(Binary initialTimestamp) {
            this.initialTimestamp = new Timestamp.GTID(initialTimestamp.getData(), null);
            return this;
        }

        public Builder initialTimestamp(BSONTimestamp initialTimestamp) {
            this.initialTimestamp = new Timestamp.BSON(initialTimestamp);
            return this;
        }

        public Builder advancedTransformation(boolean advancedTransformation) {
            this.advancedTransformation = advancedTransformation;
            return this;
        }

        public Builder skipInitialImport(boolean skipInitialImport) {
            this.skipInitialImport = skipInitialImport;
            return this;
        }

        public Builder parentTypes(Set<String> parentTypes) {
            this.parentTypes = parentTypes;
            return this;
        }

        public Builder storeStatistics(boolean storeStatistics) {
            this.storeStatistics = storeStatistics;
            return this;
        }

        public Builder statisticsIndexName(String statisticsIndexName) {
            this.statisticsIndexName = statisticsIndexName;
            return this;
        }

        public Builder statisticsTypeName(String statisticsTypeName) {
            this.statisticsTypeName = statisticsTypeName;
            return this;
        }

        public Builder importAllCollections(boolean importAllCollections) {
            this.importAllCollections = importAllCollections;
            return this;
        }

    }
}
