package com.nsdl.telemedicine.consultancy.dto;

import lombok.Data;

@Data
public class PatientDtls {

	private String apptId;
	
	private String height;

	private String weight;

	private String bloodgrp;

	private PtPersonalDetals ptPersonalDetals;

	private AddressDTO address;

	private PtMedicalDtls ptMedicalDtls;

	private PtLifeStyleDtls ptLifeStyleDtls;

}
