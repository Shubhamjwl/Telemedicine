package com.nsdl.authenticate.uin.entity;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="authenticate_uid_audit",schema = "public")
public class UinAuthenticateEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="auth_id_pk")
	private Integer authIdPk;
	
	@Column(name="auth_uid")
	private String authUin;
	
	@Column(name="auth_otp")
	private String authOtp;
	
	@Column(name="auth_otp_created_tmstmp")
	private Timestamp authOtpCreatedTmstmp;
	
	@Column(name="auth_otp_expire_tmstmp")
	private Timestamp authOtpExpireTmstmp;
	
	@Column(name="auth_otp_isverified")
	private Boolean authOtpIsverified;
	
	@Column(name="auth_otp_verified_tmstmp")
	private Timestamp authOtpVerifiedTmstmp;
	

}
