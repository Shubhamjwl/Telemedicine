package com.nsdl.telemedicine.doctor.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class DeRegisterDoctorReqDTO {
	
	@NotEmpty(message = "doctor name cann't be null")
	private String docName;
	
	@NotEmpty(message = "action cann't be null")
	private String action;
	
}
