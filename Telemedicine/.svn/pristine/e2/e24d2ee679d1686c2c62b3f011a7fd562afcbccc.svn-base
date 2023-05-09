package com.nsdl.telemedicine.doctor.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@Table(name = "city_mstr", schema = "master")
public class CityMstrEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cm_city_pk")
	private Long cmCityPk;

	@Column(name = "cm_city_name", nullable = false, unique = true)
	private String cmCityName;

	@Column(name = "cm_isactive_flg", nullable = false, columnDefinition = "char default 'Y'")
	private String cmActiveFlg;

	@Column(name = "cm_created_by")
	private String cmCreatedBy;

	@Column(name = "cm_created_tmstmp")
	private LocalDateTime cmCreatedTmstmp;

	@Version
	@Column(name = "cm_opti_version")
	private Integer cmOptiVersion;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cm_state_name_fk", referencedColumnName = "sm_state_name", nullable = false)
	private StateMstrEntity stateMstrEntity;

}
