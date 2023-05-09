package com.nsdl.ndhm.dto.error;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnprocessableErrorDTO {
	private String code;
	private String message;
	private ArrayList<ErrorDetailDTO> details;
}
