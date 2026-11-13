// class name : com.netflix.simianarmy.basic.BasicSimianArmyContext
// method name: BasicSimianArmyContext(String)
// side       : merge
// region     : lines 110-141 (affected lines: [110, 111, 112, 113, 114, 115, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 141])
class BasicSimianArmyContext {
    void BasicSimianArmyContext_String_() {
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

        // Check for and configure optional proxy configuration
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
}
