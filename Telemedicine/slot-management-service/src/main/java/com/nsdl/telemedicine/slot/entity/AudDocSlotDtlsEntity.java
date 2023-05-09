package com.nsdl.telemedicine.slot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.nsdl.telemedicine.slot.utility.DateUtils;

import lombok.Data;

@Data
@Entity
@Table(name = "aud_dr_slot_dtls", schema = "audit")
public class AudDocSlotDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "dsd_id_pk")
	private Long dsdIdPk;
	
	@Column(name = "dsd_dr_user_id_fk", nullable = false)
	private String drUserIdFk;
	
	@Column(name = "dsd_slot", nullable = false)
	private String slot;

	@Column(name = "dsd_Slot_date", nullable = false)
	private Date slotDate;

	@Column(name = "dsd_consul_fee", nullable = false)
	private Integer consulFee;

	@Column(name = "dsd_isactive", nullable = false)
	private Boolean isactive;

	@Column(name = "dsd_created_by")
	private String createdBy;

	@Column(name = "dsd_created_tmstmp", nullable = false)
	private LocalDateTime createdTmstmp;

	@Column(name = "dsd_modified_by")
	private String modifiedBy;

	@Column(name = "dsd_modified_tmstmp", nullable = false)
	private LocalDateTime modifiedTmstmp;

	@Version
	@Column(name = "dsd_opti_version")
	private int optiVersion;
	
	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;
	
	@Column(name = "user_id")
	private String user_id;

	public AudDocSlotDtlsEntity(DocSlotDtlsEntity entity, String userId) {
		super();
		this.drUserIdFk = entity.getDrUserIdFk();
		this.slot = entity.getSlot();
		this.slotDate = entity.getSlotDate();
		this.consulFee = entity.getConsulFee();
		this.isactive = entity.getIsactive();
		this.createdBy = entity.getCreatedBy();
		this.createdTmstmp = entity.getCreatedTmstmp();
		this.modifiedBy = entity.getModifiedBy();
		this.modifiedTmstmp = entity.getModifiedTmstmp();
		this.optiVersion = entity.getOptiVersion();
		this.timestamp = DateUtils.getCurrentLocalDateTime();
		this.user_id = userId;
	}

	public AudDocSlotDtlsEntity() {
		super();
	}
	
	
}
