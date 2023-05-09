package com.nsdl.telemedicine.master.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionJSONInfoDTO implements Serializable {
	private static final long serialVersionUID = 3999014525078508265L;

	private String errorCode;

	private String message;
}
