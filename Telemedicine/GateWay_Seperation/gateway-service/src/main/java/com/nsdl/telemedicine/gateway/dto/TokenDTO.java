package com.nsdl.telemedicine.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

	private String username;
	private String role;
	private String ien;
	private Integer issue_time;
	private Integer expiry_time;
}
