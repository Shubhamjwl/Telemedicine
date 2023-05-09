package com.nsdl.auth.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.DoctorRegDtlsEntity;

/**
 * @author SayaliA
 *
 */
@Repository
@Transactional
public interface RegistrationRepo extends JpaRepository<DoctorRegDtlsEntity, String> {

	@Query(value = "select * from registration.dr_reg_dtls where drd_user_id=:doctertUserID", nativeQuery = true)
	public DoctorRegDtlsEntity findDoctorDetailsByUserID(@Param("doctertUserID") String doctertUserID);

	@Query(value = "select * from registration.dr_reg_dtls where drd_isverified='R'\n or drd_isverified ='A'\n "
			+ "and (not drd_verified_lvl1_by=:loggedInUser)", nativeQuery = true)
	public List<DoctorRegDtlsEntity> findDoctorListToVerify(@Param("loggedInUser") String loggedInUser);

	@Modifying
	@Query(value = "UPDATE registration.dr_reg_dtls SET drd_isverified =:makerLevelFlag,drd_verified_lvl1_by=:makerLevelUserName ,drd_verified_lvl1_tmstmp=:date,drd_modified_by=:makerLevelUserName,drd_modified_tmstmp =:date WHERE drd_user_id =:doctorUserId", nativeQuery = true)
	int levelOneApproval(@Param("makerLevelFlag") String makerLevelFlag,
			@Param("makerLevelUserName") String makerLevelUserName, @Param("date") LocalDateTime date,
			@Param("doctorUserId") String doctorUserId);

	@Modifying
	@Query(value = "UPDATE registration.dr_reg_dtls SET drd_isverified =:checkerLevelFlag,drd_verified_lvl2_by=:checkerLevelUserName ,drd_verified_lvl2_tmstmp=:date,drd_modified_by=:checkerLevelUserName,drd_modified_tmstmp =:date WHERE drd_user_id =:doctorUserId", nativeQuery = true)
	int levelTwoApproval(@Param("checkerLevelFlag") String checkerLevelFlag,
			@Param("checkerLevelUserName") String checkerLevelUserName, @Param("date") LocalDateTime date,
			@Param("doctorUserId") String doctorUserId);

	@Modifying
	@Query(value = "UPDATE registration.dr_reg_dtls SET drd_isverified =:notVerifiedFlag,drd_rej_reason=:reason,drd_modified_by=:userName,drd_modified_tmstmp=:date WHERE drd_user_id =:doctorUserId", nativeQuery = true)
	int notVerified(@Param("notVerifiedFlag") String notVerifiedFlag, @Param("userName") String userName,
			@Param("date") LocalDateTime date, @Param("doctorUserId") String doctorUserId,
			@Param("reason") String reason);

//	@Query(value = "select * from registration.dr_reg_dtls where drd_isverified='R'\n or drd_isverified ='A'\n " + 
//			"and (not drd_verified_lvl1_by=:loggedInUser or drd_verified_lvl1_by is null) and \n" + 
//			"(not drd_verified_lvl2_by=:loggedInUser or drd_verified_lvl2_by is null)", nativeQuery = true)
}
