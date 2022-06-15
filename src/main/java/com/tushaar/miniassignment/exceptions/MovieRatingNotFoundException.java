package com.tushaar.miniassignment.exceptions;

public class MovieRatingNotFoundException extends RuntimeException {
	public MovieRatingNotFoundException(String string) {
		super(string);
	}
	
	private static final long serialVersionUID = 1L;

}
