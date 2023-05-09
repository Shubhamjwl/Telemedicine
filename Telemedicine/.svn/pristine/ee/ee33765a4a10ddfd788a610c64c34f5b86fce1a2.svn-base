package com.nsdl.telemedicine.doctor.entity;

import java.io.Serializable;
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
@Table(name = "assoc_mstr", schema = "master")
public class AssociationEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "am_id_pk")
	private Integer amIdPk;

	@Column(name = "am_name", nullable = false, unique = true)
	private String amName;

	@Column(name = "am_is_active", nullable = false)
	private Boolean amIsActive;

	@Column(name = "am_created_by")
	private String amCreatedBy;
	
	@Column(name = "am_created_tmstmp")
	private LocalDateTime amCreatedTmstmp;

}
