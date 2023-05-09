package com.nsdl.telemedicine.videoConference.constant;



public enum ErrorConstants {

	ERROR_FETCHING_PATIENT_DATA("ERR-CON-001", "Error Fetching Patient Data"),
	FUNCTION_ID_NOT_FOUND("ERR-USR-001","Function ID not found"), 
	FUNCTION_NOT_ACTIVE("ERR-USR-002","Function is not active"), 
	LIST_OF_DOCTOR_NOT_FOUND("ERR-USR-010","Doctor's are not available to verify"), 
	DOCTOR_NOT_FOUND("ERR-USR-011","Doctor is not available to verify with the given docName"),
	FUNCTION_NOT_AVAILABLE("ERR-USR-003","No function is available"),
	
	//Authentication Error Constant
	USER_NOT_LOGIN("VCA-001","User is not Logged in"),
	USER_NOT_ACTIVE("VCA-002","User is not Active"),
	INVALID_USER_ERROR("VCA-003","Please enter valid Username"),
	APPOINTMENT_NOT_FOUND("VCA-004","Appointment Details not found"),
	INVALID_APPOINTMENT("VCA-005","Appointment date/time is invalid"),
	UNABLE_TO_GENERATE_TOKEN("VCA-006","Error generating tokenfor Videocall, please try again."),
	INVALID_TOKEN("VC-007","Invalid Token"),
	VERIFICATION_FAIL("VC-008","Token Verification failed"),
	TOKEN_EXPIRED("VC-009","Token Expired"),
	INVALID_REQUEST("VC-010","Please Enter correct payload and token");
	
	
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
