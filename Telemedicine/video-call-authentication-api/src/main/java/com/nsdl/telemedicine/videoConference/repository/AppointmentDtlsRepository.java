package com.nsdl.telemedicine.videoConference.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.videoConference.entity.AppointmentDtlsEntity;

@Repository
public interface AppointmentDtlsRepository extends JpaRepository<AppointmentDtlsEntity, Long> {

	@Query(value = "FROM AppointmentDtlsEntity WHERE docMstrDtlsEntity.dmdUserId = ?1 AND patientRegDtlsEntity.prdUserId = ?2 AND adApptId = ?3")
	public AppointmentDtlsEntity findByDmdUserIdAndPrdUserIdAndAdApptId(String drName, String prdUserId,
			String appointmentName);

	public AppointmentDtlsEntity findByAdApptId(String adApptId);
	
	@Query("FROM AppointmentDtlsEntity a WHERE a.adApptId = ?1 AND a.docMstrDtlsEntity.dmdUserId = ?2")
	AppointmentDtlsEntity findByAdApptIdAndDmdUserId(String apptId, String drUserId);

	/*
	 * @Query(value =
	 * "Select a.ad_appt_id, a.ad_appt_date_fk, a.ad_appt_slot_fk, p.prd_pt_name from appointment.appt_dtls a inner join registration.pt_reg_dtls p on p.prd_user_id = a.ad_pt_user_id_fk inner join appointment.consult_priscp_dtls s on a.ad_appt_id = s.cpd_appt_id_fk  where s.cpd_is_priscp_verify = 'N' and a.ad_dr_user_id_fk =: docName"
	 * , nativeQuery = true) public List<AppointmentDtlsEntity> findByDocName(String
	 * docName);
	 */
	
	
	
	/*
	 * @Query(value =
	 * "SELECT new com.nsdl.telemedicine.consultancy.dto.AppointmentDTO(a.ad_appt_id, a.ad_appt_date_fk, "
	 * +
	 * "a.ad_appt_slot_fk, p.prd_pt_name) FROM appointment.appt_dtls a inner join registration.pt_reg_dtls p on p.prd_user_id = a.ad_pt_user_id_fk inner join appointment.consult_priscp_dtls s on a.ad_appt_id = s.cpd_appt_id_fk  where s.cpd_is_priscp_verify = 'N' and a.ad_dr_user_id_fk =: docName"
	 * , nativeQuery = true) public List<AppointmentDTO> findByDocName1(String
	 * docName);
	 */
}
