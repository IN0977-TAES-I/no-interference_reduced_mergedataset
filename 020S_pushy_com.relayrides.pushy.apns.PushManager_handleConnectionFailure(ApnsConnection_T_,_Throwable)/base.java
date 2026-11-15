class PushManager {
    ApnsEnvironment environment;
    SSLContext sslContext;
    NioEventLoopGroup eventLoopGroup;
    ReentrantLock connectionLock;
    int unfinishedConnectionCount;

    public void handleConnectionFailure(final ApnsConnection<T> connection, final Throwable cause) {

    	if (!this.isShutDown()) {
    		this.connectionLock.lock();

    		try {
    			new ApnsConnection<T>(this.environment, this.sslContext, this.eventLoopGroup, this).connect();
    			this.unfinishedConnectionCount += 1;
    		} finally {
    			this.connectionLock.unlock();
    		}
    	}
    }
}
