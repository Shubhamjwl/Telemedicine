package com.nsdl.telemedicine.patient.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.PatientReportUploadEntity;

@Repository
@Transactional
public interface PatientReportUploadRepo extends CrudRepository<PatientReportUploadEntity, String>{

	@Query(value = "SELECT * FROM appointment.patient_report_upload_dtls r WHERE r.prud_pt_user_id = ?1 ORDER BY r.prud_id_pk DESC",nativeQuery = true)
	List<PatientReportUploadEntity> getAllDocument(String ptUserID);

	@Query(value = "select * from appointment.patient_report_upload_dtls r where r.prud_id_pk= ?1 and r.prud_pt_user_id = ?2", nativeQuery = true)
	PatientReportUploadEntity findDocumentPath(Integer docId, String ptUserId);
	
}
