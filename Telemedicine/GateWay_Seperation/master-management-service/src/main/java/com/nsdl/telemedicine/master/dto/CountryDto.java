package com.nsdl.telemedicine.master.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.nsdl.telemedicine.master.constant.MasterConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDto extends MasterConstant implements Serializable{

	 private static final long serialVersionUID = 1L;
	
	 @NotBlank(message=COUNTRY_NAME_EMPTY)
	 private String countryName;

}
