package com.nsdl.telemedicine.videoConference.repository;

import java.time.LocalDateTime;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.videoConference.entity.VcAuthToken;

@Repository
@Transactional
public interface VcAuthRepository extends JpaRepository<VcAuthToken, String>{
	VcAuthToken findByAuthToken(String token);
	VcAuthToken findByUserIdIgnoreCase(String userId);
	
	
	
	@Modifying
	@Query(value = "update usrmgmt.vcauth_token set auth_token =:authToken,updated_by =:userId, updated_time =:date,expire_time=:expTime where user_id =:userId", nativeQuery = true)
	int updateToken(@Param("authToken") String authToken, @Param("userId") String userId, @Param("date") LocalDateTime date,@Param("expTime") Date expTime);

}
