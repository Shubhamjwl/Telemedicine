package com.nsdl.telemedicine.dto;

import lombok.Data;

@Data
public class RedFlagData {
	private String dr_email;
	private String mobile;
	private String date_added;
	private String form_label;
	private String red_flag;
	private String identifier;
}
