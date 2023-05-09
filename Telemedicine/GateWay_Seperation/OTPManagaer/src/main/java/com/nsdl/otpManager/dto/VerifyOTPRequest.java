package com.nsdl.otpManager.dto;

import lombok.Data;

@Data
public class VerifyOTPRequest {
	
	

private String userId;
private String mobileOTP;
private String emailOTP;
private String userRole;

}
