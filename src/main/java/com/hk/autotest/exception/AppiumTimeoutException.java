package com.hk.autotest.exception;

public class AppiumTimeoutException extends CapException  {
	public AppiumTimeoutException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AppiumTimeoutException(String msg) {
		super(msg);
	}

	public AppiumTimeoutException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5342703566853569975L;

}
