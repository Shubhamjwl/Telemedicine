package com.nsdl.telemedicine.consultancy.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the aud_consult_adv_dtls database table.
 * 
 */
@Entity
@Table(name="aud_consult_adv_dtls",schema="audit")
public class ConsultAdviceAudtDtlsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cad_id_pk")
	private Integer cadIdPk;

	@Column(name="cad_advice")
	private String cadAdvice;

	@Column(name="cad_appt_id")
	private String cadApptId;

	@Column(name="cad_created_by")
	private String cadCreatedBy;

	@Column(name="cad_created_tmstmp")
	private Timestamp cadCreatedTmstmp;

	@Column(name="cad_dr_user_id")
	private String cadDrUserId;

	@Column(name="cad_note")
	private String cadNote;

	@Column(name="cad_opti_version")
	private Integer cadOptiVersion;

	@Column(name="cad_pt_user_id")
	private String cadPtUserId;
	
	private String userid;

	public ConsultAdviceAudtDtlsEntity() {
	}

	public Integer getCadIdPk() {
		return this.cadIdPk;
	}

	public void setCadIdPk(Integer cadIdPk) {
		this.cadIdPk = cadIdPk;
	}
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCadAdvice() {
		return this.cadAdvice;
	}

	public void setCadAdvice(String cadAdvice) {
		this.cadAdvice = cadAdvice;
	}

	public String getCadApptId() {
		return this.cadApptId;
	}

	public void setCadApptId(String cadApptId) {
		this.cadApptId = cadApptId;
	}

	public String getCadCreatedBy() {
		return this.cadCreatedBy;
	}

	public void setCadCreatedBy(String cadCreatedBy) {
		this.cadCreatedBy = cadCreatedBy;
	}

	public Timestamp getCadCreatedTmstmp() {
		return this.cadCreatedTmstmp;
	}

	public void setCadCreatedTmstmp(Timestamp cadCreatedTmstmp) {
		this.cadCreatedTmstmp = cadCreatedTmstmp;
	}

	public String getCadDrUserId() {
		return this.cadDrUserId;
	}

	public void setCadDrUserId(String cadDrUserId) {
		this.cadDrUserId = cadDrUserId;
	}

	public String getCadNote() {
		return this.cadNote;
	}

	public void setCadNote(String cadNote) {
		this.cadNote = cadNote;
	}

	public Integer getCadOptiVersion() {
		return this.cadOptiVersion;
	}

	public void setCadOptiVersion(Integer cadOptiVersion) {
		this.cadOptiVersion = cadOptiVersion;
	}

	public String getCadPtUserId() {
		return this.cadPtUserId;
	}

	public void setCadPtUserId(String cadPtUserId) {
		this.cadPtUserId = cadPtUserId;
	}

}