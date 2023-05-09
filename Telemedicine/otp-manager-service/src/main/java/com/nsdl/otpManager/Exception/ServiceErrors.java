package com.nsdl.otpManager.Exception;
/**
 * @author Pegasus_swatis
 *
 */
public class ServiceErrors {

	private String errorCode;

	private String message;

	public ServiceErrors(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.message = errorMessage;
	}

	public ServiceErrors() {

	}

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
