package com.nsdl.telemedicine.consultancy.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "appt_dtls", schema = "appointment", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"ad_dr_user_id_fk", "ad_pt_user_id_fk", "ad_appt_slot_fk", "ad_appt_date_fk", "ad_pmt_trans_id_fk" }) })
public class AppointmentDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ad_id_pk")
	private Long adIdPk;

	@Column(name = "ad_appt_id")
	private String adApptId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ad_dr_user_id_fk", referencedColumnName = "dmd_user_id", nullable = false)
	private DocMstrDtlsEntity docMstrDtlsEntity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ad_pt_user_id_fk", referencedColumnName = "prd_user_id", nullable = false)
	private PatientRegDtlsEntity patientRegDtlsEntity;

	@Column(name = "ad_scrb_user_id")
	private String adScrbUserId;
	
	@Column(name = "ad_appt_slot_fk")
	private String adApptSlotFk;

	@Column(name = "ad_appt_date_fk")
	private LocalDate adApptDateFk;

	@Column(name = "ad_appt_booked_for", columnDefinition = "varchar(100) default 'Y'")
	private String adApptBookedFor;

	@Column(name = "ad_isbooked")
	private Boolean adIsbooked;

	@Column(name = "ad_appt_status", columnDefinition = "varchar(1) default 'S'")
	private String adApptStatus;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ad_pmt_trans_id_fk", referencedColumnName = "pd_pmt_trans_id", nullable = false)
	private PaymentDtlsEntity paymentDtlsEntity;

	@Column(name = "ap_cancel_reason")
	private String apCancelReason;

	@Column(name = "ap_cancel_date")
	private LocalDateTime apCancelDate;

	@Column(name = "ad_created_tmstmp")
	private LocalDateTime adCreatedTmstmp;

	@Column(name = "ad_created_by")
	private String adCreatedBy;
	
	@Column(name = "ad_modified_by")
	private String adModifiedBy;

	@Column(name = "ad_modified_tmstmp")
	private LocalDateTime adModifiedTmstmp;

	@OneToMany(mappedBy = "appointmentDtlsEntity")
	private List<ConsultCcDtlsEntity> consultCcDetailsEntity;
	
	@OneToMany(mappedBy = "appointmentDtlsEntity")
	private List<ConsultAdvDtlsEntity> consultAdvDtlsEntities ;

	@Version
	@Column(name = "ad_opti_version")
	private Integer adOptiVersion;

	@PrePersist
	private void prePersist() {
		adCreatedTmstmp = LocalDateTime.now();
	}

	@PreUpdate
	private void preUpdate() {
		adModifiedTmstmp = LocalDateTime.now();
	}
}
