package com.nsdl.ndhm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.ndhm.entity.AuthInitEntity;

public interface AuthInitRepository extends JpaRepository<AuthInitEntity, String> {

	
}
