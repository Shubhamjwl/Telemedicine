package com.nsdl.telemedicine.slot.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nsdl.telemedicine.slot.constant.SlotConstant;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SaveSlotRequestDTO extends SlotConstant implements Serializable {

	 private static final long serialVersionUID = 1L;

	 @NotBlank(message = DR_REG_ID_EMPTY)
	 private String drRegID;

	 @NotBlank(message = SLOT_DATE)
     private String slotDate;
     
	 @NotBlank(message = SLOT_TIME_EMPTY)
	 private String slotTime;
     
	 @NotBlank(message = CONSULT_AMOUNT_EMPTY)
	 @Size(max=10)
     private String consultAmount;

	 private String isActive;
}
