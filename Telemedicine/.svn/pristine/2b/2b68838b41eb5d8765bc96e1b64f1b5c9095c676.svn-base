package com.nsdl.telemedicine.patient.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.patient.entity.AuditPatientLifestyleDetailsEntity;
import com.nsdl.telemedicine.patient.entity.AuditPatientMedicalDetailsEntity;
import com.nsdl.telemedicine.patient.entity.AuditPatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.entity.PatientLifestyleDetailsEntity;
import com.nsdl.telemedicine.patient.entity.PatientMedicalDetailsEntity;
import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.repository.AuditPatientPersonalDetailsRepository;
import com.nsdl.telemedicine.patient.service.AuditPatientService;
@Service
public class AuditPatientServiceImpl implements AuditPatientService {

	@Autowired
	AuditPatientPersonalDetailsRepository auditRepo;

	@Override
	public boolean auditRegistrationServicePersonalDetail(PatientPersonalDetailEntity entity, String userId) {
		AuditPatientPersonalDetailEntity auditEntity = new AuditPatientPersonalDetailEntity();
		BeanUtils.copyProperties(entity, auditEntity);
		auditEntity.setAudUserId(userId);
		auditRepo.saveAndFlush(auditEntity);
		return false;
	}
	
	@Override
	public boolean auditRegistrationServiceMedicalDetail(PatientMedicalDetailsEntity entity, String userId) {
		AuditPatientMedicalDetailsEntity auditEntity = new AuditPatientMedicalDetailsEntity();
		BeanUtils.copyProperties(entity, auditEntity);
		auditEntity.setAudUserId(userId);
		auditRepo.saveAndFlush(auditEntity);
		return false;
	}
	
	@Override
	public boolean auditRegistrationServiceLifeStyleDetail(PatientLifestyleDetailsEntity entity, String userId) {
		AuditPatientLifestyleDetailsEntity auditEntity = new AuditPatientLifestyleDetailsEntity();
		BeanUtils.copyProperties(entity, auditEntity);
		auditEntity.setAudUserId(userId);
		auditRepo.saveAndFlush(auditEntity);
		return false;
	}
}
