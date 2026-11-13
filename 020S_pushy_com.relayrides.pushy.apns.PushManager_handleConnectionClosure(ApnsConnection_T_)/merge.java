// class name : com.relayrides.pushy.apns.PushManager
// method name: handleConnectionClosure(ApnsConnection<T>)
// side       : merge
// region     : lines 500-520 (affected lines: [500, 502, 503, 510, 515, 520])
class PushManager {
    void handleConnectionClosure_ApnsConnection_T__() {
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
