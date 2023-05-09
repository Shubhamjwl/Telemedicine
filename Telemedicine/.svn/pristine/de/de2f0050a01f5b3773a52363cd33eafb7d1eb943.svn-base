/**
 * 
 */
package com.nsdl.telemedicine.consultancy.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * @author Pegasus_girishk
 *
 */
@Entity
@Table(name="prescription_template_dtls",schema="appointment")
public class PrescriptionTemplateDetails implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ptd_id_pk")
	private Integer ptdIdPk;
	
	@Column(name="ptd_doctor_id")
	private String ptdDoctorId;
	
	@Column(name="ptd_prescription_template_path")
	private String ptdPrescriptionTemplatePath;
	
	@Column(name="ptd_created_by")
	private String ptdCreatedBy;

	@Column(name="ptd_created_tmstmp")
	private Timestamp ptdCreatedTmstmp;
	
	@Column(name="ptd_modified_by")
	private String ptdModifiedBy;

	@Column(name="ptd_modified_tmstmp")
	private Timestamp ptdModifiedTmstmp;
	
	@Version
	@Column(name="ptd_opti_version")
	private Integer ptdOptiVersion;
	
	public PrescriptionTemplateDetails() {
		
	}

	public Integer getPtdIdPk() {
		return ptdIdPk;
	}

	public void setPtdIdPk(Integer ptdIdPk) {
		this.ptdIdPk = ptdIdPk;
	}

	public String getPtdDoctorId() {
		return ptdDoctorId;
	}

	public void setPtdDoctorId(String ptdDoctorId) {
		this.ptdDoctorId = ptdDoctorId;
	}

	public String getPtdPrescriptionTemplatePath() {
		return ptdPrescriptionTemplatePath;
	}

	public void setPtdPrescriptionTemplatePath(String ptdPrescriptionTemplatePath) {
		this.ptdPrescriptionTemplatePath = ptdPrescriptionTemplatePath;
	}

	public String getPtdCreatedBy() {
		return ptdCreatedBy;
	}

	public void setPtdCreatedBy(String ptdCreatedBy) {
		this.ptdCreatedBy = ptdCreatedBy;
	}

	public Timestamp getPtdCreatedTmstmp() {
		return ptdCreatedTmstmp;
	}

	public void setPtdCreatedTmstmp(Timestamp ptdCreatedTmstmp) {
		this.ptdCreatedTmstmp = ptdCreatedTmstmp;
	}

	public String getPtdModifiedBy() {
		return ptdModifiedBy;
	}

	public void setPtdModifiedBy(String ptdModifiedBy) {
		this.ptdModifiedBy = ptdModifiedBy;
	}

	public Timestamp getPtdModifiedTmstmp() {
		return ptdModifiedTmstmp;
	}

	public void setPtdModifiedTmstmp(Timestamp ptdModifiedTmstmp) {
		this.ptdModifiedTmstmp = ptdModifiedTmstmp;
	}

	public Integer getPtdOptiVersion() {
		return ptdOptiVersion;
	}

	public void setPtdOptiVersion(Integer ptdOptiVersion) {
		this.ptdOptiVersion = ptdOptiVersion;
	}

	@PrePersist
	public void prePersist() {
		this.ptdCreatedTmstmp = Timestamp.from(Instant.now());
		this.ptdModifiedTmstmp = Timestamp.from(Instant.now());
	}
}
