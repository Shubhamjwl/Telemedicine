package com.nsdl.telemedicine.videoConference.exception;

import org.springframework.validation.Errors;

public class RequestEntityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Errors errors;

	public RequestEntityException(Errors errors) {
		this.errors = errors;
	}

	public Errors getErrors() {
		return errors;
	}
}
