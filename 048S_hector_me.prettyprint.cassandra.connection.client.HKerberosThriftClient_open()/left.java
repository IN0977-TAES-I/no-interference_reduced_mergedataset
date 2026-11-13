// class name : me.prettyprint.cassandra.connection.client.HKerberosThriftClient
// method name: open()
// side       : left
// region     : lines 69-88 (affected lines: [69, 70, 71, 72, 73, 74, 75, 76, 88])
class HKerberosThriftClient {
    void open__() {
      // Thrift exceptions aren't very good in reporting, so we have to catch the exception here and
      // add details to it.
      log.debug("Unable to open transport to " + cassandraHost.getName());
      //clientMonitor.incCounter(Counter.CONNECT_ERROR);
      throw new HectorTransportException("Unable to open transport to " + cassandraHost.getName() +" , " +
          e.getLocalizedMessage(), e);
    }
    
    // Kerberos authentication
    Socket internalSocket = socket.getSocket();

    final GSSContext clientContext = KerberosHelper.authenticateClient(internalSocket, kerberosTicket, servicePrincipalName);

    if (clientContext == null) {
      close();
      throw new HectorTransportException("Kerberos context couldn't be established with client.");
    }

    return this;
  }
    }
}
