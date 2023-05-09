package com.nsdl.otpManager.enumeration;

public enum ErrorConstant {
	
	OTP_EXPIRED("ERR-OT-0001", "OTP Expired"),
	OTP_INVALID("ERR-OT-0002", "Enter correct OTP"),
	USR_NOT_EXIST("ERR-OT-0003", "User Does Not Exist"),
	MAX_ATTEMPT_EXCEED("ERR-OT-0004", "Maximum number of verification attempts exceeded"),
	REQUEST_VALIDATION_FAILED("ERR-OT-0005", "Request Validation Faild due to bad request"),
	BLANK_EMAIL_OTP("ERR-OT-0006", "Email OTP not found"),
	BLANK_MOBILE_OTP("ERR-OT-0007", "Mobile OTP not found"),
	INVALID_INVITE_MODE("ERR-OT-0008", "Invite Mode is blank or null"),
	INVALID_CONTENT("ERR-OT-0009", "content is blank or null"),
	INVALID_USER("ERR-OT-0010", "Enter valid User ID"),
	INVALID_EMAIL("ERR-OT-0011", "Enter valid Email"),
	INVALID_TEMPLATE("ERR-OT-0012", "Template type is blank or null"),
	INVALID_PARAMETER("ERR-OT-0013", "Error generating OTP, please try again."),
	INVALID_USER_ROLE("ERR-OT-0014", "Valid User details required."),
	INVALID_MOBILE("ERR-OT-0015", "Enter valid Mobile No"),
	TEMPLATE_NOT_FOUND_IN_DB("ERR-OT-0016", "Template not found in database."),
	BAD_PARAMETER("ERR-OT-0017", "Enter valid Template Type/Send type"),
	SOMETHING_WENT_WRONG("ERR-OT-0018","Something went wrong"),
	MISSING_PASSWORD("ERR-OT-0019","System generated password is missing in request"),
	MISSING_REJECT_REASON("ERR-OT-0020","Reject reason is missing in request"),
	MISSING_APPOINTMENT_DETAILS("ERR-OT-0021","Appointment Date/Appointment time is missing in request"),
	INVALID_REQUEST("ERR-OT-0022", "Invalid Request");
	
	private String code;
	private String message;
	private ErrorConstant(String code, String message) {
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
