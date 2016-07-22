package com.hk.autotest.lanucher;
import java.net.URL;
public interface AppiumServer {
	void startAppium();

	boolean isRunning();

	URL getURL();

	void stopAppium();

	String getAppiumLog();
}
