package com.nsdl.telemedicine.patient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.DoctorPatientMapDtlsEntity;


/**
 * @author SayaliA
 *
 */
@Repository
public interface PatientRegistrationByScribeRepository extends JpaRepository<DoctorPatientMapDtlsEntity, String> {

	DoctorPatientMapDtlsEntity findByDpmdDrUserIdFkAndDpmdPtUserIdFkAndDpmdStatus(String dpmdDrUserId,String dpmdPtUserId,String dpmdStatus);

	@Query(value = "SELECT srd_dr_user_id_fk FROM registration.scrb_reg_dtls s WHERE s.srd_user_id = ?1 and s.srd_isactive = 'Y'", nativeQuery = true)
	List<Object[]> getDocRegId(String userName);

}
