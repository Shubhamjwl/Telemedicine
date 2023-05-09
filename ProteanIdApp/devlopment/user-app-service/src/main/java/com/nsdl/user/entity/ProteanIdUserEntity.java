package com.nsdl.user.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "proteaniduser")
public class ProteanIdUserEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "uid", nullable = false)
	@Id
	private Long uid;

	@Column(name = "mobileid", nullable = false, unique = true)
	private String mobileId;

	@Column(name = "whitelistflag")
	private Boolean whiteListFlag;

	@Column(name = "consentflag")
	private Boolean consentFlag;

	@Column(name = "publickey")
	private String publicKey;

	@Column(name = "privatekey")
	private String privateKey;

	@Column(name = "createdtmstmp")
	private LocalDateTime createdTmsTmp;

	@Column(name = "updatedtmstmp")
	private LocalDateTime updatedTmsTmp;

}
