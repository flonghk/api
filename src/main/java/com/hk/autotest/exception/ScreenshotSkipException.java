package com.hk.autotest.exception;

public class ScreenshotSkipException extends CapException  {

	public ScreenshotSkipException(String msg) {
		super(msg);
	}

	public ScreenshotSkipException(Throwable cause) {
		super(cause);
	}

	public ScreenshotSkipException() {
		super("ScreenshotSkipException");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1692124529409528994L;

}
