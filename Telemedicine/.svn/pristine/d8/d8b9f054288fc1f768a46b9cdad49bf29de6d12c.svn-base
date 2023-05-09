package com.nsdl.auth.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(schema = "usrmgmt", name = "usr_dtls_his")
public class UserHistory {

	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ud_user_id", nullable = false)
	private String userId;

	@Column(name = "ud_password")
	private String password;

	@Column(name = "ud_created_by")
	private String createdBy;

	@Column(name = "ud_created_tmstmp")
	private LocalDateTime createdTime;
	
	
	
	/*
	 * @PrePersist public void prePersist() { this.createdTime =
	 * LocalDateTime.now(); }
	 */
	 
}