package com.nsdl.telemedicine.patient.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionJSONInfoDTO implements Serializable {



	private static final long serialVersionUID = 3999014525078508265L;

	private String errorCode;

	private String message;

	public ExceptionJSONInfoDTO() {

	}

	public ExceptionJSONInfoDTO(String errorCode, String message) {
		//System.out.println(errorCode+" "+message+" in ExceptionJSONInfoDTO");
		this.errorCode=errorCode;
		this.message = message;
		//System.out.println(this.errorCode+" "+this.message+" in ExceptionJSONInfoDTO");
	}


}
