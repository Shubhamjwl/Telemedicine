package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import com.nsdl.telemedicine.slot.constant.SlotConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteSlotRequestDTO extends SlotConstant implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private Date slotDate;

	@NotEmpty(message = SLOT_TIME_EMPTY)
	private List<String> slotTime;

}
