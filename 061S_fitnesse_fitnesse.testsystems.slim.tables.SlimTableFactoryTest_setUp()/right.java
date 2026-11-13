// class name : fitnesse.testsystems.slim.tables.SlimTableFactoryTest
// method name: setUp()
// side       : right
// region     : lines 31-38 (affected lines: [31, 32, 38])
class SlimTableFactoryTest {
    void setUp__() {
    map.put("ordered query:", OrderedQueryTable.class);
    map.put("subset query:", SubsetQueryTable.class);
    map.put("query:", QueryTable.class);
    map.put("table:", TableTable.class);
    map.put("script", ScriptTable.class);
    map.put("script:", ScriptTable.class);
    map.put("scenario", ScenarioTable.class);
    map.put("import", ImportTable.class);
    }
}
