package com.nsdl.telemedicine.patient.dto;

import lombok.Data;

@Data
public class MedicalDetailDto {

	private String allergies;
	private String chronicdiseases;
	private String  currentMedication;
	private String pastMedication;
	private String   injuries;
	private String surgries;

}
