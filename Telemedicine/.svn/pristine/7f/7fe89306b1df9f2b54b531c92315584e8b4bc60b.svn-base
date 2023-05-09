package com.nsdl.otpManager.entity;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.sql.Timestamp;


/**
 * The persistent class for the email_sms_template_dtls database table.
 * 
 */
@Entity
@Table(name="email_sms_template_dtls", schema = "master")
@Data
public class EmailSmsTemplateDtl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="estd_template_id")
	private Integer templateId;

	@Column(name="estd_created_by")
	private String createdBy;

	@Column(name="estd_created_tmstmp")
	private Timestamp createdTmstmp;

	@Column(name="estd_email_sms_content")
	private String emailSmsContent;

	@Column(name="estd_isactive_flg")
	private String isActiveFlg;

	@Column(name="estd_modified_by")
	private String modifiedBy;

	@Column(name="estd_modified_tmstmp")
	private Timestamp modifiedTmstmp;

	@Column(name="estd_name")
	private String templateName;

	@Column(name="estd_subject")
	private String subject;

	@Column(name="estd_temp_type")
	private String templateType;

	


	
}