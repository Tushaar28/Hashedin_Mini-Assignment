package com.tushaar.miniassignment.exceptions;

public class UnauthorizedException extends RuntimeException {
	
	public UnauthorizedException(String string) {
		super(string);
	}

	private static final long serialVersionUID = 1L;
}
