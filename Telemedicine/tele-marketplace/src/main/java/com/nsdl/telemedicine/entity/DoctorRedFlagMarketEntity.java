package com.nsdl.telemedicine.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "dr_redflg_mrkt", schema = "appointment")
public class DoctorRedFlagMarketEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drm_id_pk", nullable = false, unique = true)
	private Long drmIdPk;

	@Column(name = "drm_dr_emailid")
	private String drmDrEmailid;

	@Column(name = "drm_redflag", nullable = false)
	private String drmRedflag;

	@Column(name = "drm_form_name", nullable = false)
	private String drmFormName;

	@Column(name = "drm_isactive", nullable = false, columnDefinition = "char default 'Y'")
	private String drmIsActive;
	
	@Column(name = "drm_date", nullable = false)
	private Date drmDate;

	@Column(name = "drm_pt_mobile")
	private String drmPtMobile;
	
	@Column(name = "drm_identifier")
	private String drmIdentifier;
	
	@Column(name = "drm_created_by")
	private String drmCreatedBy;

	@Column(name = "drm_created_tmstmp")
	private LocalDateTime drmCreatedTmstmp;

	@Version
	@Column(name = "drm_opti_version")
	private Integer drmOptiVersion;

}
