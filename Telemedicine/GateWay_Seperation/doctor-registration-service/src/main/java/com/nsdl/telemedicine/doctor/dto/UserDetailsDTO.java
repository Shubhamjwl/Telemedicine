package com.nsdl.telemedicine.doctor.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDetailsDTO {
	
	@NotNull(message="userId can not be null")
	@Valid
    private String userId;
    
	@NotNull(message="userId can not be null")
	@Valid
	private String userFullName;
	
	@NotNull(message="userId can not be null")
	@Valid
	private Long mobileNumber;
	
//	@NotNull(message="userId can not be null")
//	@Valid
	private String email;
	
	@NotNull(message="userId can not be null")
	@Valid
	private String smcNumber;
	
	@NotNull(message="userId can not be null")
	@Valid
	private String mciNumber;

}
