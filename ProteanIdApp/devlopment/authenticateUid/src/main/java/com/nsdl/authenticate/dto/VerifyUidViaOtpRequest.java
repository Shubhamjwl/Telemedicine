package com.nsdl.authenticate.dto;

import lombok.Data;

@Data
public class VerifyUidViaOtpRequest {
	
	private String uid;
	private String otp;

}
