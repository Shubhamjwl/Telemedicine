package com.nsdl.telemedicine.patient.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aud_pt_medi_dtls", schema = "audit")
public class AuditPatientMedicalDetailsEntity {
	
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
	
	@Column(name = "timestamp")
	private LocalDateTime audTimeStamp;
	
	@Column(name = "user_id")
	private String audUserId;
}
