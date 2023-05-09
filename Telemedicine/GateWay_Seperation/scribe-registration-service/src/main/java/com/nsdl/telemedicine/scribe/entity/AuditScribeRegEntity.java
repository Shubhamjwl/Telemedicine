package com.nsdl.telemedicine.scribe.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(schema = "audit", name = "aud_scrb_reg_dtls")
public class AuditScribeRegEntity {
	
	@Id
	@Column(name = "srd_id_pk", nullable = false)
	@GeneratedValue
	private Integer scrbId;
	
	@Column(name = "srd_scrb_name", length = 100, nullable = false, updatable = false)
	private String scrbFullName;
	
	@Column(name = "srd_mobile_no", length = 10, nullable = false, unique = true)
	private Long scrbMobNo;
	
	@Column(name = "srd_email", length = 50, nullable = true)
	private String scrbEmail;
	
	@Column(name = "srd_user_id", length = 50, nullable = false, unique = true)
	private String scrbUserID;
	
	@Column(name = "srd_dr_user_id_fk", length = 50, nullable = false)
	private String scrbdrUserIDfk;
	
	@Column(name = "srd_password", length = 100, nullable = false)
	private String scrbPassword;
	
	@Column(name = "srd_address1", length = 100)
	private String scrbAdd1;
	
	@Column(name = "srd_address2", length = 100)
	private String scrbAdd2;
	
	@Column(name = "srd_address3", length = 100)
	private String scrbAdd3;
	
	@Column(name = "srd_address4", length = 100)
	private String scrbAdd4;
	
	@Column(name = "srd_isactive", length = 1, nullable = false)
	private String scrbisActive;
	
	@Column(name = "srd_photo_path")
	private String profilePhotoPath;
	
	@Column(name = "srd_created_by", length = 50)
	private String scrbCreadtedBy;
	
	@Column(name = "srd_created_tmstmp")
	private Timestamp scrbCreatedTime;
	
	@Column(name = "srd_modified_by", length = 50)
	private String scrbModifiedBy;
	
	@Column(name = "srd_modified_tmstmp")
	private Timestamp scrbModifiedTime;
	
	@Column(name = "srd_opti_version")
	private String scrbOptiVersion; 
	
	@Column(name = "timestamp", nullable = false)
	private Timestamp timeStamp;
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	
	@PrePersist
	public void prePersist() {
		this.scrbCreatedTime = Timestamp.from(Instant.now());
		this.scrbModifiedTime = Timestamp.from(Instant.now());
		this.timeStamp = Timestamp.from(Instant.now());
	}

}
