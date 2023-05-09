package com.nsdl.otpManager.dto;


import lombok.Data;


@Data
public class OTPRequest 	{
	
	private String userId;
	private String mobileNo;
	private String emailID;
	private String sendType;
	private String otpGenerateTpye;
	private String otpFor;
	private String emailOtp;
	private String smsOtp;
	


}
