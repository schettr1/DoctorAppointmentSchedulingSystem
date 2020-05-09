package com.sbc.exception;

public class AppointmentNotFoundException extends RuntimeException {

	/**
	 * If you use 'Exception' instead of 'RuntimeException' then your controller and service will throw Exception on 
	 * each methods which makes the code look untidy.
	 */
	private static final long serialVersionUID = 1L;

	public AppointmentNotFoundException(String msg) {
		super(msg);
	}
}
