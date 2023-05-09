/**
 * 
 */
package com.nsdl.telemedicine.consultancy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.ConsultationDtl;

/**
 * @author Pegasus_girishk
 *
 */
@Repository
public interface ConsultationDtlsRepository extends JpaRepository<ConsultationDtl, String>{
	
	@Query(value = "select * from appointment.consultation_dtls where ct_appt_id=:apptId and lower(ct_doctor_id)=:drUserId and lower(ct_patient_id)=:ptUserId", nativeQuery = true)
	public ConsultationDtl checkConsultationDtlsExist(@Param("apptId") String apptId, @Param("drUserId") String drUserId, @Param("ptUserId") String ptUserId);
	
	@Query(value = "select count(*) from appointment.consultation_dtls where lower(ct_appointment_status)='completed'", nativeQuery = true)
	public Long getCountOfConsultation();
	
	public List<ConsultationDtl> findByCtApptId(String ctApptId);
}
