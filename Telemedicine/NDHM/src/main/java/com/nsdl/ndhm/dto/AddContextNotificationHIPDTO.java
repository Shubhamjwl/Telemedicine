package com.nsdl.ndhm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddContextNotificationHIPDTO {
	private int id;

	@JsonCreator
	public AddContextNotificationHIPDTO(@JsonProperty("id") int id) {
		this.id = id;
	}
}
