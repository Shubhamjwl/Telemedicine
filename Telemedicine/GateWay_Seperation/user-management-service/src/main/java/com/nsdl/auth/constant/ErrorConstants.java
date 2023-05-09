package com.nsdl.auth.constant;



public enum ErrorConstants {

	ERROR_FETCHING_PATIENT_DATA("ERR-CON-001", "Error Fetching Patient Data"),
	FUNCTION_ID_NOT_FOUND("ERR-USR-001","Function ID not found"), 
	FUNCTION_NOT_ACTIVE("ERR-USR-002","Function is not active"), 
	LIST_OF_DOCTOR_NOT_FOUND("ERR-USR-010","No records available for verification"), 
	DOCTOR_NOT_FOUND("ERR-USR-011","Doctor is not available to verify with the given docName"),
	FUNCTION_NOT_AVAILABLE("ERR-USR-003","No function is available");
	
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
