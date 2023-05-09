package com.nsdl.telemedicine.consultancy.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ConsultationDTO<T> {

	@NotBlank(message = "AppointID cannot be null or blank")
	private String appointID;

	@NotBlank(message = "TabID cannot be null or blank")
	private String tabID;

	@NotEmpty(message = "Data cannot be null or empty")
	@Valid
	private List<T> data;
	
	private List<PatientReportUploadDtlsDTO> patientReportsData;

}
