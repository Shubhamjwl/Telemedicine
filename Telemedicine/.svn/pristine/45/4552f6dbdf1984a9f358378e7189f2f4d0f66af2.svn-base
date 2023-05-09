package com.nsdl.telemedicine.patient.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.AppointmentDtlsEntity;

@Repository
@Transactional
public interface AppointmentDtlsRepository extends JpaRepository<AppointmentDtlsEntity, Long>, JpaSpecificationExecutor<AppointmentDtlsEntity> {
	
	
	@Query(value = "FROM AppointmentDtlsEntity where adApptStatus ='C' and patientRegDtlsEntity=:ptUserID", nativeQuery = false)
	List<AppointmentDtlsEntity> findapptDetails(@Param("ptUserID") String ptUserID);
	
	@Query(value = "Select * FROM appointment.appt_dtls where ad_appt_id=:apptId", nativeQuery = true)
	AppointmentDtlsEntity getCreatedTimeById(@Param("apptId") String apptId);

}
