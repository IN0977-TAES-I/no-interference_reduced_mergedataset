// class name : com.netflix.simianarmy.basic.janitor.BasicJanitorMonkeyContext
// method name: getInstanceJanitor()
// side       : base
// region     : lines 227-238 (affected lines: [227, 228, 229, 230, 232, 233, 234, 235, 237, 238])
class BasicJanitorMonkeyContext {
    void getInstanceJanitor__() {
        if (configuration().getBoolOrElse("simianarmy.janitor.rule.oldDetachedVolumeRule.enabled", false)) {
            ruleEngine.addRule(new OldDetachedVolumeRule(monkeyCalendar,
                    (int) configuration().getNumOrElse(
                            "simianarmy.janitor.rule.oldDetachedVolumeRule.detachDaysThreshold", 30),
                            (int) configuration().getNumOrElse(
                                    "simianarmy.janitor.rule.oldDetachedVolumeRule.retentionDays", 7)));

            if (configuration().getBoolOrElse("simianarmy.janitor.edda.enabled", false)
                && configuration().getBoolOrElse("simianarmy.janitor.rule.deleteOnTerminationRule.enabled", false)) {
                ruleEngine.addRule(new DeleteOnTerminationRule(monkeyCalendar, (int) configuration().getNumOrElse(
                        "simianarmy.janitor.rule.deleteOnTerminationRule.retentionDays", 3)));
            }
    }
}
