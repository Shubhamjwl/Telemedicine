package com.nsdl.ndhm.transfer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PatientDTO {
	private String id;

	@JsonCreator
	public PatientDTO(@JsonProperty("id") String id) {
		this.id = id;
	}
}
