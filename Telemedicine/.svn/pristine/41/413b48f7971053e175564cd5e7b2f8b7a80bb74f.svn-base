package com.nsdl.telemedicine.scribe.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pt_reg_dtls", schema = "registration")
public class PatientRegDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "prd_id_pk")
	private Long prdIdPk;

	@Column(name = "prd_user_id", nullable = false, unique = true)
	private String prdUserId;
	
	@OneToMany(mappedBy="patientRegDtlsEntity")
    private List<PatientRevDtlsEntity> patientRevDtlsEntities;
	
	@OneToMany(mappedBy="patientRegDtlsEntity")
    private List<AppointmentDtlsEntity> appointmentDtlsEntity;

}
