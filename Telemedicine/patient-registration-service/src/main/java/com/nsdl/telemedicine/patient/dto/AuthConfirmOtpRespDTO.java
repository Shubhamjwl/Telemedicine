package com.nsdl.telemedicine.patient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AuthConfirmOtpRespDTO {
	private String token;

	@JsonCreator
	public AuthConfirmOtpRespDTO(@JsonProperty("token") String token) {
		this.token = token;
	}
}
