// class name : com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
// method name: newWindow(ApplicationListener, Lwjgl3WindowConfiguration)
// side       : left
// region     : lines 318-325 (affected lines: [318, 325])
class Lwjgl3Application {
    void newWindow_ApplicationListener__Lwjgl3WindowConfiguration_() {
		appConfig.setWindowSizeLimits(config.windowMinWidth, config.windowMinHeight, config.windowMaxWidth, config.windowMaxHeight);
		appConfig.setResizable(config.windowResizable);
		appConfig.setDecorated(config.windowDecorated);
		appConfig.setWindowListener(config.windowListener);
		appConfig.setFullscreenMode(config.fullscreenMode);
		appConfig.setTitle(config.title);
		appConfig.setInitialBackgroundColor(config.initialBackgroundColor);		
		Lwjgl3Window window = createWindow(appConfig, listener, windows.get(0).getWindowHandle());
    }
}
