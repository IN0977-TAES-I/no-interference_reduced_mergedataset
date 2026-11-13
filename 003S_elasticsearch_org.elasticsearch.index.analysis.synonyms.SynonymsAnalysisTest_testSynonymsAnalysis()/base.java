// class name : org.elasticsearch.index.analysis.synonyms.SynonymsAnalysisTest
// method name: testSynonymsAnalysis()
// side       : base
// region     : lines 65-76 (affected lines: [65, 66, 67, 68, 69, 70, 71, 73, 75, 76])
class SynonymsAnalysisTest {
    void testSynonymsAnalysis__() {
                .put(IndexMetaData.SETTING_VERSION_CREATED, Version.CURRENT).build();

        Index index = new Index("test");

        Injector parentInjector = new ModulesBuilder().add(
                new SettingsModule(settings),
                new EnvironmentModule(new Environment(settings)),
                new IndicesAnalysisModule())
                .createInjector();
        Injector injector = new ModulesBuilder().add(
                new IndexSettingsModule(index, settings),
                new IndexNameModule(index),
    }
}
