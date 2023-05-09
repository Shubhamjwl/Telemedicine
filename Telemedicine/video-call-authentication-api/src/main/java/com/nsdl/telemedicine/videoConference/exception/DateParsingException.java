package com.nsdl.telemedicine.videoConference.exception;

public class DateParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ServiceError error;

	public DateParsingException(ServiceError error) {
		this.error = error;
	}

	public ServiceError getError() {
		return error;
	}
}
