package com.nsdl.telemedicine.videoConference.entity;

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
import javax.persistence.Version;

import lombok.Data;


/**
 * The persistent class for the consult_adv_dtls database table.
 * 
 */
@Data
@Entity
@Table(name = "consult_adv_dtls", schema = "appointment")
public class ConsultAdvDtlsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cad_id_pk", unique=true, nullable=false)
	private Integer cadIdPk;

	@Column(name="cad_advice", nullable=false, length=50)
	private String cadAdvice;

	@Column(name="cad_created_by", length=50)
	private String cadCreatedBy;

	@Column(name="cad_created_tmstmp")
	private Timestamp cadCreatedTmstmp;

	@Column(name="cad_note", length=250)
	private String cadNote;

	@Version
	@Column(name="cad_opti_version")
	private Integer cadOptiVersion;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cad_appt_id_fk", referencedColumnName = "ad_appt_id", nullable = false)
	private AppointmentDtlsEntity appointmentDtlsEntity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cad_dr_user_id_fk", referencedColumnName = "drd_user_id", nullable = false)
	private DocRegDtlsEntity docRegDtlsEntity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cad_pt_user_id_fk", referencedColumnName = "prd_user_id", nullable = false)
	private PatientRegDtlsEntity patientRegDtlsEntity;


}