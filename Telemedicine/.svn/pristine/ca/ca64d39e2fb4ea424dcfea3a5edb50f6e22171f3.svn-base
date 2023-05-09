package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.nsdl.telemedicine.slot.constant.SlotConstant;
import com.nsdl.telemedicine.slot.utility.SlotTypeValidator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlotDetailsDto extends SlotConstant implements Serializable{
	private static final long serialVersionUID = 1L;

 	private int slotDuration ;
	
 	private String [] slotWorkingDays ;
	
	@Valid
	private List<WorkingTime> workingTime;
	
 	private int repetForMonths;
 	
 	private int consultAmount;
 	
 	@SlotTypeValidator
 	private String slotType;
 	
 	private boolean autoRep;
 	
 	private String doctorUserID;
 
}