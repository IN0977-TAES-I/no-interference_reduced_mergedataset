class HikariConfig {
    static long ACQUIRE_RETRY_DELAY;
    static long CONNECTION_TIMEOUT;
    static long IDLE_TIMEOUT;
    static long MAX_LIFETIME;
    long acquireRetryDelay;
    long connectionTimeout;
    long idleTimeout;
    long leakDetectionThreshold;
    long maxLifetime;
    int maxPoolSize;
    int minPoolSize;
    String transactionIsolationName;
    String connectionCustomizerClassName;
    String connectionTestQuery;
    String dataSourceClassName;
    boolean isJdbc4connectionTest;
    DataSource dataSource;
    IConnectionCustomizer connectionCustomizer;
    int transactionIsolation;

    public void validate()
    {
        Logger logger = LoggerFactory.getLogger(getClass());

        if (acquireRetryDelay < 0)
        {
            logger.error("acquireRetryDelay cannot be negative.");
            throw new IllegalStateException("acquireRetryDelay cannot be negative.");
        }
        else if (acquireRetryDelay < 100)
        {
            logger.warn("acquireRetryDelay is less than 100ms, did you specify the wrong time unit?  Using default instead.");
            acquireRetryDelay = ACQUIRE_RETRY_DELAY;
        }

        if (connectionCustomizerClassName != null && connectionCustomizer == null)
        {
            try
            {
                Class<?> customizerClass = getClass().getClassLoader().loadClass(connectionCustomizerClassName);
                connectionCustomizer = (IConnectionCustomizer) customizerClass.newInstance();
            }
            catch (Exception e)
            {
                logger.warn("connectionCustomizationClass specified class '" + connectionCustomizerClassName + "' could not be loaded", e);
                connectionCustomizerClassName = null;
            }
        }

        if (connectionTimeout == Integer.MAX_VALUE)
        {
            logger.warn("No connection wait timeout is set, this might cause an infinite wait.");
        }
        else if (connectionTimeout < 100)
        {
            logger.warn("connectionTimeout is less than 100ms, did you specify the wrong time unit?  Using default instead.");
        	connectionTimeout = CONNECTION_TIMEOUT;
        }

        if (dataSource == null && dataSourceClassName == null)
        {
            logger.error("one of either dataSource or dataSourceClassName must be specified");
            throw new IllegalStateException("one of either dataSource or dataSourceClassName must be specified");
        }
        else if (dataSource != null && dataSourceClassName != null)
        {
            logger.warn("both dataSource and dataSourceClassName are specified, ignoring dataSourceClassName");
        }

        if (idleTimeout < 0)
        {
            logger.error("idleTimeout cannot be negative.");
            throw new IllegalStateException("idleTimeout cannot be negative.");
        }
        else if (idleTimeout < 30000 && idleTimeout != 0)
        {
            logger.warn("idleTimeout is less than 30000ms, did you specify the wrong time unit?  Using default instead.");
            idleTimeout = IDLE_TIMEOUT;
        }

        if (!isJdbc4connectionTest && connectionTestQuery == null)
        {
            logger.error("Either jdbc4ConnectionTest must be enabled or a connectionTestQuery must be specified.");
            throw new IllegalStateException("Either jdbc4ConnectionTest must be enabled or a connectionTestQuery must be specified.");
        }

        if (leakDetectionThreshold != 0 && leakDetectionThreshold < 10000)
        {
            logger.warn("leakDetectionThreshold is less than 10000ms, did you specify the wrong time unit?  Disabling leak detection.");
            leakDetectionThreshold = 0;
        }

        if (maxPoolSize < minPoolSize)
        {
            logger.warn("maxPoolSize is less than minPoolSize, forcing them equal.");
            maxPoolSize = minPoolSize;
        }

        if (maxLifetime < 0)
        {
            logger.error("maxLifetime cannot be negative.");
            throw new IllegalStateException("maxLifetime cannot be negative.");
        }
        else if (maxLifetime < 120000 && maxLifetime != 0)
        {
            logger.warn("maxLifetime is less than 120000ms, did you specify the wrong time unit?  Using default instead.");
            maxLifetime = MAX_LIFETIME;
        }

        if (transactionIsolationName != null)
        {
            try
            {
                Field field = Connection.class.getField(transactionIsolationName);
                int level = field.getInt(null);
                this.transactionIsolation = level;
            }
            catch (Exception e)
            {
                throw new IllegalArgumentException("Invalid transaction isolation value: " + transactionIsolationName);
            }
        }
    }
}
