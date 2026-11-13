// class name : com.netflix.simianarmy.basic.janitor.BasicJanitorMonkeyContext
// method name: getInstanceJanitor()
// side       : left
// region     : lines 227-238 (affected lines: [227, 228, 229, 230, 232, 233, 234, 235, 237, 238])
class BasicJanitorMonkeyContext {
    void getInstanceJanitor__() {
        }
        BasicJanitorContext instanceJanitorCtx = new BasicJanitorContext(
                monkeyRegion, ruleEngine, instanceCrawler, janitorResourceTracker,
                monkeyCalendar, configuration(), recorder());
        return new InstanceJanitor(awsClient(), instanceJanitorCtx);
    }

    private EBSVolumeJanitor getEBSVolumeJanitor() {
        JanitorRuleEngine ruleEngine = new BasicJanitorRuleEngine();
        if (configuration().getBoolOrElse("simianarmy.janitor.rule.oldDetachedVolumeRule.enabled", false)) {
            ruleEngine.addRule(new OldDetachedVolumeRule(monkeyCalendar,
                    (int) configuration().getNumOrElse(
    }
}
