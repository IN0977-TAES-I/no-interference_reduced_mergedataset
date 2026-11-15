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
    ClientConfiguration awsClientConfig;
    String proxyHost;
    String proxyPort;
    String proxyUsernaem;
    String proxyPassword;

    protected BasicSimianArmyContext(String... configFiles) {
        eventReport = new LinkedList<Event>();
        for (String configFile : configFiles) {
            loadConfigurationFileIntoProperties(configFile);
        }
        LOGGER.info("The following are properties in the context.");
        for (Entry<Object, Object> prop : properties.entrySet()) {
            Object propertyKey = prop.getKey();
            if (isSafeToLog(propertyKey)) {
                LOGGER.info(String.format("%s = %s", propertyKey, prop.getValue()));
            } else {
                LOGGER.info(String.format("%s = (not shown here)", propertyKey));
            }
        }

        config = new BasicConfiguration(properties);
        calendar = new BasicCalendar(config);

        account = config.getStr("simianarmy.client.aws.accountKey");
        secret = config.getStr("simianarmy.client.aws.secretKey");
        region = config.getStrOrElse("simianarmy.client.aws.region", "us-east-1");

        proxyHost = config.getStr("simianarmy.client.aws.proxyHost");
        proxyPort = config.getStr("simianarmy.client.aws.proxyPort");
        proxyUsernaem = config.getStr("simianarmy.client.aws.proxyUser");
        proxyPassword = config.getStr("simianarmy.client.aws.proxyPassword");
        if ((proxyHost != null) && (proxyPort != null)) {
            awsClientConfig.setProxyHost(proxyHost);
            awsClientConfig.setProxyPort(Integer.parseInt(proxyPort));
            if ((proxyUsernaem != null) && (proxyPassword != null)) {
                awsClientConfig.setProxyUsername(proxyUsernaem);
                awsClientConfig.setProxyPassword(proxyPassword);
            }
        }

        assumeRoleArn = config.getStr("simianarmy.client.aws.assumeRoleArn");
        if (assumeRoleArn != null) {
            this.awsCredentialsProvider = new STSAssumeRoleSessionCredentialsProvider(assumeRoleArn, awsClientConfig);
        }

        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(secret)) {
            this.exportCredentials(account, secret);
        }

        createClient();

        createScheduler();

        createRecorder();

    }
}
