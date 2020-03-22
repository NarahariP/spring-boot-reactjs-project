package com.hari.exception;

public class ValdiationFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValdiationFailedException(String messaage) {
		super(messaage);
	}
}
