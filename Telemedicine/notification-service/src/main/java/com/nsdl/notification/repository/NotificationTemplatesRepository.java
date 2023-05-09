package com.nsdl.notification.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.notification.entity.NotificationTemplatesEntity;

@Repository
@Transactional
public interface NotificationTemplatesRepository extends JpaRepository<NotificationTemplatesEntity, Long> {

	@Query("FROM NotificationTemplatesEntity n WHERE n.ntType = ?1")
	NotificationTemplatesEntity findByTemplateName(String name);

}
