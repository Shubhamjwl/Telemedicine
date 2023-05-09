package com.nsdl.ndhm.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {

	private String purpose;

	private List<String> modes;

}
