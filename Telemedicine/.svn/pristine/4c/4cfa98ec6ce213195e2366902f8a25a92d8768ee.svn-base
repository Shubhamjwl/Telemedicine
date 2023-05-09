package com.nsdl.ndhm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
/* @Table(name = "patient_Linked_care_contexts" , schema = "ndhm") */
@Table(name = "patient_Linked_care_contexts")
public class PatientLinkedCareContext {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String careContextId;
	private String displayName;
	private String patientId;
	private String patientName;
	private String mobileNo;
	private String healthId;
	private String healthNo;
	private String aadhaarNo;
	private String createdAt;
	private String expiredAt;
	private String patientReferenceNo;

}
