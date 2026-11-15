class PushManager {
    ApnsConnectionPool<T> connectionPool;
    Thread dispatchThread;
    ExecutorService listenerExecutorService;
    Logger log;

    public void handleConnectionClosure(final ApnsConnection<T> connection) {
    	this.connectionPool.removeConnection(connection);

    	if (!this.isShutDown()) {
    		this.startNewConnection();
    	}

    	if (this.dispatchThread != null && this.dispatchThread.isAlive()) {
    		this.dispatchThread.interrupt();
    	}

    	this.listenerExecutorService.submit(new Runnable() {

    		public void run() {
    			try {
    				connection.waitForPendingOperationsToFinish();
    				decrementConnectionCounter();
    			} catch (InterruptedException e) {
    				log.warn("Interrupted while waiting for closed connection's pending operations to finish.");
    			}
    		}
    	});
    }
}
