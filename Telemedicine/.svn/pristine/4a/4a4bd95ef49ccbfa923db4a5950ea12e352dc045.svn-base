package com.nsdl.auth.entity;

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

import lombok.Data;

/**
 * The persistent class for the dr_docs_dtls database table.
 * 
 */
@Data
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
	
}