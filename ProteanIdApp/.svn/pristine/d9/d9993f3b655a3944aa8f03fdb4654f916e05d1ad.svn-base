package com.nsdl.authenticate.constant;

public enum ErrorConstants {
	
	UID_NOT_FOUND("ERR-code-001", "UID not Found"),
	UID_STATUS("ERR-code-002", "UID not activated"),
	TRY_AGAIN("ERR-code-003", "Try again later"),
	OTP_FAILED("ERR-code-004", "OTP verification Failed Try again"),
	OTP_EXPIRED("ERR-code-005","OTP is expired try again");
	


	private String code;
	private String message;

	private ErrorConstants(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
