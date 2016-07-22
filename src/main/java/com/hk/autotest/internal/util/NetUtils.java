package com.hk.autotest.internal.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtils {

	public static String getLocalIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return null;
		}
	}

	public static String getLocalName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return null;
		}
	}
}
