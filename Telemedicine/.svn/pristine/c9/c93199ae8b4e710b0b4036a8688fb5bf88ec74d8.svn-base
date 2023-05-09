package com.nsdl.telemedicine.videoConference.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.telemedicine.videoConference.entity.TokenEntity;

public interface AuthRepo extends JpaRepository<TokenEntity, String> {
	TokenEntity findByUserIdIgnoreCase(String userId);
	TokenEntity findByAuthToken(String token);

}
