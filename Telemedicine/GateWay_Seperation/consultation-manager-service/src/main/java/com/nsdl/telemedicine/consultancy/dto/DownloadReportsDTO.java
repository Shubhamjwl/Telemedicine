package com.nsdl.telemedicine.consultancy.dto;

import java.util.List;

import lombok.Data;

@Data
public class DownloadReportsDTO<T> {

	
	private List<PatientReportDTO> reports;
}
