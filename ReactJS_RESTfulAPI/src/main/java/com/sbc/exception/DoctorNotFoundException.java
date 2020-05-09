package com.sbc.exception;

public class DoctorNotFoundException extends RuntimeException {

	/**
	 * If you use 'Exception' instead of 'RuntimeException' then your controller and service will throw Exception on 
	 * each methods which makes the code look untidy.
	 */
	private static final long serialVersionUID = 1L;

	public DoctorNotFoundException(String msg) {
		super(msg);
	}
}