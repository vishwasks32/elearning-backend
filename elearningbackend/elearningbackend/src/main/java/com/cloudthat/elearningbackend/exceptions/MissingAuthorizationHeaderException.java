package com.cloudthat.elearningbackend.exceptions;

public class MissingAuthorizationHeaderException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingAuthorizationHeaderException(String message) {
        super(message);
    }
}