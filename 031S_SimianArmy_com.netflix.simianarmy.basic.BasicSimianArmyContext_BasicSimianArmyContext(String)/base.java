// class name : com.netflix.simianarmy.basic.BasicSimianArmyContext
// method name: BasicSimianArmyContext(String)
// side       : base
// region     : lines 110-141 (affected lines: [110, 111, 112, 113, 114, 115, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 141])
class BasicSimianArmyContext {
    void BasicSimianArmyContext_String_() {
            this.awsCredentialsProvider = new STSAssumeRoleSessionCredentialsProvider(assumeRoleArn);
        }

        // if credentials are set explicitly make them available to the AWS SDK
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(secret)) {
            this.exportCredentials(account, secret);
        }

        createClient();

        createScheduler();

        createRecorder();

    }

    /** loads the given config on top of the config read by previous calls. */
    protected void loadConfigurationFileIntoProperties(String propertyFileName) {
        String propFile = System.getProperty(propertyFileName, "/" + propertyFileName);
        try {
            InputStream is = BasicSimianArmyContext.class.getResourceAsStream(propFile);
            try {
                properties.load(is);
            } finally {
                is.close();
            }
        } catch (Exception e) {
            String msg = "Unable to load properties file " + propFile + " set System property \"" + propertyFileName
                    + "\" to valid file";
            LOGGER.error(msg);
            throw new RuntimeException(msg, e);
        }
    }
}
