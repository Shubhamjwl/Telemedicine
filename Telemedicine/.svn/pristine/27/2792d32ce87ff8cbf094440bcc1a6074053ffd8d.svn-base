/**
 * 
 */
package com.nsdl.telemedicine.doctor.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.LoginUserEntity;

/**
 * @author Pegasus_girishk
 *
 */
@Repository
@Transactional
public interface UserDtlRepo extends JpaRepository<LoginUserEntity, String>{
	
	@Query(value = "select * from usrmgmt.usr_dtls where upper(ud_user_id)=:scribeUserId and ud_isactive_flg =:isActiveFlag", nativeQuery = true)
	LoginUserEntity checkScribeIsActive(@Param("scribeUserId") String scribeUserId, @Param("isActiveFlag") boolean isActiveFlag);
	
	@Modifying
	@Query(value = "update usrmgmt.usr_dtls set ud_isactive_flg =:isActiveFlag where upper(ud_user_id)=:scribeUserId", nativeQuery = true)
	public int updateScribeIsActiveStatus(@Param("scribeUserId") String scribeUserId, @Param("isActiveFlag") boolean isActiveFlag);
}
