// class name : com.netflix.simianarmy.basic.BasicSimianArmyContext
// method name: BasicSimianArmyContext(String)
// side       : right
// region     : lines 110-141 (affected lines: [110, 111, 112, 113, 114, 115, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 141])
class BasicSimianArmyContext {
    void BasicSimianArmyContext_String_() {
        secret = config.getStr("simianarmy.client.aws.secretKey");
        region = config.getStrOrElse("simianarmy.client.aws.region", "us-east-1");

        assumeRoleArn = config.getStr("simianarmy.client.aws.assumeRoleArn");
        if (assumeRoleArn != null) {
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

    /**
     * Checks whether it is safe to log the property based on the given
     * property key.
     * @param propertyKey The key for the property, expected to be resolvable to a String
     * @return A boolean indicating whether it is safe to log the corresponding property
     */
    protected boolean isSafeToLog(Object propertyKey) {
        String propertyKeyName = propertyKey.toString();
        return !propertyKeyName.contains("secretKey")
                && !propertyKeyName.contains("vsphere.password");
    }
    }
}
