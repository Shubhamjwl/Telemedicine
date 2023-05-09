package com.nsdl.telemedicine.consultancy.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "cntry_mstr", schema = "master")
public class CountryMstrEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cm_cntry_pk")
	private Long cmIdPk;

	@Column(name = "cm_cntry_name", nullable = false, unique = true)
	private String cmName;
	
	@Column(name = "cm_isactive_flg", nullable = false, columnDefinition = "char default 'Y'")
	private String cmActiveFlag;
	
	@Column(name = "cm_created_by")
	private String cmCreatedBy;
	
	@Column(name = "cm_created_tmstmp")
	private LocalDateTime cmCreatedTmstmp;
	
	@Version
	@Column(name = "cm_opti_version")
	private Integer cmOptiVersion;
	
	@OneToMany(mappedBy = "countryMstrEntity")
	List<StateMstrEntity> stateDetails;

	@OneToMany(mappedBy = "countryMstrEntity")
	private List<CityMstrEntity> cityDetails;
	
}
