package com.nsdl.patientReport.serviceImpl;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nsdl.patientReport.constant.ErrorConstant;
import com.nsdl.patientReport.dto.AppointmentDTO;
import com.nsdl.patientReport.dto.AppointmentRequestDTO;
import com.nsdl.patientReport.dto.MainRequestDTO;
import com.nsdl.patientReport.dto.MainResponseDTO;
import com.nsdl.patientReport.entity.ConsultPriscpDtl;
import com.nsdl.patientReport.exception.PatientReportException;
import com.nsdl.patientReport.repository.PatientReportRepo;
import com.nsdl.patientReport.repository.PrescriptionRepo;
import com.nsdl.patientReport.service.PatientReportService;
import com.nsdl.patientReport.utility.Utility;

@Service
@Transactional
public class PatientReposrtServiceImpl implements PatientReportService{

	@Autowired
	PatientReportRepo patientReportRepo;
	
	@Autowired
	PrescriptionRepo prescriptionRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(PatientReposrtServiceImpl.class);
	
	@Override
	public MainResponseDTO<List<AppointmentDTO>> getPatientAppointmentDetails(MainRequestDTO<AppointmentRequestDTO> payLoad) {
		if(Utility.stringIsNullOrEmpty(payLoad.getRequest().getPatient_user_id()))
		{
			logger.info("Bad request,missing Patient id.");
			throw new PatientReportException(ErrorConstant.BAD_REQUEST,ErrorConstant.PATIENT_ID_MISSING);
		}
		
		
		@SuppressWarnings("unchecked")
		MainResponseDTO<List<AppointmentDTO>> responseDTO = (MainResponseDTO<List<AppointmentDTO>>)Utility.getMainResponseDto(payLoad);
		List<AppointmentDTO> appointmentDtls=patientReportRepo.getPatientAppointmentDetails(payLoad.getRequest().getPatient_user_id());
		responseDTO.setStatus(true);
		responseDTO.setResponse(appointmentDtls);
		return responseDTO;
	}

	@Override
	public MainResponseDTO<List<AppointmentDTO>> getDoctorAppointmentDetails(MainRequestDTO<AppointmentRequestDTO> payLoad) throws Exception {
		if(Utility.stringIsNullOrEmpty(payLoad.getRequest().getDoc_user_id()))
		{
			logger.info("Bad request,missing Doctor id.");
			throw new PatientReportException(ErrorConstant.BAD_REQUEST,ErrorConstant.DOCTOR_ID_MISSING);
		}
		
		List<AppointmentDTO> appointmentDtls=null;
		@SuppressWarnings("unchecked")
		MainResponseDTO<List<AppointmentDTO>> responseDTO = (MainResponseDTO<List<AppointmentDTO>>)Utility.getMainResponseDto(payLoad);
		try
		{
			appointmentDtls=patientReportRepo.getDoctorAppointmentDetails(payLoad.getRequest().getDoc_user_id());	
		}
		catch(Exception e)
		{
			logger.error("Exception occurred while gettig doctor;s apoint list");
			e.printStackTrace();
		}
		responseDTO.setStatus(true);
		responseDTO.setResponse(appointmentDtls);
		return responseDTO;
	}

	@Override
	public ConsultPriscpDtl downloadPrescription(MainRequestDTO<ConsultPriscpDtl> doctorDocDtlsRequest) throws IOException {
	ConsultPriscpDtl prescription=prescriptionRepo.findByCpdApptIdFk(doctorDocDtlsRequest.getRequest().getCpdApptIdFk());
	return prescription;
		
	}
		
	
		
	}


