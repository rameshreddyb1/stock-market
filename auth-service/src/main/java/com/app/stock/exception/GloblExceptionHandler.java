package com.app.stock.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GloblExceptionHandler extends ResponseEntityExceptionHandler {
	
	LocalDateTime localDateTime = LocalDateTime.now();
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorType> showError(RuntimeException e) 
	{
		return new ResponseEntity<>(
				new ErrorType(e.getMessage(), "AUTH SERVICE"), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object>  badCredentialsException(BadCredentialsException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(), localDateTime,
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

}
