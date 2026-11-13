// class name : cuke4duke.internal.java.JavaLanguage
// method name: load(String)
// side       : right
// region     : lines 36-40 (affected lines: [36, 38, 40])
class JavaLanguage {
    void load_String_() {
    }

    protected void load(String java_file) throws ClassNotFoundException {
        Class<?> clazz = loadClass(java_file);
        registerStepDefinitionsFor(clazz);
    }
}
