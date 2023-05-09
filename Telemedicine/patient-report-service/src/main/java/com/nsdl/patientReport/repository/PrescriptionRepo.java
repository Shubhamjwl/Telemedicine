package com.nsdl.patientReport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.patientReport.entity.ConsultPriscpDtl;

public interface PrescriptionRepo extends JpaRepository<ConsultPriscpDtl, Integer>{
	
	public ConsultPriscpDtl findByCpdApptIdFk(String cpdApptIdFk);

}
