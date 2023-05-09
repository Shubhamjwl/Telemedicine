package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AvailableSlotResDto implements Serializable, Comparable<AvailableSlotResDto> {
	private static final long serialVersionUID = 1L;

	private LocalDate slotDate;
	
	private int numberOfSlot;
	
	private int noOfBookedSlot;
	
	private int noCompletedSlot;
	
	private boolean isHoliday=false;

	private List<SlotDto> slots=new ArrayList<SlotDto>();

	@Override
	public int compareTo(AvailableSlotResDto o) {
		return this.getSlotDate().compareTo(o.getSlotDate());
	}
}