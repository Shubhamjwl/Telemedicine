/**
 * 
 */
package com.nsdl.auth.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
