package com.nsdl.telemedicine.doctor.dto;

import java.io.Serializable;

public class ExceptionJSONInfoDTO implements Serializable {
	private static final long serialVersionUID = 3999014525078508265L;

	/**
	 * Error Code
	 */
	private String errorCode;

	/**
	 * Error Message
	 */
	private String message;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
