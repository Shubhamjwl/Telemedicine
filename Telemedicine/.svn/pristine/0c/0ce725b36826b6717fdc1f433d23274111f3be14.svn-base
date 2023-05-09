package com.nsdl.ndhm.exception;

import java.util.ArrayList;
import java.util.List;

import com.nsdl.ndhm.constant.HealthIdCreationConstands;
import com.nsdl.ndhm.dto.ExceptionJSONInfoDTO;
import com.nsdl.ndhm.dto.HealthIDResp;
import com.nsdl.ndhm.dto.MainResponseDTO;

public class CustomErrorUtils {

	private CustomErrorUtils() {
		throw new IllegalStateException("Utility class");
	}

	static List<ExceptionJSONInfoDTO> listOfExceptions = null;
	static ExceptionJSONInfoDTO exceptionJSONInfoDTO = null;
	static MainResponseDTO<HealthIDResp> mainResponse = null;

	/*
	 * exception for first name
	 */
	public static MainResponseDTO<HealthIDResp> throwExceptionForHealthIdFirstName() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.USER_FIRSTNAME_VALIDATE.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.USER_FIRSTNAME_VALIDATE.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	/*
	 * exception for last name
	 */
	public static MainResponseDTO<HealthIDResp> throwExceptionForHealthIdLastName() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.USER_LASTNAME_VALIDATE.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.USER_LASTNAME_VALIDATE.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;
	}

	/*
	 * exception for email id
	 */
	public static MainResponseDTO<HealthIDResp> throwExceptionForHealthIdEmail() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.EMAIL_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.EMAIL_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	/*
	 * exception for password
	 */
	public static MainResponseDTO<HealthIDResp> throwExceptionForHealthIdPassword() {
		mainResponse = new MainResponseDTO<>();
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.PASSWORD_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.PASSWORD_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		mainResponse.setErrors(listOfExceptions);
		return mainResponse;

	}

	/*
	 * exception for mobile No
	 */
	public static List<ExceptionJSONInfoDTO> throwExceptionForHealthIdMobileNo() {

		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.MOBILE_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.MOBILE_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		return listOfExceptions;

	}

	public static List<ExceptionJSONInfoDTO> throwExceptionForTxnId() {
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.TXNID_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.TXNID_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		return listOfExceptions;
	}
	
	public static List<ExceptionJSONInfoDTO> throwExceptionForAadhaar() {
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.AADHAAR_EXCEPTION_MSG.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.AADHAAR_EXCEPTION_MSG.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		return listOfExceptions;
	}
	
	public static List<ExceptionJSONInfoDTO> throwExceptionForOtp() {
		listOfExceptions = new ArrayList<>();
		exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode(HealthIdCreationConstands.OTP_FAILURE.getCode());
		exceptionJSONInfoDTO.setMessage(HealthIdCreationConstands.OTP_FAILURE.getMsg());
		listOfExceptions.add(exceptionJSONInfoDTO);
		return listOfExceptions;
	}
}
