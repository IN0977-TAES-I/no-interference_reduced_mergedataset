class PushManager {
    ApnsEnvironment environment;
    SSLContext sslContext;
    Vector<FailedConnectionListener<? super T>> failedConnectionListeners;
    NioEventLoopGroup eventLoopGroup;
    ReentrantLock connectionLock;
    int unfinishedConnectionCount;
    ExecutorService listenerExecutorService;

    public void handleConnectionFailure(final ApnsConnection<T> connection, final Throwable cause) {

    	final PushManager<T> pushManager = this;

    	for (final FailedConnectionListener<? super T> listener : this.failedConnectionListeners) {

    		this.listenerExecutorService.submit(new Runnable() {
    			public void run() {
    				listener.handleFailedConnection(pushManager, cause);
    			}
    		});
    	}

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
