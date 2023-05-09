package com.nsdl.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CreateUserRequest {

	@NotBlank(message = "Enter valid Full Name")
	@Size(min = 1,max = 99 , message = "Full Name must be an alphabetic value between 1 and 99 characters and cannot be empty")
	@Pattern(regexp = "^[A-Za-z]([\\']?[A-Za-z]+)*( [A-Za-z]([\\']?[A-Za-z]+)*)*$" , message = "Full Name must be an alphabetic value")
	private String userFullName;
	@NotBlank(message = "Enter valid Login Id")
	//@Size(min = 8, max = 25, message = "User Id must be a alphanumeric value between 8 and 25 characters")
	@Pattern(regexp ="^([A-Za-z]{1}[A-Za-z0-9]{7,24})$", message = "User Id must be a alphabetic or alphanumeric value between 8 and 25 characters and cannot start with number")
	private String userId;
	@NotBlank(message = "Enter valid Password")
	private String password;
	@NotNull(message = "Mobile Number cannot be empty and must be a 10 digit valid numeric value")
	private Long mobileNumber;
//	@NotBlank(message = "Enter valid Email Id")
	private String email;
	@NotBlank(message = "Enter valid User Type")
	private String userType;
//	@NotBlank(message = "User Role cannot be empty")
//	private String role ;
	@Length(max = 30, message = "SMC Number length cannnot be more than 30 Digits")
	private String smcNumber;
	@Size(max = 30, message = "MCI Number length cannnot be more than 30 Digits")
	private String mciNumber;

}
