package com.nsdl.telemedicine.doctor.dto;

import lombok.Data;

@Data
public class DoctorDetailsFetchRequestDTO {
	
	//@NotEmpty(message = "Doctor name cann't empty")
	private String docName;
}
