package com.nsdl.telemedicine.consultancy.service;

import com.nsdl.telemedicine.consultancy.dto.PatientDtls;

public interface PatientService {

	public PatientDtls getPatientDetails(String appointmentID, String drRegID, String ptRegID);
	
	public void saveConsultationHistory(String drUserId ,String sessionId, String slotId,String appointID, String patientId);
}
