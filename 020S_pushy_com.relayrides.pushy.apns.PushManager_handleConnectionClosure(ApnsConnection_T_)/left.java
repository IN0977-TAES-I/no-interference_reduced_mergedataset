class PushManager {
    ApnsEnvironment environment;
    SSLContext sslContext;
    ApnsConnectionPool<T> connectionPool;
    Thread dispatchThread;
    NioEventLoopGroup eventLoopGroup;
    ReentrantLock connectionLock;
    Condition connectionsFinished;
    int unfinishedConnectionCount;
    ExecutorService listenerExecutorService;
    Logger log;

    public void handleConnectionClosure(final ApnsConnection<T> connection) {
    	this.connectionLock.lock();

    	try {
    		this.connectionPool.removeConnection(connection);
    		this.unfinishedConnectionCount -= 1;

    		if (!this.isShutDown()) {
    			new ApnsConnection<T>(this.environment, this.sslContext, this.eventLoopGroup, this).connect();
    			this.unfinishedConnectionCount += 1;
    		}

    		if (this.unfinishedConnectionCount == 0) {
    			this.connectionsFinished.signalAll();
    		}
    	} finally {
    		this.connectionLock.unlock();
    	}

    	if (this.dispatchThread != null && this.dispatchThread.isAlive()) {
    		this.dispatchThread.interrupt();
    	}

    	this.listenerExecutorService.submit(new Runnable() {

    		public void run() {
    			try {
    				connection.waitForPendingOperationsToFinish();

    				connectionLock.lock();

    				try {
    					unfinishedConnectionCount -= 1;

    					if (unfinishedConnectionCount == 0) {
    						connectionsFinished.signalAll();
    					}
    				} finally {
    					connectionLock.unlock();
    				}
    			} catch (InterruptedException e) {
    				log.warn("Interrupted while waiting for closed connection's pending operations to finish.");
    			}
    		}
    	});
    }
}
