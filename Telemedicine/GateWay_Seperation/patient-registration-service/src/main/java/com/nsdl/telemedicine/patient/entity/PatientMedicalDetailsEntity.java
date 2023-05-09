package com.nsdl.telemedicine.patient.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.Data;
@Data
@Entity
@Table(name = "pt_medi_dtls", schema = "registration")
public class PatientMedicalDetailsEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "pmd_id_pk", nullable = false)
	private Integer id;

	@Column(name = "pmd_pt_user_id_fk", nullable = false , length = 50)
	@NotNull(message = "UserId is mandatory" )
	private String ptUserID;
	
	@Column(name = "pmd_medical_type" , length = 50)
	private String medicalType;
	
	@Column(name = "pmd_medical_type_value" , length = 50)
	private String medicalTypeValue;
	
	@Version
	@Column(name = "pmd_opti_version")
	private Long optiVersion;
}
