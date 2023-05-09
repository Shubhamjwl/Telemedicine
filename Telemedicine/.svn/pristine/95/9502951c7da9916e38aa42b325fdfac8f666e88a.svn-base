package com.nsdl.auth.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * The persistent class for the dr_reg_dtls database table.
 * 
 */
@Data
@Entity
@Table(name = "dr_reg_dtls", schema = "registration")
public class DoctorRegDtlsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drd_id_pk")
	private Integer drdIdPk;

	@Column(name = "drd_consul_fee")
	private Integer drdConsulFee;

	@NotBlank(message = "Doctor Name is mandatory")
	@Column(name = "drd_dr_name")
	private String drdDrName;

	@Column(name = "drd_email")
	private String drdEmail;

	@Column(name = "drd_is_reg_by_ipan")
	private String drdIsRegByIpan;

	@Column(name = "drd_isverified")
	private String drdIsverified;

	@Column(name = "drd_isactive")
	private String drdIsactive;

	@NotNull
	@Column(name = "drd_mci_number")
	private String drdMciNumber;

	@NotNull
	@Column(name = "drd_mobile_no")
	private String drdMobileNo;

	@Column(name = "drd_modified_by")
	private String drdModifiedBy;

	@Column(name = "drd_modified_tmstmp")
	private Timestamp drdModifiedTmstmp;

	@Column(name = "drd_opti_version")
	private Long drdOptiVersion;

	@Column(name = "drd_otp_refid_fk")
	private Integer drdOtpRefidFk;

	@NotNull
	@Column(name = "drd_password")

	private String drdPassword;
	@NotNull
	@Column(name = "drd_smc_number")
	private String drdSmcNumber;

	@NotNull
	@Column(name = "drd_specialiazation")
	private String drdSpecialiazation;

	@NotNull
	@Column(name = "drd_user_id")
	private String drdUserId;

	@Column(name = "drd_verified_lvl1_by")
	private String drdVerifiedLvl1By;

	@Column(name = "drd_verified_lvl1_tmstmp")
	private Timestamp drdVerifiedLvl1Tmstmp;

	@Column(name = "drd_verified_lvl2_by")
	private String drdVerifiedLvl2By;

	@Column(name = "drd_verified_lvl2_tmstmp")
	private Timestamp drdVerifiedLvl2Tmstmp;

	// @NotNull
	@Column(name = "drd_photo_path")
	private String drdPhotoPath;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "ddt_dr_user_id_fk")
	private List<DoctorDocsDtlEntity> drDocsDtls;

	// Verify

	// @NotNull
	@Column(name = "drd_state")
	private String drdState;

	// @NotNull
	@Column(name = "drd_city")
	private String drdCity;

	// @NotNull
	@Column(name = "drd_gender")
	private String drdGender;

	// @NotNull
	@Column(name = "drd_address1")
	private String drdAddress1;

	// @NotNull
	@Column(name = "drd_address2")
	private String drdAddress2;

	// @NotNull
	@Column(name = "drd_address3")
	private String drdAddress3;
	
	@Column(name = "drd_rej_reason")
	private String rejectReason;
}