package com.nsdl.otpManager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPResponse {
	
	 
	 private String message;
     private String description;
     
     public OTPResponse() {}
	public OTPResponse(String message, String description) {
		super();
		this.message = message;
		this.description = description;
	}


}
