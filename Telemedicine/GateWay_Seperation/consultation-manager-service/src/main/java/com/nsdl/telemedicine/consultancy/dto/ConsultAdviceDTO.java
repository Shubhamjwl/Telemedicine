package com.nsdl.telemedicine.consultancy.dto;

public class ConsultAdviceDTO {
	
	private String advice;
	
	private String note;
	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ConsultAdviceDTO() {
		
	}

	public ConsultAdviceDTO(String advice, String note) {
		this.advice = advice;
		this.note = note;
	}


	

	
	
}
