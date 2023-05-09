package com.nsdl.gupshup.sms.constants;

import lombok.Getter;

public enum GupshupServiceConstants {
	
	SMS_HTTP_SUCCESS_REQUEST("success","SMS sent successfully"),
	SMS_HTTP_FAIL_REQUEST("fail", "SMS sending failed");
	
	private GupshupServiceConstants(String httpCode, String httpMsg) {
		this.httpCode = httpCode;
		this.httpMsg = httpMsg;
	}
	
	@Getter
	private String method;
	
	@Getter
	private String sendTo;
	
	@Getter
	private String msg;
	
	@Getter
	private String msgType;
	
	@Getter
	private String userId;
	
	@Getter
	private String authScheme;
	
	@Getter
	private String password;
	
	@Getter
	private String versionValue;
	
	@Getter
	private String formatValue;
	
	@Getter
	private String methodValue;
	
	@Getter
	private String msgTypeValue;
	
	@Getter
	private String userIdValue;
	
	@Getter
	private String authSchemeValue;
	
	@Getter
	private String passwordValue;
	
	@Getter
	private String version;
	
	@Getter
	private String format;
	
	@Getter
	private String httpCode;
	
	@Getter
	private String httpMsg;

}
