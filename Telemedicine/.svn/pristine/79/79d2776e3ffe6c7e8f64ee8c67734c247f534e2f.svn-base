package com.nsdl.telemedicine.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "pt_reg_dtls", schema = "registration")
public class PatientPersonalDetailEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "prd_id_pk", nullable = false)
	private Integer id;

	@Column(name = "prd_pt_name", nullable = false , length = 100)
	@NotNull(message = "Full Name is Mandatory")
	private String ptFullName;

	@Column(name = "prd_mobile_no", nullable = false , unique = true , length = 10)
	@NotNull(message = "Mobile No Can not Duplicate")
	private Long ptMobNo;

	@Column(name = "prd_email" , length = 50)
	private String ptEmail;

	@Column(name = "prd_user_id", nullable = false , unique = true , length = 50)
	@NotNull(message = "UserID is Mandatory")
	private String ptUserID;

}
