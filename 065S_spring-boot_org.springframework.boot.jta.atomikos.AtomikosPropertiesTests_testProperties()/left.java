class AtomikosPropertiesTests {
    AtomikosProperties properties;

    @Test
    public void testProperties() {
    	this.properties.setService("service");
    	this.properties.setMaxTimeout(1L);
    	this.properties.setDefaultJtaTimeout(2L);
    	this.properties.setMaxActives(3);
    	this.properties.setEnableLogging(true);
    	this.properties.setTransactionManagerUniqueName("uniqueName");
    	this.properties.setSerialJtaTransactions(true);
    	this.properties.setAllowSubTransactions(false);
    	this.properties.setForceShutdownOnVmExit(true);
    	this.properties.setLogBaseName("logBaseName");
    	this.properties.setLogBaseDir("logBaseDir");
    	this.properties.setCheckpointInterval(4);
    	this.properties.setConsoleLogLevel(AtomikosLoggingLevel.WARN);
    	this.properties.setOutputDir("outputDir");
    	this.properties.setConsoleFileName("consoleFileName");
    	this.properties.setConsoleFileCount(5);
    	this.properties.setConsoleFileLimit(6);
    	this.properties.setThreadedTwoPhaseCommit(true);
    	this.properties.getRecovery().setForgetOrphanedLogEntriesDelay(2000);
    	this.properties.getRecovery().setDelay(3000);
    	this.properties.getRecovery().setMaxRetries(10);
    	this.properties.getRecovery().setRetryInterval(4000);

    	assertThat(this.properties.asProperties().size()).isEqualTo(22);
    	assertProperty("com.atomikos.icatch.service", "service");
    	assertProperty("com.atomikos.icatch.max_timeout", "1");
    	assertProperty("com.atomikos.icatch.default_jta_timeout", "2");
    	assertProperty("com.atomikos.icatch.max_actives", "3");
    	assertProperty("com.atomikos.icatch.enable_logging", "true");
    	assertProperty("com.atomikos.icatch.tm_unique_name", "uniqueName");
    	assertProperty("com.atomikos.icatch.serial_jta_transactions", "true");
    	assertProperty("com.atomikos.icatch.allow_subtransactions", "false");
    	assertProperty("com.atomikos.icatch.force_shutdown_on_vm_exit", "true");
    	assertProperty("com.atomikos.icatch.log_base_name", "logBaseName");
    	assertProperty("com.atomikos.icatch.log_base_dir", "logBaseDir");
    	assertProperty("com.atomikos.icatch.checkpoint_interval", "4");
    	assertProperty("com.atomikos.icatch.console_log_level", "WARN");
    	assertProperty("com.atomikos.icatch.output_dir", "outputDir");
    	assertProperty("com.atomikos.icatch.console_file_name", "consoleFileName");
    	assertProperty("com.atomikos.icatch.console_file_count", "5");
    	assertProperty("com.atomikos.icatch.console_file_limit", "6");
    	assertProperty("com.atomikos.icatch.threaded_2pc", "true");
    	assertProperty("com.atomikos.icatch.forget_orphaned_log_entries_delay", "2000");
    	assertProperty("com.atomikos.icatch.recovery_delay", "3000");
    	assertProperty("com.atomikos.icatch.oltp_max_retries", "10");
    	assertProperty("com.atomikos.icatch.oltp_retry_interval", "4000");
    }
}
