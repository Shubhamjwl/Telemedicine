package com.nsdl.payment.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.payment.entity.AppointmentDtlsEntity;

@Repository
@Transactional
public interface AppointmentDtlsRepository extends JpaRepository<AppointmentDtlsEntity, Long> {


	 @Query(value = "select * from appointment.appt_dtls where ad_pmt_trans_id_fk=:transId",nativeQuery = true)
	 AppointmentDtlsEntity findByTransactionId(@Param("transId") String transId);
	 
	@Query(value = "select * from appointment.appt_dtls where ad_appt_status='SCHEDULEWP'", nativeQuery = true)
	public List<AppointmentDtlsEntity> findapptDetailsWithoutPayment();
	
	@Modifying
	@Query(value = "delete from  appointment.appt_dtls where ad_appt_id=:ad_appt_id", nativeQuery = true)
	public int deleteApptDetails(@Param("ad_appt_id") String ad_appt_id);
	
}
