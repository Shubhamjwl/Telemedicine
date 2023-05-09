package com.nsdl.telemedicine.consultancy.entity;

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
@Table(name = "pt_lifesty_dtls", schema = "registration")
public class PatientLifestyDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plsd_id_pk", nullable = false)
	private Integer plsdIdPk;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "plsd_pt_user_id_fk", referencedColumnName = "prd_user_id", nullable = false)
	private PatientRegDtlsEntity patientRegDtlsEntity;
	
	@Column(name = "plsd_lfsty_type")
	private String plsdLfstyType;
	
	@Column(name = "plsd_lfsty_type_value")
	private String plsdLfstyTypeValue;
	
	@Version
	@Column(name = "plsd_opti_version")
	private Integer optiVersion;

	
}
