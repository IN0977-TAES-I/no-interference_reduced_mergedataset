class SlimTableFactoryTest {
    SlimTableFactory slimTableFactory;
    Table table;
    Map<String, Class<? extends SlimTable>> map;

    @Before
    public void setUp() {
      slimTableFactory = new SlimTableFactory();
      table = mock(Table.class);
      map = new HashMap<String, Class<? extends SlimTable>>();
      map.put("dt:", DecisionTable.class);
      map.put("dT:", DecisionTable.class);
      map.put("decision:", DecisionTable.class);
      map.put("ordered query:", OrderedQueryTable.class);
      map.put("subset query:", SubsetQueryTable.class);
      map.put("query:", QueryTable.class);
      map.put("table:", TableTable.class);
      map.put("script", ScriptTable.class);
      map.put("scenario", ScenarioTable.class);
      map.put("import", ImportTable.class);
      map.put("something", DecisionTable.class);
      map.put("library", LibraryTable.class);
    }
}
