package com.nsdl.telemedicine.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.nsdl.telemedicine.patient.entity.ConsultationDtl;


@Repository
public interface ConsulationPrescriptionDetailsRepo extends JpaRepository<ConsultationDtl, String> {
	
	@Query(value = "FROM ConsultationDtl where ctPrescriptionPath =:appID", nativeQuery = false)
	public ConsultationDtl getprecsriptionPath(@RequestParam String appID);
	
	@Query(value = "FROM ConsultationDtl where ctApptId =:appID", nativeQuery = false)
	public ConsultationDtl getPath(@RequestParam String appID);
	
}
