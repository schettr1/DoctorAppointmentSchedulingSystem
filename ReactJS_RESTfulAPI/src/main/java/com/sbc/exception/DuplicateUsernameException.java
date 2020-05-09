package com.sbc.exception;

public class DuplicateUsernameException extends RuntimeException {

	/**
	 * If you use 'Exception' instead of 'RuntimeException' then your controller and service will throw Exception on 
	 * each methods which makes the code look untidy.
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateUsernameException(String msg) {
		super(msg);
	}
}