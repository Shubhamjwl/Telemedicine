package com.nsdl.telemedicine.gateway.constant;

public enum RequestErrorCode {

	REQUEST_DATA_NOT_VALID("TIN-SRV-999", "Invalid request input"),
	INTERNAL_SERVER_ERROR("TIN-SRV-500", "Internal server error");

	private final String errorCode;
	private final String errorMessage;

	private RequestErrorCode(final String errorCode, final String errorMessage) {
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