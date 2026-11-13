// class name : org.elasticsearch.index.query.SimpleIndexQueryParserTests
// method name: setup()
// side       : right
// region     : lines 208-217 (affected lines: [208, 217])
class SimpleIndexQueryParserTests {
    void setup__() {
                .put("index.queryparser.query.dummy.type", DummyQueryParser.class)
                .put("index.cache.filter.type", "none")
                .put("name", "SimpleIndexQueryParserTests")
                .build();
        IndexService indexService = createIndex("test", settings);
        MapperService mapperService = indexService.mapperService();

        String mapping = copyToStringFromClasspath("/org/elasticsearch/index/query/mapping.json");
        mapperService.merge("person", new CompressedString(mapping), true);
        ParsedDocument doc = mapperService.documentMapper("person").parse(new BytesArray(copyToBytesFromClasspath("/org/elasticsearch/index/query/data.json")));
    }
}
