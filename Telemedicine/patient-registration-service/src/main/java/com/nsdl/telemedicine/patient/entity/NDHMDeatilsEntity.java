package com.nsdl.telemedicine.patient.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ndhm_trans_dtls database table.
 * 
 */
@Entity
@Table(name="ndhm_trans_dtls",schema = "usrmgmt")
//@NamedQuery(name="NdhmTransDtl.findAll", query="SELECT n FROM NdhmTransDtl n")
public class NDHMDeatilsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="nd_id_pk")
	private Integer ndIdPk;

	@Column(name="nd_errorcode")
	private String ndErrorcode;

	@Column(name="nd_errormessage")
	private String ndErrormessage;

	@Column(name="nd_mobileno")
	private Long ndMobileno;

	@Column(name="nd_status")
	private String ndStatus;

	public NDHMDeatilsEntity() {
	}

	public Integer getNdIdPk() {
		return this.ndIdPk;
	}

	public void setNdIdPk(Integer ndIdPk) {
		this.ndIdPk = ndIdPk;
	}

	public String getNdErrorcode() {
		return this.ndErrorcode;
	}

	public void setNdErrorcode(String ndErrorcode) {
		this.ndErrorcode = ndErrorcode;
	}

	public String getNdErrormessage() {
		return this.ndErrormessage;
	}

	public void setNdErrormessage(String ndErrormessage) {
		this.ndErrormessage = ndErrormessage;
	}

	public Long getNdMobileno() {
		return this.ndMobileno;
	}

	public void setNdMobileno(Long ndMobileno) {
		this.ndMobileno = ndMobileno;
	}

	public String getNdStatus() {
		return this.ndStatus;
	}

	public void setNdStatus(String ndStatus) {
		this.ndStatus = ndStatus;
	}

}