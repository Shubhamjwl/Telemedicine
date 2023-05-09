package com.nsdl.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.payment.entity.WalletEntity;

public interface WalletRepo extends JpaRepository<WalletEntity, Long> {

	
	
}
