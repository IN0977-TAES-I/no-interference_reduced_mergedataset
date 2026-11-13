// class name : com.relayrides.pushy.apns.PushManager
// method name: handleConnectionFailure(ApnsConnection<T>, Throwable)
// side       : merge
// region     : lines 476-490 (affected lines: [476, 478, 481, 482, 483, 484, 485, 490])
class PushManager {
    void handleConnectionFailure_ApnsConnection_T___Throwable_() {
		final PushManager<T> pushManager = this;

		for (final FailedConnectionListener<? super T> listener : this.failedConnectionListeners) {

			// Handle connection failures in a separate thread in case a handler takes a long time to run
			this.listenerExecutorService.submit(new Runnable() {
				public void run() {
					listener.handleFailedConnection(pushManager, cause);
				}
			});
		}

		// As long as we're not shut down, keep trying to open a replacement connection.
		if (!this.isShutDown()) {
			this.startNewConnection();
    }
}
