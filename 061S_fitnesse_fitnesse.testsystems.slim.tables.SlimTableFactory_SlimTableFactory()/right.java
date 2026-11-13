// class name : fitnesse.testsystems.slim.tables.SlimTableFactory
// method name: SlimTableFactory()
// side       : right
// region     : lines 24-31 (affected lines: [24, 25, 31])
class SlimTableFactory {
    void SlimTableFactory__() {
  public void setUp() {
    slimTableFactory = new SlimTableFactory();
    table = mock(Table.class);
    map = new HashMap<String, Class<? extends SlimTable>>();
    map.put("dt:", DecisionTable.class);
    map.put("dT:", DecisionTable.class);
    map.put("decision:", DecisionTable.class);
    map.put("ordered query:", OrderedQueryTable.class);
    }
}
