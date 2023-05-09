package com.nsdl.patientReport.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the consult_priscp_dtls database table.
 * 
 */
@Entity
@Table(name="consult_priscp_dtls", schema="appointment")
@NamedQuery(name="ConsultPriscpDtl.findAll", query="SELECT c FROM ConsultPriscpDtl c")
public class ConsultPriscpDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cpd_id_pk")
	private Integer cpdIdPk;

	@Column(name="cpd_appt_id_fk")
	private String cpdApptIdFk;

	@Column(name="cpd_created_by")
	private String cpdCreatedBy;

	@Column(name="cpd_created_tmst")
	private Timestamp cpdCreatedTmst;

	@Column(name="cpd_dr_tmplt_name")
	private String cpdDrTmpltName;

	@Column(name="cpd_dr_user_id_fk")
	private String cpdDrUserIdFk;

	@Column(name="cpd_opti_version")
	private String cpdOptiVersion;

	@Column(name="cpd_priscp_path")
	private String cpdPriscpPath;

	@Column(name="cpd_pt_user_id_fk")
	private String cpdPtUserIdFk;

	public ConsultPriscpDtl() {
	}

	public Integer getCpdIdPk() {
		return this.cpdIdPk;
	}

	public void setCpdIdPk(Integer cpdIdPk) {
		this.cpdIdPk = cpdIdPk;
	}

	public String getCpdApptIdFk() {
		return this.cpdApptIdFk;
	}

	public void setCpdApptIdFk(String cpdApptIdFk) {
		this.cpdApptIdFk = cpdApptIdFk;
	}

	public String getCpdCreatedBy() {
		return this.cpdCreatedBy;
	}

	public void setCpdCreatedBy(String cpdCreatedBy) {
		this.cpdCreatedBy = cpdCreatedBy;
	}

	public Timestamp getCpdCreatedTmst() {
		return this.cpdCreatedTmst;
	}

	public void setCpdCreatedTmst(Timestamp cpdCreatedTmst) {
		this.cpdCreatedTmst = cpdCreatedTmst;
	}

	public String getCpdDrTmpltName() {
		return this.cpdDrTmpltName;
	}

	public void setCpdDrTmpltName(String cpdDrTmpltName) {
		this.cpdDrTmpltName = cpdDrTmpltName;
	}

	public String getCpdDrUserIdFk() {
		return this.cpdDrUserIdFk;
	}

	public void setCpdDrUserIdFk(String cpdDrUserIdFk) {
		this.cpdDrUserIdFk = cpdDrUserIdFk;
	}

	public String getCpdOptiVersion() {
		return this.cpdOptiVersion;
	}

	public void setCpdOptiVersion(String cpdOptiVersion) {
		this.cpdOptiVersion = cpdOptiVersion;
	}

	public String getCpdPriscpPath() {
		return this.cpdPriscpPath;
	}

	public void setCpdPriscpPath(String cpdPriscpPath) {
		this.cpdPriscpPath = cpdPriscpPath;
	}

	public String getCpdPtUserIdFk() {
		return this.cpdPtUserIdFk;
	}

	public void setCpdPtUserIdFk(String cpdPtUserIdFk) {
		this.cpdPtUserIdFk = cpdPtUserIdFk;
	}

}