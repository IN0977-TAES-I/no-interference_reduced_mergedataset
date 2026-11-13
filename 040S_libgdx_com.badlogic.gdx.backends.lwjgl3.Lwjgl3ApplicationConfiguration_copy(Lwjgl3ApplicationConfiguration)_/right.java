// class name : com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
// method name: copy(Lwjgl3ApplicationConfiguration)
// side       : right
// region     : lines 96-107 (affected lines: [96, 97, 98, 99, 107])
class Lwjgl3ApplicationConfiguration {
    void copy_Lwjgl3ApplicationConfiguration_() {
		try {
			loop();
		} catch(Throwable t) {
			if (t instanceof RuntimeException)
				throw (RuntimeException) t;
			else
				throw new GdxRuntimeException(t);
		} finally {
			cleanup();
		}
	}

    }
}
