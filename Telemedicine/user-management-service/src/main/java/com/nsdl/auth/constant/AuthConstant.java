package com.nsdl.auth.constant;

public class AuthConstant {

	public final static String USERPWD_SUCCESS_MESSAGE = "User validated successfully";

	//public final static String INVALID_USER_ERROR = "Please enter valid Username / Mobile Number";
	
	public final static String INVALID_USER_ERROR = "\u2022 If you are a doctor, please use Sign Up link to register yourself and our team will connect with you to complete on-boarding and verification process."+System.lineSeparator()+"\u2022 If you are a patient, please note that you will be able to login if your doctor uses Protean Clinic and has registered you";

	public final static String USER_LOGOUT_SUCCESS = "You have logged out successfully";

	public final static String USER_LOGOUT_ERROR = "Error while logout.Please try again";

	public final static String INCORRECT_PASSWORD_ERROR = "Login failed due to invalid credentials.Please try again.";

	public final static String PWD_EXPIRED_ERROR = "Password has Expired, please set new password";

	public final static String FAILURE_AND_FREEZED_MESSAGE = "User Blocked.Please try after some time";

	public final static String ACTIVE = "ACTIVE";

	public final static String DEACTIVE = "DEACTIVE";

	public final static String USER_ALREADY_LOGGED_IN = "User already logged into another machine";

	public final static String CONFIRM_PASSWORD_MISMATCH_ERROR = "New Password and Confirm Password should be same. Please enter password properly";

	public final static String PASSWORD_MATCH_ERROR = "New Password should not Match with last 3 passwords";

	public final static String PWD_RESET_SUCCESS = "Your password have been changed successfully. Request you to please login again using the new password.";

	public final static String INVALID_EMAIL_FORMAT = "Enter valid email";

	public final static String INVALID_MOBILE_FORMAT = "Enter valid Mobile Number";

	public final static String INVALID_PASSWORD_FORMAT = "Password Should be Minimum 8 characters and contain at least one uppercase letter, one lowercase letter, one number and one special character";

	public final static String USERID_ALREADY_EXISTS = "User already Exists for given User Id";

	public final static String MOBILE_ALREADY_EXISTS = "User already Exists for given Mobile Number";

	public final static String USER_CREATE_SUCCESS = "User Created Successfully.";

	public final static String USER_CREATE_FAIL = "Please try again.";

	public final static String USER_NOT_LOGIN = "User is not Logged in";

	public final static String ROLE_FUNCTION_DATA_NOT_FOUND = "Role/Function Data Not found";

	public final static String INACTIVE_USER_ERROR = "User is not Active";

	public final static String USER_ALREADY_EXIST_FOR_ADMIN = "User Already Exists for the given User Type : ADMIN";

	public final static String OPERATION_NOT_ALLOWED = "Operation is not allowed for this User";

	public final static String ACTIVE_DEACTIVE_SUCCESS = "Activation/Deactivation Success";

	public final static String ACTIVE_DEACTIVE_FAILED = "Activation/Deactivation Failed";

	public final static String ROLE_FUNCTION_ALREADY_ACTIVE = "Requested role Function mapping is already Active";

	public final static String ROLE_FUNCTION_ALREADY_DEACTIVE = "Requested role Function mapping is already Deactive";

	public final static String USER_ALREADY_ACTIVE = "Requested user is already Active";

	public final static String USER_ALREADY_DEACTIVE = "Requested user is already Deactive";

	public final static String INVALID_OPERATION = "Operation not supported";

	public final static String INVALID_FUNCTION_ROLE = "Invalid given function role mapping. No Data Found";

	public final static String ROLE_EXISTS = "Role %s Already Exists";

	public final static String ROLE_NOT_EXISTS = "Requested Role %s not found in system";

	public final static String ROLE_CREATE_SUCCESS = "Role Created Successfully";

	public final static String ROLE_DELETE_SUCCESS = "Role Deleted Successfully";

	public final static String ROLE_DELETE_FAILED = "Role Deletion Failed";

	public final static String ROLE_ALREADY_DELETED = "Requested Role %s is already Deleted";

	public final static String ROLE_NOT_ACTIVE_OR_DELETED = "Requested Role %s is not Active or is Deleted";

	public final static String ROLE_UPDATE_SUCCESS = "Role Updated Successfully";

	public final static String ROLE_UPDATE_FAILED = "Role : %s Updation Failed";

	public final static String FORGOT_PASS_TEMPLATE = "forgot";

	public final static String API_ID = "1";

	public final static String API_VERSION = "V1";

	public final static String API_METHOD = "POST";

	public final static String DOCTOR_USER_TYPE = "DOCTOR";
	
	public final static String SYSTEM_USER_TYPE = "SYSTEMUSER";

	public final static String TECHNICAL_EXCEPTION = "Technical Error Occurred.Please try again later";

	public final static String EMAIL_SEND_SUCCESS = "A system generated password have been sent to your registered Email ID / Mobile no.Request you to login using the same and change the password at the first attempt.";

	public final static String LEVEL_ONE_SUCCESS = "Doctor verified successfully";

	public final static String LEVEL_TWO_SUCCESS = "Doctor verified successfully";

	public final static String DOCTOR_VERIFICATION_FAILED = "Please try again";

	public final static String INVALID_DOCTOR_VERIFICATION_STATUS = "Invalid Doctor verification Status code %s";

	public final static String DR_ALREADY_VERIFIED = "Doctor already verified";

	public final static String DR_NOT_FOUND = "No record available for the Doctor ID - { %s }";
	
	public final static String PT_NOT_FOUND = "No record available for the Patient ID - { %s }";

	public final static String DR_REJECTION_SUCCESS_MESSAGE = "Doctor de-registered successfully.";

	public final static String DR_REJECT_EMPTY_REASON_ = "Please enter a valid reason.";

	public final static String NOTIFY_BOTH_SEND_TYPE = "both";

	public final static String DR_NOTIFY_TEMPLATE_TYPE_VERIFY = "verify";

	public final static String DR_NOTIFY_TEMPLATE_TYPE_REJECT = "reject";

	public final static String UPDATE_USR_DETAILS_SUCCESS = "User Details Updated Successfully";

	public final static String UPDATE_DTLS_NOT_PRESENT = "User details are not present in request for updation";

	public final static String FUNCTION_ID_NOT_FOUND = "Requested Function %s not found in system";

	public final static String FUNCTION_NOT_ACTIVE_OR_DELETED = "Requested Function %s is not Active or is Deleted";

	public final static String FUNCTION_EXISTS = "Function %s Already Exists";
	
	public final static String FUNCTION_UPDATE_SUCCESS = "Function Updated Successfully ";
	
	public final static String FUNCTION_UPDATE_FAILED = "Function : %s Updation Failed";
	
	public final static String RECORD_NOT_FOUND = "Record not Found";
	
	public final static String DOCTOR_LIST_EMPTY_REQUEST = "Doctors list empty in request";
	
	public final static String FUNCTION_ALREADY_DELETED = "Requested function %s is already Deleted";
	
	public final static String FUNCTION_DELETE_SUCCESS = "Function Deleted Successfully";

	public final static String FUNCTION_DELETE_FAILED = "Function Deletion Failed";
	
	public final static String SOMETHING_WENT_WRONG = "Something went wrong.Please try after some time";

	public final static String OTP_VERIFICATION_FAILED = "OTP verification failed.";
	
	public final static String INVALID_OTP = "Invalid OTP value";
	
	public final static String UNABLE_TO_CONNECT_OTP_VERIFICATION_SERVICE = "Unable to connect to OTP verification service.";
	
	public final static String PATIENT_EXISTS = "Patient details are not available";
	
	public final static String NO_DATA_FOUND = "Data Not Found";
	
	public final static String CRITERIA_TYPE_INVALID = "Criteria Type Invalid";
	
	public final static String CRITERIA_STATUS="Updated Successfully";
	
	public final static String OLDPASSWOD_MISMATCH_ERR_CODE = "Old password is incorrect";

	public final static String PWD_CHANGED_SUCCESS = "Your password have been updated successfully. Please use the new password for accessing Protean Clinic going forward.";
	
	public final static String LOGGEDIN = "loggedIn";
	
	public final static String REGISTER = "Register";
	
	public final static String DATE_ERROR = "Please select from-to date";
	
	public final static String[] CATEGORY_NAME = { "Doctor website link", "Appointment book link",
			"Pre-Assessment link", "Routine Diagnostic Services", "Specialist", "Specialized services" };
	
}
