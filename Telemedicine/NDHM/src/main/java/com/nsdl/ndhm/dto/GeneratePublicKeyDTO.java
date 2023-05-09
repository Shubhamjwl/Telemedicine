package com.nsdl.ndhm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneratePublicKeyDTO {

	private String publicKey;

	@JsonCreator
	public GeneratePublicKeyDTO(@JsonProperty("publicKey") String publicKey) {
		this.publicKey = publicKey;
	}
}
