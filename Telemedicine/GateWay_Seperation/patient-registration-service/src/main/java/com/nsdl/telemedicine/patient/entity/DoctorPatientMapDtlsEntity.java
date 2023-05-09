package com.nsdl.telemedicine.patient.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the dr_pt_map_dtls database table.
 * 
 */
@Data
@EqualsAndHashCode
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="dr_pt_map_dtls",schema = "registration")
//@NamedQuery(name="DrPtMapDtl.findAll", query="SELECT d FROM DrPtMapDtl d")
public class DoctorPatientMapDtlsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="dpmd_id_pk")
	private Integer dpmdIdPk;

	@Column(name="dpmd_created_by")
	private String dpmdCreatedBy;

	@CreatedDate
	@Column(name="dpmd_created_tmstmp")
	private LocalDateTime dpmdCreatedTmstmp;
	
	@Column(name="dpmd_status")
	private String dpmdStatus;

	@Column(name="dpmd_dr_user_id_fk")
	private String dpmdDrUserIdFk;

	@Column(name="dpmd_modified_by")
	private String dpmdModifiedBy;

	@Column(name="dpmd_modified_tmstmp")
	private Timestamp dpmdModifiedTmstmp;

	@Column(name="dpmd_pt_user_id_fk")
	private String dpmdPtUserIdFk;


}