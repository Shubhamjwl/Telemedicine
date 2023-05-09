package com.nsdl.login.exception;

public enum LoginErrorConstant {

	INTERNAL_SERVER_ERROR("KER-ATH-001", "Something went wrong, try again later"),
	CANNOT_CONNECT_TO_AUTH_SERVICE("KER-ATH-002","Problem in connecting to auth service"),
	MOBILE_ID_NOT_PRESENT("NOT-EXP-003", "mobile id not present"),
	INVALID_CREDENTIALS("KER-ATH-001", "Invalid Credentials");

	private final String errorCode;

	private final String errorMessage;

	private LoginErrorConstant(final String errorCode, final String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
