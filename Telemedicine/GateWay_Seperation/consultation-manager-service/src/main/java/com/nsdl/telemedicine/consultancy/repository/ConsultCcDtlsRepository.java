package com.nsdl.telemedicine.consultancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.dto.ChiefComplaintsDtls;
import com.nsdl.telemedicine.consultancy.entity.ConsultCcDtlsEntity;

@Repository
public interface ConsultCcDtlsRepository extends JpaRepository<ConsultCcDtlsEntity, Long> {

	@Query("Select new com.nsdl.telemedicine.consultancy.dto.ChiefComplaintsDtls(c.ccdSymptom, c.ccdSympDuration, "
			+ "c.ccdSympSeverity, c.ccdSympNote) FROM ConsultCcDtlsEntity c WHERE c.appointmentDtlsEntity.adApptId = ?1 ORDER BY c.ccdCreatedTmstmp")
	public List<ChiefComplaintsDtls> findByCccdApptIdFk(String apptId);

	@Query("FROM ConsultCcDtlsEntity c WHERE c.ccdSymptom = ?1 AND c.appointmentDtlsEntity.adApptId = ?2")
	public ConsultCcDtlsEntity findAllByCcdSymptomAndCccdApptIdFk(String symptom, String apptId);
	
}
