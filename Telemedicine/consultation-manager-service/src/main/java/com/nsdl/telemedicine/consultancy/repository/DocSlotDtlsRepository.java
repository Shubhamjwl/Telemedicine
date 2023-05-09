package com.nsdl.telemedicine.consultancy.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.DocSlotDtlsEntity;

@Repository
@Transactional
public interface DocSlotDtlsRepository extends JpaRepository<DocSlotDtlsEntity, Long> {

	
	@Query(value = "SELECT * FROM appointment.dr_slot_dtls s WHERE s.dsd_dr_user_id_fk = ?1 and s.dsd_isactive = true and s.dsd_slot_date = ?2  and NOT EXISTS ( select 1 from appointment.appt_dtls a where s.dsd_dr_user_id_fk = a.ad_dr_user_id_fk and s.dsd_slot = a.ad_appt_slot_fk and s.dsd_Slot_date = a.ad_appt_date_fk and a.ad_appt_status in ('C','S','R','SCHEDULEWP')) order by s.dsd_Slot_date asc", nativeQuery = true)
	public List<DocSlotDtlsEntity> findDocSlotDtlsByDate(String docId, LocalDate date);//C,S,R !X

	@Query(value = "SELECT srd_dr_user_id_fk FROM registration.scrb_reg_dtls s WHERE s.srd_user_id = ?1 and s.srd_isactive = 'Y'", nativeQuery = true)
	public List<String> getDocRegId(String upperCase);
	
	@Query(value = "SELECT rrd_dr_user_id_fk FROM registration.recept_reg_dtls s WHERE s.rrd_user_id = ?1 and s.rrd_isactive = 'Y'", nativeQuery = true)
	public List<String> getDocRegIdByReceptionistId(String upperCase);
	
	@Query(value = "SELECT * FROM appointment.dr_slot_dtls s WHERE s.dsd_dr_user_id_fk = ?1 and s.dsd_isactive = true and s.dsd_slot_date = ?2  and EXISTS ( select 1 from appointment.appt_dtls a where s.dsd_dr_user_id_fk = a.ad_dr_user_id_fk and s.dsd_slot = a.ad_appt_slot_fk and s.dsd_Slot_date = a.ad_appt_date_fk and a.ad_appt_status in ('S','R')) order by s.dsd_Slot_date asc", nativeQuery = true)
	public List<DocSlotDtlsEntity> findDocSlotDtlsByStatus(String docId, LocalDate date);//C,S,R !X
}
