package com.nsdl.telemedicine.constant;

public enum ErrorConstants {

	SOMETHING_WENT_WRONG("ERR-MST-001", "Something went wrong, Please contact admin."),
	REDFLAG_DATA_NOT_AVAILABLE("ER-MST-002", "RedFlag details not available for requested Data"),
	MARKET_PLACE_DTLS_NOT_SAVED("ERR-MST-003", "Market place details not saved"),
	PT_MOBILE_NOT_VALID("ERR-MST-004","Patient number must be numeric"),
	FILE_NOT_SAVED("ERR-MST-006","Prescription file not save"),
	ORDER_DTLS_NOT_SAVED("ERR-MST-005", "Order details not saved"),
	PT_MOBILE_LENGTH_NOT_VALID("ERR-MST-006","Patient number must be 10 digit"),
    NO_DATA_FOUND("ERR-MST-007","No data found for given transt Id"),
	PRESCRIPTION_FILE_INVALID("ERR-MST-008","Something went wrong while storing file"),
	SEARCH_fIELD_EMPTY("ERR-MST-009","Search fields can't be empty"),
	NO_SEARCH_DATA_FOUND("ERR-MST-009","No search data found");

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
