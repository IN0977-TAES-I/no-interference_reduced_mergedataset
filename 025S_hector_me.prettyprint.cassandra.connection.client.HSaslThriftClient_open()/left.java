class HSaslThriftClient {
    static Logger log;
    String servicePrincipalName;
    TSSLTransportParameters params;

    public HSaslThriftClient open() {
      if ( isOpen() ) {
        throw new IllegalStateException("Open called on already open SASL connection. You should not have gotten here.");
      }
      if ( log.isDebugEnabled() ) {
        log.debug("Creating a new SASL thrift connection to {}", cassandraHost);
      }

      TSocket socket;
      try {
        if (params == null)
          socket = new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout);
        else
          socket = TSSLTransportFactory.getClientSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout, params);
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

      try {
        transport = openKerberosTransport(socket, servicePrincipalName);
      } catch (LoginException e) {
        log.error("Kerberos login failed: ", e);
        close();
        throw new HectorTransportException("Kerberos context couldn't be established with client: ", e);
      } catch (TTransportException e) {
        log.error("Failed to open Kerberos transport.", e);
        close();
        throw new HectorTransportException("Kerberos context couldn't be established with client: ", e);
      }

      if (cassandraHost.getUseThriftFramedTransport()) {
        transport = new TFramedTransport(transport);
      }

      return this;
    }
}
