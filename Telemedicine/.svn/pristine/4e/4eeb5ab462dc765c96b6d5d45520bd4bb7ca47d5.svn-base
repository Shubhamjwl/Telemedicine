package com.nsdl.otpManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.otpManager.entity.PatientPersonalDetailEntity;

public interface PatientRegRepository extends JpaRepository<PatientPersonalDetailEntity, Integer>{
	public PatientPersonalDetailEntity findByPtUserIDIgnoreCase(String ptUserID);

}
