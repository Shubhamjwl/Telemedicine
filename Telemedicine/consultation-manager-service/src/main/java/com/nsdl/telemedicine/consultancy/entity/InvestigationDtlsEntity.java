package com.nsdl.telemedicine.consultancy.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "consult_inves_dtls", schema = "appointment", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "cid_dr_user_id_fk", "cid_pt_user_id_fk", "cid_appt_id_fk",
				"cid_appt_ul_report_name", "cid_appt_ul_report_type" })})
public class InvestigationDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "cid_id_pk")
	private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cid_dr_user_id_fk", referencedColumnName = "dmd_user_id", nullable = false)
	private DocMstrDtlsEntity docMstrDtlsEntity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cid_pt_user_id_fk", referencedColumnName = "prd_user_id", nullable = false)
	private PatientRegDtlsEntity patientRegDtlsEntity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cid_appt_id_fk", referencedColumnName = "ad_appt_id", nullable = false)
	private AppointmentDtlsEntity appointmentDtlsEntity;

	@Column(name = "cid_appt_ul_report_name", nullable = false)
	private String cidApptUlReportName;
	
	@Column(name = "cid_appt_ul_report_type")
	private String cidApptUlReportType;
	
	@Column(name = "cid_spo2_level")
	private String spo2Level;
	
	@Column(name = "cid_hb")
	private double hb;
	
	@Column(name = "cid_pulse")
	private int pulse;
	
	@Column(name = "cid_height")
	private int height;
	
	@Column(name = "cid_weight")
	private int weight;
	
	@Column(name = "cid_ofc")
	private int ofc;
	
	@Column(name = "cid_systolic")
	private int systolic;
	
	@Column(name = "cid_diastolic")
	private int diastolic;
	
	@Column(name = "cid_respiration_rate")
	private int respirationRate;
	
	@Column(name = "cid_pefr")
	private int pefr;
	
	@Column(name = "cid_physical_exam")
	private String physicalExam;
	
	@Column(name = "cid_allergies_history")
	private String allergiesHistory;
	
	@Column(name = "cid_family_history")
	private String familyHistory;
	
	@Column(name = "cid_inves_note")
	private String cidInvesNote;
	
	@Column(name = "cid_created_By")
	private String cidCreatedBy;
	
	@Column(name = "cid_created_tmstmp")
	private LocalDateTime cidCreatedTmstmp;
	
	@Version
	@Column(name = "cid_opti_version")
	private Integer cidOptiVersion;
	
	@PrePersist
	private void prePersist() {
		cidCreatedTmstmp = LocalDateTime.now();
	}
}
