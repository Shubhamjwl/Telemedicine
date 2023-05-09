package com.nsdl.telemedicine.doctor.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nsdl.telemedicine.doctor.dto.DeRegisterDoctorReqDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorActiveDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorDeregistrationDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorDetailsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorDetailsFetchRequestDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorRegDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.MainRequestDTO;
import com.nsdl.telemedicine.doctor.dto.MainResponseDTO;
import com.nsdl.telemedicine.doctor.dto.RegistrationResponseDTO;
import com.nsdl.telemedicine.doctor.dto.VerifyOTPForDocToDeReg;
import com.nsdl.telemedicine.doctor.loggers.DoctorLoggingClientInfo;
import com.nsdl.telemedicine.doctor.service.DoctorDeRegService;
import com.nsdl.telemedicine.doctor.utility.DateUtils;
import com.nsdl.telemedicine.doctor.utility.DoctorAuthUtil;

/**
 * @author SudipB
 *
 */
@RestController("DoctorDeRegistrationController")
@RequestMapping("${doctor.deregistration.service-version}")
//@CrossOrigin("*")
@DoctorLoggingClientInfo
public class DoctorDeRegistrationController {

	@Autowired
	private DoctorDeRegService doctorDeRegService;

	private static final Logger logger = LoggerFactory.getLogger(DoctorDeRegistrationController.class);

	/**
	 * This API would be used for getting doctor entity based on doctor name </br>
	 * 
	 * @author SudipB
	 * @param MainRequestDTO<DoctorDetailsFetchRequestDTO> this is request body
	 * @return MainResponseDTO<DoctorRegDtlsDTO> this is the response
	 */
	@GetMapping(value = "/getDoctorByName")
	public ResponseEntity<MainResponseDTO<DoctorRegDtlsDTO>> getDoctorByName(
			@RequestBody @Valid MainRequestDTO<DoctorDetailsFetchRequestDTO> request) {
		logger.info("Request for get Doctor by name is received" + request.getRequest().getDocName());
		DoctorDetailsFetchRequestDTO doctorDetailsFetchRequestDTO = request.getRequest();
		return ResponseEntity.status(HttpStatus.OK)
				.body(doctorDeRegService.getDoctorByName(doctorDetailsFetchRequestDTO.getDocName()));
	}

	/**
	 * This API would be used for de-register doctor</br>
	 * 
	 * @author SudipB
	 * @param MainRequestDTO<DeRegisterDoctorReqDTO> this is request body
	 * @return MainResponseDTO<RegistrationResponseDTO> this is the response
	 */
	@PostMapping(value = "/deRegisterDoctor")
	public ResponseEntity<MainResponseDTO<RegistrationResponseDTO>> deRegisterDoctor(
			@RequestBody @Valid MainRequestDTO<DeRegisterDoctorReqDTO> request) {
		logger.info("Request for deRegister Doctor is received with action:" + request.getRequest().getAction()
				+ "for doctor:" + request.getRequest().getDocName());
		DeRegisterDoctorReqDTO deRegisterDoctorReqDTO = request.getRequest();
		MainResponseDTO<RegistrationResponseDTO> response = doctorDeRegService.deRegisterDoctor(deRegisterDoctorReqDTO);
		response.setId(request.getId());
		response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		response.setVersion(request.getVersion());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * This API would be used for de-register doctor after OTP is verified</br>
	 * 
	 * @author SudipB
	 * @param MainRequestDTO<VerifyOTPForDocToDeReg> this is request body
	 * @return MainResponseDTO<RegistrationResponseDTO> this is the response
	 */
	@PostMapping(value = "/verifiedOTPForDocDeRegister")
	public ResponseEntity<MainResponseDTO<RegistrationResponseDTO>> verifiedOTPForDoctorToDeRegister(
			@RequestBody @Valid MainRequestDTO<VerifyOTPForDocToDeReg> request) {
		logger.info("Request for deRegister Doctor After OTP verification is received by:" + request.getRequest().getDocName()
				+ "with OTP:" + request.getRequest().getOtp());
		VerifyOTPForDocToDeReg verifyOTPForDocToDeReg = request.getRequest();
		MainResponseDTO<RegistrationResponseDTO> response = doctorDeRegService.verifiedOTPForDoctorToDeRegister(verifyOTPForDocToDeReg);
		response.setId(request.getId());
		response.setResponsetime(DateUtils.getUTCCurrentDateTimeString());
		response.setVersion(request.getVersion());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * This API would be used to get list of active doctor to deregister
	 * 
	 * @author SayaliA
	 * @return MainResponseDTO<List<DoctorActiveDTO>> this is the response
	 */
	@PostMapping(value = "/getListOfDoctorTodeRegister")
	public ResponseEntity<?> getListOfDoctorTodeRegister() {
		try{
			MainResponseDTO<List<DoctorActiveDTO>> responseWrapper = new MainResponseDTO<List<DoctorActiveDTO>>();
			responseWrapper=doctorDeRegService.getListOfDoctorTodeRegister();
			logger.info("Returning response");
			return ResponseEntity.ok().body(responseWrapper);
		}catch(Exception e)
		{
			return ResponseEntity.status(HttpStatus.OK).body(DoctorAuthUtil.getExceptionDetails(e));
		}
	
	}
	/**
	 * This API would be used to deregister list of Doctors
	 * 
	 * @author SayaliA
	 * @return MainResponseDTO<RegistrationResponseDTO> this is the response
	 */
	@PostMapping(value = "/listOfDoctorTodeRegister", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<RegistrationResponseDTO>> listOfDoctorTodeRegister(@RequestBody @Valid MainRequestDTO<DoctorDeregistrationDTO<DoctorDetailsDTO>> request) {
		MainResponseDTO<RegistrationResponseDTO> responseWrapper = new MainResponseDTO<RegistrationResponseDTO>();
		logger.info("Request Receive for doctor registartion,Inside save doctor details method");
		try {
			responseWrapper = doctorDeRegService.listOfDoctorTodeRegister(request);;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseWrapper);
		}
		return ResponseEntity.status(HttpStatus.OK).body(responseWrapper);
   }

}
