package com.nsdl.telemedicine.consultancy.entity.audit;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultPriscpDtl;
import com.nsdl.telemedicine.consultancy.entity.DocMstrDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "aud_consult_priscp_dtls", schema = "audit")
public class AuditConsultPriscpDtl {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "cpd_audit_id_pk")
	private Long cpdAuditIdPk;

	@Column(name = "cpd_id_pk")
	private Long cpdIdPk;

	@Column(name = "cpd_appt_id_fk", nullable = false)
	private String cpdApptIdFk;

	@Column(name = "cpd_dr_user_id_fk", nullable = false)
	private String cpdDrUserIdFk;

	@Column(name = "cpd_pt_user_id_fk", nullable = false)
	private String cpdPtUserIdFk;

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

	@Column(name = "cpd_opti_version")
	private Integer cpdOptiVersion;

	@Column(name = "cpd_priscp_path")
	private String cpdPriscpPath;

	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	
	@Column(name = "user_id")
	private String userId;
	
	@PrePersist
	private void prePersist() {
		timestamp = LocalDateTime.now();
	}

}
