package com.nsdl.telemedicine.patient.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient_care_contexts", schema = "public")
public class PatientCareContextEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pcc_id")
	private Long id;

	@Column(name = "care_context_id")
	private String careContextsId;
	
	@Column(name = "aadhaar_no")
	private String aadhaarNo;
	
	@Column(name = "display_name")
	private String displayName;
	
	@Column(name = "health_id")
	private String healthId;
	
	@Column(name = "health_no")
	private String healthNo;
	
	@Column(name = "mobile_no")
	private String mobileNo;
	
	@Column(name = "patient_id")
	private String patientId;
	
	@Column(name = "patient_name")
	private String patientName;
	
}
