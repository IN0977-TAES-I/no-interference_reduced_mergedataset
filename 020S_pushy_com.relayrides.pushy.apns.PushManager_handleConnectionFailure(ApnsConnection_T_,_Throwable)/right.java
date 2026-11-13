// class name : com.relayrides.pushy.apns.PushManager
// method name: handleConnectionFailure(ApnsConnection<T>, Throwable)
// side       : right
// region     : lines 476-490 (affected lines: [476, 478, 481, 482, 483, 484, 485, 490])
class PushManager {
    void handleConnectionFailure_ApnsConnection_T___Throwable_() {
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.relayrides.pushy.apns.ApnsConnectionListener#handleRejectedNotification(com.relayrides.pushy.apns.ApnsConnection, com.relayrides.pushy.apns.ApnsPushNotification, com.relayrides.pushy.apns.RejectedNotificationReason)
	 */
	public void handleRejectedNotification(final ApnsConnection<T> connection, final T rejectedNotification,
			final RejectedNotificationReason reason) {

		for (final RejectedNotificationListener<? super T> listener : this.rejectedNotificationListeners) {

			// Handle the notifications in a separate thread in case a listener takes a long time to run
			this.rejectedNotificationExecutorService.submit(new Runnable() {
				public void run() {
    }
}
