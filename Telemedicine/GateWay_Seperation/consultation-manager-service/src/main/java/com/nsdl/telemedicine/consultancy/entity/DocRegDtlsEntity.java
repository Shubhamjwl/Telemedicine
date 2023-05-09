package com.nsdl.telemedicine.consultancy.entity;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "dr_reg_dtls", schema = "registration")
public class DocRegDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drd_id_pk")
	private Long drdIdPk;

	@Column(name = "drd_consul_fee")
	private Integer drdConsulFee;

	@Column(name = "drd_dr_name")
	private String drdDrName;

	@Column(name = "drd_user_id")
	private String drdUserId;

	@Column(name = "drd_email")
	private String drdEmail;

	@Column(name = "drd_is_reg_by_ipan")
	private String drdIsRegByIpan;

	@Column(name = "drd_isverified")
	private String drdIsverified;

	@Column(name = "drd_mci_number")
	private String drdMciNumber;

	@Column(name = "drd_mobile_no")
	private String drdMobileNo;

	@Column(name = "drd_modified_by")
	private String drdModifiedBy;

	@Column(name = "drd_modified_tmstmp")
	private Timestamp drdModifiedTmstmp;

	@Version
	@Column(name = "drd_opti_version")
	private Long drdOptiVersion;

	@Column(name = "drd_otp_refid_fk")
	private Integer drdOtpRefidFk;

	@Column(name = "drd_password")
	private String drdPassword;

	@Column(name = "drd_smc_number")
	private String drdSmcNumber;

	@Column(name = "drd_specialiazation")
	private String drdSpecialiazation;

	@Column(name = "drd_verified_lvl1_by")
	private String drdVerifiedLvl1By;

	@Column(name = "drd_verified_lvl1_tmstmp")
	private LocalDateTime drdVerifiedLvl1Tmstmp;

	@Column(name = "drd_verified_lvl2_by")
	private String drdVerifiedLvl2By;

	@Column(name = "drd_verified_lvl2_tmstmp")
	private LocalDateTime drdVerifiedLvl2Tmstmp;

	@OneToMany(mappedBy = "docRegDtlsEntity")
	private List<ConsultCcDtlsEntity> consultCcDetailsEntity;
	
	@OneToMany(mappedBy = "docRegDtlsEntity")
	private List<ConsultAdvDtlsEntity> consultAdvDtlsEntities ;

}