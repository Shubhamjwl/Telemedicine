package com.nsdl.ndhm.dto.datareport;

import com.nsdl.ndhm.dto.ExceptionJSONInfoDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseWrapper<T> implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;
	private String version;
    private String responsetime;
	private boolean status;
	private T response;
	private List<ExceptionJSONInfoDTO> errors = new ArrayList<>();
}
