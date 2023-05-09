package com.nsdl.telemedicine.slot.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;


/**
 * The persistent class for the holiday_dtls database table.
 * 
 */
@Data
@Entity
@Table(name="holiday_dtls",schema = "appointment")
public class HolidayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="hd_id_pk")
	private Integer idPk;

	@Column(name="hd_created_by")
	private String createdBy;

	@Column(name="hd_holiday_reason")
	private String holidayReason;
	
	@Column(name="hd_created_tmstmp")
	private LocalDateTime createdTmstmp;

	@Column(name="hd_dr_user_id_fk")
	private String drUserIdFk;

	@Column(name="hd_holiday_date")
	private LocalDate holidayDate;

	@Column(name="hd_isactive")
	private Boolean isactive;

	@Column(name="hd_modified_by")
	private String modifiedBy;

	@Column(name="hd_modified_tmstmp")
	private LocalDateTime modifiedTmstmp;

	@Column(name="hd_opti_version")
	@Version
	private Integer optiVersion;

}