package com.nsdl.notification.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "notification_templates", schema = "usrmgmt")
public class NotificationTemplatesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "nt_id")
	private Integer ntId;

	@Column(name = "nt_message", nullable = false)
	private String ntMessage;
	
	@Column(name = "nt_color", nullable = false)
	private String ntColor;

	@Column(name = "nt_type", nullable = false)
	private String ntType;

}
