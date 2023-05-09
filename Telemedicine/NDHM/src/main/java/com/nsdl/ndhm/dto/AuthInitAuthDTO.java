package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthInitAuthDTO {
	 private String transactionId;
	 private String mode;
	 private AuthInitMetaDTO meta;
}
