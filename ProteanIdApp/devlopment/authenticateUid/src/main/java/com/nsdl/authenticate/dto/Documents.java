package com.nsdl.authenticate.dto;

import lombok.Data;

@Data
public class Documents {
	
	/** The doc type. */
	private String category;
	
	/** The doc value. */
	private String value; 

}
