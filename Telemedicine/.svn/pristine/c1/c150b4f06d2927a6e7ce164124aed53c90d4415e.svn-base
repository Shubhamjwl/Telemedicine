package com.nsdl.telemedicine.patient.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PatientDetailsWithHealthIdDTO {
	private String patientName;
	private Long ptMobileNo;
	private String doctorUserID;
	private String ptProfilePhoto;
	private String ptEmailID;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ptDateOfBirth;

	private String healthId;

	private String healthNo;
	
	private String accessToken;
}
