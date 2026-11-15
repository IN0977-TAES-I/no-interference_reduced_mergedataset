class JavaLanguage {
    ObjectFactory objectFactory;

    protected void load(String java_file) throws ClassNotFoundException {
        Class<?> clazz = loadClass(java_file);
        registerStepDefinitionsFor(clazz);
        objectFactory.addClass(clazz);
    }
}
