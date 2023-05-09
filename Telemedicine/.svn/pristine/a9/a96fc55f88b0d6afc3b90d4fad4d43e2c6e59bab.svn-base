package com.nsdl.telemedicine.consultancy.entity.audit;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "aud_consult_diag_dtls" ,schema = "audit")
public class AuditConsultDiagDtlsEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aud_id_pk")
	private Long auditIdPk;
	
	@Column(name = "cdd_id_pk")
	private Long cddIdPk;

	@Column(name = "cdd_appt_id_fk")
	private String appointmentId;
	
	@Column(name = "cdd_dr_user_id_fk")
	private String docUserId;

	@Column(name = "cdd_pt_user_id_fk")
	private String patientUserId;

	@Column(name = "cdd_diagnosis")
	private String diagnosis;

	@Column(name = "cdd_created_by")
	private String cddCreatedBy;

	@Column(name = "cdd_created_tmstmp")
	private LocalDateTime cddCreatedTmstmp;

	@Version
	@Column(name = "cdd_opti_version")
	private Long cddOptiVersion;
	
	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;
	
	@Column(name = "user_id")
	private String userId;

	@PrePersist
	public void prePersist() {
		timestamp = LocalDateTime.now();
	}
}
