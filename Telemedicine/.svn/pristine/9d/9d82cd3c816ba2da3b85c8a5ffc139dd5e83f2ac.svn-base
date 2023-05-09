package com.nsdl.otpManager.entity;

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
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;


/**
 * The persistent class for the dr_reg_dtls database table.
 * 
 */
@Data
@Entity
@Table(name="dr_reg_dtls", schema = "registration")
public class DoctorRegDtlsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="drd_id_pk")
	private Integer id;

	@Column(name="drd_consul_fee")
	private Integer consulFee;

	@NotBlank(message = "Doctor Name is mandatory")
	@Column(name="drd_dr_name")
	private String drName;

	@Column(name="drd_email")
	private String email;

	@Column(name="drd_is_reg_by_ipan")
	private String isRegByIpan;

	@Column(name="drd_isverified")
	private String isVerified;
	
	@Column(name="drd_isactive")
	private String isActive;
	
	@NotNull
	@Column(name="drd_mci_number")
	private String mciNumber;

	@NotNull
	@Column(name="drd_mobile_no")
	private String mobileNo;

	@Column(name="drd_modified_by")
	private String modifiedBy;

	@Column(name="drd_modified_tmstmp")
	private Timestamp modifiedTmstmp;

	@Version
	@Column(name="drd_opti_version")
	private Long optiVersion;

	@Column(name="drd_otp_refid_fk")
	private Integer otpRefidFk;

	@NotNull
	@Column(name="drd_password")
	
	private String password;
	@NotNull
	@Column(name="drd_smc_number")
	private String smcNumber;
	
	@NotNull
	@Column(name="drd_specialiazation")
	private String specialiazation;

	@NotNull
	@Column(name="drd_user_id")
	private String docUserId;

	@Column(name="drd_verified_lvl1_by")
	private String verifiedLvl1By;

	@Column(name="drd_verified_lvl1_tmstmp")
	private Timestamp verifiedLvl1Tmstmp;

	@Column(name="drd_verified_lvl2_by")
	private String verifiedLvl2By;

	@Column(name="drd_verified_lvl2_tmstmp")
	private Timestamp verifiedLvl2Tmstmp;
	
	@Column(name="drd_photo_path")
	private String photoPath;
	
	@OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ddt_dr_user_id_fk")
	private List<DoctorDocsDtlEntity> drDocsDtls;
	
	@Column(name="drd_state")
	private String state;

	@Column(name="drd_city")
	private String city;
	
	@Column(name="drd_gender")
	private String gender;
	
	@Column(name="drd_address1")
	private String address1;
	
	@Column(name="drd_address2")
	private String address2;
		
	@Column(name="drd_address3")
	private String address3;
	

}