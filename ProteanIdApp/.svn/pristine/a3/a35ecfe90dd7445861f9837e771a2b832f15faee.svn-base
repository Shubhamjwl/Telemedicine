package com.nsdl.authenticate.id.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="uin",schema = "idrepo")
public class UinIdRepoEntity {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="uin_ref_id")
	private String uinRefId;
	
	@Column(name="uin")
	private String uin;
	
	@Column(name="uin_hash")
	private String uinHash;
	
	@Column(name="uin_data")
	private String uinData;
	
	@Column(name="uin_data_hash")
	private String uinDataHash;
	
	@Column(name="reg_id")
	private String regId;

	@Column(name="bio_ref_id")
	private String bioRefId;
	
	@Column(name="lang_code")
	private String langCode;
	
	@Column(name="status_code")
	private String statusCode;
	
	@Column(name="cr_by")
	private String createdBy;
	
	@Column(name="cr_dtimes")
	private Timestamp createdTmstmp;
	
	@Column(name="upd_by")
	private String updatedBy;
	
	@Column(name="upd_dtimes")
	private Timestamp updatedTmstmp;
	
	@Column(name="is_deleted")
	private Boolean isDeleted;
	
	@Column(name="del_dtimes")
	private Timestamp deleteTmstmp;

}
