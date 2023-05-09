package com.nsdl.otpManager.dto;

import lombok.Data;

@Data
public class RegistrationRequest {
	private String userId;
	private String email;
	private String mobile;
	
	private String smcNo;
	private String mciNo;
}
