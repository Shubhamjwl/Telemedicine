package com.nsdl.telemedicine.dto;

import lombok.Data;

@Data
public class RequestWrapperDTO<T> {

	private String id;

	private String version;

	private String requesttime;

	private T request;

}
