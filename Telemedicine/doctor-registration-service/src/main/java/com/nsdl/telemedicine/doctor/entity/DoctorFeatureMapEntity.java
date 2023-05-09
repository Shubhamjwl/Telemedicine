package com.nsdl.telemedicine.doctor.entity;

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
@Table(name="dr_feature_map_dtls", schema = "registration",uniqueConstraints= {@UniqueConstraint(columnNames = {"dfmd_dr_userid","dfmd_category_name"})})
public class DoctorFeatureMapEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator ="seq_generator")
	@SequenceGenerator(name ="seq_generator",sequenceName ="registration.dr_feature_map_dtls_sequence",allocationSize = 1)
	@Column(name="dfmd_id")
	private Integer dfmdId;

	@Column(name="dfmd_dr_userid")
	private String dfmdDrUserId;

	@Column(name="dfmd_category_name")
	private String dfmdCategoryName;

	@Column(name="dfmd_dr_emailid")
	private String dfmdDrEmail;
	
	@Column(name="dfmd_created_tmpstmp")
	private Timestamp dfmdCreatedTime;

	@Column(name="dfmd_updated_tmpstmp")
	private Timestamp dfmdUpdatedTime;
	
	@Column(name="dfmd_flag")
	private Boolean dfmdFlag;
	
}
