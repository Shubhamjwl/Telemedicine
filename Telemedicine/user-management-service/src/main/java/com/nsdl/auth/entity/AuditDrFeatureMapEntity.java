package com.nsdl.auth.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name="aud_dr_feature_map_dtls", schema = "audit")
public class AuditDrFeatureMapEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator ="audit_seq_generator")
	@SequenceGenerator(name ="audit_seq_generator",sequenceName ="audit.aud_dr_feature_map_dtls_seq",allocationSize = 1)
	@Column(name="adfmd_id")
	private Integer adfmdId;

	@Column(name="adfmd_dr_userid")
	private String adfmdDrUserId;

	@Column(name="adfmd_category_name")
	private String adfmdCategoryName;

	@Column(name="adfmd_dr_emailid")
	private String adfmdDrEmail;
	
	@Column(name="adfmd_created_tmpstmp")
	private Timestamp adfmdCreatedTime;

	@Column(name="adfmd_updated_tmpstmp")
	private Timestamp adfmdUpdatedTime;
	
	@Column(name="adfmd_flag")
	private Boolean adfmdFlag;
	
}
