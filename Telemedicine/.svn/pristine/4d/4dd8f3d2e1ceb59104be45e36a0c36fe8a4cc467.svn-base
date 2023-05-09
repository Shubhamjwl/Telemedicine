package com.nsdl.telemedicine.consultancy.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(schema = "registration", name = "scrb_reg_dtls")
public class ScribeRegEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "srd_id_pk")
	private Integer srdIdPk;

	@Column(name = "srd_scrb_name", length = 100, nullable = false, updatable = false)
	private String srdScrbName;

	@Column(name = "srd_mobile_no", length = 10, nullable = false, unique = true)
	private Long srdMobileNo;

	@Column(name = "srd_email", length = 50)
	private String srdEmail;

	@Column(name = "srd_user_id", length = 50, nullable = false, unique = true)
	private String srdUserID;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "srd_dr_user_id_fk", referencedColumnName = "dmd_user_id", nullable = false)
	private DocMstrDtlsEntity docMstrDtlsEntity;

	@Column(name = "srd_password", length = 100, nullable = false)
	private String srdPassword;

	@Column(name = "srd_address1", length = 100)
	private String srdAddress1;

	@Column(name = "srd_address2", length = 100)
	private String srdAddress2;

	@Column(name = "srd_address3", length = 100)
	private String srdAddress3;

	@Column(name = "srd_address4", length = 100)
	private String srdAddress4;

	@Column(name = "srd_isactive", length = 1, nullable = false, columnDefinition = "char(1) default 'Y'")
	private String srdIsActive;

	@Column(name = "srd_created_by", length = 50)
	private String srdCreadtedBy;

	@Column(name = "srd_created_tmstmp")
	private LocalDateTime srdCreatedTmstmp;

	@Column(name = "srd_modified_by", length = 50)
	private String srdModifiedBy;

	@Column(name = "srd_modified_tmstmp")
	private LocalDateTime srdModifiedTmstmp;

	@Version
	@Column(name = "srd_opti_version")
	private Integer srdOptiVersion;
	
	@Column(name = "srd_photo_path")
	private String ProfilePhoto;
	
	@Column(name = "srd_is_default_scribe", length = 1)
	private String isDefaultScribe;

	@PrePersist
	public void prePersist() {
		this.srdCreatedTmstmp = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.srdModifiedTmstmp = LocalDateTime.now();
	}

}
