package com.nsdl.telemedicine.patient.constant;

public class AuthConstant {

	public final static String INVALID_PASSWORD_FORMAT = "Invalid Password Format";

	public final static String INVALID_EMAIL_FORMAT = "Invalid Email Format";

	public final static String INVALID_MOBILE_FORMAT = "Invalid Mobile-Number Format";

	public final static String INVALID_USER_ERROR = "Invalid User";

	public final static String INVALID_REQUEST = "Something went wrong, please check logs for more details";

	public final static String USERID_ALREADY_EXISTS = "User ID already Exists";

	public final static String PATIENT_REGISTRATION_FAIL = "User Creation Fail";

	public static final String OTP_FAILURE = "Could not send OTP.";

	public static final String MOBILE_NUMBER_ALREADY_REGISTERED = "Mobile Number already registered";

	public static final String USER_MANAGEMENT_API_FAIL = "User Management API Down";

	public static final String OTP_SUCCESS = "Otp has been sent to your Registered Mobile : mobileNo And Email : emailID registered with Virtual App and will be valid for 10 minutes. If you have not received the OTP,Please click on 'Regenerate OTP' button.";

	public static final String ALLERGIES = "allergies" ;
	
	public static final String CHRONICDISEASES = "chronicdiseases";
	
	public static final String CURRENTMEDICATION = "currentmedication";
	
	public static final String PASTMEDICATION = "pastmedication";
	
	public static final String INJURIES="injuries";
	
	public static final String SURGRIES="surgries";
	
	public static final String SMOKING ="";
	
	public static final String ALCOHOL ="alcohol";
	
	public static final String ACTIVITYLVL = "activitylvl";
	
	public static final String FOODPREFERENCE ="foodpreference";
	
	public static final String OCCUPATION = "occupation";
	
	public final static String UPDATE_FAILED = "Updation failed";
	
	public final static String REG_UPDATE_SUCEESS = "Updated SuccessFully";
	
	public final static String OTP_FOR = "reg";
	
	public final static String OTP_GENERATE_TYPE = "same";
	
	public final static String OTP_SEND_TYPE = "both";
	
	public final static String PATIENT = "Patient";
	
	public final static String PROFILE_PHOTO_INVALID = "Loaded patient profile photo is not an base 64 binary data";
	
	public final static String PROFILE_PHOTO_SUCCESS = "Patient profile photo successfully added";
	
	public final static String DELETE_FAILED = "Error while delete privious values";

	public final static String INVALID_PROFILE_PHOTO_DATA = "profile photo is invalid binary data or is null";
	
	public final static String patientProfileDirectory = "profilePhoto";
	
	public final static String CAPTCHA_VERIFICATION_FAILED = "Captcha verification failed";
	
	public final static String CAPTCHA_VERIFICATION_SUCCESS = "Captcha verification success";
	
	public final static String INVALID_CAPTCHA_CODE = "Invalid Captcha Code";

	public static final String INVALID_USERID = "User Id must be a alphanumeric value between 8 and 25 characters";

	public static final String INVALID_FULL_NAME = "Full Name must be an alphabetic value";

	public static final String SIZE_LIMIT = "Size limit exceed, Please selct profile photo up to 1 MB";

	public static final String EMAIL_ID_ALREADY_EXISTS = "Email Id already Exists";

	public static final String MOBILE_NUMBER_ALREADY_EXISTS = "MobileNo already Exists";
	
	public static final String NO_APPOINTMENT_FOUND = "No Appointments Found";
	
	public static final String PATIENT_DETAILS_NOT_FOUND = "Patient Details Are Not Available";
	
	private String validate;
	
	public final static String BULK_FILE_UPLOAD_FAILED = "Exception while uploading patient details excel file.";
	
	public static final String READ_BULK_FILE_FAILED = "Exception while reading patient details from excel file.";

	public AuthConstant(String validate) {
		super();
		this.validate = validate;
	}

	public String getValidate() {
		return validate;
	}
}
