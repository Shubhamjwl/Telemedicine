package com.nsdl.telemedicine.consultancy.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChiefComplaintsDtls implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Symptoms cannot be null or blank")
	private String symptoms;

	@NotBlank(message = "Duration cannot be null or blank")
	private String duration;

	@NotBlank(message = "Severity cannot be null or blank")
	private String severity;

	private String note;

	public ChiefComplaintsDtls() {
		super();
	}
	
	public ChiefComplaintsDtls(String symptoms, String duration, String severity, String note) {
		super();
		this.symptoms = symptoms;
		this.duration = duration;
		this.severity = severity;
		this.note = note;
	}

}