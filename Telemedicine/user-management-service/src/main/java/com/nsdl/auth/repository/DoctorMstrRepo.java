/**
 * 
 */
package com.nsdl.auth.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.DoctorMstrDtlsEntity;


/**
 * @author Pegasus_girishk
 *
 */

@Repository
@Transactional
public interface DoctorMstrRepo extends JpaRepository<DoctorMstrDtlsEntity, String>{
	public DoctorMstrDtlsEntity findByDmdUserId(String dmdUserId);
	
	public DoctorMstrDtlsEntity findByDmdEmail(String dmdEmail);
	
	@Query("FROM DoctorMstrDtlsEntity ORDER BY dmdDrName ASC")
	public List<DoctorMstrDtlsEntity> findAllDoctors();
	
	
}
