package com.nsdl.ndhm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DisLinkCareContextDTO {
	private String referenceNumber;

	@JsonCreator
	public DisLinkCareContextDTO(@JsonProperty("referenceNumber") String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
}
