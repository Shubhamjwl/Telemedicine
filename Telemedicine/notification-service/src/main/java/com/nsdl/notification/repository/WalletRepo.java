package com.nsdl.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.notification.entity.WalletEntity;

public interface WalletRepo extends JpaRepository<WalletEntity, Long> {

}
