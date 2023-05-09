package com.nsdl.appointment.dto;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReschduleApptDetails {
	
	@NotNull(message = "date must not be null")
	@Valid
	private Date reschduleappDate;
	
	@NotNull(message = "date must not be null")
	@Valid
	private String reschduleappSlot;

}
