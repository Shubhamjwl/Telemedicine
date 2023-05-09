package com.nsdl.telemedicine.consultancy.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
@Data
public class SavePatientReportRequestDTO {

	@NotEmpty(message = "Appointment ID cann't be Null")
	private String appId;
	
}


