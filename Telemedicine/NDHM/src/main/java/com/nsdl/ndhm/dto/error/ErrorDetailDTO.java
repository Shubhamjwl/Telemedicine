package com.nsdl.ndhm.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetailDTO {
	private String message;
	private String code;
	private AttributeDTO attribute;
}
