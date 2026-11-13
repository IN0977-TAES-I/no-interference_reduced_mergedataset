// class name : org.elasticsearch.common.settings.IndexScopedSettings
// method name: BUILT_IN_INDEX_SETTINGS
// side       : right
// region     : lines 119-121 (affected lines: [119, 120, 121])
class IndexScopedSettings {
    void BUILT_IN_INDEX_SETTINGS() {
        version = in.readZLong();
        forcedRefresh = in.readBoolean();
    }
    }
}
