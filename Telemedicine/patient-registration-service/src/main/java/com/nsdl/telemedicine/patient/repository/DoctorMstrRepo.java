/**
 * 
 */
package com.nsdl.telemedicine.patient.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.DoctorMstrDtlsEntity;

@Repository
@Transactional
public interface DoctorMstrRepo extends JpaRepository<DoctorMstrDtlsEntity, String> {

	@Query(value = "FROM DoctorMstrDtlsEntity where dmdUserId =:dmdUserId", nativeQuery = false)
	public DoctorMstrDtlsEntity findByDmdUserId(@Param("dmdUserId") String dmdUserId);

	
}
