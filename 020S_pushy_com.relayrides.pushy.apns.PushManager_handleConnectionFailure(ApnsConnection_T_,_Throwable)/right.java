class PushManager {
    public void handleConnectionFailure(final ApnsConnection<T> connection, final Throwable cause) {

    	if (!this.isShutDown()) {
    		this.startNewConnection();
    	}
    }
}
