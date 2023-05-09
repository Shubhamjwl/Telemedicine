package com.nsdl.ndhm.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("healthIdCommonValidation")
public class CommonValidationUtil implements Validator {
	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private String MOBILE_PATTERN = "^[7-9][0-9]{9}$";

	
	private String OTP_PATTERN = "^[0-9]{6,6}$";
	
	private String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

	public boolean validateEmail(String emailId) {
		if (emailId != null) {
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(emailId);
		}
		return matcher.matches();
	}

	public boolean validateMobileNo(String mobileNo) {
		if (mobileNo != null) {
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(mobileNo);
		}
		return matcher.matches();
	}

	public boolean validatePassword(String pwd) {
		if (pwd != null) {
			pattern = Pattern.compile(PASSWORD_PATTERN);
			matcher = pattern.matcher(pwd);
		}
		return matcher.matches();
	}

	public boolean validateScribePhoto(String scribePhoto) {

		return scribePhoto != null ? true : false;
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
	
	public boolean validateOtp(String otp) {
		if (otp != null) {
			pattern = Pattern.compile(OTP_PATTERN);
			matcher = pattern.matcher(otp);
		}
		return matcher.matches();
	}

}
