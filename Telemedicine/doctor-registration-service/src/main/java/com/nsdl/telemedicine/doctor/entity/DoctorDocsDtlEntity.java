package com.nsdl.telemedicine.doctor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The persistent class for the dr_docs_dtls database table.
 * 
 */
@Getter
@Setter
@Entity
@Table(name="dr_docs_dtls",schema = "registration")
//@NamedQuery(name="DoctorDocsDtlEntity.findAll", query="SELECT d FROM DoctorDocsDtlEntity d")
public class DoctorDocsDtlEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ddt_doc_id_pk")
	private Integer ddtDocIdPk;

	@Column(name="ddt_created_by")
	private String ddtCreatedBy;

	@Column(name="ddt_created_tmstmp")
	private Timestamp ddtCreatedTmstmp;

	@NotNull
	@Column(name="ddt_docs_name")
	private String ddtDocsName;
	
	@NotNull
	@Column(name="ddt_docs_path")
	private String ddtDocsPath;

	@Column(name="ddt_docs_remark")
	private String ddtDocsRemark;

	@Column(name="ddt_docs_type")
	private String ddtDocsType;

	@Column(name="ddt_modified_by")
	private String ddtModifiedBy;

	@Column(name="ddt_modified_tmstmp")
	private Timestamp ddtModifiedTmstmp;

	@Column(name="ddt_opti_version")
	private Long ddtOptiVersion;

	//bi-directional many-to-one association to DrMstrDtl
	@ManyToOne(cascade=CascadeType.ALL)
	@NotNull
	@JoinColumn(name="ddt_dr_user_id_fk", referencedColumnName="drd_user_id")
	private DoctorRegDtlsEntity doctorRegDtlsEntity;
	

	public Integer getDdtDocIdPk() {
		return ddtDocIdPk;
	}


	public void setDdtDocIdPk(Integer ddtDocIdPk) {
		this.ddtDocIdPk = ddtDocIdPk;
	}


	public String getDdtCreatedBy() {
		return ddtCreatedBy;
	}


	public void setDdtCreatedBy(String ddtCreatedBy) {
		this.ddtCreatedBy = ddtCreatedBy;
	}


	public Timestamp getDdtCreatedTmstmp() {
		return ddtCreatedTmstmp;
	}


	public void setDdtCreatedTmstmp(Timestamp ddtCreatedTmstmp) {
		this.ddtCreatedTmstmp = ddtCreatedTmstmp;
	}


	public String getDdtDocsName() {
		return ddtDocsName;
	}


	public void setDdtDocsName(String ddtDocsName) {
		this.ddtDocsName = ddtDocsName;
	}


	public String getDdtDocsPath() {
		return ddtDocsPath;
	}


	public void setDdtDocsPath(String ddtDocsPath) {
		this.ddtDocsPath = ddtDocsPath;
	}


	public String getDdtDocsRemark() {
		return ddtDocsRemark;
	}


	public void setDdtDocsRemark(String ddtDocsRemark) {
		this.ddtDocsRemark = ddtDocsRemark;
	}


	public String getDdtDocsType() {
		return ddtDocsType;
	}


	public void setDdtDocsType(String ddtDocsType) {
		this.ddtDocsType = ddtDocsType;
	}


	public String getDdtModifiedBy() {
		return ddtModifiedBy;
	}


	public void setDdtModifiedBy(String ddtModifiedBy) {
		this.ddtModifiedBy = ddtModifiedBy;
	}


	public Timestamp getDdtModifiedTmstmp() {
		return ddtModifiedTmstmp;
	}


	public void setDdtModifiedTmstmp(Timestamp ddtModifiedTmstmp) {
		this.ddtModifiedTmstmp = ddtModifiedTmstmp;
	}


	public Long getDdtOptiVersion() {
		return ddtOptiVersion;
	}


	public void setDdtOptiVersion(Long ddtOptiVersion) {
		this.ddtOptiVersion = ddtOptiVersion;
	}


	public DoctorRegDtlsEntity getDoctorRegDtlsEntity() {
		return doctorRegDtlsEntity;
	}


	public void setDoctorRegDtlsEntity(DoctorRegDtlsEntity doctorRegDtlsEntity) {
		this.doctorRegDtlsEntity = doctorRegDtlsEntity;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public DoctorDocsDtlEntity() {
	}
}