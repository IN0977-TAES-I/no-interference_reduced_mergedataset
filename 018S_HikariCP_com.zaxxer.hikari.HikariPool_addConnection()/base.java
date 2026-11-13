// class name : com.zaxxer.hikari.HikariPool
// method name: addConnection()
// side       : base
// region     : lines 339-374 (affected lines: [339, 340, 341, 342, 343, 344, 345, 346, 348, 356, 362, 365, 372, 374])
class HikariPool {
    void addConnection__() {

    /** {@inheritDoc} */
    public long getLeakDetectionThreshold()
    {
        return leakDetectionThreshold;
    }

    /** {@inheritDoc} */
    public void setLeakDetectionThreshold(long leakDetectionThresholdMs)
    {
        this.leakDetectionThreshold = leakDetectionThresholdMs; 
    }

    public void setUseInstrumentation(boolean useInstrumentation)
    {
        // no longer used as of HikariCP 1.2.5
    }

    /** {@inheritDoc} */
    public long getMaxLifetime()
    {
        return maxLifetime;
    }

    /** {@inheritDoc} */
    public void setMaxLifetime(long maxLifetimeMs)
    {
        this.maxLifetime = maxLifetimeMs;
    }

    /** {@inheritDoc} */
    public int getMinimumPoolSize()
    {
        return minPoolSize;
    }

    }
}
