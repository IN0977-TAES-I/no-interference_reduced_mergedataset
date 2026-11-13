// class name : com.relayrides.pushy.apns.PushManager
// method name: handleConnectionFailure(ApnsConnection<T>, Throwable)
// side       : left
// region     : lines 476-490 (affected lines: [476, 478, 481, 482, 483, 484, 485, 490])
class PushManager {
    void handleConnectionFailure_ApnsConnection_T___Throwable_() {
			this.connectionPool.addConnection(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.relayrides.pushy.apns.ApnsConnectionListener#handleConnectionFailure(com.relayrides.pushy.apns.ApnsConnection, java.lang.Throwable)
	 */
	public void handleConnectionFailure(final ApnsConnection<T> connection, final Throwable cause) {

		final PushManager<T> pushManager = this;

		for (final FailedConnectionListener<? super T> listener : this.failedConnectionListeners) {

			// Handle connection failures in a separate thread in case a handler takes a long time to run
    }
}
