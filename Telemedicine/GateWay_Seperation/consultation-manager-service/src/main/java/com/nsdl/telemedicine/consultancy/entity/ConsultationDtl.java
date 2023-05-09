package com.nsdl.telemedicine.consultancy.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the consultation_dtls database table.
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="consultation_dtls", schema = "appointment")
public class ConsultationDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ct_id_pk")
	private Integer ctIdPk;

	@Column(name="ct_advice")
	private String ctAdvice;

	@Column(name="ct_appointment_status")
	private String ctAppointmentStatus;

	@Column(name="ct_appt_id")
	private String ctApptId;

	@Column(name="ct_chief_complaints")
	private String ctChiefComplaints;

	@Column(name="ct_created_by")
	private String ctCreatedBy;

	@Column(name="ct_created_tmstmp")
	private LocalDateTime ctCreatedTmstmp;

	@Column(name="ct_diagnosis")
	private String ctDiagnosis;

	@Column(name="ct_doctor_id")
	private String ctDoctorId;

	@Column(name="ct_medication")
	private String ctMedication;

	@Column(name="ct_modified_by")
	private String ctModifiedBy;

	@Column(name="ct_modified_tmstmp")
	private LocalDateTime ctModifiedTmstmp;

	@Column(name="ct_patient_id")
	private String ctPatientId;

	@Column(name="ct_prescription_path")
	private String ctPrescriptionPath;

	@Column(name="ct_scribe_id")
	private String ctScribeId;

	@Column(name="ddt_opti_version")
	private Long ddtOptiVersion;
	
	/*
	 * @PrePersist private void prePersist() { ctCreatedTmstmp =
	 * LocalDateTime.now(); ctModifiedTmstmp = LocalDateTime.now(); }
	 */
}