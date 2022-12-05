package com.app.stock.exception;

public class JwtTokenMissingException extends Exception {
	private static final long serialVersionUID = 1L;

	public JwtTokenMissingException(String msg) {
		super(msg);
	}

}