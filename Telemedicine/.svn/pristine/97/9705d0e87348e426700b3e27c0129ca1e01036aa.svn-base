package com.nsdl.telemedicine.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "marketplace_order_dtls", schema = "appointment")
public class MarketPlaceDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mod_id_pk", nullable = false, unique = true)
	private Long modIdPk;

	@Column(name = "mod_transt_id", nullable = true)
	private String modTranstIid;

	@Column(name = "mod_dr_email", nullable = true)
	private String modDrEmail;
	
	//@Column(name = "mod_dr_user_id", nullable = true)
	//private String modDrUserId;

	@Column(name = "mod_pt_name", nullable = true)
	private String modPtNname;

	@Column(name = "mod_pt_mobile", nullable = true)
	private String modPtMobile;

	@Column(name = "mod_order_id ", nullable = true)
	private String modOrderId;

	@Column(name = "mod_order_date", nullable = true)
	private Date modOrderDate;
	
	@Column(name = "mod_specialist_doctor_name", nullable = true)
	private String modSpecialistDoctorName;
	
	@Column(name = "mod_product_name", nullable = true)
	private String modProductName;

	@Column(name = "mod_product_price", nullable = true)
	private Double modProductPrice;

	@Column(name = "mod_order_prescription_path", nullable = true)
	private String modOrderPrescriptionPath;
	
	@Column(name = "mod_order_prescription_text", nullable = true)
	private String modOrderPrescriptionText;

	@Column(name = "modOrderStatus ", nullable = true)
	private String modOrderStatus;

	@Column(name = "mod_creation_tmstmp", nullable = true)
	private Timestamp modCreationTmstmp;

	@Column(name = "mod_creation_by", nullable = true)
	private String modCreationBy;

	@Column(name = "mod_modify_tmstmp ", nullable = true)
	private Timestamp modModifyTmstmp;

	@Column(name = "mod_modify_by", nullable = true)
	private String modModifyBy;

}
