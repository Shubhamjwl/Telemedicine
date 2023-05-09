package com.nsdl.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.AuditRoleMasterEntity;
import com.nsdl.auth.entity.RoleMasterEntity;
/**
 * 
 * @author Sudip Banerjee
 * 
 */
@Repository
public interface AuditRoleServiceRepository extends JpaRepository<AuditRoleMasterEntity, Long> {
	
	boolean existsByRoleName(@Param("roleName") String roleName);
	
	public RoleMasterEntity findByRoleName(String role);
	
	
}
