// class name : cuke4duke.internal.java.JavaLanguage
// method name: load(String)
// side       : merge
// region     : lines 36-40 (affected lines: [36, 38, 40])
class JavaLanguage {
    void load_String_() {
        Class<?> clazz = loadClass(java_file);
        registerStepDefinitionsFor(clazz);
        if(!Modifier.isAbstract(clazz.getModifiers())) {
            objectFactory.addClass(clazz);
        }
    }
}
