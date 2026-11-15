class OutOfTimeCrossValidatorRunner {
    private static PredictiveModelBuilder getRandomForestBuilder(int maxDepth, int numTrees) {

        TreeBuilder treeBuilder = new TreeBuilder().maxDepth(maxDepth).ignoreAttributeAtNodeProbability(.7).minLeafInstances(20);
        RandomForestBuilder RandomForestBuilder = new RandomForestBuilder(treeBuilder).numTrees(numTrees);
        return RandomForestBuilder;

    }
}
