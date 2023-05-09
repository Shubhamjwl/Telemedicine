package com.nsdl.auth.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.FunctionMasterEntity;

@Repository
public interface FunctionServiceRepository extends JpaRepository<FunctionMasterEntity, String> {

	boolean existsByFunctionName(@Param("functionName") String roleName);
	
	List<FunctionMasterEntity> findByIsActiveAndIsDeleted(boolean isActive, boolean isDeleted);

	@Modifying
	@Query(value = "UPDATE usrmgmt.func_mstr SET fm_func_name_pk=:newFunction,upd_dtimes=:updatedTime,upd_by=:updatedBy WHERE fm_func_name_pk=:functionName", nativeQuery = true)
	int updateFunction(@Param("functionName") String functionName, @Param("newFunction") String newFunction,
			@Param("updatedTime") LocalDateTime updatedTime, @Param("updatedBy") String updatedBy);
	
	@Modifying
	@Query(value = "UPDATE usrmgmt.func_mstr SET is_active = false,is_deleted=true,del_dtimes =:deletedTime WHERE fm_func_name_pk=:functionName", nativeQuery = true)
	int deleteFunction(@Param("functionName") String functionName, @Param("deletedTime") LocalDateTime deletedTime);

}
