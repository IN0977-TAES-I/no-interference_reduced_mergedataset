class AutoConfigurationReportLoggingInitializer {
    Log logger;
    ConfigurableApplicationContext applicationContext;
    ConditionEvaluationReport report;

    public void logAutoConfigurationReport(boolean isCrashReport) {
    	if (this.report == null) {
    		if (this.applicationContext == null) {
    			this.logger.info("Unable to provide auto-configuration report "
    					+ "due to missing ApplicationContext");
    			return;
    		}
    		this.report = ConditionEvaluationReport.get(this.applicationContext
    				.getBeanFactory());
    	}
    	if (this.report.getConditionAndOutcomesBySource().size() > 0) {
    		if (isCrashReport && this.logger.isInfoEnabled()
    				&& !this.logger.isDebugEnabled()) {
    			this.logger.info("\n\nError starting ApplicationContext. "
    					+ "To display the auto-configuration report enabled "
    					+ "debug logging (start with --debug)\n\n");
    		}
    		if (this.logger.isDebugEnabled()) {
    			this.logger.debug(getLogMessage(this.report));
    		}
    	}
    }
}
