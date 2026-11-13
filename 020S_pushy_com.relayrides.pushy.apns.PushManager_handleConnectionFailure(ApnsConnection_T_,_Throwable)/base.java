// class name : com.relayrides.pushy.apns.PushManager
// method name: handleConnectionFailure(ApnsConnection<T>, Throwable)
// side       : base
// region     : lines 476-490 (affected lines: [476, 478, 481, 482, 483, 484, 485, 490])
class PushManager {
    void handleConnectionFailure_ApnsConnection_T___Throwable_() {

		if (this.dispatchThread != null && this.dispatchThread.isAlive()) {
			this.dispatchThread.interrupt();
		}

		// TODO Do this in an executor service instead of spawning a new thread
		new Thread(new Runnable() {

			public void run() {
				try {
					connection.waitForPendingOperationsToFinish();

					connectionLock.lock();

					try {
    }
}
