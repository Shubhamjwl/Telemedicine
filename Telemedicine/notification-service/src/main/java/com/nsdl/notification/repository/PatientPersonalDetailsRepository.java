package com.nsdl.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.notification.entity.PatientRegDtlsEntity;

@Repository
public interface PatientPersonalDetailsRepository extends JpaRepository<PatientRegDtlsEntity, Integer> {

	@Query(value = "SELECT r.prd_user_id FROM registration.pt_reg_dtls r WHERE r.prd_user_id IN ?1", nativeQuery = true)
	List<String> findAllByPtUserID(List<String> ptUserID);

	@Query(value = "SELECT * FROM registration.pt_reg_dtls r WHERE r.prd_user_id = ?1", nativeQuery = true)
	PatientRegDtlsEntity findByPtUserID(String ptUserID);

}
