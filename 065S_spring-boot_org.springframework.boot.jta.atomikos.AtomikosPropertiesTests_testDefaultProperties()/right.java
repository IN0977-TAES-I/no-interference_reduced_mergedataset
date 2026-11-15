class AtomikosPropertiesTests {
    AtomikosProperties properties;

    @Test
    public void testDefaultProperties() {
    	Properties defaultSettings = loadDefaultSettings();
    	Properties properties = this.properties.asProperties();
    	assertThat(properties).contains(defaultOf(defaultSettings,
    			"com.atomikos.icatch.max_timeout",
    			"com.atomikos.icatch.default_jta_timeout",
    			"com.atomikos.icatch.max_actives",
    			"com.atomikos.icatch.enable_logging",
    			"com.atomikos.icatch.serial_jta_transactions",
    			"com.atomikos.icatch.force_shutdown_on_vm_exit",
    			"com.atomikos.icatch.log_base_name",
    			"com.atomikos.icatch.checkpoint_interval",
    			"com.atomikos.icatch.threaded_2pc"));
    	assertThat(properties).hasSize(9);
    }
}
