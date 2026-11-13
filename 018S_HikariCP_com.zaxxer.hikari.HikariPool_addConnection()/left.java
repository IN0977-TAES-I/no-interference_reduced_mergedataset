// class name : com.zaxxer.hikari.HikariPool
// method name: addConnection()
// side       : left
// region     : lines 339-374 (affected lines: [339, 340, 341, 342, 343, 344, 345, 346, 348, 356, 362, 365, 372, 374])
class HikariPool {
    void addConnection__() {
        dataSourceProperties.put(propertyName, value);
    }

    public Properties getDataSourceProperties()
    {
        return dataSourceProperties;
    }

    public void setDataSourceProperties(Properties dsProperties)
    {
        dataSourceProperties.putAll(dsProperties);
    }

    public void setDriverClassName(String driverClassName)
    {
        try
        {
            Class<?> driverClass = this.getClass().getClassLoader().loadClass(driverClassName);
            driverClass.newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("driverClassName specified class '" + driverClassName + "' could not be loaded", e);
        }
    }

    /** {@inheritDoc} */
    public long getIdleTimeout()
    {
        return idleTimeout;
    }

    /** {@inheritDoc} */
    public void setIdleTimeout(long idleTimeoutMs)
    {
        this.idleTimeout = idleTimeoutMs;
    }
}
