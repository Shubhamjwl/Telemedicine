package com.nsdl.ndhm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddContextNotificationPatientDTO {
	private String id;

	@JsonCreator
	public AddContextNotificationPatientDTO(@JsonProperty("id") String id) {
		this.id = id;
	}
}
