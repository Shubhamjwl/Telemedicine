package com.nsdl.telemedicine.scribe.dto;

import lombok.Data;

@Data
public class OtpRequestDTO {

	
	 private String userId;
	 private String mobileNo;
	 private String emailID;
	 private String sendType;
	 private String otpGenerateTpye;
	 private String otpFor;
}
