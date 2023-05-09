package com.nsdl.telemedicine.patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.PatientCareContextEntity;

@Repository
public interface PatientCareContextRepo extends JpaRepository<PatientCareContextEntity, String> {
	
	@Query(value = "select * from public.patient_care_contexts where health_no =:healthId OR health_id=:healthId" , nativeQuery = true)
	List<PatientCareContextEntity> getDetailsByHealthId(@Param("healthId") String healthId);
}
