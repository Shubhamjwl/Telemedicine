package com.nsdl.telemedicine.doctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsdl.telemedicine.doctor.entity.DoctorFeatureMapEntity;

public interface DoctorFeatureMapRepo extends JpaRepository<DoctorFeatureMapEntity, String>{ 

	@Query(value="Select * from registration.dr_feature_map_dtls where dfmd_dr_userid=:drUserId" , nativeQuery=true)
	List<DoctorFeatureMapEntity> getCategoryDtls(@Param("drUserId") String drUserId);
	
}


