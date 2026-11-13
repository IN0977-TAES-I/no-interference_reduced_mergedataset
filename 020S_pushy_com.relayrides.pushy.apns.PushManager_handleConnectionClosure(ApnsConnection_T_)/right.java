// class name : com.relayrides.pushy.apns.PushManager
// method name: handleConnectionClosure(ApnsConnection<T>)
// side       : right
// region     : lines 500-520 (affected lines: [500, 502, 503, 510, 515, 520])
class PushManager {
    void handleConnectionClosure_ApnsConnection_T__() {
	 */
	public void handleUnprocessedNotifications(ApnsConnection<T> connection, Collection<T> unprocessedNotifications) {
		this.retryQueue.addAll(unprocessedNotifications);

		if (this.dispatchThread != null) {
			this.dispatchThread.interrupt();
		}
	}

	private void startNewConnection() {
		this.connectionLock.lock();

		try {
			new ApnsConnection<T>(this.environment, this.sslContext, this.eventLoopGroup, this).connect();
			this.unfinishedConnectionCount += 1;
		} finally {
			this.connectionLock.unlock();
		}
	}

	private void decrementConnectionCounter() {
    }
}
