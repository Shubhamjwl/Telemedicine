package com.nsdl.telemedicine.doctor.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;


/**
 * The persistent class for the mstr_tbl database table.
 * 
 */
@Entity
@Table(name="mstr_tbl", schema = "master")
@Getter
@Setter
public class MasterEntity implements Serializable {
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

}