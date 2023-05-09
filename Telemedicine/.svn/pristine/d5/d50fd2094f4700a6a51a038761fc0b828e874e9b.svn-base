package com.nsdl.telemedicine.slot.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.slot.entity.DocSlotDtlsEntity;

@Repository
public interface SlotRepo extends JpaRepository<DocSlotDtlsEntity, String>{

 	@Query(value = "SELECT * FROM appointment.dr_slot_dtls where dsd_dr_user_id_fk =:dsd_dr_user_id_fk and dsd_slot_date=:dsd_slot_date and dsd_isactive ='true'  ORDER BY dsd_slot_date ;", nativeQuery = true)
	public List<DocSlotDtlsEntity> getSlotDetails(@Param("dsd_dr_user_id_fk") String dsd_dr_user_id_fk, @Param("dsd_slot_date") Date dsd_slot_date);
	
	@Query(value = "SELECT * FROM appointment.dr_slot_dtls s WHERE s.dsd_dr_user_id_fk = ?1 and s.dsd_isactive = true and s.dsd_slot_date = ?2 and s.dsd_slot=?3 ORDER BY dsd_slot_date", nativeQuery = true)
	public List<DocSlotDtlsEntity> getSlotDetailsByDocAndDate(String docId, Date date, String slotTime);

	@Query(value = "SELECT srd_dr_user_id_fk FROM registration.scrb_reg_dtls s WHERE s.srd_user_id = ?1 and s.srd_isactive = 'Y'", nativeQuery = true)
	public List<Object[]> getDocRegId(String upperCase);

	@Query(value = " SELECT * FROM appointment.dr_slot_dtls s WHERE s.dsd_dr_user_id_fk = :userName " + 
			" and s.dsd_isactive = true and EXTRACT(YEAR from dsd_slot_date)=:year  " + 
			" and EXTRACT(MONTH from s.dsd_slot_date)=:month and s.dsd_slot_date>=current_date and NOT EXISTS ( select 1 from appointment.appt_dtls a where " + 
			" s.dsd_dr_user_id_fk = a.ad_dr_user_id_fk and s.dsd_slot = a.ad_appt_slot_fk and " + 
			" s.dsd_Slot_date = a.ad_appt_date_fk and a.ad_appt_status in ('C','S','R')) " + 
			" order by s.dsd_Slot_date asc;", nativeQuery = true)
	public List<DocSlotDtlsEntity> getAvailableSlotByMonth(String userName, int year, int month);
	
	@Query(value = "SELECT * FROM appointment.dr_slot_dtls s WHERE s.dsd_dr_user_id_fk = ?1 and s.dsd_isactive = true and s.dsd_slot_date = ?2  and EXISTS ( select 1 from appointment.appt_dtls a where s.dsd_dr_user_id_fk = a.ad_dr_user_id_fk and s.dsd_slot = a.ad_appt_slot_fk and s.dsd_Slot_date = a.ad_appt_date_fk and a.ad_appt_status in ('S','R')) order by s.dsd_Slot_date asc", nativeQuery = true)
	public List<DocSlotDtlsEntity> findDocSlotDtlsByDate(String docId, Date date);
	
	@Query(value = "SELECT * FROM appointment.dr_slot_dtls where dsd_dr_user_id_fk =:dsd_dr_user_id_fk and dsd_slot_date=:dsd_slot_date and dsd_isactive ='false'  ORDER BY dsd_slot_date ;", nativeQuery = true)
	public List<DocSlotDtlsEntity> getInActiveSlotDetails(@Param("dsd_dr_user_id_fk") String dsd_dr_user_id_fk, @Param("dsd_slot_date") Date dsd_slot_date);
}
