package com.nsdl.captcha.constant;



public enum Constants {

	VERIFY_CAPTCHA_SUCCESS("CAP-0001", "Captcha verified successfully"),
	VERIFY_CAPTCHA_FAILED("CAP-0002","Invalid Captcha : Captcha verification failed"), 
	CAPTCHA_EXPIRED("CAP-0003","Captcha has expired"), 
	SESSION_NOT_FOUND("CAP-0004","Invalid Session Id : Captcha Session id not Found");
	
	
 	private String code;
	private String message;
	private Constants(String code, String message) {
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
