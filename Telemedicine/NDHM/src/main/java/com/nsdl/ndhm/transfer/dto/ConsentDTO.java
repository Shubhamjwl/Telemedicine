package com.nsdl.ndhm.transfer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class ConsentDTO {
	
	private String id;
	
	@JsonCreator
	public ConsentDTO(@JsonProperty("id") String id) {
		this.id = id;
	}
}
