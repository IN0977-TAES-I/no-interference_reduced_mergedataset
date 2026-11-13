// class name : org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory
// method name: createDeploymentManager(ServletContextInitializer)
// side       : merge
// region     : lines 345-364 (affected lines: [345, 357, 358, 359, 360, 361, 362, 364])
class UndertowEmbeddedServletContainerFactory {
    void createDeploymentManager_ServletContextInitializer_() {
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
    }
}
