package com.nsdl.telemedicine.consultancy.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "appt_ul_rprt_dtls", schema = "appointment")
public class PatientReportUploadDtlsEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "aurd_id_pk")
	private int id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "aurd_appt_id_fk", referencedColumnName = "ad_appt_id", nullable = false)
	private AppointmentDtlsEntity appointmentDtlsEntity;
	
	@Column(name = "aurd_ul_report_type", nullable = false, length = 50)
	private String reportType;
	
	@Column(name = "aurd_ul_report_name", nullable = false, length = 100)
	private String reportName;
	
	@Column(name = "aurd_ul_report_path", nullable = false, length = 250)
	private String path;
	
	@Column(name = "aurd_ul_other_rpt_type", length = 50)
	private String otherReportType;
	
	@Column(name = "aurd_ul_note", length = 250)
	private String ulNote;
	
	@Column(name = "aurd_opti_version")
	private String optiVersion;
}
