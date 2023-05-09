package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchByHealthIdDTO {
	private String healthId;
	private String healthNumber;
}
