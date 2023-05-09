package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.nsdl.telemedicine.slot.constant.SlotConstant;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ScribeDTO extends SlotConstant implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = SCRIBE_ID_EMPTY)
	private String scribeId;
	
}
