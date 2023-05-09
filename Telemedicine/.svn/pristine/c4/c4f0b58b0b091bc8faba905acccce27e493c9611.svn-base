package com.nsdl.telemedicine.patient.repository;

import java.time.LocalDateTime;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.AuditPatientLifestyleDetailsEntity;
import com.nsdl.telemedicine.patient.entity.AuditPatientMedicalDetailsEntity;
import com.nsdl.telemedicine.patient.entity.AuditPatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;

@Repository
public interface AuditPatientPersonalDetailsRepository extends JpaRepository<AuditPatientPersonalDetailEntity, Integer>{

	@Query(value = "SELECT * FROM registration.pt_reg_dtls r WHERE r.prd_user_id = ?1",nativeQuery = true)
	PatientPersonalDetailEntity findByPtUserID(String ptUserID);

	@Modifying
	@Transactional
	@Query(value = "UPDATE registration.pt_reg_dtls SET prd_height = ?1, prd_weight = ?2, prd_blood_grp = ?3, prd_dob = ?4, prd_address1 = ?5, prd_address2 =?6,  prd_address3 = ?7,  prd_modified_tmstmp = ?8   WHERE prd_user_id = ?9", nativeQuery = true)
	int updateByPtUserID(Double height, Double weight, String bloodgrp, Date dob, String address1,
			String address2, String address3, LocalDateTime now, String ptUserID);

	boolean existsByPtUserID(String ptUserID);

	void saveAndFlush(AuditPatientLifestyleDetailsEntity auditEntity);

	void saveAndFlush(AuditPatientMedicalDetailsEntity auditEntity);
}







