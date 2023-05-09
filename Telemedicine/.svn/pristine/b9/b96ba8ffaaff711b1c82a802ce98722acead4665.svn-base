/**
 * 
 */
package com.nsdl.auth.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.DoctorDocsDtlEntity;


/**
 * @author Pegasus_girishk
 *
 */

@Repository
@Transactional
public interface DoctorDocumentRepo extends JpaRepository<DoctorDocsDtlEntity, String>{
	
	@Query(value = "FROM DoctorDocsDtlEntity where doctorRegDtlsEntity.drdUserId =:userId", nativeQuery = false)
	public List<DoctorDocsDtlEntity> findDoctorDocuments(@Param("userId") String userId);
	
	public DoctorDocsDtlEntity findByDdtDocIdPk(Integer fileId);
}
