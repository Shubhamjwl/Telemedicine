package com.nsdl.telemedicine.patient.dto;

import lombok.Data;

@Data
public class TokenDetailsDTO {
	private String docUserID;
	private String patientName;
	private Long ptMobileNo;
	
}
