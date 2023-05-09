package com.nsdl.telemedicine.patient.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SearchByHealthIdReqDTO {
	private String healthId;

	@JsonCreator
	public SearchByHealthIdReqDTO(@JsonProperty("healthId") String healthId) {
		this.healthId = healthId;
	}
}
