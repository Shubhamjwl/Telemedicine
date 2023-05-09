package com.nsdl.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.auth.entity.RoleFunctionMappingEntity;

public interface RoleFunctionMappingRepo extends JpaRepository<RoleFunctionMappingEntity, Long>{
	
	List<RoleFunctionMappingEntity> findByRoleNameAndIsActive(String role,Boolean flag);

}
