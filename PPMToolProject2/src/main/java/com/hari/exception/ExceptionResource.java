package com.hari.exception;

import java.util.Date;

public class ExceptionResource {

	private final Date timeStamp;
	private final String message;
	private final String details;

	public ExceptionResource(Date timeStamp, String message, String details) {
		super();
		this.timeStamp = timeStamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
