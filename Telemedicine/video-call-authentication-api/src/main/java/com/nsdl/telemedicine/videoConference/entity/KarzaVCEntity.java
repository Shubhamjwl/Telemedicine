package com.nsdl.telemedicine.videoConference.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "karza_vc_dtls", schema = "usrmgmt")
@Data
public class KarzaVCEntity {

	@Id
	@Column(name = "kvd_id_pk")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer kvdIdPk;

	@Column(name = "kvd_appointment_id")
	private String kvdAppointmentId;

	@Column(name = "kvd_doctor_id")
	private String kvdDoctorId;

	@Column(name = "kvd_patient_id")
	private String kvdPatientId;

	@Column(name = "kvd_patient_name")
	private String kvdPatientName;

	@Column(name = "kvd_phone")
	private String kvdPhone;

	@Column(name = "kvd_email")
	private String kvdEmail;

	@Column(name = "kvd_meeting_url")
	private String kvdMeetingUrl;

	@Column(name = "kvd_expiry_time")
	public LocalDateTime kvdExpiryTime;

	@Column(name = "kvd_created_tmstmp")
	public LocalDateTime kvdCreatedTmstmp;

	@Column(name = "kvd_created_by")
	private String kvdCreatedBy;

}
