package com.nsdl.telemedicine.review.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceError {

	private String code;
	
	private String message;
	
}
