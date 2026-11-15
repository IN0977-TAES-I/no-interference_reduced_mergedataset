class NestedInnerQueryParseSupport {
    QueryParseContext parseContext;
    BitDocIdSetFilter parentFilter;
    Filter childFilter;
    ObjectMapper nestedObjectMapper;
    ObjectMapper parentObjectMapper;

    private void setPathLevel() {
        ObjectMapper objectMapper = parseContext.nestedScope().getObjectMapper();
        if (objectMapper == null) {
            parentFilter = parseContext.bitsetFilter(Queries.newNonNestedFilter());
        } else {
            parentFilter = parseContext.bitsetFilter(objectMapper.nestedTypeFilter());
        }
        childFilter = nestedObjectMapper.nestedTypeFilter();
        parentObjectMapper = parseContext.nestedScope().nextLevel(nestedObjectMapper);
    }
}
