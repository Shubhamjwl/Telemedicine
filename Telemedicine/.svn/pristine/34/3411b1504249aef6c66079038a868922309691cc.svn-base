package com.nsdl.otpManager.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.sql.Timestamp;


/**
 * The persistent class for the usr_otp_email_verify_dtls database table.
 * 
 */
@Data
@Entity
@Table(name="usr_otp_email_verify_dtls", schema = "usrmgmt")
public class UsrOtpEmailVerifyDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="uvd_id_pk")
	private Integer idPk;

	@Column(name="uvd_email_otp")
	private String emailOtp;

	@Column(name="uvd_email_otp_sent_tmstmp")
	private Timestamp emailOtpSentTmstmp;

	@Column(name="uvd_email_verify_tmstmp")
	private Timestamp emailVerifyTmstmp;

	@Column(name="uvd_is_email_otp_verified")
	private char isEmailOtpVerified='N';

	@Column(name="uvd_is_sms_otp_verified")
	private char isSmsOtpVerified='N';

	@Version
	@Column(name="uvd_opti_version")
	private Integer optiVersion;

	@Column(name="uvd_otp_expired_tmstmp")
	private Timestamp otpExpiredTmstmp;

	@Column(name="uvd_otp_for")
	private String otpFor;

	@Column(name="uvd_otp_generate_attempts")
	private Integer otpGenerateAttempts=0;

	@Column(name="uvd_otp_incorrect_attempts")
	private Integer otpIncorrectAttempts=0;

	@Column(name="uvd_otp_sent_tmstmp")
	private Timestamp otpSentTmstmp;

	@Column(name="uvd_otp_type")
	private String otpType;

	@Column(name="uvd_sms_otp")
	private String smsOtp;

	@Column(name="uvd_sms_verify_tmstmp")
	private Timestamp smsVerifyTmstmp;

	@Column(name="uvd_user_id_fk")
	private String userIdFk;
	
	@Column(name="uvd_created_tmstmp")
	private Timestamp createdTimestamp;


	
}