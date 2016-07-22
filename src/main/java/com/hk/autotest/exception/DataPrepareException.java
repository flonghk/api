package com.hk.autotest.exception;

/**
 * Template load and render execption wrapper to RuntimeException
 * @author 
 */

public class DataPrepareException extends CapException  {

	public DataPrepareException(Throwable cause) {
		super(cause);
	}

	public DataPrepareException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8787229869074453324L;
}
