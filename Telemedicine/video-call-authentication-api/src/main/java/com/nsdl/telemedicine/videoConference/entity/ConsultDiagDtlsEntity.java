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
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "consult_diag_dtls" ,schema = "appointment")
public class ConsultDiagDtlsEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cdd_id_pk")
	private Long cddIdPk;

	@ManyToOne
	@JoinColumn(name = "cdd_appt_id_fk" , referencedColumnName = "ad_appt_id" , nullable = false)
	private AppointmentDtlsEntity appointmentDtlsEntity;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cdd_dr_user_id_fk", referencedColumnName = "drd_user_id", nullable = false)
	private DocRegDtlsEntity docRegDtlsEntity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cdd_pt_user_id_fk", referencedColumnName = "prd_user_id", nullable = false)
	private PatientRegDtlsEntity patientRegDtlsEntity;

	@Column(name = "cdd_diagnosis")
	private String diagnosis;

	@Column(name = "cdd_created_by")
	private String cddCreatedBy;

	@Column(name = "cdd_created_tmstmp")
	private LocalDateTime cddCreatedTmstmp;

	@Version
	@Column(name = "cdd_opti_version")
	private Long cddOptiVersion;
	

	@PrePersist
	public void prePersist() {
		cddCreatedTmstmp = LocalDateTime.now();
	}

}
