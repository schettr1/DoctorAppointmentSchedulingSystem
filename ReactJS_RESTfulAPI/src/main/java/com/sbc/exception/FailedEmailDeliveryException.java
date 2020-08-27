package com.sbc.exception;

public class FailedEmailDeliveryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedEmailDeliveryException(String message) {
		super(message);
	}
}
