package com.nsdl.ndhm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcknowledgementDTO {
	private String status;

	@JsonCreator
	public AcknowledgementDTO(@JsonProperty("status") String status) {
		this.status = status;
	}
}
