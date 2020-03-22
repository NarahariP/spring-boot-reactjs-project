package com.hari.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BackLogNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BackLogNotFoundException(String messaage) {
		super(messaage);
	}

}
