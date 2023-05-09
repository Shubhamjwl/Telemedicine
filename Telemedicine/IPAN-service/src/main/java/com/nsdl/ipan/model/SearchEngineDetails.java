package com.nsdl.ipan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SearchEngineDetails {

@Id
	
	@Column(name = "DIAGNOSIS_ID")
	private Long diagnosis_id;

	@Column(name ="DIAGNOSIS")
	private String diagnosis;
	

	@Column(name = "SYMPTOMS_ID")
	private Long symptoms_id;

	@Column(name ="SYMPTOMS")
	private String symptoms;
	
	@Column(name = "MEDICINE_ID")
	private Long medicine_id;

	@Column(name ="MEDICINE")
	private String medicine;
	
	@Column(name = "ADVICE_ID")
	private Long advice_id;

	@Column(name ="ADVICE")
	private String advice;
	
	public Long getDiagnosis_id() {
		return diagnosis_id;
	}

	public void setDiagnosis_id(Long diagnosis_id) {
		this.diagnosis_id = diagnosis_id;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Long getSymptoms_id() {
		return symptoms_id;
	}

	public void setSymptoms_id(Long symptoms_id) {
		this.symptoms_id = symptoms_id;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public Long getMedicine_id() {
		return medicine_id;
	}

	public void setMedicine_id(Long medicine_id) {
		this.medicine_id = medicine_id;
	}

	public String getMedicine() {
		return medicine;
	}

	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	public Long getAdvice_id() {
		return advice_id;
	}

	public void setAdvice_id(Long advice_id) {
		this.advice_id = advice_id;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}
	
}