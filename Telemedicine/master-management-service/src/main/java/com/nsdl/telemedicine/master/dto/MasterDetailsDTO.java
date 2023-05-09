package com.nsdl.telemedicine.master.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nsdl.telemedicine.master.constant.MasterConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MasterDetailsDTO extends MasterConstant implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message=MASTER_NAME_EMPTY)
	@Size(max=100,message = MASTER_NAME_SIZE)
	private String masterName;

	@Size(max=10 ,message = MASTER_UNIT_SIZE)
 	private String masterUnit;
	
	@NotBlank(message=MASTER_VALUE_EMPTY)
	@Size(max=250 ,message = MASTER_VALUE_SIZE)
 	private String masterValue;
}