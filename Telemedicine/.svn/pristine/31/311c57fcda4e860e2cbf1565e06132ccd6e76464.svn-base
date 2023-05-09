package com.nsdl.telemedicine.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="dr_feature_map_dtls", schema="registration")
@Data
public class FeatureCategoryMapDtlsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dfmd_id")
	private Integer dfmdId;
	
	@Column(name="dfmd_dr_userid")
	private String dfmdDrUserId;
	
	@Column(name="dfmd_category_name")
	private String dfmdCategoryName;
	
	@Column(name="dfmd_dr_emailid")
	private String dfmdDrEmailId;
	
	@Column(name="dfmd_flag")
	private Boolean dfmdFlag;
	
	@Column(name="dfmd_created_tmpstmp")
	private Timestamp dfmdCreatedTmpstmp;
	
	@Column(name="dfmd_updated_tmpstmp")
	private Timestamp dfmdUpdatedTmpstmp;
	
	

}
