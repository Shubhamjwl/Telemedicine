package com.nsdl.telemedicine.slot.utility;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nsdl.telemedicine.slot.constant.SlotConstant;

public class SlotValidation implements ConstraintValidator<SlotTypeValidator, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {


		return value != null && value.equalsIgnoreCase(SlotConstant.TELECONSULTATION)
		          || value.equalsIgnoreCase(SlotConstant.INCLINIC);
	}

}
