package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthInitMetaDTO {
	private Object hint;
	private String expiry;
}
