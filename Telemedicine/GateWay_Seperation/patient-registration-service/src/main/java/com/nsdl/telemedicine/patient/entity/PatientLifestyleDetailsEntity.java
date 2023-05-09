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
@Table(name = "pt_lifesty_dtls", schema = "registration")
public class PatientLifestyleDetailsEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "plsd_id_pk", nullable = false)
	private Integer id;

	@Column(name = "plsd_pt_user_id_fk", nullable = false , length = 50)
	@NotNull(message = "UserID is mandatory")
	private String ptUserID;
	
	@Column(name = "plsd_lfsty_type" , length = 50)
	private String lifeStyleType;
	
	@Column(name = "plsd_lfsty_type_value", length = 50)
	private String lifeStyleTypeValue;
	
	@Version
	@Column(name = "plsd_opti_version")
	private Long optiVersion;
}
