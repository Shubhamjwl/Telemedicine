package com.nsdl.telemedicine.videoConference.entity;

import java.time.LocalDateTime;

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

@Data
@Entity
@Table(name = "consult_cc_dtls", schema = "appointment")
public class ConsultCcDtlsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cccd_id_pk")
	private Long ccdIdPk;

	@Column(name = "cccd_symptom", length = 50, nullable = false)
	private String ccdSymptom;

	@Column(name = "cccd_Symp_severity", length = 50, nullable = false)
	private String ccdSympSeverity;

	@Column(name = "cccd_Symp_duration", length = 50, nullable = false)
	private String ccdSympDuration;

	@Column(name = "cccd_symp_note", length = 250)
	private String ccdSympNote;

	@Column(name = "cccd_created_By", length = 50, nullable = false)
	private String ccdCreatedBy;

	@Column(name = "cccd_created_tmstmp", nullable = false)
	private LocalDateTime ccdCreatedTmstmp;

	@Version
	@Column(name = "cccd_opti_version")
	private Integer ccdOptiVersion;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cccd_appt_id_fk", referencedColumnName = "ad_appt_id", nullable = false)
	private AppointmentDtlsEntity appointmentDtlsEntity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cccd_dr_user_id_fk", referencedColumnName = "drd_user_id", nullable = false)
	private DocRegDtlsEntity docRegDtlsEntity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cccd_Pt_user_id_fk", referencedColumnName = "prd_user_id", nullable = false)
	private PatientRegDtlsEntity patientRegDtlsEntity;

}
