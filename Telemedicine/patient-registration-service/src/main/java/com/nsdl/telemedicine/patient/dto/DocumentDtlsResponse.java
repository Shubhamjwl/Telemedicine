package com.nsdl.telemedicine.patient.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class DocumentDtlsResponse {

	private Long docId;
	private String documentName;
	private String documentType;
	private String documentPath;
	private LocalDateTime uploadDate;
	private String uploadedBy;
	
}
