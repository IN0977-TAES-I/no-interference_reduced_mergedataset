class UndertowEmbeddedServletContainerFactory {
    List<UndertowDeploymentInfoCustomizer> deploymentInfoCustomizers;

    private DeploymentManager createDeploymentManager(
    		ServletContextInitializer... initializers) {
    	DeploymentInfo deployment = Servlets.deployment();
    	registerServletContainerInitializerToDriveServletContextInitializers(deployment,
    			initializers);
    	deployment.setClassLoader(getServletClassLoader());
    	deployment.setContextPath(getContextPath());
    	deployment.setDisplayName(getDisplayName());
    	deployment.setDeploymentName("spring-boot");
    	if (isRegisterDefaultServlet()) {
    		deployment.addServlet(Servlets.servlet("default", DefaultServlet.class));
    	}
    	configureErrorPages(deployment);
    	deployment.setServletStackTraces(ServletStackTraces.NONE);
    	deployment.setResourceManager(getDocumentRootResourceManager());
    	configureMimeMappings(deployment);
    	for (UndertowDeploymentInfoCustomizer customizer : this.deploymentInfoCustomizers) {
    		customizer.customize(deployment);
    	}
    	if (isAccessLogEnabled()) {
    		configureAccessLog(deployment);
    	}
    	if (isPersistSession()) {
    		File dir = getValidSessionStoreDir();
    		deployment.setSessionPersistenceManager(new FileSessionPersistence(dir));
    	}
    	DeploymentManager manager = Servlets.newContainer().addDeployment(deployment);
    	manager.deploy();
    	SessionManager sessionManager = manager.getDeployment().getSessionManager();
    	int sessionTimeout = (getSessionTimeout() > 0 ? getSessionTimeout() : -1);
    	sessionManager.setDefaultSessionTimeout(sessionTimeout);
    	return manager;
    }
}
