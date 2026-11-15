class BasicJanitorMonkeyContext {
    JanitorResourceTracker janitorResourceTracker;
    String monkeyRegion;
    MonkeyCalendar monkeyCalendar;

    private InstanceJanitor getInstanceJanitor() {
        JanitorRuleEngine ruleEngine = new BasicJanitorRuleEngine();
        if (configuration().getBoolOrElse("simianarmy.janitor.rule.orphanedInstanceRule.enabled", false)) {
            ruleEngine.addRule(new OrphanedInstanceRule(monkeyCalendar,
                    (int) configuration().getNumOrElse(
                            "simianarmy.janitor.rule.orphanedInstanceRule.instanceAgeThreshold", 2),
                            (int) configuration().getNumOrElse(
                                    "simianarmy.janitor.rule.orphanedInstanceRule.retentionDaysWithOwner", 3),
                                    (int) configuration().getNumOrElse(
                                            "simianarmy.janitor.rule.orphanedInstanceRule.retentionDaysWithoutOwner",
                                            8),
                                    configuration().getBoolOrElse(
                                            "simianarmy.janitor.rule.orphanedInstanceRule.opsworks.parentage",
                                            false)));
        }
        if (configuration().getBoolOrElse("simianarmy.janitor.rule.untaggedRule.enabled", false)) {
            ruleEngine.addRule(new UntaggedRule(monkeyCalendar, getPropertySet("simianarmy.janitor.rule.untaggedRule.requiredTags"),
                    (int) configuration().getNumOrElse(
                            "simianarmy.janitor.rule.untaggedRule.retentionDaysWithOwner", 3),
                            (int) configuration().getNumOrElse(
                                    "simianarmy.janitor.rule.untaggedRule.retentionDaysWithoutOwner",
                                    8)));
        }

        JanitorCrawler instanceCrawler;
        if (configuration().getBoolOrElse("simianarmy.janitor.edda.enabled", false)) {
            instanceCrawler = new EddaInstanceJanitorCrawler(createEddaClient(), awsClient().region());
        } else {
            instanceCrawler = new InstanceJanitorCrawler(awsClient());
        }
        BasicJanitorContext instanceJanitorCtx = new BasicJanitorContext(
                monkeyRegion, ruleEngine, instanceCrawler, janitorResourceTracker,
                monkeyCalendar, configuration(), recorder());
        return new InstanceJanitor(awsClient(), instanceJanitorCtx);
    }
}
