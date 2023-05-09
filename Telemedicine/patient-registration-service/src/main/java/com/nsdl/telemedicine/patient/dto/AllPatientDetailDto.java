package com.nsdl.telemedicine.patient.dto;

import javax.validation.Valid;

import lombok.Data;

@Data
public class AllPatientDetailDto {
	
	@Valid
	private PersonalDetailDto ptPersonalDtls;
	
	@Valid
	private MedicalDetailDto ptMedicalDtls;
	
	@Valid
	private LifeStyleDetailDto ptLifeStyleDtls;
}
