package com.hk.autotest.exception;


public class AppDownloadException  extends CapException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1689933218918511200L;

	public AppDownloadException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AppDownloadException(String msg) {
		super(msg);
	}
}
