// class name : org.elasticsearch.test.ESSingleNodeTestCase
// method name: newNode()
// side       : left
// region     : lines 171-174 (affected lines: [171, 173, 174])
class ESSingleNodeTestCase {
    void newNode__() {
            .put(EsExecutors.PROCESSORS_SETTING.getKey(), 1) // limit the number of threads created
            .put("http.enabled", false)
            .put("node.local", true)
            .put("node.data", true)
    }
}
