/**
 * 
 */
package com.nsdl.telemedicine.slot.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.slot.entity.DoctorMstrDtlsEntity;



/**
 * @author Pegasus_girishk
 *
 */

@Repository
@Transactional
public interface DoctorMstrRepo extends JpaRepository<DoctorMstrDtlsEntity, String>{
	
	
	
	@Query(value = "FROM DoctorMstrDtlsEntity where dmdUserId =:dmdUserId", nativeQuery = false)
	public DoctorMstrDtlsEntity findByDmdUserId(@Param("dmdUserId") String dmdUserId);
	
}
