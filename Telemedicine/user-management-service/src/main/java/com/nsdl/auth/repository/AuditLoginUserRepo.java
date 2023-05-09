package com.nsdl.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.AuditLoginUserEntity;

@Repository
public interface AuditLoginUserRepo extends JpaRepository<AuditLoginUserEntity, Long>{

}
