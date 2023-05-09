package com.nsdl.auth.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsdl.auth.entity.DoctorFeatureMapEntity;

public interface DoctorFeatureMapRepo extends JpaRepository<DoctorFeatureMapEntity, String>{ 

	@Query(value="Select * from registration.dr_feature_map_dtls where dfmd_dr_userid=:drUserId and dfmd_category_name in (:categoryType)" , nativeQuery=true)
	List<DoctorFeatureMapEntity> getCategoryDtls(@Param("drUserId") String drUserId, @Param("categoryType") List<String> categoryType);
	
	@Query(value="Select * from registration.dr_feature_map_dtls where dfmd_dr_userid=:drUserId and dfmd_category_name=:categoryType" , nativeQuery=true)
	Optional<DoctorFeatureMapEntity> IsRecordExist(@Param("drUserId") String drUserId, @Param("categoryType") String categoryType);
	
	@Modifying
	@Query(value="update registration.dr_feature_map_dtls set dfmd_flag=:flag,dfmd_updated_tmpstmp=:time  where dfmd_dr_userid=:drUserId and dfmd_category_name=:categoryType" , nativeQuery=true)
	public void updateFlag(@Param("drUserId") String drUserId, @Param("categoryType") String categoryType,@Param("flag") Boolean flag,@Param("time") Timestamp time);
	
}


