package com.nsdl.telemedicine.master.constant;

public enum ErrorConstants {

	SOMETHING_WENT_WRONG("ERR-MST-001", "Something went wrong, Please contact admin."),
	MASTER_ALREADY_AVAILABLE("ERR-MST-002", "Master already available."),
	FAIL_TO_SAVE_RECORD("ERR-MST-003", "Failed to save records "),
	MASTER_SAVE("ERR-MST-004", "Master saved successfully "),;
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
