package com.nsdl.telemedicine.scribe.constants;

public enum ScribeRegConstants {
	
	OTP_SUCCESS("OTP_SUCCESS", "Otp has been sent to your Registered Mobile : @mobileNo And Email : @emailID registered with Virtual App and will be valid for 10 minutes. If you have not received the OTP,Please click on 'Regenerate OTP' button."),
	OTP_FAILURE("OTP_FAIL", "Registration and Otp service failed due to wrong info."),
	STATUS_SUCCESS("SUCCESS", "Success"),
	STATUS_FAIL("FAIL", "Fail"),
	SERVICE_SUCCESS_STATUS(true),
	SERVICE_FAIL_STATUS(false),
	MOBILE_EXCEPTION_MSG("MBLE_EXC", "Invalid Mobile-Number"),
	EMAIL_EXCEPTION_MSG("EMAIL_EXC", "Invalid EMail-ID"),
	PASSWORD_EXCEPTION_MSG("PSWD", "Password is not in correct format"),
	USERID_EXCEPTION_MSG("USRID", "Scribe user ID is null or empty"),
	SCRIBE_PHOTO_MSG("SCRB_PHOTO", "Scribe profile photo is mandatory"),
	SCRIBE_PATH_PHOTO("SCRB_PHOTO_PATH", "Failed to store scribe profile photo"),
	SCRIBE_SAVE_FAIL("SCRD_FAIL", "Scribe details failed to store"),
	SCRIBE_FULLNAME_VALIDATE("SCRB_NAME", "Scribe name should not be blank"),
	ACTIVE_STATUS("Y"),
	DEACTIVE_STATUS("N"),
	SCRIBE("SCRIBE"),
	OTP_FOR("reg"),
	OTP_SEND_TYPE("both"),
	OTP_GENERATE_TYPE("same"),
	USER_ID_EXIST("USER_ID_EXIST", "UserID already exist"),
	MOBILE_EXIST("MBL_EXIST", "Mobile is already exist"),
	PROFILE_PHOTO_INVALID("PHOTO_INVALID", "Loaded scribe profile photo is not an base 64 binary data"),
	DOCTOR_USERID_EXIST("DCTR_USRID_INVALID", "Refering doctor userID is invalid"),
	SCRIBE_USERID_EXIST("SCRB_USRID_INVALID", "Scribe userID is invalid"),
	CAPTCHA_VERIFY_MSG("CAPTCHA_MSG", "Capctha Sevice failed due to wrong information"), 
	EMAILID_EXIST("EML_EXIST", "Email Id is already exist"),
	REGISTRATION_SUCCESS("REGISTRATION_SUCCESS" , "Scribe Registration Successfully"), USER_MANAGEMENT_FAIL("USER_MANAGEMENT_FAIL" , "User Management Call Failed");
	private boolean status;
	
	private String validate;
	
	private String code;
	
	private String msg;

	private ScribeRegConstants(boolean status) {
		this.status = status;
	}
	
	private ScribeRegConstants(String validate) {
		this.validate = validate;
	}
	
	private ScribeRegConstants(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public boolean isStatus() {
		return status;
	}
	
	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String getValidate() {
		return validate;
	}
	
	

}
