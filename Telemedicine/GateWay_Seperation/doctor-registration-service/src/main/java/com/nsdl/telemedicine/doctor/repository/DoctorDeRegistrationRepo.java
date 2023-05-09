/**
 * 
 */
package com.nsdl.telemedicine.doctor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.LoginUserEntity;

/**
 * @author SayaliA
 *
 */

@Repository
@Transactional
public interface DoctorDeRegistrationRepo extends JpaRepository<LoginUserEntity, String>{
	
	@Query(value = "FROM LoginUserEntity where isActive ='true' and userType='DOCTOR' ", nativeQuery = false)
	public List<LoginUserEntity> findAciveDoctor();
	
	@Query(value = "FROM LoginUserEntity where userId =:userID", nativeQuery = false)
	public LoginUserEntity finduserdetails(@Param("userID") String userID);
	
}
