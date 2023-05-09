package com.nsdl.telemedicine.scribe.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import com.nsdl.telemedicine.scribe.entity.ScribeRegEntity;

public interface ScribeRegistrationRepositoryJpa extends JpaRepository<ScribeRegEntity, Integer> {
	
	public Optional<ScribeRegEntity> findByscrbUserID(String scrbUserID);
	
	public Optional<ScribeRegEntity> findByscrbMobNo(Long scrbMobNo);
	
	public Optional<ScribeRegEntity> findByscrbEmail(String scrbEmail);

	public List<ScribeRegEntity> findByScrbdrUserIDfk(String scrbdrUserIDfk);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE registration.scrb_reg_dtls SET srd_is_default_scribe =?1 WHERE srd_dr_user_id_fk = ?2" ,nativeQuery= true)
	public int updateByScrbdrUserIDfk(String flag , String scrbdrUserIDfk);
	
	

}
