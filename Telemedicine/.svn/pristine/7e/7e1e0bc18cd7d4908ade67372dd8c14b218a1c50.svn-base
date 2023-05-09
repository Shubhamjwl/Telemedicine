package com.nsdl.telemedicine.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.PatientReportUploadDtlsEntity;

@Repository
public interface PatientReportUploadDtlsRepository extends JpaRepository<PatientReportUploadDtlsEntity, Integer>{
	
	@Query(value = "SELECT * FROM appointment.appt_ul_rprt_dtls c where c.aurd_appt_id_fk =:adApptId", nativeQuery = true)
	public PatientReportUploadDtlsEntity getUploadReportDtls(@Param("adApptId") String adApptId);
	
}
