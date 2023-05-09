package com.nsdl.telemedicine.consultancy.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PrescriptionRequestDTO {
	
	@NotEmpty(message = "Doctor name cann't be Null")
	private String regDocName;
}
