package com.nsdl.telemedicine.doctor.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlotDetailsDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int slotDuration;

	private String[] slotWorkingDays;

	@Valid
	private List<WorkingTime> workingTime;

	private int repetForMonths;

	private int consultAmount;

	private String slotType;

	private boolean autoRep;

	private String doctorUserID;

}