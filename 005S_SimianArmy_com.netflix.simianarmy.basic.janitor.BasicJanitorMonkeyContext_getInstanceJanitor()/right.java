// class name : com.netflix.simianarmy.basic.janitor.BasicJanitorMonkeyContext
// method name: getInstanceJanitor()
// side       : right
// region     : lines 227-238 (affected lines: [227, 228, 229, 230, 232, 233, 234, 235, 237, 238])
class BasicJanitorMonkeyContext {
    void getInstanceJanitor__() {
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
    }
}
