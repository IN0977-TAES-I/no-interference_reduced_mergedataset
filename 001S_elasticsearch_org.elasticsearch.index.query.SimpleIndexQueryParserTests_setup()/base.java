// class name : org.elasticsearch.index.query.SimpleIndexQueryParserTests
// method name: setup()
// side       : base
// region     : lines 208-217 (affected lines: [208, 217])
class SimpleIndexQueryParserTests {
    void setup__() {
        IndexQueryParserService queryParser = queryParser();
        QueryStringQueryBuilder builder = queryStringQuery("field:boosted^2");
        Query parsedQuery = queryParser.parse(builder).query();
        assertThat(parsedQuery, instanceOf(TermQuery.class));
        assertThat(((TermQuery) parsedQuery).getTerm(), equalTo(new Term("field", "boosted")));
        assertThat(parsedQuery.getBoost(), equalTo(2.0f));
        builder.boost(2.0f);
        parsedQuery = queryParser.parse(builder).query();
        assertThat(parsedQuery.getBoost(), equalTo(4.0f));

    }
}
