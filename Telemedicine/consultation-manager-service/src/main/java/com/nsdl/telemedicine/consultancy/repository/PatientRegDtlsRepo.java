package com.nsdl.telemedicine.consultancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;

public interface PatientRegDtlsRepo extends JpaRepository<PatientRegDtlsEntity, String>{

	@Query(value = "select * from registration.pt_reg_dtls where prd_user_id=:prdUserId", nativeQuery = true)
	public PatientRegDtlsEntity getPatientRegDtls(@Param("prdUserId") String prdUserId);
	
	
	
}
