// class name : me.prettyprint.cassandra.connection.client.HKerberosThriftClient
// method name: open()
// side       : right
// region     : lines 69-88 (affected lines: [69, 70, 71, 72, 73, 74, 75, 76, 88])
class HKerberosThriftClient {
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

    // TODO (patricioe) What should I do with it ?
    // KerberosHelper.getSourcePrinciple(clientContext));

    }
}
