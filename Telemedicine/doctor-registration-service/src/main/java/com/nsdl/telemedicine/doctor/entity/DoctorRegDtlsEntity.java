package com.nsdl.telemedicine.doctor.entity;

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
import javax.persistence.SequenceGenerator;
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
@Table(name="dr_reg_dtls", schema = "registration")
public class DoctorRegDtlsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dr_reg_generator")
	@SequenceGenerator(name = "dr_reg_generator", sequenceName = "registration.dr_reg_dtls_drd_id_pk_seq", allocationSize = 1)
	@Column(name="drd_id_pk")
	private Integer drdIdPk;

	@Column(name="drd_consul_fee")
	private Integer drdConsulFee;
	
	@NotBlank(message = "Doctor first Name is mandatory")
	@Column(name="drd_dr_first_name")
	private String drdDrFirstName;
	
	@NotBlank(message = "Doctor Name is mandatory")
	@Column(name="drd_dr_middle_name")
	private String drdDrMiddleName;
	
	@NotBlank(message = "Doctor Last Name is mandatory")
	@Column(name="drd_dr_last_name")
	private String drdDrLastName;

	@Column(name="drd_email")
	private String drdEmail;

	@Column(name="drd_is_reg_by_ipan")
	private String drdIsRegByIpan;

	@Column(name="drd_isverified")
	private String drdIsverified;
	
	@Column(name="drd_isactive")
	private String drdIsactive;
	
	@NotNull
	@Column(name="drd_mci_number")
	private String drdMciNumber;

	@NotNull
	@Column(name="drd_mobile_no")
	private String drdMobileNo;

	@Column(name="drd_modified_by")
	private String drdModifiedBy;

	@Column(name="drd_modified_tmstmp")
	private Timestamp drdModifiedTmstmp;

	@Column(name="drd_opti_version")
	private Long drdOptiVersion;

	@Column(name="drd_otp_refid_fk")
	private Integer drdOtpRefidFk;

	@NotNull
	@Column(name="drd_password")
	private String drdPassword;
	
	@NotNull
	@Column(name="drd_smc_number")
	private String drdSmcNumber;
	
	@NotNull
	@Column(name="drd_specialiazation")
	private String drdSpecialiazation;

	@NotNull
	@Column(name="drd_user_id")
	private String drdUserId;

	@Column(name="drd_verified_lvl1_by")
	private String drdVerifiedLvl1By;

	@Column(name="drd_verified_lvl1_tmstmp")
	private Timestamp drdVerifiedLvl1Tmstmp;

	@Column(name="drd_verified_lvl2_by")
	private String drdVerifiedLvl2By;

	@Column(name="drd_verified_lvl2_tmstmp")
	private Timestamp drdVerifiedLvl2Tmstmp;
	
	@Column(name="drd_photo_path")
	private String drdPhotoPath;
	
	@OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ddt_dr_user_id_fk")
	private List<DoctorDocsDtlEntity> drDocsDtls;
	
	//Verify

	@NotNull
	@Column(name="drd_state")
	private String drdState;

	@NotNull
	@Column(name="drd_city")
	private String drdCity;
	
	@NotNull
	@Column(name="drd_gender")
	private String drdGender;
	
	@NotNull
	@Column(name="drd_address1")
	private String drdAddress1;
	
	@Column(name="drd_address2")
	private String drdAddress2;
		
	@Column(name="drd_address3")
	private String drdAddress3;
	
	@Column(name="drd_rej_reason")
	private String drdRejReason;
	
	@Column(name="drd_pre_assessment_link")
	private String drdPreassessmentLink;
	
	@Column(name="drd_dr_profile_link")
	private String drdDrProfileLink;
	
	@Column(name="drd_pre_assessment_flag")
	private Boolean drdPreassessmentFlag;
	
	@Column(name="drd_isipan_or_marsha")
	private String drdIsIpanorMarsha;
	
	@Column(name="drd_tc_flag")
	private Boolean drdTcFlag;
	
	@Column(name="drd_association_name")
	private String drdAssociationName;
	
	@Column(name="drd_association_number")
	private String drdAssociationNumber;
	
	@Column(name="drd_job_id")
	private String drdJobId;

}