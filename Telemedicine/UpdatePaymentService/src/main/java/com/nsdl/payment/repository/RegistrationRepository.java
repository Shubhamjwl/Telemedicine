package com.nsdl.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nsdl.payment.entity.PatientPersonalDetailEntity;

@Repository
@Transactional
public interface RegistrationRepository extends JpaRepository<PatientPersonalDetailEntity, Long> {
	public PatientPersonalDetailEntity findByPtUserIDIgnoreCase(String userId);


}
