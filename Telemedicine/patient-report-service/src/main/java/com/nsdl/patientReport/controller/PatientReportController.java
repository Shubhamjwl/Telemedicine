package com.nsdl.patientReport.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.nsdl.patientReport.constant.ErrorConstant;
import com.nsdl.patientReport.dto.AppointmentDTO;
import com.nsdl.patientReport.dto.AppointmentRequestDTO;
import com.nsdl.patientReport.dto.MainRequestDTO;
import com.nsdl.patientReport.dto.MainResponseDTO;
import com.nsdl.patientReport.entity.ConsultPriscpDtl;
import com.nsdl.patientReport.exception.PatientReportException;
import com.nsdl.patientReport.logger.LoggingClientInfo;
import com.nsdl.patientReport.service.PatientReportService;



@RestController
//@CrossOrigin("*")
@LoggingClientInfo
public class PatientReportController {
	
	
	@Autowired
	PatientReportService patientReportService;
	
	private static final Logger logger = LoggerFactory.getLogger(PatientReportController.class);
	
	@PostMapping(value = "/getPatientAppointmentDetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<List<AppointmentDTO>>> getPatientAppointmentDetails(@RequestBody @Valid MainRequestDTO<AppointmentRequestDTO> appointmentRequest) throws Exception 
	{
			logger.info("Request Received to get Patient's Appointment Details");
			return ResponseEntity.status(HttpStatus.OK).body(patientReportService.getPatientAppointmentDetails(appointmentRequest));

	}
	
	@PostMapping(value = "/getDoctorAppointmentDetails", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MainResponseDTO<List<AppointmentDTO>>> getDoctorAppointmentDetails(@RequestBody @Valid MainRequestDTO<AppointmentRequestDTO> appointmentRequest) throws Exception 
	{
		logger.info("Request Received to get Doctor's Appointment Details");	
		return ResponseEntity.status(HttpStatus.OK).body(patientReportService.getDoctorAppointmentDetails(appointmentRequest));

	}
	
	@PostMapping(value = "/downloadPrescription", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> downloadDocument(@RequestBody MainRequestDTO<ConsultPriscpDtl> prescriptionDtlsRequest,HttpServletResponse response) throws IOException 
		{
		logger.info("Request Received to download prescription for appointment id:"+prescriptionDtlsRequest.getRequest().getCpdApptIdFk());
		ConsultPriscpDtl consultPriscpDtl=patientReportService.downloadPrescription(prescriptionDtlsRequest);
		File file = new File(consultPriscpDtl.getCpdPriscpPath().trim());
		if (file.exists()) {

			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) 
			{
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\";");
			response.setContentLength((int) file.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
		else
		{
			logger.info("Fail to download prescription.File not exist for given path:"+prescriptionDtlsRequest.getRequest().getCpdPriscpPath());
			throw new PatientReportException(ErrorConstant.DOWNLOAD_FAILED,ErrorConstant.FILE_NOT_FOUND);
		}
			
		
		return new ResponseEntity<String>("", HttpStatus.OK);
		}
		
}
	
		
		
	
	


