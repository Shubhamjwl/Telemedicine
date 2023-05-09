package com.nsdl.telemedicine.consultancy.dto;

import java.util.List;

import lombok.Data;

@Data
public class ScribeDtlsDTO {

	private String drRegId;

	private List<ScribeDtls> scribeDtls;

}
