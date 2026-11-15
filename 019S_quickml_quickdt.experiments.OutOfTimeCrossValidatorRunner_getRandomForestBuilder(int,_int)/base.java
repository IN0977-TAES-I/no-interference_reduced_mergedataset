class OutOfTimeCrossValidatorRunner {
    private static PredictiveModelBuilder getRandomForestBuilder(int maxDepth, int numTrees) {
        TreeBuilder treeBuilder = new TreeBuilder().maxDepth(maxDepth).ignoreAttributeAtNodeProbability(.7).minLeafInstances(20);
        RandomForestBuilder randomForestBuilder = new RandomForestBuilder(treeBuilder).numTrees(numTrees);
        UpdatableRandomForestBuilder updatableRandomForestBuilder = (UpdatableRandomForestBuilder) new UpdatableRandomForestBuilder(randomForestBuilder).rebuildThreshold(2).splitNodeThreshold(2);
        return updatableRandomForestBuilder;
    }
}
