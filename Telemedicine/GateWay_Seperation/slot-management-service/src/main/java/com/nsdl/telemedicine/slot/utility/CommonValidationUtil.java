package com.nsdl.telemedicine.slot.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CommonValidationUtil implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private String MOBILE_PATTERN = "^[7-9][0-9]{9}$";
	
	private String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	
	private String NUMBER_PATTERN="[+]?\\d+";
	
	private String SLOT_TIME_PATTERN="^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])-(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$";

	public boolean validateEmail(String emailId) {
		if (emailId != null) {
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(emailId);
		}
		return matcher.matches();
	}

	public boolean validateMobileNo(Long mobileNo) {
		if(mobileNo != null) {
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(mobileNo.toString());
		}
		return matcher.matches();
	}
	public boolean validateNumber(String numberString) {
		if(numberString != null) {
			pattern = Pattern.compile(NUMBER_PATTERN);
			matcher = pattern.matcher(numberString.toString());
		}
		return matcher.matches();
	}
	
	public boolean validatePassword(String pwd) {
		if(pwd != null) {
			pattern = Pattern.compile(PASSWORD_PATTERN);
			matcher = pattern.matcher(pwd);
		}
		return matcher.matches();
	}
	public boolean validateSlotTime(String slotTime) {
		if(slotTime != null) {
			pattern = Pattern.compile(SLOT_TIME_PATTERN);
			matcher = pattern.matcher(slotTime);
		}
		return matcher.matches();
	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub

	}

}
