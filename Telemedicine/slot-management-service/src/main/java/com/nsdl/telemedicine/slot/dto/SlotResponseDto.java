package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SlotResponseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String slotDate;
	
	private String dayOfWeek;

	private List<SlotDto> slots=new ArrayList<SlotDto>();
}