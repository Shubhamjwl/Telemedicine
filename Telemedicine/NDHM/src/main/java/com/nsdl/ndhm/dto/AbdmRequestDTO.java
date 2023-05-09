package com.nsdl.ndhm.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AbdmRequestDTO<T> implements Serializable {

	private static final long serialVersionUID = 3384945682672832638L;

	private String requestId;

	private String timestamp;

	private T request;
}
