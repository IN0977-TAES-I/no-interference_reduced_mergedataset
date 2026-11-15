class AtomikosPropertiesTests {
    AtomikosProperties properties;

    @Test
    public void testDefaultProperties() {
    	Properties defaultSettings = loadDefaultSettings();
    	Properties properties = this.properties.asProperties();
    	assertThat(properties).contains(defaultOf(defaultSettings,
    			"com.atomikos.icatch.max_timeout",
    			"com.atomikos.icatch.default_jta_timeout",
    			"com.atomikos.icatch.max_actives", "com.atomikos.icatch.enable_logging",
    			"com.atomikos.icatch.serial_jta_transactions",
    			"com.atomikos.icatch.allow_subtransactions",
    			"com.atomikos.icatch.force_shutdown_on_vm_exit",
    			"com.atomikos.icatch.log_base_name",
    			"com.atomikos.icatch.checkpoint_interval",
    			"com.atomikos.icatch.threaded_2pc",
    			"com.atomikos.icatch.forget_orphaned_log_entries_delay",
    			"com.atomikos.icatch.oltp_max_retries",
    			"com.atomikos.icatch.oltp_retry_interval"));
    	assertThat(properties).contains(
    			entry("com.atomikos.icatch.recovery_delay", defaultSettings.get(
    					"com.atomikos.icatch.default_jta_timeout")),
    			entry("com.atomikos.icatch.console_log_level", "WARN"),
    			entry("com.atomikos.icatch.console_file_name", "tm.out"),
    			entry("com.atomikos.icatch.console_file_count", "1"),
    			entry("com.atomikos.icatch.console_file_limit", "-1"));
    	assertThat(properties).hasSize(18);
    }
}
