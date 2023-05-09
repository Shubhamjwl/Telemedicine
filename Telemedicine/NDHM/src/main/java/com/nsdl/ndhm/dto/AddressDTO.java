package com.nsdl.ndhm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {

	private String line;

	private String district;

	private String state;

	private String pincode;

}
