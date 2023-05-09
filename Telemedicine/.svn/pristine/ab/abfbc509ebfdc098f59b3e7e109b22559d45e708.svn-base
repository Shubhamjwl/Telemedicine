package com.nsdl.notification.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.notification.entity.NotificationUserTokenEntity;

@Repository
@Transactional
public interface NotificationUserTokenRepo extends JpaRepository<NotificationUserTokenEntity, Long> {

	@Query("FROM NotificationUserTokenEntity n WHERE n.nutUserId = ?1 AND n.nutUserType = ?2")
	NotificationUserTokenEntity findByUserIdAndUserType(String userId, String userType);

}
