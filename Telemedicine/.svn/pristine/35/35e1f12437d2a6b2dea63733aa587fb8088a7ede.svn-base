package com.nsdl.telemedicine.videoConference.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the vcauth_token database table.
 * 
 */
@Entity
@Table(name="vcauth_token", schema = "usrmgmt")
@NamedQuery(name="VcAuthToken.findAll", query="SELECT v FROM VcAuthToken v")
public class VcAuthToken implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="appt_id")
	private String apptId;

	@Column(name="auth_token")
	private String authToken;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_time")
	private LocalDateTime createdTime;

	@Column(name="is_active")
	private Boolean isActive;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_time")
	private LocalDateTime updatedTime;

	@Column(name="user_id")
	private String userId;

	@Column(name="user_role")
	private String userRole;
	
	@Column(name="expire_time")
	private Date expireTime;


	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public VcAuthToken() {
	}

	public String getApptId() {
		return this.apptId;
	}

	public void setApptId(String apptId) {
		this.apptId = apptId;
	}

	public String getAuthToken() {
		return this.authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return this.userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}