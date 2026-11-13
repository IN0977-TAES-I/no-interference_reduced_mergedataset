// class name : me.prettyprint.cassandra.connection.client.HSaslThriftClient
// method name: open()
// side       : right
// region     : lines 94-122 (affected lines: [94, 95, 96, 97, 111, 122])
class HSaslThriftClient {
    void open__() {

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

      try {
        transport = openKerberosTransport(socket, servicePrincipalName, clientPrincipalName);
      } catch (LoginException e) {
        log.error("Kerberos login failed: ", e);
        close();
        throw new HectorTransportException("Kerberos context couldn't be established with client: ", e);
      } catch (TTransportException e) {
        log.error("Failed to open Kerberos transport.", e);
        close();
        throw new HectorTransportException("Kerberos context couldn't be established with client: ", e);
      }
    }
}
