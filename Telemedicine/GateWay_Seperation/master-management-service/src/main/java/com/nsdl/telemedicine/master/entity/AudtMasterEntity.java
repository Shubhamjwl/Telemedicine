package com.nsdl.telemedicine.master.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.nsdl.telemedicine.master.constant.MasterConstant;
import com.nsdl.telemedicine.master.utility.DateUtils;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the mstr_tbl database table.
 * 
 */
@Entity
@Table(name="aud_mstr_tbl", schema = "audit")
@Getter
@Setter
public class AudtMasterEntity extends MasterConstant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mt_master_pk")
	private Integer masterPk;

	@Column(name="mt_created_by")
	private String createdBy;

	@Column(name="mt_created_tmstmp")
	private LocalDateTime createdTmstmp;

	@Column(name="mt_isactive_flg")
	private String isactiveFlg;
	
	@Column(name="mt_master_name")
	private String masterName;

	@Column(name="mt_master_unit")
	private String masterUnit;

	@Column(name="mt_master_value")
	private String masterValue;
	
	@Version
	@Column(name="mt_opti_version")
	private Integer optiVersion;

	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;
	
	@Column(name = "user_id")
	private String user_id;

	public AudtMasterEntity(MasterEntity entity,String userId) {
		super();
		this.createdBy = entity.getCreatedBy();
		this.createdTmstmp = entity.getCreatedTmstmp();
		this.isactiveFlg = entity.getIsactiveFlg();
		this.masterName = entity.getMasterName();
		this.masterUnit = entity.getMasterUnit();
		this.masterValue = entity.getMasterValue();
		this.optiVersion = entity.getOptiVersion();
		this.timestamp = DateUtils.getCurrentLocalDateTime();
		this.user_id = userId;
	}

	public AudtMasterEntity() {
		super();
	}

	
	
}