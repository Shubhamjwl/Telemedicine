package com.nsdl.captcha.exception;

public class DataParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ServiceError error;

	public DataParsingException(ServiceError error) {
		this.error = error;
	}

	public ServiceError getError() {
		return error;
	}
}
