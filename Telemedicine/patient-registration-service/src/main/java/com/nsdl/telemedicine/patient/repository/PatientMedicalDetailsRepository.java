package com.nsdl.telemedicine.patient.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.nsdl.telemedicine.patient.entity.PatientMedicalDetailsEntity;


@Repository
public interface PatientMedicalDetailsRepository extends JpaRepository<PatientMedicalDetailsEntity, Integer>{

	List<PatientMedicalDetailsEntity> findByPtUserID(String ptRegID);

	@Modifying
	@Transactional
	@Query(value = "delete FROM registration.pt_medi_dtls r WHERE r.pmd_pt_user_id_fk = ?1",nativeQuery = true)
	void deleteAllByPtUserID(String ptRegID);
	
	boolean existsByPtUserID(String ptUserID);
}
