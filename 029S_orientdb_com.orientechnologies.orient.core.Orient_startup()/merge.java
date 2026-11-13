// class name : com.orientechnologies.orient.core.Orient
// method name: startup()
// side       : merge
// region     : lines 158-191 (affected lines: [158, 191])
class Orient {
    void startup__() {
      profiler = new OProfiler();

      shutdownHook = new OrientShutdownHook();
      if (signalHandler == null) {
        signalHandler = new OSignalHandler();
        signalHandler.installDefaultSignals();
      }

      final int cores = Runtime.getRuntime().availableProcessors();

      workers = new ThreadPoolExecutor(cores, cores * 3, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(cores * 500) {
        @Override
        public boolean offer(Runnable e) {
          // turn offer() and add() into a blocking calls (unless interrupted)
          try {
            put(e);
            return true;
          } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
          }
          return false;
        }
      });

      // REGISTER THE EMBEDDED ENGINE
      registerEngine(new OEngineLocalPaginated());
      registerEngine(new OEngineMemory());
      registerEngine("com.orientechnologies.orient.client.remote.OEngineRemote");


      if (OGlobalConfiguration.ENVIRONMENT_DUMP_CFG_AT_STARTUP.getValueAsBoolean())
        OGlobalConfiguration.dumpConfiguration(System.out);

      active = true;
    }
}
