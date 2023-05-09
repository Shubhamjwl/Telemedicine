package com.nsdl.ndhm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nsdl.ndhm.dto.CareContextDTO;
import com.nsdl.ndhm.entity.CareContextEntity;

public interface CareContextRepository extends JpaRepository<CareContextEntity, String> {
	@Query("SELECT C FROM  CareContextEntity C WHERE C.patientId = ?1")
	List<CareContextEntity> getCareContextsByPatientId(String id);

	@Query("SELECT C FROM  CareContextEntity C WHERE C.mobileNo = ?1")
	List<CareContextEntity> getCareContextsByMobileNo(String id);

	@Query("SELECT C FROM  CareContextEntity C WHERE C.healthId = ?1")
	List<CareContextEntity> getCareContextsByHealthId(String id);

	@Query("SELECT C FROM  CareContextEntity C WHERE C.healthNo = ?1")
	List<CareContextEntity> getCareContextsByHealthNo(String id);

	@Query("SELECT C.careContextId , C.displayName FROM  CareContextEntity C WHERE C.healthId = ?1")
	List<CareContextDTO> getCareContextsByOnlyHealthId(String id);

	@Query("SELECT Distinct C.mobileNo  FROM  CareContextEntity C WHERE C.healthId = ?1")
	 String getMobileNoByPatientId(String id);
}
