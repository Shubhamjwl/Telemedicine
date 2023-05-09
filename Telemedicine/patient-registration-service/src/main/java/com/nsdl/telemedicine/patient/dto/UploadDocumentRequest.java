package com.nsdl.telemedicine.patient.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UploadDocumentRequest {
	
	@NotEmpty(message = "Document Type cannot be null or empty")
	private String docType;
	
	@NotEmpty(message = "Document Name cannot be null or empty")
	private String docName;
	
	@NotEmpty(message = "Document Data cannot be null or empty")
	private String docData;

}
