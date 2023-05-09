package com.nsdl.auth.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CommonValidationUtil implements Validator {

//	@Value("${login.email.regex}")
//	private String emailPattern;

	private static Pattern pattern;
	private static  Matcher matcher;

	private static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static String mobilePattern = "^[7-9][0-9]{9}$";

	private static String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!~@#$%&*()-,./+=])(?=\\S+$).{8,16}$";

	private static  String smcNoPattern = "^[1-9A-Za-z]{1}[A-Za-z0-9]*$";

	private static String mciNoPattern = "^[1-9A-Za-z]{1}[A-Za-z0-9]*$";

	public static boolean validateEmail(String emailId) {
		if (emailId != null) {
			pattern = Pattern.compile(emailPattern);
			matcher = pattern.matcher(emailId);
		}
		return matcher.matches();
	}

	public static boolean validateMobileNo(Long mobileNo) {
		if (mobileNo != null) {
			pattern = Pattern.compile(mobilePattern);
			matcher = pattern.matcher(mobileNo.toString());
		}
		return matcher.matches();
	}

	public static boolean validateSmcNumber(String smc) {
		if (smc != null) {
			pattern = Pattern.compile(smcNoPattern);
			matcher = pattern.matcher(smc.toString());
		}
		return matcher.matches();
	}

	public static boolean validateMciNumber(String mci) {
		if (mci != null) {
			pattern = Pattern.compile(mciNoPattern);
			matcher = pattern.matcher(mci.toString());
		}
		return matcher.matches();
	}

	public static boolean validatePassword(String pwd) {
		if (pwd != null) {
			pattern = Pattern.compile(passwordPattern);
			matcher = pattern.matcher(pwd);
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
