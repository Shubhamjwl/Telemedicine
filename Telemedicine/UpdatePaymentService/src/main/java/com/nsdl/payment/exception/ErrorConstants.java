package com.nsdl.payment.exception;

public enum ErrorConstants {
	
	INVALID_REQUEST("UPS-001","Transaction Id is blank or null"),
	RECORD_MISMATCH("UPS-002","Record updation failed due to mismatch in requested transactionId and received transactionId"),
	PAYMENT_RECORD_NOT_FOUND("UPS-003","Payment record not found in db for gven transaction id"),
	USER_NOT_LOGIN("UPS-004","User is not Logged in"),
	USER_NOT_ACTIVE("UPS-005","User is not Active"),
	INVALID_USER_ERROR("UPS-006","Please enter valid Username"),
	
	PAYMENT_SUCCESS("PD-004","Payment Completed Successfully!!"),
	PAYMENT_FAIL("PD-005","Payment Fail"),
	SIGNATURE_VERIFICATION_FAIL("PD-006","Signature not verified"),
	UNABLE_TO_COMPLETE_PAYMENT("PD-007","Unable to complete payment"),
	TECHNICAL_EXCEPTION("PD-008","Technical Error Occurred.Please try again later"),
	PAST_DATE_TIME_CANNOT_BOOKED("PD-009", "Past date time can't be booked"),
	DOCTOR_DETAILS_NOT_PRESENT("PD-010", "Doctor details not found");
	
	
	
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
