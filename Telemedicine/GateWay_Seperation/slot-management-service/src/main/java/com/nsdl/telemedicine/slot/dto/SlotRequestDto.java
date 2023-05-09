package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nsdl.telemedicine.slot.constant.SlotConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlotRequestDto extends SlotConstant implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotBlank(message = SLOT_FROM_TIME_EMPTY)
 	private String slotFromDateTime;

	@NotBlank(message = SLOT_TO_TIME_EMPTY)
 	private String slotToDateTime;

	@NotBlank(message = SLOT_DURATION)
	@Size(max = 3)
 	private String slotDuration ;
 
}