package com.nsdl.telemedicine.consultancy.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PrescriprionVerifyRequestDTO {

	@NotEmpty(message = "Appointment ID cann't be Null")
	private String appId;
	
	@NotEmpty(message = "status cann't be Null")
	private String status;
	
}
