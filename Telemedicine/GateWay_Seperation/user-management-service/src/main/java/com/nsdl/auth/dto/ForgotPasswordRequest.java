package com.nsdl.auth.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
	@NotBlank(message = "Enter valid userId")
	private String userId;
	
	private String otp;
}
