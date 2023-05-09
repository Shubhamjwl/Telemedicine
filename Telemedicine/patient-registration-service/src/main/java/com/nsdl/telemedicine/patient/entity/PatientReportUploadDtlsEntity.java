package com.nsdl.telemedicine.patient.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appt_ul_rprt_dtls", schema = "appointment")
public class PatientReportUploadDtlsEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "aurd_id_pk")
	private int id;
	
	@Column(name = "aurd_ul_report_type")
	private String reportType;
	
	@Column(name = "aurd_ul_report_name")
	private String reportName;
	
	@Column(name = "aurd_ul_report_path")
	private String path;
	
	@Column(name = "aurd_ul_other_rpt_type")
	private String otherReportType;
	
	@Column(name = "aurd_ul_note")
	private String ulNote;
	
	@Column(name = "aurd_opti_version")
	private String optiVersion;
	
	@Column(name = "aurd_appt_id_fk")
	private String apptIdFk;
}
