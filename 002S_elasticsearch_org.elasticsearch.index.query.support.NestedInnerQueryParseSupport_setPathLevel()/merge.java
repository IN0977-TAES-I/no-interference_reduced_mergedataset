// class name : org.elasticsearch.index.query.support.NestedInnerQueryParseSupport
// method name: setPathLevel()
// side       : merge
// region     : lines 193-200 (affected lines: [193, 195, 197, 199, 200])
class NestedInnerQueryParseSupport {
    void setPathLevel__() {
        ObjectMapper objectMapper = shardContext.nestedScope().getObjectMapper();
        if (objectMapper == null) {
            parentFilter = shardContext.bitsetFilter(Queries.newNonNestedFilter());
        } else {
            parentFilter = shardContext.bitsetFilter(objectMapper.nestedTypeFilter());
        }
        childFilter = nestedObjectMapper.nestedTypeFilter();
        parentObjectMapper = shardContext.nestedScope().nextLevel(nestedObjectMapper);
    }
}
