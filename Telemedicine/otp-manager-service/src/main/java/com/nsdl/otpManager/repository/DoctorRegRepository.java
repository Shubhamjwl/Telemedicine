package com.nsdl.otpManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.otpManager.entity.DoctorRegDtlsEntity;



public interface DoctorRegRepository extends JpaRepository<DoctorRegDtlsEntity, Integer>{
	public DoctorRegDtlsEntity findByDocUserIdIgnoreCase(String userId);
	
	public DoctorRegDtlsEntity findByDrdAssociationNumber(String drdAssociationNumber);
}
