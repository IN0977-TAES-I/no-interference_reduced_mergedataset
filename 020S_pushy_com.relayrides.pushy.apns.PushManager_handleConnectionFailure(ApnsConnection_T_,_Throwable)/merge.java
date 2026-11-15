class PushManager {
    Vector<FailedConnectionListener<? super T>> failedConnectionListeners;
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
    		this.startNewConnection();
    	}
    }
}
