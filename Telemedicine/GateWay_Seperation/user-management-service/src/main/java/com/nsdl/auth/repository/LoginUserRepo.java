package com.nsdl.auth.repository;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.auth.entity.LoginUserEntity;

@Repository
@Transactional
public interface LoginUserRepo extends JpaRepository<LoginUserEntity, Integer> {

	public LoginUserEntity findByUserIdOrMobile(String userId, Long mobile);

	public LoginUserEntity findByUserId(String userId);

	public LoginUserEntity findByEmail(String emailId);

	boolean existsByUserId(String userId);

	boolean existsByMobile(Long MobNo);
	
	boolean existsByMobileAndUserIdNot(Long MobNo,String userId);
	
	boolean existsByEmail(String email);
	
	boolean existsByEmailAndUserIdNot(String email,String userId);

	boolean existsByUserType(String userType);

	@Modifying
	@Query(value = "UPDATE usrmgmt.usr_dtls SET ud_logged_in_flg = true ,ud_modified_by =:userId ,ud_modified_tmstmp =:date WHERE ud_user_id =:userId", nativeQuery = true)
	public int userLoggedIn(@Param("userId") String userId, @Param("date") LocalDateTime date);
	
	@Modifying
	@Query(value = "UPDATE usrmgmt.usr_dtls SET ud_logged_in_flg = true ,ud_ischange_pwd = true,ud_modified_by =:userId ,ud_modified_tmstmp =:date WHERE ud_user_id =:userId", nativeQuery = true)
	public int userLoggedInOnPasswordExpired(@Param("userId") String userId, @Param("date") LocalDateTime date);

//	@Modifying
//	@Query(value = "update usrmgmt.usr_dtls set ud_fail_attempt_count = ud_fail_attempt_count + 1, ud_modified_by =:userId, ud_modified_tmstmp =:date  where ud_user_id =:userId", nativeQuery = true)
//	public int updateValidationAttemptCount(@Param("userId") String userId, @Param("date") LocalDateTime date);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE LoginUserEntity u SET u.failAttemptCount = u.failAttemptCount + 1 , u.modifiedBy =:userId, u.modifiedTime =:date ,ud_fail_attempt_tmstmp=:date WHERE u.userId =:userId")
	public int updateValidationAttemptCount(@Param("userId") String userId, @Param("date") LocalDateTime date);

	@Modifying
	@Query(value = "UPDATE usrmgmt.usr_dtls SET ud_islock_flg =true, ud_modified_by =:userId, ud_modified_tmstmp =:date WHERE ud_user_id =:userId", nativeQuery = true)
	int freezeUser(@Param("userId") String userId, @Param("date") LocalDateTime date);

	@Modifying
	@Query(value = "UPDATE usrmgmt.usr_dtls SET ud_fail_attempt_count = 0,ud_islock_flg = false ,ud_modified_by =:userId, ud_modified_tmstmp =:date WHERE ud_user_id =:userId", nativeQuery = true)
	int unFreezeUser(@Param("userId") String userId, @Param("date") LocalDateTime date);

	@Modifying
	@Query(value = "UPDATE usrmgmt.usr_dtls SET ud_fail_attempt_count = 0,ud_logged_in_flg = false , ud_modified_by =:modifiedBy, ud_modified_tmstmp =:date WHERE ud_user_id =:userId", nativeQuery = true)
	int userLoggedOut(@Param("userId") String userId, @Param("date") LocalDateTime date,
			@Param("modifiedBy") String modifiedBy);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE usrmgmt.usr_dtls SET ud_fail_attempt_count = 0,ud_ischange_pwd = false,ud_logged_in_flg = false,ud_modified_by =:userId, ud_modified_tmstmp =:date, ud_pwd_expiry_tmstmp =:expiryTime, ud_password =:password where ud_user_id =:userId and ud_islock_flg = false", nativeQuery = true)
	int resetPassword(@Param("userId") String userId, @Param("expiryTime") LocalDateTime expiryTime,
			@Param("date") LocalDateTime date, @Param("password") String password);

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE usrmgmt.usr_dtls SET ud_fail_attempt_count = 0,ud_ischange_pwd = true,ud_modified_by =:userId, ud_modified_tmstmp =:date, ud_pwd_expiry_tmstmp =null, ud_password =:password where ud_user_id =:userId and ud_islock_flg =false", nativeQuery = true)
	int setRandomPassword(@Param("userId") String userId, @Param("date") LocalDateTime date,
			@Param("password") String password);

	@Modifying
	@Query(value = "UPDATE usrmgmt.usr_dtls SET ud_fail_attempt_count = 0, ud_isactive_flg=:status,ud_de_reg_reason=:reason,ud_modified_by =:modifiedBy,ud_modified_tmstmp =:date WHERE ud_user_id =:userId", nativeQuery = true)
	int activeDeactiveUser(@Param("userId") String userId, @Param("status") boolean status,
			@Param("reason") String reason, @Param("date") LocalDateTime date, @Param("modifiedBy") String modifiedBy);

	
//	@Modifying                                                                             
//	@Query(value = "update usrmgmt.usr_dtls set ud_role_id_fk = roleId,ud_modified_by =:userId, ud_modified_tmstmp =:date where ud_user_id =:userId", nativeQuery = true)
//	int updateUserRole(@Param ("roleId") String roleId, @Param("userId") String userId, @Param("date") LocalDateTime date);
}
