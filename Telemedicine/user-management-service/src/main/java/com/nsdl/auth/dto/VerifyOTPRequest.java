package com.nsdl.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VerifyOTPRequest {

	@NotNull(message = "userId must not be null")
	@NotBlank(message = "Please provide userId")
	private String userId;
	private String mobileOTP;
	private String emailOTP;
	private String userRole;
	
	
    


}
