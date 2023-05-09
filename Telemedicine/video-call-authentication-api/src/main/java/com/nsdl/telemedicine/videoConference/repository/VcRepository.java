package com.nsdl.telemedicine.videoConference.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.telemedicine.videoConference.entity.LoginUserEntity;



public interface VcRepository extends JpaRepository<LoginUserEntity, Integer>{

	public LoginUserEntity findByUserIdIgnoreCase(String userId);

}
