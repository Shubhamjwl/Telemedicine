package com.nsdl.telemedicine.consultancy.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.ConsultPriscpDtl;

@Repository
public interface ConsultationPrescriptionRepo  extends JpaRepository<ConsultPriscpDtl, Long> {
	
	@Query(value = "SELECT * FROM appointment.consult_priscp_dtls c where c.cpd_appt_id_fk =:adApptId", nativeQuery = true)
	public ConsultPriscpDtl findByAdApptId(@Param("adApptId")String adApptId);
	
	@Query(value = "SELECT * FROM appointment.consult_priscp_dtls c where c.cpd_dr_user_id_fk =:docId", nativeQuery = true)
	public List<ConsultPriscpDtl> findByDocName(@Param("docId") String docId);
	
}
