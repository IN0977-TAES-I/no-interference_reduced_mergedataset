class HConnectionManager {
    static Logger log;
    StopWatchFactory stopWatchFactory;
    NonBlockingHashMap<CassandraHost,HClientPool> hostPools;
    ExceptionsTranslator exceptionsTranslator;
    CassandraClientMonitor monitor;

    public void operateWithFailover(Operation<?> op) throws HectorException {
      final StopWatch stopWatch = stopWatchFactory.getStopWatch();
      int retries = Math.min(op.failoverPolicy.numRetries, hostPools.size());
      HThriftClient client = null;
      HClientPool pool = null;
      boolean success = false;
      boolean retryable = false;
      Set<CassandraHost> excludeHosts = new HashSet<CassandraHost>();
      while ( !success ) {
        try {
          pool = getClientFromLBPolicy(excludeHosts);
          client = pool.borrowClient();
          Cassandra.Client c = client.getCassandra(op.keyspaceName);
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
            throw he;
          } else if ( he instanceof HectorTransportException) {
            --retries;
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
            doTimeoutCheck(pool.getCassandraHost());
            if ( hostPools.size() > 1) {
              retryable = true;
            }
            monitor.incCounter(Counter.RECOVERABLE_TIMED_OUT_EXCEPTIONS);
            client.close();
          } else if ( he instanceof PoolExhaustedException ) {
            retryable = true;
            --retries;
            if ( hostPools.size() == 1 ) {
              throw he;
            }
            monitor.incCounter(Counter.POOL_EXHAUSTED);
            excludeHosts.add(pool.getCassandraHost());
          } else {
            retryable = false;
          }
          if ( retries <= 0 || retryable == false) throw he;
          log.warn("Could not fullfill request on this host {}", client);
          log.warn("Exception: ", he);
          monitor.incCounter(Counter.SKIP_HOST_SUCCESS);
          sleepBetweenHostSkips(op.failoverPolicy);
        } finally {
          if ( !success ) {
            monitor.incCounter(op.failCounter);
            stopWatch.stop(op.stopWatchTagName + ".fail_");
          }
          releaseClient(client);
        }
      }
    }
}
