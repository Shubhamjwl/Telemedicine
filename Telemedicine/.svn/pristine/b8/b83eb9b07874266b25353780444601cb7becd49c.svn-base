package com.nsdl.telemedicine.scribe.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsdl.telemedicine.scribe.constants.ScribeRegQueriesConstants;
import com.nsdl.telemedicine.scribe.entity.DocMstrDtlsEntity;

public interface DoctorMasterDetailsJpaRepo extends JpaRepository<DocMstrDtlsEntity, Integer> {
	
	@Query(value = ScribeRegQueriesConstants.GET_DOCTOR_BY_USER_ID, nativeQuery = true)
	public String findBydmdUserId(@Param("dmdUserId") String dmdUserId);

	public DocMstrDtlsEntity findByDmdUserId(String dmdUserId);

}
