package com.nsdl.authenticate.dto;

import lombok.Data;

@Data
public class RequestWrapper<T> {

	private String id;
	private String version;
	private T request;
}
