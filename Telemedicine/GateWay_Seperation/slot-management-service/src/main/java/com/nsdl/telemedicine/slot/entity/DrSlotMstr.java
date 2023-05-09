package com.nsdl.telemedicine.slot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;


/**
 * The persistent class for the dr_slot_mstr database table.
 * 
 */
@Data
@Entity
@Table(name="dr_slot_mstr", schema = "appointment")
public class DrSlotMstr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="dsm_id_pk")
	private Integer idPk;

	@Column(name="dsm_auto_rep")
	private Boolean autoRep;

	@Column(name="dsm_created_by")
	private String createdBy;

	@Column(name="dsm_created_tmstmp")
	private LocalDateTime createdTmstmp;

	@Column(name="dsm_dr_user_id_fk")
	private String drUserIdFk;

	@Column(name="dsm_fri")
	private Boolean fri=false;

	@Column(name="dsm_from_month")
	private Integer fromMonth;

	@Column(name="dsm_isactive")
	private Boolean isactive;

	@Column(name="dsm_modified_by")
	private String modifiedBy;

	@Column(name="dsm_modified_tmstmp")
	private LocalDateTime modifiedTmstmp;

	@Column(name="dsm_mon")
	private Boolean mon=false;

	@Version
	@Column(name="dsm_opti_version")
	private Integer optiVersion;

	@Column(name="dsm_rep_for_month")
	private Integer repForMonth;

	@Column(name="dsm_sat")
	private Boolean sat=false;

	@Column(name="dsm_slot_duration")
	private Integer slotDuration;

	@Column(name="dsm_sun")
	private Boolean sun=false;

	@Column(name="dsm_thu")
	private Boolean thu=false;

	@Column(name="dsm_to_month")
	private Integer toMonth;

	@Column(name="dsm_tue")
	private Boolean tue=false;

	@Column(name="dsm_wed")
	private Boolean wed=false;
	
	@Column(name="dsm_mon_time")
	private String monTime;

	@Column(name="dsm_tue_time")
	private String tueTime;

	@Column(name="dsm_wed_time")
	private String wedTime;
	
	@Column(name="dsm_thu_time")
	private String thuTime;

	@Column(name="dsm_fri_time")
	private String friTime;

	@Column(name="dsm_sat_time")
	private String satTime;

	@Column(name="dsm_sun_time")
	private String sunTime;

}