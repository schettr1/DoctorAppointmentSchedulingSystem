package com.sbc.controller_advice;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sbc.exception.AppointmentNotFoundException;
import com.sbc.exception.DoctorNotFoundException;
import com.sbc.exception.DuplicateUsernameException;
import com.sbc.exception.EmployeeNotFoundException;
import com.sbc.exception.ExceptionMessage;
import com.sbc.exception.FeedbackNotFoundException;
import com.sbc.exception.ForbiddenException;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	private static final int CODE_NOT_FOUND = 404;
	private static final int CODE_BAD_REQUEST = 400;
	private static final int UNAUTHORIZED = 401;   // 401 is handled by CustomBasicAuthenticationEntryPoint
	private static final int FORBIDDEN = 403;
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public final ResponseEntity<Object> handleEmployeeNotFoundException(EmployeeNotFoundException e) {
		ExceptionMessage message = new ExceptionMessage(CODE_NOT_FOUND, HttpStatus.NOT_FOUND, new Date(), e.getMessage());
		// return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);	
	}
	
	@ExceptionHandler(DoctorNotFoundException.class)
	public final ResponseEntity<Object> handleDoctorNotFoundExceptionException(DoctorNotFoundException e) {
		ExceptionMessage message = new ExceptionMessage(CODE_NOT_FOUND, HttpStatus.NOT_FOUND, new Date(), e.getMessage());
		// return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);	
	}
	
	@ExceptionHandler(FeedbackNotFoundException.class)
	public final ResponseEntity<Object> handleFeedbackNotFoundExceptionException(FeedbackNotFoundException e) {
		ExceptionMessage message = new ExceptionMessage(CODE_NOT_FOUND, HttpStatus.NOT_FOUND, new Date(), e.getMessage());
		// return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);	
	}
	
	@ExceptionHandler(AppointmentNotFoundException.class)
	public final ResponseEntity<Object> handleAppointmentNotFoundException(AppointmentNotFoundException e) {
		ExceptionMessage message = new ExceptionMessage(CODE_BAD_REQUEST, HttpStatus.BAD_REQUEST, new Date(), e.getMessage());
		// return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);	
	}
	
	@ExceptionHandler(ForbiddenException.class)
	public final ResponseEntity<Object> handleForbiddenException(ForbiddenException e) {
		ExceptionMessage message = new ExceptionMessage(FORBIDDEN, HttpStatus.FORBIDDEN, new Date(), e.getMessage());
		// return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);	
	}
	
	@ExceptionHandler(DuplicateUsernameException.class)
	public final ResponseEntity<Object> handleDuplicateUsernameException(DuplicateUsernameException e) {
		ExceptionMessage message = new ExceptionMessage(CODE_BAD_REQUEST, HttpStatus.BAD_REQUEST, new Date(), e.getMessage());
		// return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);	
	}
}
