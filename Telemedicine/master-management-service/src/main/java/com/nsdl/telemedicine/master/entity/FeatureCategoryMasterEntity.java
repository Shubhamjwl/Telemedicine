package com.nsdl.telemedicine.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="feature_category_mstr", schema = "master")
public class FeatureCategoryMasterEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fcm_id")
	private Integer categoryIdPk; 
	
	@Column(name="fcm_category_name")
	private String categoryName;
}
