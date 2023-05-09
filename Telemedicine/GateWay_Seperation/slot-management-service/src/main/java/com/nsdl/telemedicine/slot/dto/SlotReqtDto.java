package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.nsdl.telemedicine.slot.constant.SlotConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlotReqtDto extends SlotConstant implements Serializable{
	private static final long serialVersionUID = 1L;

 	private int slotDuration ;
	
 	private String [] slotWorkingDays ;
	
	@Valid
	private List<WorkingTime> workingTime;
	
 	private int repetForMonths;
 	
 	private int consultAmount;
 	
 	private boolean autoRep;
 
}