package com.nsdl.telemedicine.consultancy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScribeDtls {

	private String scrbFullName;

	private String scrbMobileNo;

	private String scrbEmailID;
	
	private String scrbUserId;
	
	private String isDefaultScribe;
	
	private String profilePhoto;

}
