package com.nsdl.telemedicine.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.entity.PatientPersonalDetailEntity;

@Repository
public interface PatientPersonalDetailsRepository extends JpaRepository<PatientPersonalDetailEntity, Integer>{

	
	@Query("FROM PatientPersonalDetailEntity where ptUserID=:userId")
	public PatientPersonalDetailEntity findByPatUserId(@Param("userId")String userId);
	
}







