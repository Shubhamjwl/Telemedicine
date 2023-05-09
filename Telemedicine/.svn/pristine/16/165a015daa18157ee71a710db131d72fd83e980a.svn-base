package com.nsdl.auth.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.RoleMasterEntity;

/**
 * Repository class for fetching, updating, deleting and adding roles.
 * 
 * @author Sudip Banerjee
 * 
 */
@Repository
public interface RoleServiceRepository extends JpaRepository<RoleMasterEntity, String> {

	boolean existsByRoleName(@Param("roleName") String roleName);

	public RoleMasterEntity findByRoleName(String role);

	List<RoleMasterEntity> findByIsActiveAndIsDeleted(boolean isActive, boolean isDeleted);

	@Modifying
	@Query(value = "UPDATE usrmgmt.role_mstr SET is_active = false,is_deleted=true,del_dtimes =:deletedTime WHERE rm_role_name_pk=:roleName", nativeQuery = true)
	int deleteRole(@Param("roleName") String roleName, @Param("deletedTime") LocalDateTime deletedTime);

	@Modifying
	@Query(value = "UPDATE usrmgmt.role_mstr SET rm_role_name_pk=:newRole,upd_dtimes=:updatedTime,upd_by=:updatedBy WHERE rm_role_name_pk=:roleName", nativeQuery = true)
	int updateRole(@Param("roleName") String roleName, @Param("newRole") String newRole,
			@Param("updatedTime") LocalDateTime updatedTime, @Param("updatedBy") String updatedBy);

}
