package com.nsdl.appointment.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.appointment.entity.AppointmentDtlsEntity;

@Repository
@Transactional
public interface AppointmentDtlsRepository extends JpaRepository<AppointmentDtlsEntity, Long> {

	AppointmentDtlsEntity findByAdApptId(String adApptId);

	@Query("FROM AppointmentDtlsEntity a WHERE a.adApptId = ?1 AND a.patientRegDtlsEntity.prdUserId = ?2")
	AppointmentDtlsEntity findByAdApptIdAndPrdUserId(String apptId, String ptUserName);

	@Query("FROM AppointmentDtlsEntity a WHERE a.adApptId = ?1 AND a.docMstrDtlsEntity.dmdUserId = ?2")
	AppointmentDtlsEntity findByAdApptIdAndDmdUserId(String apptId, String drUserName);

	@Query("FROM AppointmentDtlsEntity a WHERE a.adApptDateFk = ?1 AND a.docMstrDtlsEntity.dmdUserId = ?2")
	List<AppointmentDtlsEntity> findByAdApptDateFkAndDmdUserId(Date apptDate, String drUserName);

	@Query("FROM AppointmentDtlsEntity a WHERE a.adScrbUserId = ?1 and a.adApptDateFk in (?2, ?3) order by a.adApptDateFk, a.adApptSlotFk")
	List<AppointmentDtlsEntity> findByAdScrbUserId(String scribeId, Date today, Date tomorrow);

	@Query(value = "SELECT * FROM appointment.appt_dtls a WHERE a.ad_scrb_user_id = ?1 and a.ad_appt_date_fk in (?2, ?3) order by a.ad_appt_date_fk, a.ad_appt_slot_fk", nativeQuery = true)
	List<AppointmentDtlsEntity> findByAdScrbUserIdAndAdApptDateFk(String scribeId, LocalDate today, LocalDate tomorrow);

	@Query(value = "SELECT * FROM appointment.appt_dtls a WHERE a.ad_appt_status = 'C' and a.ad_pt_user_id_fk = ?1 order by a.ad_modified_tmstmp desc limit 3", nativeQuery = true)
	List<AppointmentDtlsEntity> findByAdPtUserId(String ptUserId);
	
	@Query("FROM AppointmentDtlsEntity a WHERE a.adApptId = ?1")
	AppointmentDtlsEntity findByAdApptId(String apptId, String userName);
	
	@Query(value = "SELECT * FROM appointment.appt_dtls a where a.ad_appt_date_fk = ?1 and a.ad_appt_status in ('S','R') and (select CAST (CURRENT_TIME + (5 * interval '1 minute') AS VARCHAR(5))) in (select substring(ad_appt_slot_fk,1,5) FROM appointment.appt_dtls a1 where a1.ad_appt_date_fk=?1)", nativeQuery = true)
	List<AppointmentDtlsEntity> sendNotificationBeforeAppointmentStart(LocalDate today);
}
