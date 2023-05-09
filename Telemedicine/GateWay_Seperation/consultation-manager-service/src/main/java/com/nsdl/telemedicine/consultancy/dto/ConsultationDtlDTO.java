package com.nsdl.telemedicine.consultancy.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * author : pegasus_girishk
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDtlDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ctAdvice;

	private String ctAppointmentStatus;

	private String ctApptId;

	private String ctChiefComplaints;

	private String ctDiagnosis;

	private String ctDoctorId;

	private String ctMedication;

	private String ctPatientId;

	private String ctPrescriptionPath;

	private String ctScribeId;
	
	private String handwrittenPrescription;
	
	private String templateHeader;
	
	private String templateFooter;
}