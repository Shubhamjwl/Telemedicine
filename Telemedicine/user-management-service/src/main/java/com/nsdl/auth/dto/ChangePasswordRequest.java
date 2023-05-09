package com.nsdl.auth.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChangePasswordRequest {
	
	@NotBlank(message = "Please enter valid user id")
	private String userId;
	@NotBlank(message = "Please enter valid old password")
	private String oldPwd;
	@NotBlank(message = "Please enter valid new  password")
	private String newPwd;
	@NotBlank(message = "Please enter valid confirm password")
	private String confirmPwd;
	
	
	
}
