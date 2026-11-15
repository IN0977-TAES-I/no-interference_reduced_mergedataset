class SlimTableFactory {
    Map<String, Class<? extends SlimTable>> tableTypes;
    Map<String, String> tableTypeArrays;

    public  SlimTableFactory() {
      tableTypes = new HashMap<String, Class<? extends SlimTable>>(16);
      tableTypeArrays = new HashMap<String, String>();
      addTableType("dt:", DecisionTable.class);
      addTableType("decision:", DecisionTable.class);
      addTableType("ddt:", DynamicDecisionTable.class);
      addTableType("dynamic decision:", DynamicDecisionTable.class);
      addTableType("ordered query:", OrderedQueryTable.class);
      addTableType("subset query:", SubsetQueryTable.class);
      addTableType("query:", QueryTable.class);
      addTableType("table:", TableTable.class);
      addTableType("script", ScriptTable.class);
      addTableType("script:", ScriptTable.class);
      addTableType("scenario", ScenarioTable.class);
      addTableType("import", ImportTable.class);
      addTableType("library", LibraryTable.class);
    }
}
