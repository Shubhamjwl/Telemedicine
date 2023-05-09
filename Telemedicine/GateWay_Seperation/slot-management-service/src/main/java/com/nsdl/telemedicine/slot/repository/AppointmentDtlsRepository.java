package com.nsdl.telemedicine.slot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.slot.entity.AppointmentDtlsEntity;

@Repository
public interface AppointmentDtlsRepository extends JpaRepository<AppointmentDtlsEntity, Long> {

	@Query(value = "SELECT * FROM appointment.appt_dtls where ad_dr_user_id_fk = ?1 and ad_appt_status in ('S','R','C') and ad_appt_date_fk = ?2 order by ad_appt_slot_fk", nativeQuery = true)
	public List<AppointmentDtlsEntity> getBookedSlot(String drUserId,LocalDate date);
 }
