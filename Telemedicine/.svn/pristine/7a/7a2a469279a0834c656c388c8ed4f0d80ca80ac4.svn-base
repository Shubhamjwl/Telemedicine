package com.nsdl.telemedicine.consultancy.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.nsdl.telemedicine.consultancy.dto.AppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.ChiefComplaintsDtls;
import com.nsdl.telemedicine.consultancy.dto.ConsultAdviceDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultMedicationDtlsDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultPresDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationDtlDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.DocumentReportDTO;
import com.nsdl.telemedicine.consultancy.dto.DownloadReportsDTO;
import com.nsdl.telemedicine.consultancy.dto.InvestigationDetailsDTO;
import com.nsdl.telemedicine.consultancy.dto.InvestigationRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.MainRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.MainResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.PatientDocumentsDtlsDTO;
import com.nsdl.telemedicine.consultancy.dto.PatientReportDTO;
import com.nsdl.telemedicine.consultancy.dto.PrescriprionVerifyRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.PrescriptionDTO;
import com.nsdl.telemedicine.consultancy.dto.PrescriptionDetailsDTO;
import com.nsdl.telemedicine.consultancy.dto.SavePatientReportRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtlsDTO;

public interface ConsultationService {

	public ConsultationResponseDTO saveConsultationDiagnosisService(ConsultationDTO<String> request);

	public ConsultationDTO<String> getConsultationDiagnosisService(String request);

	public ScribeDtlsDTO getListOfScribeByDoctor();

	public ConsultationResponseDTO saveConsultationChiefComplaint(ConsultationDTO<ChiefComplaintsDtls> request);
	
	public ConsultationResponseDTO saveInvestigationDetails(InvestigationRequestDTO request);

	public ConsultationDTO<ChiefComplaintsDtls> getConsultationChiefComplaint(String appointID);

	public ConsultationDTO<InvestigationDetailsDTO> getConsultationInvestigationDtls(String appointID);
	
	public MainResponseDTO<?> saveConsultationMedicationDtls(MainRequestDTO<List<ConsultMedicationDtlsDTO>> medicationRequest);

	public List<AppointmentDTO> getPrescriptionListByDoctorId(String docName);

	public ConsultationResponseDTO verifyPrescriptionByAppId(PrescriprionVerifyRequestDTO request);

	public ConsultationResponseDTO saveConsultationAdvice(ConsultationDTO<ConsultAdviceDTO> consultationDTO);

	public ConsultationDTO<ConsultAdviceDTO> getConsultationAdvice(String appointID);

	public MainResponseDTO<PrescriptionDTO> viewPrescriptionByApptID(MainRequestDTO<AppointmentDTO> appointmentDTO);

	public ConsultationResponseDTO savePrescriptionDetails(ConsultPresDTO request) throws IOException;

	public PrescriptionDetailsDTO getDetailsForPrescription(String appointID) throws IOException;
	
	public MainResponseDTO<?> getPatientMedicationDtls(MainRequestDTO<ConsultMedicationDtlsDTO> medicationRequest);
	
	public MainResponseDTO<?> saveConsultationDtls(MainRequestDTO<ConsultationDtlDTO> consultationRequest);
	
	public PrescriptionDetailsDTO getPrescriptionDetails(ConsultationDtlDTO consultationRequest) throws IOException;
	
	public ConsultationDtlDTO getConsultationDetails(ConsultationDtlDTO consultationRequest) throws IOException;
	
	public MainResponseDTO<?> updateConsultationStatus(MainRequestDTO<ConsultationDtlDTO> consultationRequest) throws IOException;
	
	public ConsultationResponseDTO saveConsultationPatientReports(
			@Valid MainRequestDTO<PatientDocumentsDtlsDTO<DocumentReportDTO>> request) throws IOException ;

	public Long getCountOfConsultation();
	
	public MainResponseDTO<?> uploadPrescriptionTemplate(MainRequestDTO<ConsultationDtlDTO> prescriptionUploadRequest);

	public MainResponseDTO<List<PatientReportDTO>> getPatientReportDetails(MainRequestDTO<AppointmentDTO> request);
	
	
}
