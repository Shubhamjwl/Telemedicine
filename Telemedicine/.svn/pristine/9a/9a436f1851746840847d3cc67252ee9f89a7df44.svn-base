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
@Table(name="document_type_mstr", schema = "master")
public class DocumentMasterEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dtm_id")
	private Integer docIdPk; 
	
	@Column(name="dtm_document_name")
	private String documentName;
}
