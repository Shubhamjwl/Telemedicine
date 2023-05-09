package com.nsdl.telemedicine.consultancy.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PrescriptionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String drFullName;
	private String appointmentID;
	private String appointmentDate;
	private String appointmentTimme;
	private String patientName;
	private String patientMobNo;
	private String patientEmailID;
	private PrescriptionDtlsDTO prescriptionDtls;

}
