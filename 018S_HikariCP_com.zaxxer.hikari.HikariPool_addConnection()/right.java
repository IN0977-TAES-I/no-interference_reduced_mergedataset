class HikariPool {
    static Logger LOGGER;
    DataSource dataSource;
    IConnectionCustomizer connectionCustomizer;
    HikariConfig configuration;
    ConcurrentBag<IHikariConnectionProxy> idleConnectionBag;
    AtomicInteger totalConnections;
    boolean isAutoCommit;
    String catalog;
    int transactionIsolation;
    boolean shutdown;
    boolean debug;

    private void addConnection()
    {
        final int acquisitionTimeout = (int) configuration.getConnectionTimeout();
        int retries = configuration.getAcquireRetries();
        int loginTimeout = 2000;
        if (retries == 0)
        {
            loginTimeout = (acquisitionTimeout == 0 ? Integer.MAX_VALUE : acquisitionTimeout);
        }
        else if (acquisitionTimeout > 0)
        {
            loginTimeout = (acquisitionTimeout / (retries + 1));
        }

        while (!shutdown)
        {
            try
            {
                if (totalConnections.incrementAndGet() > configuration.getMaximumPoolSize())
                {
                    totalConnections.decrementAndGet();
                    break;
                }

                dataSource.setLoginTimeout(loginTimeout);
                Connection connection = dataSource.getConnection();

                if (transactionIsolation < 0)
                {
                    transactionIsolation = connection.getTransactionIsolation();
                }

                if (connectionCustomizer != null)
                {
                    connectionCustomizer.customize(connection);
                }

                IHikariConnectionProxy proxyConnection = ProxyFactory.getProxyConnection(this, connection, transactionIsolation, isAutoCommit, catalog);

                String initSql = configuration.getConnectionInitSql();
                if (initSql != null && initSql.length() > 0)
                {
                    connection.setAutoCommit(true);
                    Statement statement = connection.createStatement();
                    try
                    {
                        statement.execute(initSql);
                    }
                    finally
                    {
                        statement.close();
                    }
                }

                proxyConnection.resetConnectionState();
                idleConnectionBag.add(proxyConnection);
                break;
            }
            catch (Exception e)
            {
                if (retries++ > configuration.getAcquireRetries())
                {
                    if (debug)
                    {
                        LOGGER.error("Maximum connection creation retries exceeded: {}", e.getMessage(), e);
                    }
                    else
                    {
                        LOGGER.error("Maximum connection creation retries exceeded: {}", e.getMessage());
                    }
                    totalConnections.decrementAndGet();
                    break;
                }

                try
                {
                    Thread.sleep(configuration.getAcquireRetryDelay());
                }
                catch (InterruptedException e1)
                {
                    totalConnections.decrementAndGet();
                    break;
                }
            }
        }
    }
}
