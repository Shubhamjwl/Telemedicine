package com.nsdl.ndhm.transfer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurposeDTO {
	private String text ;
	private String code ;
	private Object refUri ;
}
