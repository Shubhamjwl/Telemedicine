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

import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultCcDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.DocRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "aud_consult_cc_dtls", schema = "audit")
public class AuditConsultCcDtls {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cccd_audit_id_pk")
	private Long ccdAuditIdPk;
	
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

	@Column(name = "cccd_appt_id_fk", nullable = false)
	private String cccdApptIdFk;
	
	@Column(name = "cccd_dr_user_id_fk", nullable = false)
	private String cccdDrUserIdFk;
	
	@Column(name = "cccd_Pt_user_id_fk", nullable = false)
	private String cccdPtUserIdFk;
	
	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;
	
	@Column(name = "user_id")
	private String userId;
	
	@PrePersist
	public void prePersist() {
		timestamp = LocalDateTime.now();
	}
	
}
