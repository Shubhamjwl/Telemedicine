package com.nsdl.telemedicine.master.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "state_mstr", schema = "master")
public class StateMstrEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sm_state_pk")
	private Long smStatePk;

	@Column(name = "sm_state_name", nullable = false, unique = true)
	private String smStateName;
	
	@Column(name = "sm_isactive_flg", nullable = false, columnDefinition = "char default 'Y'")
	private String smActiveFlg;
	
	@Column(name = "sm_created_by")
	private String smCreatedBy;
	
	@Column(name = "sm_created_tmstmp")
	private LocalDateTime smCreatedTmstmp;
	
	@Version
	@Column(name = "sm_opti_version")
	private Integer smOptiVersion;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sm_cntry_name_fk", referencedColumnName = "cm_cntry_name", nullable = false)
	private CountryMstrEntity countryMstrEntity;
	
	@OneToMany(mappedBy = "stateMstrEntity")
	private List<CityMstrEntity> cityDetails;
	
}
