package com.nsdl.telemedicine.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "dr_reg_dtls", schema = "registration")
public class DocRegDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "drd_id_pk")
	private Long drdIdPk;

	@Column(name = "drd_consul_fee")
	private Integer drdConsulFee;

	@Column(name = "drd_dr_name")
	private String drdDrName;

	@Column(name = "drd_user_id")
	private String drdUserId;

	@Column(name = "drd_email")
	private String drdEmail;

}