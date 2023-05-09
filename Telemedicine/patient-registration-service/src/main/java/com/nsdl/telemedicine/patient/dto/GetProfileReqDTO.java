package com.nsdl.telemedicine.patient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetProfileReqDTO {
	private String xToken;

	@JsonCreator
	public GetProfileReqDTO(@JsonProperty("xToken") String xToken) {
		this.xToken = xToken;
	}
}
