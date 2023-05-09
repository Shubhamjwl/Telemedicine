package com.nsdl.telemedicine.consultancy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientReportUploadDtlsDTO {
	
	private String reportType;
	
	private String reportName;
	
	private String path;

}
