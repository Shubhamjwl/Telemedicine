package com.nsdl.telemedicine.slot.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.slot.entity.AppointmentDtlsEntity;

@Repository
public interface AppointmentDtlsRepository extends JpaRepository<AppointmentDtlsEntity, Long> {

	@Query(value = "SELECT * FROM appointment.appt_dtls where ad_dr_user_id_fk = ?1 and ad_appt_status in ('S','R') and ad_appt_date_fk = ?2 order by ad_appt_slot_fk", nativeQuery = true)
	public List<AppointmentDtlsEntity> getBookedSlot(String drUserId,LocalDate date);
	
	@Query(value = "SELECT * FROM appointment.appt_dtls where ad_dr_user_id_fk = ?1 and ad_appt_status in ('C') and ad_appt_date_fk = ?2 order by ad_appt_slot_fk", nativeQuery = true)
	public List<AppointmentDtlsEntity> getCompletedSlot(String drUserId,LocalDate date);
	
	@Query(value = "SELECT * FROM appointment.appt_dtls where ad_dr_user_id_fk = ?1 and ad_appt_slot_fk in ?2 and ad_appt_date_fk = ?3 order by ad_appt_slot_fk", nativeQuery = true)
	public List<AppointmentDtlsEntity> getSlotsByDateTime(String drUserId, List<String> time, Date date);

	@Query(value = "(select count(ad_appt_status) as completed from appointment.appt_dtls where  ad_appt_status='C' and ad_dr_user_id_fk=?1 and extract(YEAR from ad_appt_date_fk )=?2  and EXTRACT(MONTH from ad_appt_date_fk)=?3 )union all " + 
			"(select count(ad_appt_status) as noShow from appointment.appt_dtls where  ad_appt_status='S' and ad_dr_user_id_fk=?1 and extract(YEAR from ad_appt_date_fk )=?2  and EXTRACT(MONTH from ad_appt_date_fk)=?3 )union all " + 
			"(select count(ad_appt_status) as cancel from appointment.appt_dtls where  ad_appt_status='X' and ad_dr_user_id_fk=?1 and extract(YEAR from ad_appt_date_fk )=?2  and EXTRACT(MONTH from ad_appt_date_fk)=?3 )union all " + 
			"(select COALESCE(avg(EXTRACT(hour FROM cast(ad_appt_total_time as time))*60+EXTRACT(minutes FROM cast(ad_appt_total_time as time))),0) from appointment.appt_dtls where ad_appt_total_time is not null  and  ad_appt_status='C' and ad_dr_user_id_fk=?1 and extract(YEAR from ad_appt_date_fk )=?2  and EXTRACT(MONTH from ad_appt_date_fk)=?3 )", nativeQuery = true)
	public List<Double> getApptHistoryByDrId(String userId, int year, int month);
 }
