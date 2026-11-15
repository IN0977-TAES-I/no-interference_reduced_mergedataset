class HThriftClient {
    static Logger log;
    CassandraHost cassandraHost;
    int timeout;
    TTransport transport;
    TSSLTransportParameters params;

    public HThriftClient open() {
      if ( isOpen() ) {
        throw new IllegalStateException("Open called on already open connection. You should not have gotten here.");
      }
      if ( log.isDebugEnabled() ) {
        log.debug("Creating a new thrift connection to {}", cassandraHost);
      }

      TSocket socket;
      try {
          socket = params == null ?
                                  new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout)
                                  : TSSLTransportFactory.getClientSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout, params);
      } catch (TTransportException e) {
          throw new HectorTransportException("Could not get client socket: ", e);
      }

      if ( cassandraHost.getUseSocketKeepalive() ) {
        try {
          socket.getSocket().setKeepAlive(true);
        } catch (SocketException se) {
          throw new HectorTransportException("Could not set SO_KEEPALIVE on socket: ", se);
        }
      }
      if (cassandraHost.getUseThriftFramedTransport()) {
        transport = new TFramedTransport(socket);
      } else {
        transport = socket;
      }

      if (!transport.isOpen()) {
        try {
          transport.open();
        } catch (TTransportException e) {
          log.debug("Unable to open transport to " + cassandraHost.getName());
          throw new HectorTransportException("Unable to open transport to " + cassandraHost.getName() +" , " +
              e.getLocalizedMessage(), e);
        }
      }
      return this;
    }
}
