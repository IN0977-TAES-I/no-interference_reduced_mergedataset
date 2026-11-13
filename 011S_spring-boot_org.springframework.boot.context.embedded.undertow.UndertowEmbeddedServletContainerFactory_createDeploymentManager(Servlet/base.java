// class name : org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory
// method name: createDeploymentManager(ServletContextInitializer)
// side       : base
// region     : lines 345-364 (affected lines: [345, 357, 358, 359, 360, 361, 362, 364])
class UndertowEmbeddedServletContainerFactory {
    void createDeploymentManager_ServletContextInitializer_() {
	}

	private void registerServletContainerInitializerToDriveServletContextInitializers(
			DeploymentInfo deployment, ServletContextInitializer... initializers) {
		ServletContextInitializer[] mergedInitializers = mergeInitializers(initializers);
		Initializer initializer = new Initializer(mergedInitializers);
		deployment.addServletContainerInitalizer(new ServletContainerInitializerInfo(
				Initializer.class,
				new ImmediateInstanceFactory<ServletContainerInitializer>(initializer),
				NO_CLASSES));
	}

	private ClassLoader getServletClassLoader() {
		if (this.resourceLoader != null) {
			return this.resourceLoader.getClassLoader();
		}
		return getClass().getClassLoader();
	}

	private ResourceManager getDocumentRootResourceManager() {
    }
}
