package com.nsdl.telemedicine.patient.service;

import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.patient.entity.PatientLifestyleDetailsEntity;
import com.nsdl.telemedicine.patient.entity.PatientMedicalDetailsEntity;
import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;

@Service
public interface AuditPatientService {

	boolean auditRegistrationServicePersonalDetail(PatientPersonalDetailEntity entity, String userId);
	
	public boolean auditRegistrationServiceMedicalDetail(PatientMedicalDetailsEntity entity, String userId);
	
	public boolean auditRegistrationServiceLifeStyleDetail(PatientLifestyleDetailsEntity entity, String userId);

}
