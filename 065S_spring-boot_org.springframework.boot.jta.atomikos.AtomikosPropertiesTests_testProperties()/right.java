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
    	this.properties.setForceShutdownOnVmExit(true);
    	this.properties.setLogBaseName("logBaseName");
    	this.properties.setLogBaseDir("logBaseDir");
    	this.properties.setCheckpointInterval(4);
    	this.properties.setThreadedTwoPhaseCommit(true);

    	assertThat(this.properties.asProperties().size()).isEqualTo(12);
    	assertProperty("com.atomikos.icatch.service", "service");
    	assertProperty("com.atomikos.icatch.max_timeout", "1");
    	assertProperty("com.atomikos.icatch.default_jta_timeout", "2");
    	assertProperty("com.atomikos.icatch.max_actives", "3");
    	assertProperty("com.atomikos.icatch.enable_logging", "true");
    	assertProperty("com.atomikos.icatch.tm_unique_name", "uniqueName");
    	assertProperty("com.atomikos.icatch.serial_jta_transactions", "true");
    	assertProperty("com.atomikos.icatch.force_shutdown_on_vm_exit", "true");
    	assertProperty("com.atomikos.icatch.log_base_name", "logBaseName");
    	assertProperty("com.atomikos.icatch.log_base_dir", "logBaseDir");
    	assertProperty("com.atomikos.icatch.checkpoint_interval", "4");
    	assertProperty("com.atomikos.icatch.threaded_2pc", "true");
    }
}
