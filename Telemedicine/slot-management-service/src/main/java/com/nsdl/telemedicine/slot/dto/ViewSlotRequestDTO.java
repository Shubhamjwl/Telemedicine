package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.nsdl.telemedicine.slot.constant.SlotConstant;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ViewSlotRequestDTO extends SlotConstant implements Serializable {

	 private static final long serialVersionUID = 1L;

	 @NotBlank(message = DR_REG_ID_EMPTY)
	 private String drRegID;

	 @NotBlank(message = SLOT_DATE)
     private String slotDate;
     
}
