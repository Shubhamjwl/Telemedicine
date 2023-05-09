package com.nsdl.telemedicine.consultancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsdl.telemedicine.consultancy.entity.ConsultDiagDtlsEntity;

public interface ConsultDiagDtlsRepository extends JpaRepository<ConsultDiagDtlsEntity, Long> {

	// @Query(value = "FROM ConsultDiagDtlsEntity WHERE
	// appointmentDtlsEntity.adApptId = ?1 ")
	@Query(value = "SELECT cdd_id_pk,cdd_appt_id_fk,cdd_diagnosis,cdd_dr_user_id_fk,cdd_pt_user_id_fk,cdd_created_by,cdd_created_tmstmp,cdd_opti_version FROM appointment.consult_diag_dtls WHERE cdd_appt_id_fk =:appointmentID", nativeQuery = true)
	List<ConsultDiagDtlsEntity> findByAppointId(@Param("appointmentID") String appointmentID);
	
	@Query("Select new com.nsdl.telemedicine.consultancy.dto.DignosisDTO(c.diagnosis) FROM ConsultDiagDtlsEntity c WHERE c.appointmentDtlsEntity.adApptId = ?1")
	List<ConsultDiagDtlsEntity> findDignosisDetailsByAppID(String apptId);
}
