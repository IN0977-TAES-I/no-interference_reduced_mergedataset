// class name : me.prettyprint.cassandra.connection.HConnectionManager
// method name: operateWithFailover(Operation<?>)
// side       : left
// region     : lines 200-268 (affected lines: [200, 267, 268])
class HConnectionManager {
    void operateWithFailover_Operation____() {
    return stats;
  }


  public void operateWithFailover(Operation<?> op) throws HectorException {
    final StopWatch stopWatch = new Slf4JStopWatch(perf4jLogger);
    int retries = Math.min(op.failoverPolicy.numRetries, hostPools.size());
    HThriftClient client = null;
    HClientPool pool = null;
    boolean success = false;
    boolean retryable = false;
    Set<CassandraHost> excludeHosts = new HashSet<CassandraHost>(); // HLT.getExcludedHosts() (will be empty most times)
    // TODO start timer for limiting retry time spent
    while ( !success ) {
      try {
        // TODO how to 'timeout' on this op when underlying pool is exhausted
        pool = getClientFromLBPolicy(excludeHosts);
        client = pool.borrowClient();
        Cassandra.Client c = client.getCassandra(op.keyspaceName);
        // Keyspace can be null for some system_* api calls
        if ( op.credentials != null && !op.credentials.isEmpty() ) {
          c.login(new AuthenticationRequest(op.credentials));
        }

        op.executeAndSetResult(c, pool.getCassandraHost());
        success = true;
        stopWatch.stop(op.stopWatchTagName + ".success_");
        break;

      } catch (Exception ex) {
        HectorException he = exceptionsTranslator.translate(ex);
        if ( he instanceof HInvalidRequestException || he instanceof HCassandraInternalException || he instanceof HUnavailableException) {
          // break out on HUnavailableException as well since we can no longer satisfy the CL
          throw he;
        } else if ( he instanceof HectorTransportException) {
          --retries;
          // client can be null in this situation
          if ( client != null ) {            
            client.close();
          }
          markHostAsDown(pool.getCassandraHost());
          excludeHosts.add(pool.getCassandraHost());
          retryable = true;
          if ( retries > 0 ) {
            monitor.incCounter(Counter.RECOVERABLE_TRANSPORT_EXCEPTIONS);
          }        
        } else if (he instanceof HTimedOutException ) {
          // DO NOT drecrement retries, we will be keep retrying on timeouts until it comes back
          // if HLT.checkTimeout(cassandraHost): suspendHost(cassandraHost);          
          doTimeoutCheck(pool.getCassandraHost());
          if ( hostPools.size() > 1) {
            retryable = true;
          }
          monitor.incCounter(Counter.RECOVERABLE_TIMED_OUT_EXCEPTIONS);
          client.close();
          // TODO timecheck on how long we've been waiting on timeouts here
          // suggestion per user moores on hector-users
        } else if ( he instanceof PoolExhaustedException ) {
          retryable = true;
          --retries;
          if ( hostPools.size() == 1 ) {
            throw he;
          }
          monitor.incCounter(Counter.POOL_EXHAUSTED);
          excludeHosts.add(pool.getCassandraHost());
        } else {
          // something strange happened. Added here as suggested by sbridges.
          // I think this gives a sane way to future-proof against any API additions
          // that we don't add in time. 
    }
}
