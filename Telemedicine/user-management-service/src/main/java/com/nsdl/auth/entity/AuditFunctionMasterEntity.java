package com.nsdl.auth.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "aud_func_mstr", schema = "audit")
public class AuditFunctionMasterEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8541947597557592345L;

	@Column(name = "aud_id_pk")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long audId;
	
	@Column(name = "fm_func", nullable = false)
	private int ID;

	@Column(name = "fm_func_name_pk", length = 100)
	private String functionName;

	@Column(name = "timestamp")
	private LocalDateTime timeStamp;

	@Column(name = "user_id")
	private String userId;
	
	@PrePersist
	public void prePersist() {
		this.timeStamp = LocalDateTime.now();
	}

}
