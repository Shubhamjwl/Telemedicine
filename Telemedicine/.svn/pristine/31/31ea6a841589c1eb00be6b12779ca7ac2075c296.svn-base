package com.nsdl.auth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.LoginUserEntity;

@Repository
public interface UserLoginRepositiory extends JpaRepository<LoginUserEntity, Integer>{

	
	@Modifying
	@Query(value = "delete from usrmgmt.usr_dtls where ud_user_id=:ud_user_id",nativeQuery= true)
	public int deleteByUserId(@Param("ud_user_id") String ud_user_id);
	
	@Modifying
	@Query(value = "update usrmgmt.usr_dtls set ud_islogin_allowed=true where ud_user_id in :ud_user_id", nativeQuery = true)
	public int allowLoginForUsers(@Param("ud_user_id") List<String> ud_user_id);
	
	@Query(value = "FROM LoginUserEntity where userId =:rrdUserId", nativeQuery = false)
	public LoginUserEntity findPatientDetails(@Param("rrdUserId") String rrdUserId);
	
	@Query(value = "select userId FROM LoginUserEntity where isLoginAllowed = false", nativeQuery = false)
	public List<String> findDoctorDetailsByIsAllowedLoginFlag();
}







