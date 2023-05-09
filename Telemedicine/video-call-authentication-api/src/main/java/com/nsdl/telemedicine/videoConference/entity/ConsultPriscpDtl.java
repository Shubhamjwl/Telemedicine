package com.nsdl.telemedicine.videoConference.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "consult_priscp_dtls", schema = "appointment", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"cpd_appt_id_fk", "cpd_dr_user_id_fk", "cpd_pt_user_id_fk", "cpd_dr_tmplt_name" }) })
public class ConsultPriscpDtl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "cpd_id_pk")
	private Long cpdIdPk;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cpd_appt_id_fk", referencedColumnName = "ad_appt_id", nullable = false)
	private AppointmentDtlsEntity appointmentDtlsEntity;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cpd_dr_user_id_fk", referencedColumnName = "dmd_user_id", nullable = false)
	private DocMstrDtlsEntity docMstrDtlsEntity;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cpd_pt_user_id_fk", referencedColumnName = "prd_user_id", nullable = false)
	private PatientRegDtlsEntity patientRegDtlsEntity;

	@Column(name = "cpd_created_by")
	private String cpdCreatedBy;

	@Column(name = "cpd_created_tmst")
	private LocalDateTime cpdCreatedTmst;
	
	@Column(name = "cpd_is_priscp_verify", columnDefinition = "varchar(1) default 'N'")
	private String cpdIsPriscpVerify;

	@Column(name = "cpd_verified_By")
	private String cpdVerifiedBy;
	
	@Column(name = "cpd_verified_tmstmp")
	private LocalDateTime cpdVerifiedTmstmp;
	
	@Column(name = "cpd_dr_tmplt_name")
	private String cpdDrTmpltName;

	@Version
	@Column(name = "cpd_opti_version")
	private Integer cpdOptiVersion;

	@Column(name = "cpd_priscp_path")
	private String cpdPriscpPath;

}
