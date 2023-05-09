package com.nsdl.telemedicine.videoConference.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "pt_reg_dtls", schema = "registration")
public class PatientRegDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prd_id_pk")
	private Long prdIdPk;

	@Column(name = "prd_pt_name", nullable = false)
	private String prdName;

	@Column(name = "prd_user_id", nullable = false, unique = true)
	private String prdUserId;

	@Column(name = "prd_password", nullable = false)
	private String prdPassword;

	@Column(name = "prd_mobile_no", nullable = false, length = 10, unique = true)
	private String prdMobileNo;

	@Column(name = "prd_email")
	private String prdEmail;

	@Column(name = "prd_gender")
	private String prdGender;
	
	@Column(name = "prd_dob")
	private LocalDate prdDOB;
	
	@Column(name = "prd_blood_grp", length = 5)
	private String prdBloodGroup;

	@Column(name = "prd_height", length = 6)
	private Float prdHeight;
	
	@Column(name = "prd_weight", length = 6)
	private Float prdWeight;
	
	@Column(name = "prd_emerg_contact_no", length = 12)
	private Long prdEmergContactNo;
	
	@Column(name = "prd_address1", length = 50)
	private String prdAddress1;
	
	@Column(name = "prd_address2", length = 50)
	private String prdAddress2;
	
	@Column(name = "prd_address3", length = 50)
	private String prdAddress3;
	
	@Column(name = "prd_pincode", length = 6)
	private Integer prdPinCode;

	@Column(name = "prd_isprofile_compl_flg", nullable = false, columnDefinition = "char default 'N'")
	private String prdProfileComplFlag;
	
	@Column(name = "prd_isactive", nullable = false, columnDefinition = "char default 'Y'")
	private String prdActiveFlag;
	
	@Column(name = "prd_is_reg_by_ipan", nullable = false, columnDefinition = "char default 'V'")
	private String prdRegByIpan;
	
	@Column(name = "prd_created_tmstmp")
	private LocalDateTime prdCreatedTmstmp;
	
	@Column(name = "prd_modified_tmstmp")
	private LocalDateTime prdModifiedTmstmp;
	
	@Version
	@Column(name = "prd_opti_version")
	private Integer prdOptiVersion;
	
	@Column(name = "prd_city_name_fk")
	private String cityMstrEntity;
	
	@Column(name = "prd_state_name_fk")
	private String stateMstrEntity;
	
	@Column(name = "prd_cntry_name_fk")
	private String countryMstrEntity;
	
	@OneToMany(mappedBy = "patientRegDtlsEntity")
	private List<PatientRevDtlsEntity> patientRevDtlsEntities;

	@OneToMany(mappedBy = "patientRegDtlsEntity")
	private List<AppointmentDtlsEntity> appointmentDtlsEntity;

	@OneToMany(mappedBy = "patientRegDtlsEntity")	
	private List<PatientMediDtlsEntity> patientMediDtlsEntity;
	
	@OneToMany(mappedBy = "patientRegDtlsEntity")	
	private List<PatientLifestyDtlsEntity> patientLifestyDtlsEntity;
	
	@OneToMany(mappedBy = "patientRegDtlsEntity")
	private List<ConsultCcDtlsEntity> consultCcDetailsEntity;
	
	@OneToMany(mappedBy = "patientRegDtlsEntity")
	private List<ConsultAdvDtlsEntity> consultAdvDtlsEntities ;
}
