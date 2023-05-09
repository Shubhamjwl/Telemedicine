package com.nsdl.telemedicine.scribe.exception;



public class ScribeRegException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 125881260791066519L;
	
	private String errorCode;
	
	private String errorMsg;
	
	public ScribeRegException() {}
	
	public ScribeRegException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public String getErrorMsg() {
		return errorMsg;
	}
	

}
