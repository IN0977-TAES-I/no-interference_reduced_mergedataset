class BasicSimianArmyContext {
    static Logger LOGGER;
    Properties properties;
    MonkeyCalendar calendar;
    BasicConfiguration config;
    LinkedList<Event> eventReport;
    AWSCredentialsProvider awsCredentialsProvider;
    String assumeRoleArn;
    String account;
    String secret;
    String region;

    protected BasicSimianArmyContext(String... configFiles) {
        eventReport = new LinkedList<Event>();
        for (String configFile : configFiles) {
            loadConfigurationFileIntoProperties(configFile);
        }
        LOGGER.info("The following are properties in the context.");
        for (Entry<Object, Object> prop : properties.entrySet()) {
            LOGGER.info(String.format("%s = %s", prop.getKey(), prop.getValue()));
        }

        config = new BasicConfiguration(properties);
        calendar = new BasicCalendar(config);

        account = config.getStr("simianarmy.client.aws.accountKey");
        secret = config.getStr("simianarmy.client.aws.secretKey");
        region = config.getStrOrElse("simianarmy.client.aws.region", "us-east-1");

        assumeRoleArn = config.getStr("simianarmy.client.aws.assumeRoleArn");
        if (assumeRoleArn != null) {
            this.awsCredentialsProvider = new STSAssumeRoleSessionCredentialsProvider(assumeRoleArn);
        }

        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(secret)) {
            this.exportCredentials(account, secret);
        }

        createClient();

        createScheduler();

        createRecorder();

    }
}
