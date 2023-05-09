/**
 * 
 */
package com.nsdl.telemedicine.doctor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.ScribeRegEntity;


/**
 * @author Pegasus_girishk
 *
 */

@Repository
@Transactional
public interface ScribeRegRepo extends JpaRepository<ScribeRegEntity, String>{
	
	@Query(value = "FROM ScribeRegEntity where lower(scrbdrUserIDfk) =:dmdUserId and lower(scrbisActive) =:scrbisActive", nativeQuery = false)
	public List<ScribeRegEntity> findScribeDetails(@Param("dmdUserId") String dmdUserId, @Param("scrbisActive") String scrbisActive);
	
	@Modifying
	@Query(value = "update registration.scrb_reg_dtls set srd_isactive =:srdIsActive where lower(srd_dr_user_id_fk) =:srdDrUserId and lower(srd_user_id) =:srdUserId", nativeQuery = true)
	public void activeDeactiveScribe(@Param("srdUserId") String srdUserId , @Param("srdDrUserId") String srdDrUserId , @Param("srdIsActive") String srdIsActive);
	
	@Query(value = "FROM ScribeRegEntity where lower(scrbUserID) =:srdUserId and lower(scrbdrUserIDfk) =:dmdUserId", nativeQuery = false)
	public ScribeRegEntity findScribe(@Param("srdUserId") String srdUserId , @Param("dmdUserId") String dmdUserId);
	
	@Query(value = "FROM ScribeRegEntity where upper(scrbdrUserIDfk) =:dmdUserId", nativeQuery = false)
	public List<ScribeRegEntity> getScribeListByDoctorToActiveDeactive(@Param("dmdUserId") String dmdUserId);
	
	@Modifying
	@Query(value = "update registration.scrb_reg_dtls SET srd_is_default_scribe = CASE WHEN upper(srd_user_id)=:scribeUserId THEN 'Y' ELSE 'N' END where upper(srd_dr_user_id_fk)=:doctorUserId", nativeQuery = true)
	public void changeDefaultScribe(@Param("scribeUserId") String scribeUserId , @Param("doctorUserId") String doctorUserId);
}
