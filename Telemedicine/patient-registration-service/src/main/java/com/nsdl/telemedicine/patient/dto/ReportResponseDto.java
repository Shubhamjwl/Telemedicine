package com.nsdl.telemedicine.patient.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReportResponseDto {

	private PatientRegDetailsDTO patient;	
	
	private List<CareContextResponse> careContextDetails;
	
}
