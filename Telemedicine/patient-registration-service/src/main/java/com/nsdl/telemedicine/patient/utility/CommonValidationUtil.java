package com.nsdl.telemedicine.patient.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.repository.PatientPersonalDetailsRepository;

@Component("patientCommonValidation")
public class CommonValidationUtil implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	@Autowired
	private PatientPersonalDetailsRepository patientDetailsRepo;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private String MOBILE_PATTERN = "^[7-9][0-9]{9}$";

	private String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

	private String USER_ID_PATTERN = "^[A-Za-z]([ ]?[A-Za-z0-9]+)*$";

	// private String FULL_NAME_PATTERN = "^[A-Za-z]([\\\\']?[A-Za-z]+)*(
	// [A-Za-z]([\\\\']?[A-Za-z]+)*)*$";

	private static String FULL_NAME_PATTERN = "^[ A-Za-z]+[ A-Za-z'-.]*$";

	private static String DOB_PATTERN = "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";

	public boolean validateEmail(String emailId) {
		if (emailId != null) {
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(emailId);
		}
		return matcher.matches();
	}

	public boolean validateMobileNo(Long mobileNo) {
		if (mobileNo != null) {
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(mobileNo.toString());
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

	public boolean validateUserID(String ptUserID) {
		if (ptUserID != null) {
			pattern = Pattern.compile(USER_ID_PATTERN);
			matcher = pattern.matcher(ptUserID);
		}
		return matcher.matches();
	}

	public boolean validateUserName(String ptFullName) {
		if (ptFullName != null) {
			pattern = Pattern.compile(FULL_NAME_PATTERN);
			matcher = pattern.matcher(ptFullName);
		}
		return matcher.matches();
	}

	public boolean validateDob(String dob) {
		if (dob != null) {
			pattern = Pattern.compile(DOB_PATTERN);
			matcher = pattern.matcher(dob);
		}
		return matcher.matches();
	}

	public boolean checkUniqueMobileNumber(Long mobileNumber) {

		PatientPersonalDetailEntity patientExist = patientDetailsRepo.existsByPtMobNo(mobileNumber.toString());
		if (patientExist == null) {
			return true;
		} else
			return false;
	}

}
