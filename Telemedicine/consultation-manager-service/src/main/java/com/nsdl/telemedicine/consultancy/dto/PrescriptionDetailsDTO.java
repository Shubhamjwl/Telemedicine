package com.nsdl.telemedicine.consultancy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDetailsDTO {
	
	private String info;
	
	private String pdfpath;
	
	private byte[] pdfData;
	
}
