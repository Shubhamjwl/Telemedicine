package com.nsdl.auth.repository;

import java.time.LocalDateTime;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.TokenEntity;

@Repository
@Transactional
public interface TokenRepo extends JpaRepository<TokenEntity, String> {

	TokenEntity findByUserId(String userId);
	
	TokenEntity findByAuthToken(String token);
	
	int deleteByUserId(String userId);

	@Modifying
	@Query(value = "update usrmgmt.oauth_token set auth_token =:authToken, expiration_time =:expirationTime, upd_by =:userId, upd_time =:date where user_id =:userId", nativeQuery = true)
	int updateToken(@Param("authToken") String authToken, @Param("expirationTime") Date expirationTime,
			@Param("userId") String userId, @Param("date") LocalDateTime date);
}
