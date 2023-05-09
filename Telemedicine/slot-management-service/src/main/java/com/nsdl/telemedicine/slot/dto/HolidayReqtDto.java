package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.nsdl.telemedicine.slot.constant.SlotConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HolidayReqtDto extends SlotConstant implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = HOLIDY_DATE_EMPTY)
 	private LocalDate [] holidayDate ;
	
	@NotBlank(message = IS_HOLDAY_FLAG_EMPTY)
	@Pattern(regexp = "^(true|false)$", message = IS_HOLDAY_FLAG_EMPTY)
	private String isHoliday;
	
	private String holidayReason;
 
}