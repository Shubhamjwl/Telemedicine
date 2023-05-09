package com.nsdl.notification.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "audit_wallet_dtls", schema = "audit")
public class WalletEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_generator")
	@SequenceGenerator(name = "wallet_generator", sequenceName = "audit.wal_id_pk_seq", allocationSize = 1)
	@Column(name = "wal_id_pk")
	private Integer walIdPk;

	@Column(name = "wal_pat_user_id", nullable = false)
	private String walPatUserId;

	@Column(name = "wal_appt_id", nullable = false)
	private String walApptId;

	@Column(name = "wal_transt_amount", nullable = false)
	private Integer walTranstAmount;

	@Column(name = "wal_transt_by", nullable = false)
	private String walTranstBy;
	
	@Column(name = "wal_transt_type", nullable = false)
	private String walTranstType;

	@Column(name = "wal_transt_tmpstmp", nullable = false)
	private LocalDateTime walTranstTmpstmp;

}
