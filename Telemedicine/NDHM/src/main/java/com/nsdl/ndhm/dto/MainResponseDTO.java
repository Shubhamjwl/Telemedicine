package com.nsdl.ndhm.dto;

import java.io.Serializable;
import java.util.List;

import com.nsdl.ndhm.exception.ErrorsDTO;

import lombok.Data;

@Data
public class MainResponseDTO<T> implements Serializable {
	private static final long serialVersionUID = 3384945682672832638L;

	private String id;

	private String version;

	private String responsetime;

	private T response;

	private boolean status;

	private List<ExceptionJSONInfoDTO> errors;  
}
