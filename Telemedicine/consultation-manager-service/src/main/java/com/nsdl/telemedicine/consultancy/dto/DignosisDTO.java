package com.nsdl.telemedicine.consultancy.dto;

public class DignosisDTO {
	private String diagnosis;

	public DignosisDTO(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

}
