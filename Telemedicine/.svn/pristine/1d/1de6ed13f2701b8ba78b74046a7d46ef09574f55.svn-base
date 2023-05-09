package com.nsdl.ndhm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CareContextTMRequsetDTO {
	private String healthId;

	@JsonCreator
	public CareContextTMRequsetDTO(@JsonProperty("healthId") String healthId) {
		this.healthId = healthId;
	}
}

