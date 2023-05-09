package com.nsdl.notification.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.notification.entity.AppointmentDtlsEntity;

@Repository
@Transactional
public interface AppointmentDtlsRepository extends JpaRepository<AppointmentDtlsEntity, Long> {

	@Query(value = "SELECT * FROM appointment.appt_dtls a where a.ad_appt_date_fk = ?1 and a.ad_appt_status in ('S','R') and (substring(ad_appt_slot_fk,1,5)) in (select CAST (CURRENT_TIME + (30 * interval '1 minute') AS VARCHAR(5)))", nativeQuery = true)
	public List<AppointmentDtlsEntity> findAppointmentsForCurrentTimePlus30Mins(Date date);

	@Query(value = "SELECT * FROM appointment.appt_dtls a where a.ad_appt_date_fk = ?1 and a.ad_appt_status in ('S','R') and (substring(ad_appt_slot_fk,1,5)) in (select CAST (CURRENT_TIME - (5 * interval '1 minute') AS VARCHAR(5)))", nativeQuery = true)
	public List<AppointmentDtlsEntity> findAppointmentsForCurrentTimeMinus5Mins(Date date);

}
