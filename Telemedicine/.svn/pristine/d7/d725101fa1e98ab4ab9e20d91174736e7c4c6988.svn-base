package com.nsdl.telemedicine.videoConference.entity;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "pt_medi_dtls", schema = "registration")
public class PatientMediDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pmd_id_pk", nullable = false)
	private Integer pmdIdPk;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "pmd_pt_user_id_fk", referencedColumnName = "prd_user_id", nullable = false)
	private PatientRegDtlsEntity patientRegDtlsEntity;
	
	@Column(name = "pmd_medical_type")
	private String medicalType;
	
	@Column(name = "pmd_medical_type_value")
	private String medicalTypeValue;
	
	@Version
	@Column(name = "pmd_opti_version")
	private Integer optiVersion;
	
}
