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

@Data
@Entity
@Table(name="aud_dr_slot_mstr", schema = "audit")
public class AudDrSlotMstr implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="adsm_id_pk")
	private Integer aIdPk;
	
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

	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;
	
	@Column(name = "user_id")
	private String user_id;

	public AudDrSlotMstr(DrSlotMstr drSlotMstr, String user_id) {
		super();
		this.idPk =drSlotMstr.getIdPk();
		this.autoRep = drSlotMstr.getAutoRep();
		this.createdBy = drSlotMstr.getCreatedBy();
		this.createdTmstmp = drSlotMstr.getCreatedTmstmp();
		this.drUserIdFk = drSlotMstr.getDrUserIdFk();
		this.fri = drSlotMstr.getFri();
		this.fromMonth = drSlotMstr.getFromMonth();
		this.isactive = drSlotMstr.getIsactive();
		this.modifiedBy = drSlotMstr.getModifiedBy();
		this.modifiedTmstmp = drSlotMstr.getModifiedTmstmp();
		this.mon = drSlotMstr.getMon();
		this.optiVersion = drSlotMstr.getOptiVersion();
		this.repForMonth = drSlotMstr.getRepForMonth();
		this.sat = drSlotMstr.getSat();
		this.slotDuration = drSlotMstr.getSlotDuration();
		this.sun = drSlotMstr.getSun();
		this.thu = drSlotMstr.getThu();
		this.toMonth = drSlotMstr.getToMonth();
		this.tue = drSlotMstr.getTue();
		this.wed = drSlotMstr.getWed();
		this.timestamp = LocalDateTime.now();
		this.user_id = user_id;
	}
	public AudDrSlotMstr() {
		super();
	}
	

}

	
