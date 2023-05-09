package com.nsdl.telemedicine.videoConference.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_token", schema = "usrmgmt")
public class TokenEntity {

	@Column(name = "user_id", nullable = false)
	@Id
	// @GeneratedValue(strategy=GenerationType.IDENTITY)
	private String userId;

	@Column(name = "auth_token", length = 1000)
	private String authToken;

	@Column(name = "expiration_time")
	private Date expirationTime;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "cr_by")
	private String crBy;

	@Column(name = "created_time")
	private LocalDateTime CreatedTime;

	@Column(name = "upd_by")
	private String updBy;

	@Column(name = "upd_time")
	private LocalDateTime updTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getCrBy() {
		return crBy;
	}

	public void setCrBy(String crBy) {
		this.crBy = crBy;
	}

	public LocalDateTime getCreatedTime() {
		return CreatedTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		CreatedTime = createdTime;
	}

	public String getUpdBy() {
		return updBy;
	}

	public void setUpdBy(String updBy) {
		this.updBy = updBy;
	}

	public LocalDateTime getUpdTime() {
		return updTime;
	}

	public void setUpdTime(LocalDateTime updTime) {
		this.updTime = updTime;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

}
