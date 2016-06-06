package com.codernb.exception;

@SuppressWarnings("serial")
public class NoEntryException extends RuntimeException {

	public NoEntryException() {
		super();
	}

	public NoEntryException(Exception e) {
		super(e);
	}

	public NoEntryException(String message) {
		super(message);
	}

}
