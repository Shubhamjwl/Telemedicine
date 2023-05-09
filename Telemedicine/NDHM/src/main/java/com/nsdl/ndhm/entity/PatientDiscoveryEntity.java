package com.nsdl.ndhm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
/*@Table(name = "discovery_dtls" , schema = "ndhm")*/
@Table(name = "discovery_dtls")
public class PatientDiscoveryEntity {
	@Id
	private String requestId;
	private String timestamp;
	@Column(columnDefinition = "TEXT")
	private String response;
	@Column(name = "isCalled", columnDefinition = "boolean default false")
	private boolean status;
}
