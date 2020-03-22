package com.hari.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ProjectResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ProjectNotFoundException.class)
	public final ResponseEntity<ExceptionResource> handleUserNotFoundException(ProjectNotFoundException ex,
			WebRequest request) {
		final ExceptionResource exceptionResource = new ExceptionResource(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResource, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BackLogNotFoundException.class)
	public final ResponseEntity<ExceptionResource> handleBackLogNotFoundException(BackLogNotFoundException ex,
			WebRequest request) {
		final ExceptionResource exceptionResource = new ExceptionResource(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResource, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValdiationFailedException.class)
	public final ResponseEntity<ExceptionResource> handleValidationFailedException(ValdiationFailedException ex,
			WebRequest request) {
		final ExceptionResource exceptionResource = new ExceptionResource(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResource, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResource> handleAllExceptions(Exception ex, WebRequest request) {
		final ExceptionResource exceptionResource = new ExceptionResource(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResource, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
