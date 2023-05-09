package com.nsdl.auth.exception;


import java.util.Arrays;
import java.util.List;

public class UserServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 767964083086679098L;

	private List<ServiceError> errors;

	public UserServiceException(List<ServiceError> errors) {
		super();
		this.errors = errors;
	}

	public UserServiceException(String errorCode, String errorMessage) {
		super();
		this.errors = Arrays.asList(new ServiceError(errorCode, errorMessage));
	}

//	public ConsultationServiceException(Errors errors) {
//		super();
//		this.errors = errors.getAllErrors().stream().map(error -> new ServiceError(error.getCode(), error.getDefaultMessage()))
//				.collect(Collectors.toList());
//	}

	public List<ServiceError> getError() {
		return errors;
	}

}
