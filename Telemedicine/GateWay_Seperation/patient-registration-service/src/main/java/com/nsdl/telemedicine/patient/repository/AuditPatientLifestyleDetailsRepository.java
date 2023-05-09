package com.nsdl.telemedicine.patient.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.AuditPatientLifestyleDetailsEntity;
import com.nsdl.telemedicine.patient.entity.PatientLifestyleDetailsEntity;

@Repository
public interface AuditPatientLifestyleDetailsRepository extends JpaRepository<AuditPatientLifestyleDetailsEntity, Integer>{

	List<PatientLifestyleDetailsEntity> findByPtUserID(String ptRegID);
	
	@Modifying
	@Transactional
	@Query(value = "delete FROM registration.pt_lifesty_dtls r WHERE r.plsd_pt_user_id_fk = ?1",nativeQuery = true)
	void deleteAllByPtUserID(String ptRegID);
	
	boolean existsByPtUserID(String ptUserID);

}
