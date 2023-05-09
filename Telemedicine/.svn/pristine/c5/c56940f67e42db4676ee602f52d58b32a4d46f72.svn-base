package com.nsdl.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateUserDetailsRequest {
	@NotBlank(message = "Please enter valid User id")
	private String userId;
	@Size(max = 99 , message = "Full Name must be an alphabetic value between 1 and 99 characters and cannot be empty")
	@Pattern(regexp = "^[A-Za-z]([\\']?[A-Za-z]+)*( [A-Za-z]([\\']?[A-Za-z]+)*)*$" , message = "Full Name must be an alphabetic value")
	private String userFullName;
	private Long mobileNumber;
	private String email;
	@Size(max = 30, message = "SMC Number length cannnot be more than 30 Digits")
	private String smcNumber;
	@Size(max = 30, message = "MCI Number length cannnot be more than 30 Digits")
	private String mciNumber;
}
