package com.nsdl.telemedicine.consultancy.dto;

import java.util.List;

import lombok.Data;

@Data
public class PtMedicalDtls {

	private List<String> allergies;
	
	private List<String> chronicDiseases;
	
}
