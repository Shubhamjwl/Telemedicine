package com.nsdl.telemedicine.consultancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.PatientReportUploadDtlsEntity;

@Repository
public interface PatientReportUploadDtlsRepository extends JpaRepository<PatientReportUploadDtlsEntity, Integer>{
	
	@Query(value = "SELECT * FROM appointment.appt_ul_rprt_dtls c where c.aurd_appt_id_fk =:adApptId", nativeQuery = true)
	public List<PatientReportUploadDtlsEntity> findByAurdApptIdFk(@Param("adApptId") String adApptId);
	
}
