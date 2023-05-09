package com.nsdl.auth.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AboutUsPdfRequest {
	
	@NotBlank(message = "Enter valid pdfName")
	private String pdfName;
}
