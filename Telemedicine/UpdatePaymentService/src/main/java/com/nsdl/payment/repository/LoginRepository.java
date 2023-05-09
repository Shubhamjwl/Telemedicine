package com.nsdl.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.payment.entity.LoginUserEntity;



public interface LoginRepository extends JpaRepository<LoginUserEntity, Integer>{
	public LoginUserEntity findByUserIdIgnoreCase(String userId);

}
