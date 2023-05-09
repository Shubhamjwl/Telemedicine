package com.nsdl.payment.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.payment.entity.AppointmentAuditEntity;



@Repository
@Transactional
public interface AppointmentDtlsAuditRepository extends JpaRepository<AppointmentAuditEntity, Long> {
	
	@Modifying
	@Query(value = "delete from audit.appt_dtls_aud where ad_appt_Id=:ad_appt_id", nativeQuery = true)
	public int deleteAuditApptDetails(@Param("ad_appt_id") String ad_appt_id);

}
