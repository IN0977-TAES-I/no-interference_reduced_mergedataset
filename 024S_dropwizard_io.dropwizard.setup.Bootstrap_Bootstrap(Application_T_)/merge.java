class Bootstrap {
    ObjectMapper objectMapper;
    List<Bundle> bundles;
    List<ConfiguredBundle<? super T>> configuredBundles;
    List<Command> commands;
    MetricRegistry metricRegistry;
    ValidatorFactory validatorFactory;
    ConfigurationSourceProvider configurationSourceProvider;
    ClassLoader classLoader;
    ConfigurationFactoryFactory<T> configurationFactoryFactory;

    public Bootstrap(Application<T> application) {
        this.application = application;
        this.objectMapper = Jackson.newObjectMapper();
        this.bundles = Lists.newArrayList();
        this.configuredBundles = Lists.newArrayList();
        this.commands = Lists.newArrayList();
        this.metricRegistry = new MetricRegistry();
        this.validatorFactory = Validation.buildDefaultValidatorFactory();
        getMetricRegistry().register("jvm.buffers", new BufferPoolMetricSet(ManagementFactory
                                                                               .getPlatformMBeanServer()));
        getMetricRegistry().register("jvm.gc", new GarbageCollectorMetricSet());
        getMetricRegistry().register("jvm.memory", new MemoryUsageGaugeSet());
        getMetricRegistry().register("jvm.threads", new ThreadStatesGaugeSet());

        JmxReporter.forRegistry(metricRegistry).build().start();

        this.configurationSourceProvider = new FileConfigurationSourceProvider();
        this.classLoader = Thread.currentThread().getContextClassLoader();
        this.configurationFactoryFactory = new DefaultConfigurationFactoryFactory<T>();
    }
}
