package com.nsdl.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.AuditFunctionMasterEntity;

@Repository
public interface AuditFunctionServiceRepository extends JpaRepository<AuditFunctionMasterEntity, String> {
	
	
	
	
}
