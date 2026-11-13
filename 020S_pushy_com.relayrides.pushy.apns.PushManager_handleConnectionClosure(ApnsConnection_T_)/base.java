// class name : com.relayrides.pushy.apns.PushManager
// method name: handleConnectionClosure(ApnsConnection<T>)
// side       : base
// region     : lines 500-520 (affected lines: [500, 502, 503, 510, 515, 520])
class PushManager {
    void handleConnectionClosure_ApnsConnection_T__() {
					log.warn("Interrupted while waiting for closed connection's pending operations to finish.");
				}
			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * @see com.relayrides.pushy.apns.ApnsConnectionListener#handleWriteFailure(com.relayrides.pushy.apns.ApnsConnection, com.relayrides.pushy.apns.ApnsPushNotification, java.lang.Throwable)
	 */
	public void handleWriteFailure(ApnsConnection<T> connection, T notification, Throwable cause) {
		this.retryQueue.add(notification);

		if (this.dispatchThread != null) {
			this.dispatchThread.interrupt();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.relayrides.pushy.apns.ApnsConnectionListener#handleRejectedNotification(com.relayrides.pushy.apns.ApnsConnection, com.relayrides.pushy.apns.ApnsPushNotification, com.relayrides.pushy.apns.RejectedNotificationReason)
    }
}
