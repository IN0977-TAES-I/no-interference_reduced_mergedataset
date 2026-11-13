// class name : voldemort.server.VoldemortConfig
// method name: VoldemortConfig(Props)
// side       : right
// region     : lines 359-455 (affected lines: [359, 448, 455])
class VoldemortConfig {
    void VoldemortConfig_Props_() {
                                                      DEFAULT_FETCHER_BUFFER_SIZE);
        this.fetcherSocketTimeout = props.getInt("hdfs.fetcher.socket.timeout",
                                                 DEFAULT_FETCHER_SOCKET_TIMEOUT);
        this.readOnlyKeytabPath = props.getString("readonly.keytab.path",
                                                  this.metadataDirectory
                                                          + VoldemortConfig.DEFAULT_KEYTAB_PATH);
        this.readOnlyKerberosUser = props.getString("readonly.kerberos.user",
                                                    VoldemortConfig.DEFAULT_KERBEROS_PRINCIPAL);
        this.hadoopConfigPath = (props.getString(VoldemortConfig.HADOOP_CONFIG_PATH,
                                                 this.metadataDirectory + "/hadoop-conf"));
        this.readOnlyKerberosKdc = props.getString("readonly.kerberos.kdc",
                                                   VoldemortConfig.DEFAULT_KERBEROS_KDC);
        this.readOnlykerberosRealm = props.getString("readonly.kerberos.realm",
                                                     VoldemortConfig.DEFAULT_KERBEROS_REALM);
        this.fileFetcherClass = props.getString("file.fetcher.class",
                                                VoldemortConfig.DEFAULT_FILE_FETCHER_CLASS);
        this.readOnlyStatsFileEnabled = props.getBoolean("readonly.stats.file.enabled", true);
        this.readOnlyMaxVersionsStatsFile = props.getInt("readonly.stats.file.max.versions", 1000);

        // To set the Voldemort RO server compression codec to GZIP, explicitly
        // set this
        // property "readonly.compression.codec" to "GZIP"
        this.readOnlyCompressionCodec = props.getString("readonly.compression.codec",
                                                        VoldemortConfig.DEFAULT_RO_COMPRESSION_CODEC);

        this.highAvailabilityPushClusterId = props.getString(PUSH_HA_CLUSTER_ID, null);
        this.highAvailabilityPushLockPath = props.getString(PUSH_HA_LOCK_PATH, null);
        this.highAvailabilityPushLockImplementation = props.getString(PUSH_HA_LOCK_IMPLEMENTATION, null);
        this.highAvailabilityPushMaxNodeFailures = props.getInt(PUSH_HA_MAX_NODE_FAILURES, 0);
        this.highAvailabilityPushEnabled = props.getBoolean(PUSH_HA_ENABLED, false);

        this.mysqlUsername = props.getString("mysql.user", "root");
        this.mysqlPassword = props.getString("mysql.password", "");
        this.mysqlHost = props.getString("mysql.host", "localhost");
        this.mysqlPort = props.getInt("mysql.port", 3306);
        this.mysqlDatabaseName = props.getString("mysql.database", "voldemort");

        this.testingSlowQueueingDelays = new OpTimeMap(0);
        this.testingSlowQueueingDelays.setOpTime(VoldemortOpCode.GET_OP_CODE,
                                                 props.getInt("testing.slow.queueing.get.ms", 0));
        this.testingSlowQueueingDelays.setOpTime(VoldemortOpCode.GET_ALL_OP_CODE,
                                                 props.getInt("testing.slow.queueing.getall.ms", 0));
        this.testingSlowQueueingDelays.setOpTime(VoldemortOpCode.GET_VERSION_OP_CODE,
                                                 props.getInt("testing.slow.queueing.getversions.ms",
                                                              0));
        this.testingSlowQueueingDelays.setOpTime(VoldemortOpCode.PUT_OP_CODE,
                                                 props.getInt("testing.slow.queueing.put.ms", 0));
        this.testingSlowQueueingDelays.setOpTime(VoldemortOpCode.DELETE_OP_CODE,
                                                 props.getInt("testing.slow.queueing.delete.ms", 0));

        this.testingSlowConcurrentDelays = new OpTimeMap(0);
        this.testingSlowConcurrentDelays.setOpTime(VoldemortOpCode.GET_OP_CODE,
                                                   props.getInt("testing.slow.concurrent.get.ms", 0));
        this.testingSlowConcurrentDelays.setOpTime(VoldemortOpCode.GET_ALL_OP_CODE,
                                                   props.getInt("testing.slow.concurrent.getall.ms",
                                                                0));
        this.testingSlowConcurrentDelays.setOpTime(VoldemortOpCode.GET_VERSION_OP_CODE,
                                                   props.getInt("testing.slow.concurrent.getversions.ms",
                                                                0));
        this.testingSlowConcurrentDelays.setOpTime(VoldemortOpCode.PUT_OP_CODE,
                                                   props.getInt("testing.slow.concurrent.put.ms", 0));
        this.testingSlowConcurrentDelays.setOpTime(VoldemortOpCode.DELETE_OP_CODE,
                                                   props.getInt("testing.slow.concurrent.delete.ms",
                                                                0));

        this.maxThreads = props.getInt("max.threads", 100);
        this.coreThreads = props.getInt("core.threads", Math.max(1, maxThreads / 2));

        // Admin client should have less threads but very high buffer size.
        this.adminMaxThreads = props.getInt("admin.max.threads", 20);
        this.adminCoreThreads = props.getInt("admin.core.threads", Math.max(1, adminMaxThreads / 2));
        this.adminStreamBufferSize = (int) props.getBytes("admin.streams.buffer.size",
                                                          10 * 1000 * 1000);
        this.adminConnectionTimeout = props.getInt("admin.client.connection.timeout.sec", 60);
        this.adminSocketTimeout = props.getInt("admin.client.socket.timeout.sec", 24 * 60 * 60);

        this.streamMaxReadBytesPerSec = props.getBytes("stream.read.byte.per.sec", 10 * 1000 * 1000);
        this.streamMaxWriteBytesPerSec = props.getBytes("stream.write.byte.per.sec",
                                                        10 * 1000 * 1000);
        this.multiVersionStreamingPutsEnabled = props.getBoolean("use.multi.version.streaming.puts",
                                                                 true);

        this.socketTimeoutMs = props.getInt("socket.timeout.ms", 5000);
        this.socketBufferSize = (int) props.getBytes("socket.buffer.size", 64 * 1024);
        this.socketKeepAlive = props.getBoolean("socket.keepalive", false);

        this.useNioConnector = props.getBoolean("enable.nio.connector", true);
        this.nioConnectorKeepAlive = props.getBoolean("nio.connector.keepalive", false);
        this.nioConnectorSelectors = props.getInt("nio.connector.selectors",
                                                  Math.max(8, Runtime.getRuntime()
                                                                     .availableProcessors()));
        this.nioAdminConnectorSelectors = props.getInt("nio.admin.connector.selectors",
                                                       Math.max(8, Runtime.getRuntime()
                                                                          .availableProcessors()));
        this.nioAdminConnectorKeepAlive = props.getBoolean("nio.admin.connector.keepalive", false);
        // a value <= 0 forces the default to be used
        this.nioAcceptorBacklog = props.getInt("nio.acceptor.backlog", 256);
    }
}
