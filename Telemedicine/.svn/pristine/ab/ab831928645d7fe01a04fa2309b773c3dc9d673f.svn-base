package com.nsdl.telemedicine.master.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.nsdl.telemedicine.master.constant.MasterConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterRequestDto extends MasterConstant implements Serializable{
	private static final long serialVersionUID = 1L;

	private String isactiveFlg;

	@NotBlank(message = MASTER_NAME_EMPTY)
 	private String masterName;

 	private String masterUnit;

 	private String masterValue;
 	
 	private String loginId ;
 
}