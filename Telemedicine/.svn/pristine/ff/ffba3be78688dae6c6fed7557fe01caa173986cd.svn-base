package com.nsdl.telemedicine.master.constant;

public enum ErrorConstants {

	SOMETHING_WENT_WRONG("ERR-MST-001", "Something went wrong, Please contact admin."),
	MASTER_ALREADY_AVAILABLE("ERR-MST-002", "Master already available."),
	FAIL_TO_SAVE_RECORD("ERR-MST-003", "Failed to save records "),
	MASTER_SAVE("ERR-MST-004", "Master saved successfully "),
	REDFLAG_URL_NOT_FOUND("ERR-MST-004", "red flag url not found for requested data.");
	
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
