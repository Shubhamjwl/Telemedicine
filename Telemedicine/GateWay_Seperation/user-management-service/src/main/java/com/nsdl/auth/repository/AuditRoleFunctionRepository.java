package com.nsdl.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.AuditRoleFunctionEntity;
import com.nsdl.auth.entity.RoleFunctionEntity;

@Repository
public interface AuditRoleFunctionRepository extends JpaRepository<AuditRoleFunctionEntity, Long> {

	@Query(value = "SELECT * FROM usrmgmt.role_function_dtls where rfd_role_name_fk =:roleName", nativeQuery = true)
	List<RoleFunctionEntity> getFunctionListByRoleName(@Param("roleName") String roleName);

}
