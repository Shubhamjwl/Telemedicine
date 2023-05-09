package com.nsdl.auth.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.RoleFunctionEntity;

@Repository
public interface RoleFunctionRepository extends JpaRepository<RoleFunctionEntity, Integer> {

//	@Query(value = "SELECT * FROM usrmgmt.role_function_dtls where rfd_role_name_fk =:roleName and is_active=true", nativeQuery = true)
//	List<RoleFunctionEntity> getFunctionListByRoleName(@Param("roleName") String roleName);
	
	//List<RoleFunctionEntity> findByRoleNameAndIsActive(String roleName,boolean flag);
	
	//List<RoleFunctionEntity> findByRoleNameAndIsActiveAndMainMenuNameNotNull(String roleName,boolean flag);

	@Modifying
	@Query(value = "UPDATE usrmgmt.role_function_dtls set is_active=:status, upd_by=:userId,upd_dtimes=:date WHERE rfd_function_fk =:functionName and rfd_role_name_fk =:role", nativeQuery = true)
	int updateFunctionRoleStatus(@Param("status") boolean status, @Param("functionName") String functionName,
			@Param("role") String role, @Param("userId") String userId, @Param("date") LocalDateTime date);

	List<RoleFunctionEntity> findByIsActive(boolean flag);
	
	List<RoleFunctionEntity> findByRoleNameAndFunctionName(String roleName,String functionName);
}
