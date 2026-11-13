// class name : com.netflix.simianarmy.basic.janitor.BasicJanitorMonkeyContext
// method name: getInstanceJanitor()
// side       : merge
// region     : lines 227-238 (affected lines: [227, 228, 229, 230, 232, 233, 234, 235, 237, 238])
class BasicJanitorMonkeyContext {
    void getInstanceJanitor__() {
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
}
