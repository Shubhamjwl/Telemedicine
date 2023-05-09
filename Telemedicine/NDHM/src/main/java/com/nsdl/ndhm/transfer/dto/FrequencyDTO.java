package com.nsdl.ndhm.transfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FrequencyDTO {

	private String unit;
	private int value;
	private int repeats;
}
