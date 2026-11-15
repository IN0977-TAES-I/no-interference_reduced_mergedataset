class HKerberosThriftClient {
    static Logger log;
    Subject kerberosTicket;
    String servicePrincipalName;

    public HKerberosThriftClient open() {
      if ( isOpen() ) {
        throw new IllegalStateException("Open called on already open connection. You should not have gotten here.");
      }
      if ( log.isDebugEnabled() ) {
        log.debug("Creating a new thrift connection to {}", cassandraHost);
      }

      TSocket socket = new TSocket(cassandraHost.getHost(), cassandraHost.getPort(), timeout);
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

      try {
        transport.open();
      } catch (TTransportException e) {
        log.debug("Unable to open transport to " + cassandraHost.getName());
        throw new HectorTransportException("Unable to open transport to " + cassandraHost.getName() +" , " +
            e.getLocalizedMessage(), e);
      }

      Socket internalSocket = socket.getSocket();

      final GSSContext clientContext = KerberosHelper.authenticateClient(internalSocket, kerberosTicket, servicePrincipalName);

      if (clientContext == null) {
        close();
        throw new HectorTransportException("Kerberos context couldn't be established with client.");
      }

      return this;
    }
}
