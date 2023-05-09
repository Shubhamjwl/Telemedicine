package com.nsdl.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FunctionListDtls {
	private String functionName;
	private String functionUrl;
}
