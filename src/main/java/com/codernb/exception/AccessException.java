package com.codernb.exception;

@SuppressWarnings("serial")
public class AccessException extends RuntimeException {
	
	public AccessException() {
		super();
	}
	
	public AccessException(Exception e) {
		super(e);
	}

}
