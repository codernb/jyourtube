package com.codernb.exception;

@SuppressWarnings("serial")
public class AlreadyExistsException extends RuntimeException {
	
	public AlreadyExistsException() {
		super();
	}
	
	public AlreadyExistsException(Exception e) {
		super(e);
	}

}
