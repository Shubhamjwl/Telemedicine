package com.nsdl.notification.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.notification.entity.NotificationEntity;

@Repository
@Transactional
public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {

	@Query("FROM NotificationEntity n where n.ndUserId =:userId and n.ndUserType =:userType order by n.ndCreatedOn desc")
	List<NotificationEntity> findNotificationsByUserIdAndUserType(@Param("userId") String userId,
			@Param("userType") String userType);
	
	@Query("FROM NotificationEntity n where n.ndId =:notificationId")
	NotificationEntity findNotificationsById(@Param("notificationId") int notificationId);

	@Modifying
	@Query("UPDATE NotificationEntity n SET n.ndUnreadFlag =:flag WHERE n.ndId =:notificationId and n.ndUserId =:userId")
	int updateNotificationFlag(@Param("flag") boolean flag, @Param("notificationId") int notificationId,
			@Param("userId") String userId);

}
