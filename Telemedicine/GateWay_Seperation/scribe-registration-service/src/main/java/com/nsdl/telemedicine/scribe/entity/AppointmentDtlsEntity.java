package com.nsdl.telemedicine.scribe.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appt_dtls", schema = "appointment")
public class AppointmentDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
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

	@Column(name = "ad_appt_slot_fk", nullable = false)
	private String adApptSlotFk;

	@Column(name = "ad_appt_date_fk", nullable = false)
	private Date adApptDateFk;

	@Column(name = "ad_appt_booked_for", columnDefinition = "varchar(100) default 'Y'")
	private String adApptBookedFor;

	@Column(name = "ad_isbooked")
	private Boolean adIsbooked;

	@Column(name = "ad_appt_status", columnDefinition = "varchar(1) default 'S'")
	private String ad_appt_status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ad_pmt_trans_id_fk", referencedColumnName = "pd_pmt_trans_id", nullable = false, unique = true)
	private PaymentDtlsEntity paymentDtlsEntity;

	@Column(name = "ap_cancel_reason")
	private String apCancelReason;

	@Column(name = "ap_cancel_date")
	private LocalDateTime apCancelDate;

	@Column(name = "ad_created_tmstmp")
	private LocalDateTime adCreatedTmstmp;

	@Column(name = "ad_modified_by")
	private String adModifiedBy;

	@Column(name = "ad_modified_tmstmp")
	private LocalDateTime adModifiedTmstmp;

	@Column(name = "ad_opti_version")
	private String adOptiVersion;

}
