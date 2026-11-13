// class name : org.elasticsearch.index.analysis.synonyms.SynonymsAnalysisTest
// method name: testSynonymsAnalysis()
// side       : merge
// region     : lines 65-76 (affected lines: [65, 66, 67, 68, 69, 70, 71, 73, 75, 76])
class SynonymsAnalysisTest {
    void testSynonymsAnalysis__() {
        InputStream synonyms = getClass().getResourceAsStream("synonyms.txt");
        InputStream synonymsWordnet = getClass().getResourceAsStream("synonyms_wordnet.txt");
        Path home = createTempDir();
        Path config = home.resolve("config");
        Files.createDirectory(config);
        Files.copy(synonyms, config.resolve("synonyms.txt"));
        Files.copy(synonymsWordnet, config.resolve("synonyms_wordnet.txt"));

        String json = "/org/elasticsearch/index/analysis/synonyms/synonyms.json";
        Settings settings = settingsBuilder().
            loadFromStream(json, getClass().getResourceAsStream(json))
                .put("path.home", home)
    }
}
