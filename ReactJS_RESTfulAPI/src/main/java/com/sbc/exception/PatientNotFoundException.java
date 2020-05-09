package com.sbc.exception;

public class PatientNotFoundException extends RuntimeException {

	/**
	 * If you use 'Exception' instead of 'RuntimeException' then your controller and service will throw Exception on 
	 * each methods which makes the code look untidy.
	 */
	private static final long serialVersionUID = 1L;

	public PatientNotFoundException(String msg) {
		super(msg);
	}

}
