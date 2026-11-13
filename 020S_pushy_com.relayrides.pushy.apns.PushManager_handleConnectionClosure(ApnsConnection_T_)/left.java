// class name : com.relayrides.pushy.apns.PushManager
// method name: handleConnectionClosure(ApnsConnection<T>)
// side       : left
// region     : lines 500-520 (affected lines: [500, 502, 503, 510, 515, 520])
class PushManager {
    void handleConnectionClosure_ApnsConnection_T__() {
			this.connectionLock.lock();

			try {
				new ApnsConnection<T>(this.environment, this.sslContext, this.eventLoopGroup, this).connect();
				this.unfinishedConnectionCount += 1;
			} finally {
				this.connectionLock.unlock();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.relayrides.pushy.apns.ApnsConnectionListener#handleConnectionClosure(com.relayrides.pushy.apns.ApnsConnection)
	 */
	public void handleConnectionClosure(final ApnsConnection<T> connection) {
		this.connectionLock.lock();

		try {
			this.connectionPool.removeConnection(connection);
			this.unfinishedConnectionCount -= 1;
    }
}
