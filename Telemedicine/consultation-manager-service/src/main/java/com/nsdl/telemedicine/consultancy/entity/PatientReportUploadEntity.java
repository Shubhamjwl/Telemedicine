package com.nsdl.telemedicine.consultancy.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "patient_report_upload_dtls", schema = "appointment")
public class PatientReportUploadEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "prud_id_pk")
	private Long id;

	@Column(name = "prud_pt_user_id")
	private String ptUserId;
	
	@Column(name = "prud_report_type")
	private String reportType;
	
	@Column(name = "prud_report_name")
	private String reportName;
	
	@Column(name = "prud_report_path")
	private String reportPath;
	
	@Column(name = "prud_upload_date")
	private LocalDateTime uploadDate;
	
	@Column(name = "prud_uploaded_by")
	private String uploadedBy;
	
}
