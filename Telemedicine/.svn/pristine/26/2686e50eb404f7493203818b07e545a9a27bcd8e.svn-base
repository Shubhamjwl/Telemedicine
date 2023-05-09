package com.nsdl.auth.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyDoctorRequestDTO {
	
	@NotEmpty(message = "Please provide valid registered Doctor User Id")
	private String regDocUserName;
	
	@NotEmpty(message = "Please provide isVerified flag")
	private String verificationStatusFlag;
	
	private String reason;
	
}
