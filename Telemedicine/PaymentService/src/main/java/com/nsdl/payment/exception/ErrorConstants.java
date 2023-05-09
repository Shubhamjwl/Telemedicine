package com.nsdl.payment.exception;

public enum ErrorConstants {
	
	RECORD_NOT_FOUND("PD-001","Record not found against this transaction id"),
	UNABLE_TO_CREATE_ORDER("PD-002","Unable to create order"),
	RECORD_UPDATED_SUCCESSFULLY("PD-003","Record updated Successfully"),
	PAYMENT_SUCCESS("PD-004","Payment Completed Successfully!!"),
	PAYMENT_FAIL("PD-005","Payment Fail"),
	SIGNATURE_VERIFICATION_FAIL("PD-006","Signature not verified"),
	UNABLE_TO_COMPLETE_PAYMENT("PD-007","Unable to complete payment"),
	TECHNICAL_EXCEPTION("PD-008","Technical Error Occurred.Please try again later"),
	INVALID_AMOUNT("PD-009","Amount cannot be null or zero"),
	INVALID_MOBILE("PD-010","Customer mobile number cannot be null/blank"),
	INVALID_WALLET_AMOUNT("PD-011","You cannot use more then your wallet amount"),
	INVALID_CONSULT_AMOUNT("PD-012","You cannot use wallet amount more then consultation fee.");
	/*
	 * PAST_DATE_TIME_CANNOT_BOOKED("PD-009", "Past date time can't be booked"),
	 * DOCTOR_DETAILS_NOT_PRESENT("PD-010", "Doctor details not found");
	 */
	
	
	
	
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
