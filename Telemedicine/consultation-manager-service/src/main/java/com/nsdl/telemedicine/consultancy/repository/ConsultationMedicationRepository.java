package com.nsdl.telemedicine.consultancy.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.ConsultMedicationDtls;

/**
 * @author pegasus_girishk
 *
 */
@Repository
@Transactional
public interface ConsultationMedicationRepository extends JpaRepository<ConsultMedicationDtls, String>{

	public List<ConsultMedicationDtls> findAllByCmdApptIdFk(String cmdApptIdFk);
	
	@Query(value = "select * from appointment.consult_mdcn_dtls where cmd_appt_id_fk=:apptId and lower(cmd_dr_user_id_fk)=:drUserId and lower(cmd_pt_user_id_fk)=:ptUserId", nativeQuery = true)
	public List<ConsultMedicationDtls> findMedicationDetails(@Param("apptId") String apptId, @Param("drUserId") String drUserId, @Param("ptUserId") String ptUserId);

	@Query(value = "select * from appointment.consult_mdcn_dtls where cmd_appt_id_fk=:apptId and lower(cmd_dr_user_id_fk)=:drUserId and lower(cmd_pt_user_id_fk)=:ptUserId and lower(cmd_medicine_type)=:medicineType and lower(cmd_medicine_name)=:medicineName", nativeQuery = true)
	public ConsultMedicationDtls checkMedicationDtlsExist(@Param("apptId") String apptId, @Param("drUserId") String drUserId, @Param("ptUserId") String ptUserId, @Param("medicineType") String medicineType,@Param("medicineName") String medicineName);
}
