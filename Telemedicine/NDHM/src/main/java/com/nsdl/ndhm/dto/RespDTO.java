package com.nsdl.ndhm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespDTO {

	private String requestId;

	@JsonCreator
	public RespDTO(@JsonProperty("requestId") String requestId) {
		this.requestId = requestId;
	}
}
