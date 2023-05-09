package com.nsdl.telemedicine.master.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MasterResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String masterName;

 	private String masterUnit;

 	private String masterValue;
}