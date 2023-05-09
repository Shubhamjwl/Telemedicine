package com.nsdl.telemedicine.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareContextResponseDto {

    private String careContextId;
	
	private String displayName;
	
    private String patientId;
    
    private String patientName;
	
	private String mobileNo;
	
	private String healthId;
	
	private String healthNo;
	
	private String aadhaarNo;
	
	
}
