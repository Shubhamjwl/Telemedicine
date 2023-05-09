package com.nsdl.user.exception;

public enum UserErrorConstant {

	INTERNAL_SERVER_ERROR("NOT-EXP-001", "Something went wrong, try again later"),
	ANOTHER_MOBILEID_WHITELISTED("NOT-EXP-002", "Another mobile id whitelisted for given UID"),
	MOBILE_ID_NOT_PRESENT("NOT-EXP-003", "mobile id not present"),
	MOBILE_ID_ALREADY_PRESENT("NOT-EXP-004", "mobile id already present"),
	PARSE_ERROR("NOT-EXP-005", "parse error");

	private final String errorCode;

	private final String errorMessage;

	private UserErrorConstant(final String errorCode, final String errorMessage) {
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
