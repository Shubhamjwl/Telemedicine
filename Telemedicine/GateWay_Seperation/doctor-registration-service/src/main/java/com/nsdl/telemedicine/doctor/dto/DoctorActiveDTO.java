package com.nsdl.telemedicine.doctor.dto;

import lombok.Data;

@Data
public class DoctorActiveDTO {
	
	private String docUserID;
	
	private String docName;
	
	private String MICNO;
	
	private String SMCNO;
	
	private Long mobile;
	
	private String emailID;
	
	private String photopath;
	
}
