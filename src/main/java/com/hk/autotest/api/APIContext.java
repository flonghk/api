package com.hk.autotest.api;

import com.hk.autotest.domain.Context;

public class APIContext extends Context{
	public static final String API_PROXY_HOST = "apiProxyHost";
	public static final String API_PROXY_PORT = "apiProxyPort";
	public static final String TIME_OUT = "TIME_OUT";

	public String getTestParameter(String key) {
		return getAllTestParamaters().get(key);
	}

	public int getWebSessionTimeout() {
		try {
			return Integer.parseInt(getTestParameter(TIME_OUT));
		} catch (Exception e) {
			return 90000; // Default
		}
	}
}
