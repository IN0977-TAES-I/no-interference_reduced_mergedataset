// class name : com.zaxxer.hikari.HikariPool
// method name: addConnection()
// side       : right
// region     : lines 339-374 (affected lines: [339, 340, 341, 342, 343, 344, 345, 346, 348, 356, 362, 365, 372, 374])
class HikariPool {
    void addConnection__() {
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

    /** {@inheritDoc} */
    public void setMinimumPoolSize(int minPoolSize)
    {
        if (minPoolSize < 0)
        {
            throw new IllegalArgumentException("minPoolSize cannot be negative");
        }
        this.minPoolSize = minPoolSize;
    }

    /** {@inheritDoc} */
    public int getMaximumPoolSize()
    {
        return maxPoolSize;
    }
    }
}
