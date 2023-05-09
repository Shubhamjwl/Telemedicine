package com.nsdl.telemedicine.patient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthInitRespDTO {
	private String txnId;

	@JsonCreator
	public AuthInitRespDTO(@JsonProperty("txnId") String txnId) {
		this.txnId = txnId;
	}
}
