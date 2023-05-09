package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.nsdl.telemedicine.slot.constant.SlotConstant;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class WorkingTime extends SlotConstant implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = SLOT_FROM_TIME_EMPTY)
	private String startTime;
	
	@NotBlank(message = SLOT_TO_TIME_EMPTY)
	private String endTime;
	
}
