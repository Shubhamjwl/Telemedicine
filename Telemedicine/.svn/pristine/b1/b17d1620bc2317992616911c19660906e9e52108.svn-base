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
@Table(name="assoc_mstr", schema = "master")
public class AssociationNameMasterEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="am_id_pk")
	private Integer assocIdPk; 
	
	@Column(name="am_name")
	private String associationName;

}
