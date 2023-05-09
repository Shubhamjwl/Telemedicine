package com.nsdl.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.notification.entity.UserEntity;

@Repository
public interface UserRepositiory extends JpaRepository<UserEntity, Integer> {

	@Query(value = "select * from usrmgmt.usr_dtls where date(ud_pwd_expiry_tmstmp) = CURRENT_DATE + 10", nativeQuery = true)
	public List<UserEntity> findExpiredUsersBy10Days();

	@Query(value = "select * from usrmgmt.usr_dtls where date(ud_pwd_expiry_tmstmp) = CURRENT_DATE + 1", nativeQuery = true)
	public List<UserEntity> findExpiredUsersBy1Day();
}
