package com.nsdl.telemedicine.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.LoginUserEntity;


@Repository
public interface UserLoginRepositiory extends JpaRepository<LoginUserEntity, Integer>{

	
	@Modifying
	@Query(value = "delete from usrmgmt.usr_dtls where ud_user_id=:ud_user_id",nativeQuery= true)
	public int deleteByUserId(@Param("ud_user_id") String ud_user_id);
	
	
	@Query(value = "FROM LoginUserEntity where userId =:rrdUserId", nativeQuery = false)
	public LoginUserEntity findPatientDetails(@Param("rrdUserId") String rrdUserId);
}







