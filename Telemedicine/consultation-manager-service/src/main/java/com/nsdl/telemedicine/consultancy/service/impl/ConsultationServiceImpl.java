package com.nsdl.telemedicine.consultancy.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.nsdl.telemedicine.consultancy.constant.ConsultationConstants;
import com.nsdl.telemedicine.consultancy.constant.ErrorConstants;
import com.nsdl.telemedicine.consultancy.dto.AppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.ChiefComplaintsDtls;
import com.nsdl.telemedicine.consultancy.dto.ConsultAdviceDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultMedicationDtlsDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultPresDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationDtlDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.DiagnosisResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.DocumentReportDTO;
import com.nsdl.telemedicine.consultancy.dto.InvestigationDetailsDTO;
import com.nsdl.telemedicine.consultancy.dto.InvestigationRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.MainRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.MainResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.OtpResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.PatientDocumentsDtlsDTO;
import com.nsdl.telemedicine.consultancy.dto.PatientReportDTO;
import com.nsdl.telemedicine.consultancy.dto.PatientReportUploadDtlsDTO;
import com.nsdl.telemedicine.consultancy.dto.PrescriprionVerifyRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.PrescriptionDTO;
import com.nsdl.telemedicine.consultancy.dto.PrescriptionDetailsDTO;
import com.nsdl.telemedicine.consultancy.dto.PrescriptionDtlsDTO;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtls;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtlsDTO;
import com.nsdl.telemedicine.consultancy.dto.TemplateDtls;
import com.nsdl.telemedicine.consultancy.dto.UserDTO;
import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.AuditInvestigationDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultAdvDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultAdviceAudtDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultCcDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultDiagDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultMedicationDtls;
import com.nsdl.telemedicine.consultancy.entity.ConsultMedicationDtlsAudited;
import com.nsdl.telemedicine.consultancy.entity.ConsultPriscpDtl;
import com.nsdl.telemedicine.consultancy.entity.ConsultationDtl;
import com.nsdl.telemedicine.consultancy.entity.DocMstrDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.DocRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.InvestigationDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientCareContextEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientReportUploadDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientReportUploadEntity;
import com.nsdl.telemedicine.consultancy.entity.PrescriptionTemplateDetails;
import com.nsdl.telemedicine.consultancy.entity.ScribeRegEntity;
import com.nsdl.telemedicine.consultancy.entity.audit.AppointmentDtlsAuditEntity;
import com.nsdl.telemedicine.consultancy.entity.audit.AuditConsultCcDtls;
import com.nsdl.telemedicine.consultancy.entity.audit.AuditConsultDiagDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.audit.AuditConsultPriscpDtl;
import com.nsdl.telemedicine.consultancy.entity.audit.AuditConsultationDtl;
import com.nsdl.telemedicine.consultancy.exception.ConsultationServiceException;
import com.nsdl.telemedicine.consultancy.logger.LoggingClientInfo;
import com.nsdl.telemedicine.consultancy.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.AuditInvestigationDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultAdvDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultAdviceAuditRepo;
import com.nsdl.telemedicine.consultancy.repository.ConsultCcDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultDiagDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationDtlsAuditedRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationMedicationAuditedRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationMedicationRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationPrescriptionRepo;
import com.nsdl.telemedicine.consultancy.repository.DocMstrDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.DocRegDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.InvestigationDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.PatientCareContextRepo;
import com.nsdl.telemedicine.consultancy.repository.PatientRegDtlsRepo;
import com.nsdl.telemedicine.consultancy.repository.PatientReportUploadDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.PatientReportUploadRepo;
import com.nsdl.telemedicine.consultancy.repository.PrescriptionTemplateRepo;
import com.nsdl.telemedicine.consultancy.repository.audit.AppointmentDtlsAuditRepository;
import com.nsdl.telemedicine.consultancy.repository.audit.AuditConsultCcDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.audit.AuditConsultDiagDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.audit.AuditConsultPriscpDtlRepository;
import com.nsdl.telemedicine.consultancy.service.ConsultationService;
import com.nsdl.telemedicine.consultancy.utility.DateTimeUtil;
import com.nsdl.telemedicine.consultancy.utility.EmptyCheckUtility;
import com.nsdl.telemedicine.consultancy.utility.PdfGenerator;

@Transactional
@Service
@LoggingClientInfo
public class ConsultationServiceImpl implements ConsultationService {

	@Value("${consultation.manager.chief.complain.response.msg}")
	private String chiefComplainResponseMsg;

	@Value("${consultation.manager.diagnosis.response.msg}")
	private String diagnosisResponseMsg;

	@Value("${consultation.manager.investigation.response.msg}")
	private String consultationInvestigationResponseMsg;

	@Value("${consultation.manager.advice.response.msg}")
	private String adviceResponseMsg;

	@Value("${consultation.manager.verifyPrescription.response.msg}")
	private String verifyPrescriptionResponseMsg;

	@Value("${consultation.manager.savePrescription.response.msg}")
	private String savePrescriptionResponseMsg;

	@Value("${uploadedPath}")
	private String uploadedPath;

	@Value("${consultation.manager.savePatient.report.response.msg}")
	private String savePatientReportResponseMsg;

	@Value("${consultation.manager.service.prescription.path}")
	private String prescriptionPath;

	@Value("${consultation.manager.service.path.seperator}")
	private String pathSeperator;

	@Value("${consultation.manager.service.prescription.email}")
	private String prescriptionEmailURL;

	@Value("${consultation.manager.service.notification.sms}")
	private String notificationSMSURL;

	@Autowired
	private ConsultAdvDtlsRepository consultAdvDtlsRepository;

	@Autowired
	private ConsultationMedicationRepository consultationMedicationRepository;

	@Autowired
	private AppointmentDtlsRepository appointmentRepo;
	
	

	@Autowired
	private ConsultDiagDtlsRepository consultDiagnosisRepo;

	@Autowired
	private DocMstrDtlsRepository docMstrDtlsRepository;

	@Autowired
	private AppointmentDtlsRepository appointmentDtlsRepository;

	@Autowired
	private ConsultCcDtlsRepository consultCcDtlsRepository;

	@Autowired
	private DocRegDtlsRepository docRegDtlsRepository;

	@Autowired
	private InvestigationDtlsRepository investigationDtlsRepository;

	@Autowired
	private PatientReportUploadDtlsRepository patientReportUploadDtlsRepository;

	@Autowired
	private AuditInvestigationDtlsRepository auditInvestigationDtlsRepository;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private ConsultationPrescriptionRepo consultationPrescriptionRepo;

	@Autowired
	private AuditConsultCcDtlsRepository consultCcDtlsAuditRepository;

	@Autowired
	private AuditConsultPriscpDtlRepository consultPriscpDtlAuditRepository;

	@Autowired
	private UserDTO userDetails;

	@Autowired
	private ConsultAdviceAuditRepo consultAdviceAuditRepo;
	@Autowired
	ConsultationMedicationAuditedRepository consultationMedicationRepositoryAudited;

	@Autowired
	AuditConsultDiagDtlsRepository auditDiagnosisRepo;

	@Autowired
	ConsultationDtlsRepository consultationDtlsRepository;

	@Autowired
	ConsultationDtlsAuditedRepository consultationDtlsAuditedRepository;

	@Autowired
	private AppointmentDtlsAuditRepository appointmentDtlsAuditRepository;
	
	@Autowired
	private PatientRegDtlsRepo patientRegDtlsRepo;
	
	@Autowired
	private PatientCareContextRepo patientCareContextRepo;

	@Value("${DocumentUploadSize}")
	private String documentUploadSize;

	@Value("${DocumentUploadSizeForPrescription}")
	private String documentUploadSizePrescription;

	@Autowired
	private PrescriptionTemplateRepo prescriptionTemplateRepo;

	@Autowired
	private RestTemplate template;
	
	@Autowired
	PatientReportUploadRepo patientReportUploadRepo;

	private static final Logger logger = LoggerFactory.getLogger(ConsultationServiceImpl.class);

	@Override
	public ConsultationResponseDTO saveConsultationDiagnosisService(ConsultationDTO<String> request) {
		
		logger.info("Inside Save Consultation Diagnosis Service");
		AppointmentDtlsEntity appointmentDtlsEntity = appointmentRepo.findByAdApptId(request.getAppointID());

		List<ConsultDiagDtlsEntity> diagnosisEntityList = new ArrayList<ConsultDiagDtlsEntity>();
		List<AuditConsultDiagDtlsEntity> auditdiagnosisEntityList = new ArrayList<AuditConsultDiagDtlsEntity>();

		if (appointmentDtlsEntity != null) {
			logger.info("Fetched Appointment Details from entity table");
			DocRegDtlsEntity docRegDtls = docRegDtlsRepository
					.findByDrdUserId(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());

			if (docRegDtls != null) {
				logger.info("Fetched Doctor Details from entity table");
				request.getData().forEach(str -> {
					ConsultDiagDtlsEntity entity = new ConsultDiagDtlsEntity();
					AuditConsultDiagDtlsEntity auditEntity = new AuditConsultDiagDtlsEntity();
					entity.setAppointmentDtlsEntity(appointmentDtlsEntity);
					entity.setDocRegDtlsEntity(docRegDtls);
					entity.setPatientRegDtlsEntity(appointmentDtlsEntity.getPatientRegDtlsEntity());
					entity.setDiagnosis(str.trim().toUpperCase());
					entity.setCddCreatedBy(userDetails.getUserName());
					BeanUtils.copyProperties(entity, auditEntity);
					auditEntity.setAppointmentId(entity.getAppointmentDtlsEntity().getAdApptId());
					auditEntity.setDocUserId(entity.getDocRegDtlsEntity().getDrdUserId());
					auditEntity.setPatientUserId(entity.getPatientRegDtlsEntity().getPrdUserId());
					auditEntity.setUserId(userDetails.getUserName());
					diagnosisEntityList.add(entity);
					auditdiagnosisEntityList.add(auditEntity);
				});
				auditDiagnosisRepo.saveAll(auditdiagnosisEntityList);
				consultDiagnosisRepo.saveAll(diagnosisEntityList);
				logger.info("Saved Diagnosis Details : Success");
			} else {
				logger.error("No Doctor Data found for given Appointment id");
				throw new ConsultationServiceException(
						ErrorConstants.NO_DOCTOR_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getCode(),
						ErrorConstants.NO_DOCTOR_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getMessage());
			}
		} else {
			logger.error("No Appointment Data found for given Appointment id");
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
		}
		logger.info("Returning from saveConsultationDiagnosisService() of service class");
		return new ConsultationResponseDTO(diagnosisResponseMsg);
	}

	@Override
	public ConsultationDTO<String> getConsultationDiagnosisService(String appointId) {
		logger.info("Fetched Consultation Diagnosis Service");
		List<ConsultDiagDtlsEntity> diagnosisListEntity = consultDiagnosisRepo.findByAppointId(appointId);
		ConsultationDTO<String> response = new ConsultationDTO<String>();
		if (!diagnosisListEntity.isEmpty() && diagnosisListEntity != null) {
			logger.info("Fetched Diagnosis Details from entity table");
			List<String> diagnosisList = diagnosisListEntity.stream().map(diag -> diag.getDiagnosis())
					.collect(Collectors.toList());
			response.setAppointID(appointId);
			response.setData(diagnosisList);
			response.setTabID("DIAGNOSIS");
		} else {
			logger.info("No Diagnosis Data found for given appointment id");
			throw new ConsultationServiceException(
					ErrorConstants.NO_DIAGNOSIS_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getCode(),
					ErrorConstants.NO_DIAGNOSIS_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getMessage());
		}
		logger.info("Returning from getConsultationDiagnosisService() of service class");
		return response;
	}

	@Override
	public ScribeDtlsDTO getListOfScribeByDoctor() {
		logger.info("Fetched List Of Scribe By Doctor");
		ScribeDtlsDTO response = new ScribeDtlsDTO();
		DocMstrDtlsEntity docMstrDtls = docMstrDtlsRepository.findByDmdUserId(userDetails.getUserName());
		List<ScribeDtls> scribeTotalList = new ArrayList<>();
		if (docMstrDtls != null) {
			logger.info("Fetched doctor master details from entity table");
			List<ScribeRegEntity> scribeEntityList = docMstrDtls.getScribeRegEntity();
			if (scribeEntityList != null && !scribeEntityList.isEmpty()) {
				List<ScribeDtls> scribeList = scribeEntityList.parallelStream()
						.map(dtls -> ScribeDtls.builder().scrbEmailID(dtls.getSrdEmail())
								.scrbFullName(dtls.getSrdScrbName()).isDefaultScribe(dtls.getIsDefaultScribe())
								.profilePhoto(dtls.getProfilePhoto()).srdIsActive(dtls.getSrdIsActive())
								.scrbMobileNo(dtls.getSrdMobileNo() != null ? dtls.getSrdMobileNo().toString() : "")
								.scrbUserId(dtls.getSrdUserID()).build())
						.collect(Collectors.toList());

				for (ScribeDtls scribeDtls : scribeList) {
					if (scribeDtls.getSrdIsActive().equalsIgnoreCase("Y")) {
						scribeTotalList.add(scribeDtls);
					}
				}
				response.setScribeDtls(scribeTotalList);
				logger.info("Mapped data to response");
			} else {
				logger.error("No scribe details found");
				throw new ConsultationServiceException(ErrorConstants.SCRIBE_DATA_NOT_FOUND.getCode(),
						ErrorConstants.SCRIBE_DATA_NOT_FOUND.getMessage());
			}
		} else {
			logger.error("No doctor details found for provided userId");
			throw new ConsultationServiceException(ErrorConstants.DOCTOR_DATA_NOT_FOUND.getCode(),
					ErrorConstants.DOCTOR_DATA_NOT_FOUND.getMessage());
		}
		response.setDrRegId(userDetails.getUserName());
		logger.info("Returning from getListOfScribeByDoctor() of service class");
		return response;
	}

	@Override
	public ConsultationResponseDTO saveConsultationChiefComplaint(ConsultationDTO<ChiefComplaintsDtls> request) {
		logger.info("Inside Save Consultation Chief Complaint");
		if (!EmptyCheckUtility.isNullEmpty(request.getAppointID())) {
			logger.info("Validated appointmentId successfully");

			AppointmentDtlsEntity appointmentDtlsEntity = null;
			if (userDetails.getRole().equals(ConsultationConstants.DOCTOR_ROLE.getValue())) {
				logger.info("Consultation Role is Doctor");
				appointmentDtlsEntity = appointmentDtlsRepository.findByAdApptIdAndDmdUserId(request.getAppointID(),
						userDetails.getUserName());
			} else if (userDetails.getRole().equals(ConsultationConstants.SCRIBE_ROLE.getValue())) {
				logger.info("Consultation Role is Scribe");
				appointmentDtlsEntity = appointmentDtlsRepository.findByAdApptIdAndAdScrbUserId(request.getAppointID(),
						userDetails.getUserName());
			}

			if (appointmentDtlsEntity != null) {
				logger.info("Fetched appointment data from entity table");
				DocRegDtlsEntity docRegDtls = docRegDtlsRepository.findByDrdUserId(userDetails.getUserName());
				if (docRegDtls != null) {
					logger.info("Fetched doctor registration details from entity table");
					List<ConsultCcDtlsEntity> entityList = new ArrayList<ConsultCcDtlsEntity>();
					List<AuditConsultCcDtls> auditEntityList = new ArrayList<AuditConsultCcDtls>();
					for (ChiefComplaintsDtls dtls : request.getData()) {
						AuditConsultCcDtls entityAudit = new AuditConsultCcDtls();
						ConsultCcDtlsEntity entity = consultCcDtlsRepository
								.findAllByCcdSymptomAndCccdApptIdFk(dtls.getSymptoms(), request.getAppointID());
						if (entity != null) {
							initializeCccDtlsAuditForExistingEntity(entityAudit, entity);
						} else {
							entity = new ConsultCcDtlsEntity();
						}
						entity.setAppointmentDtlsEntity(appointmentDtlsEntity);
						entity.setCcdCreatedBy(userDetails.getUserName());
						entity.setCcdSympDuration(dtls.getDuration());
						entity.setCcdSympNote(dtls.getNote());
						entity.setCcdSympSeverity(dtls.getSeverity());
						entity.setCcdSymptom(dtls.getSymptoms());
						entity.setDocRegDtlsEntity(docRegDtls);
						entity.setPatientRegDtlsEntity(appointmentDtlsEntity.getPatientRegDtlsEntity());
						entity.setCcdCreatedTmstmp(LocalDateTime.now());
						if (entity.getCcdIdPk() == null) {
							initializeCccDtlsAuditForNewEntity(appointmentDtlsEntity, docRegDtls, entityAudit, entity);
						}
						entityAudit.setTimestamp(LocalDateTime.now());
						entityAudit.setUserId(userDetails.getUserName());

						entityList.add(entity);
						auditEntityList.add(entityAudit);
					}
					appointmentDtlsEntity.getConsultCcDetailsEntity().addAll(entityList);
					consultCcDtlsAuditRepository.saveAll(auditEntityList);
					logger.info("Mapped requested input data to entities");
					consultCcDtlsRepository.saveAll(entityList);
					logger.info("Saved request data to the table");

				} else {
					logger.error("Doctor data not found in doctor registraion table");
					throw new ConsultationServiceException(
							ErrorConstants.NO_DOCTOR_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getCode(),
							ErrorConstants.NO_DOCTOR_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getMessage());
				}
			} else {
				logger.error(ErrorConstants.NO_APPOINTMENT_MAPPED_WITH_PROVIDED_APPOINTMENTID.getMessage());
				throw new ConsultationServiceException(
						ErrorConstants.NO_APPOINTMENT_MAPPED_WITH_PROVIDED_APPOINTMENTID.getCode(),
						ErrorConstants.NO_APPOINTMENT_MAPPED_WITH_PROVIDED_APPOINTMENTID.getMessage());
			}
			return new ConsultationResponseDTO(chiefComplainResponseMsg);
		} else {
			logger.error("Provided appointmentId cannot be null or empty");
			throw new ConsultationServiceException(ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getCode(),
					ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getMessage());
		}
		
	}

	private void initializeCccDtlsAuditForNewEntity(AppointmentDtlsEntity appointmentDtlsEntity,
			DocRegDtlsEntity docRegDtls, AuditConsultCcDtls entityAudit, ConsultCcDtlsEntity entity) {
		BeanUtils.copyProperties(entity, entityAudit);
		entityAudit.setCccdApptIdFk(appointmentDtlsEntity.getAdApptId());
		entityAudit.setCccdDrUserIdFk(docRegDtls.getDrdUserId());
		entityAudit.setCccdPtUserIdFk(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdUserId());
	}

	private void initializeCccDtlsAuditForExistingEntity(AuditConsultCcDtls entityAudit, ConsultCcDtlsEntity entity) {
		BeanUtils.copyProperties(entity, entityAudit);
		entityAudit.setCccdApptIdFk(entity.getAppointmentDtlsEntity().getAdApptId());
		entityAudit.setCccdDrUserIdFk(entity.getDocRegDtlsEntity().getDrdUserId());
		entityAudit.setCccdPtUserIdFk(entity.getPatientRegDtlsEntity().getPrdUserId());
	}

	@Override
	public ConsultationResponseDTO saveInvestigationDetails(InvestigationRequestDTO request) {

		logger.info("call saveInvestigationDetails() mthod of service class for saving investigation details");
		AppointmentDtlsEntity appointmentDtlsEntity = null;
		DocMstrDtlsEntity docMstrDtlsEntity = null;
		PatientRegDtlsEntity patientRegDtlsEntity = null;

		if (request.getAppointmentId() != null) {
			appointmentDtlsEntity = appointmentDtlsRepository.findByAdApptId(request.getAppointmentId());
		} else {
			logger.error("Appointment entity details not found based on given appointment");
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
		}
		if (appointmentDtlsEntity != null) {
			logger.info("Fetching Doctor and patient Entity details from appointmentEntity");
			docMstrDtlsEntity = appointmentDtlsEntity.getDocMstrDtlsEntity();
			patientRegDtlsEntity = appointmentDtlsEntity.getPatientRegDtlsEntity();
		} else {
			logger.error("Appointment entity details not found");
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
		}
		for (InvestigationDetailsDTO investigationDetailsDTO : request.getInvestigationDetailsDTOList()) {

			/*
			 * investigationDtlsEntityList =
			 * investigationDtlsRepository.findByCcidApptIdFk(request.getAppointmentId());
			 * if (null != investigationDtlsEntityList &&
			 * !investigationDtlsEntityList.isEmpty() ) {
			 * 
			 * 
			 * 
			 * }
			 */
			InvestigationDtlsEntity investigationDtlsEntity = new InvestigationDtlsEntity();
			AuditInvestigationDtlsEntity auditInvestigationDtlsEntity = new AuditInvestigationDtlsEntity();
			investigationDtlsEntity.setAppointmentDtlsEntity(appointmentDtlsEntity);
			auditInvestigationDtlsEntity.setAppointmentDtlsEntity(appointmentDtlsEntity);
			if (docMstrDtlsEntity != null) {
				logger.info("Set doctor entity details in investigation entity");
				investigationDtlsEntity.setDocMstrDtlsEntity(docMstrDtlsEntity);
				auditInvestigationDtlsEntity.setDocMstrDtlsEntity(docMstrDtlsEntity);
			} else {
				logger.error("Doctor entity not found");
				throw new ConsultationServiceException(
						ErrorConstants.NO_DOCTOR_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getCode(),
						ErrorConstants.NO_DOCTOR_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getMessage());
			}
			if (patientRegDtlsEntity != null) {
				logger.info("Set patient entity details in investigation entity");
				investigationDtlsEntity.setPatientRegDtlsEntity(patientRegDtlsEntity);
				auditInvestigationDtlsEntity.setPatientRegDtlsEntity(patientRegDtlsEntity);
			} else {
				logger.error("Patient entity not found");
				throw new ConsultationServiceException(
						ErrorConstants.NO_PATIENT_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getCode(),
						ErrorConstants.NO_PATIENT_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getMessage());
			}

			auditInvestigationDtlsEntity.setCidCreatedBy(userDetails.getUserName());
			auditInvestigationDtlsEntity.setCidCreatedTmstmp(LocalDateTime.now());
			auditInvestigationDtlsEntity.setCidApptUlReportName(investigationDetailsDTO.getReportName());
			auditInvestigationDtlsEntity.setAllergiesHistory(investigationDetailsDTO.getAllergiesHistory());
			auditInvestigationDtlsEntity.setCidApptUlReportType(investigationDetailsDTO.getReportType());
			auditInvestigationDtlsEntity.setDiastolic(investigationDetailsDTO.getDiastolic());
			auditInvestigationDtlsEntity.setFamilyHistory(investigationDetailsDTO.getFamilyHistory());
			auditInvestigationDtlsEntity.setHb(investigationDetailsDTO.getHb());
			auditInvestigationDtlsEntity.setHeight(investigationDetailsDTO.getHeight());
			auditInvestigationDtlsEntity.setOfc(investigationDetailsDTO.getOfc());
			auditInvestigationDtlsEntity.setPefr(investigationDetailsDTO.getPefr());
			auditInvestigationDtlsEntity.setPhysicalExam(investigationDetailsDTO.getPhysicalExam());
			auditInvestigationDtlsEntity.setPulse(investigationDetailsDTO.getPulse());
			auditInvestigationDtlsEntity.setRespirationRate(investigationDetailsDTO.getRespirationRate());
			auditInvestigationDtlsEntity.setSpo2Level(investigationDetailsDTO.getSpo2Level());
			auditInvestigationDtlsEntity.setSystolic(investigationDetailsDTO.getSystolic());
			auditInvestigationDtlsEntity.setWeight(investigationDetailsDTO.getWeight());
			auditInvestigationDtlsEntity.setCidInvesNote(investigationDetailsDTO.getNote());
			auditInvestigationDtlsEntity.setTimeStamp(LocalDateTime.now());
			auditInvestigationDtlsEntity.setUserId(userDetails.getUserName());
			auditInvestigationDtlsRepository.save(auditInvestigationDtlsEntity);

			investigationDtlsEntity.setCidCreatedBy(userDetails.getUserName());
			investigationDtlsEntity.setCidApptUlReportName(investigationDetailsDTO.getReportName());
			investigationDtlsEntity.setAllergiesHistory(investigationDetailsDTO.getAllergiesHistory());
			investigationDtlsEntity.setCidApptUlReportType(investigationDetailsDTO.getReportType());
			investigationDtlsEntity.setDiastolic(investigationDetailsDTO.getDiastolic());
			investigationDtlsEntity.setFamilyHistory(investigationDetailsDTO.getFamilyHistory());
			investigationDtlsEntity.setHb(investigationDetailsDTO.getHb());
			investigationDtlsEntity.setHeight(investigationDetailsDTO.getHeight());
			investigationDtlsEntity.setOfc(investigationDetailsDTO.getOfc());
			investigationDtlsEntity.setPefr(investigationDetailsDTO.getPefr());
			investigationDtlsEntity.setPhysicalExam(investigationDetailsDTO.getPhysicalExam());
			investigationDtlsEntity.setPulse(investigationDetailsDTO.getPulse());
			investigationDtlsEntity.setRespirationRate(investigationDetailsDTO.getRespirationRate());
			investigationDtlsEntity.setSpo2Level(investigationDetailsDTO.getSpo2Level());
			investigationDtlsEntity.setSystolic(investigationDetailsDTO.getSystolic());
			investigationDtlsEntity.setWeight(investigationDetailsDTO.getWeight());
			investigationDtlsEntity.setCidInvesNote(investigationDetailsDTO.getNote());
			investigationDtlsRepository.save(investigationDtlsEntity);

		}
		logger.info("Returning from saveInvestigationDetails() of service class");
		return new ConsultationResponseDTO(consultationInvestigationResponseMsg);
	}

	@Override
	public ConsultationDTO<InvestigationDetailsDTO> getConsultationInvestigationDtls(String appointID) {
		logger.info("Fetch Consultation Investigation Dtls");
		
		ConsultationDTO<InvestigationDetailsDTO> response = new ConsultationDTO<InvestigationDetailsDTO>();
		List<InvestigationDetailsDTO> investigationDetailsDTOList = null;
		List<PatientReportUploadDtlsDTO> PatientReportUploadDtlsDTOList = null;
		if (!EmptyCheckUtility.isNullEmpty(appointID)) {
			logger.info("Fetching List of Investigation details Entity based on Appointment ID");
			List<InvestigationDtlsEntity> investigationDtlsEntityList = investigationDtlsRepository
					.findByCcidApptIdFk(appointID);
			List<PatientReportUploadDtlsEntity> patientReportUploadDtlsEntityList = patientReportUploadDtlsRepository
					.findByAurdApptIdFk(appointID);
			if (investigationDtlsEntityList != null && !investigationDtlsEntityList.isEmpty()) {
				logger.info("Mapping list of investigation entity to list of investigation DTO");
				investigationDetailsDTOList = investigationDtlsEntityList.parallelStream()
						.map(invDtls -> InvestigationDetailsDTO.builder().reportName(invDtls.getCidApptUlReportName())
								.allergiesHistory(invDtls.getAllergiesHistory()).diastolic(invDtls.getDiastolic())
								.familyHistory(invDtls.getFamilyHistory()).hb(invDtls.getHb())
								.height(invDtls.getHeight()).note(invDtls.getCidInvesNote()).ofc(invDtls.getOfc())
								.pefr(invDtls.getPefr()).physicalExam(invDtls.getPhysicalExam())
								.pulse(invDtls.getPulse()).reportType(invDtls.getCidApptUlReportType())
								.respirationRate(invDtls.getRespirationRate()).spo2Level(invDtls.getSpo2Level())
								.systolic(invDtls.getSystolic()).weight(invDtls.getSystolic()).build())
						.collect(Collectors.toList());
				logger.info("Size of Consultation Investigation Dtls ::"+investigationDetailsDTOList.size());
			} else {
				logger.error("Investigation details Entity not found based on given appointment Id");
				throw new ConsultationServiceException(ErrorConstants.NO_INVESTIGATION_DETAILS_FOUND.getCode(),
						ErrorConstants.NO_INVESTIGATION_DETAILS_FOUND.getMessage());
			}
			if (patientReportUploadDtlsEntityList != null && !patientReportUploadDtlsEntityList.isEmpty()) {
				logger.info(
						"Mapping list of patientReportUploadDtlsEntity entity to list of patientReportUploadDtlsDTO DTO");
				PatientReportUploadDtlsDTOList = patientReportUploadDtlsEntityList.parallelStream()
						.map(reportDtls -> PatientReportUploadDtlsDTO.builder().reportName(reportDtls.getReportName())
								.reportType(reportDtls.getReportType()).path(reportDtls.getPath()).build())
						.collect(Collectors.toList());
				logger.info("Size of Patient Report Dtls ::"+PatientReportUploadDtlsDTOList.size());
			} else {
				logger.error("patient Report details Entity not found based on given appointment Id");
				throw new ConsultationServiceException(ErrorConstants.NO_PATIENT_REPORT_DETAILS_FOUND.getCode(),
						ErrorConstants.NO_PATIENT_REPORT_DETAILS_FOUND.getMessage());
			}
			response.setAppointID(appointID);
			response.setData(investigationDetailsDTOList);
			response.setPatientReportsData(PatientReportUploadDtlsDTOList);
			response.setTabID("investigate");
				
		} else {
			logger.error("Appointment ID not found");
			throw new ConsultationServiceException(ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getCode(),
					ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getMessage());
		}
		
		logger.info("Returning from getConsultationInvestigationDtls() of service class");
		return response;
	}


	@Override
	public List<AppointmentDTO> getPrescriptionListByDoctorId(String docName) {
		logger.info("Fetch Prescription List By Doctor Id");
		List<AppointmentDTO> appointmentist = new ArrayList<AppointmentDTO>();
		logger.info("Getting Doctor Entity details based on given doctor name");
		DocMstrDtlsEntity docMstrDtlsEntity = docMstrDtlsRepository.findByDmdUserId(docName);
		if (docMstrDtlsEntity != null) {
			logger.info("Getting list of appointment entity details from doctor entity");
			List<AppointmentDtlsEntity> appointDtlsList = docMstrDtlsEntity.getAppointmentDtlsEntity();
			for (AppointmentDtlsEntity appointmentDtlsEntity : appointDtlsList) {
				AppointmentDTO appointmentDTO = new AppointmentDTO();
				logger.info("Fetching consult prescription entity details based on Appointment Id:"
						+ appointmentDtlsEntity.getAdApptId());
				ConsultPriscpDtl consultPriscpDtl = consultationPrescriptionRepo
						.findByAdApptId(appointmentDtlsEntity.getAdApptId());
				if (consultPriscpDtl != null) {
					logger.info("checking prescription verification status for 'N' for given appointment id");
					if (consultPriscpDtl.getCpdIsPriscpVerify().equalsIgnoreCase("N")) {
						logger.info("For status 'N' preparing response with required params");
						appointmentDTO.setAppointmentID(appointmentDtlsEntity.getAdApptId());
						appointmentDTO.setAppointmentDate(appointmentDtlsEntity.getAdApptDateFk().toString());
						appointmentDTO.setAppointmentTime(appointmentDtlsEntity.getAdApptSlotFk());
						appointmentDTO.setPatientName(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdName());
						appointmentist.add(appointmentDTO);
					}
				} else {
					logger.error("No Consult prescription details Entity found for given appointment Id");
					throw new ConsultationServiceException(
							ErrorConstants.NO_CONSULT_PRESCRIPTION_DETAILS_FOUND.getCode(),
							ErrorConstants.NO_CONSULT_PRESCRIPTION_DETAILS_FOUND.getMessage());
				}
			}
		} else {
			logger.error("No doctor details found for provided doctor name");
			throw new ConsultationServiceException(ErrorConstants.DOCTOR_DATA_NOT_FOUND.getCode(),
					ErrorConstants.DOCTOR_DATA_NOT_FOUND.getMessage());
		}
		logger.info("Returning from getPrescriptionListByDoctorId() of service class");
		return appointmentist;
	}

	@Override
	public MainResponseDTO<PrescriptionDTO> viewPrescriptionByApptID(MainRequestDTO<AppointmentDTO> appointmentDTO) {
		logger.info("Inside View Prescription By Appointment ID ");
		MainResponseDTO<PrescriptionDTO> mainResponseDto = new MainResponseDTO<PrescriptionDTO>();
		AppointmentDtlsEntity appointmentDtlsEntity = null;
		List<ChiefComplaintsDtls> chiefCompDtlsList = new ArrayList<ChiefComplaintsDtls>();
		List<DiagnosisResponseDTO> diagnosisDtlsList = new ArrayList<DiagnosisResponseDTO>();
		List<InvestigationDetailsDTO> investigationList = new ArrayList<InvestigationDetailsDTO>();
		List<ConsultMedicationDtlsDTO> medicationDtlsList = new ArrayList<ConsultMedicationDtlsDTO>();
		PrescriptionDTO prescriptionDTO = new PrescriptionDTO();
		PrescriptionDtlsDTO prescriptionDtlsDTO = new PrescriptionDtlsDTO();
		try {
			if (!EmptyCheckUtility.isNullEmpty(appointmentDTO.getRequest().getAppointmentID()))
				appointmentDtlsEntity = appointmentRepo.findByAdApptId(appointmentDTO.getRequest().getAppointmentID());
			else {
				logger.error("Appointment Id not found" + appointmentDTO.getRequest().getAppointmentID());
				throw new ConsultationServiceException(ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getCode(),
						ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getMessage());
			}
			if (appointmentDtlsEntity != null) {
				prescriptionDTO
						.setAppointmentDate(DateTimeUtil.formatDateToString(appointmentDtlsEntity.getAdApptDateFk()));
				prescriptionDTO.setAppointmentID(appointmentDtlsEntity.getAdApptId());
				prescriptionDTO.setAppointmentTimme(appointmentDtlsEntity.getAdApptSlotFk());
				prescriptionDTO.setDrFullName(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdDrName());
				prescriptionDTO.setPatientEmailID(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdEmail());
				prescriptionDTO.setPatientMobNo(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdMobileNo());
				prescriptionDTO.setPatientName(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdName());
				for (ConsultCcDtlsEntity consultCcDtlsEntity : appointmentDtlsEntity.getConsultCcDetailsEntity()) {
					ChiefComplaintsDtls chiefComplaintsDtls = new ChiefComplaintsDtls();
					chiefComplaintsDtls.setDuration(consultCcDtlsEntity.getCcdSympDuration());
					chiefComplaintsDtls.setNote(consultCcDtlsEntity.getCcdSympNote());
					chiefComplaintsDtls.setSeverity(consultCcDtlsEntity.getCcdSympSeverity());
					chiefComplaintsDtls.setSymptoms(consultCcDtlsEntity.getCcdSymptom());
					chiefCompDtlsList.add(chiefComplaintsDtls);

				}
				List<ConsultDiagDtlsEntity> consultDiagDtlsList = consultDiagnosisRepo
						.findByAppointId(appointmentDtlsEntity.getAdApptId());
				for (ConsultDiagDtlsEntity consultDiagDtlsEntity : consultDiagDtlsList) {
					DiagnosisResponseDTO dto = new DiagnosisResponseDTO();
					dto.setDiagnosis(consultDiagDtlsEntity.getDiagnosis());
					diagnosisDtlsList.add(dto);
				}
				List<ConsultMedicationDtls> consultMedicationDtlsList = consultationMedicationRepository
						.findAllByCmdApptIdFk(appointmentDtlsEntity.getAdApptId());
				for (ConsultMedicationDtls consultMedicationDtls : consultMedicationDtlsList) {
					ConsultMedicationDtlsDTO dto = new ConsultMedicationDtlsDTO();
					dto.setCmdMedicineDoseDtls(consultMedicationDtls.getCmdMedicineDoseDtls().trim());
					dto.setCmdMedicineName(consultMedicationDtls.getCmdMedicineName().trim());
					dto.setCmdMedicineType(consultMedicationDtls.getCmdMedicineType().trim());
					dto.setCmdMedicineUnit(consultMedicationDtls.getCmdMedicineUnit().trim());
					medicationDtlsList.add(dto);
				}
				List<InvestigationDtlsEntity> InvestigationDtlsList = investigationDtlsRepository
						.findByCcidApptIdFk(appointmentDtlsEntity.getAdApptId());
				if (InvestigationDtlsList.size() > 0) {
					investigationList = InvestigationDtlsList.parallelStream()
							.map(invDtls -> InvestigationDetailsDTO.builder()
									.reportName(invDtls.getCidApptUlReportName())
									.allergiesHistory(invDtls.getAllergiesHistory()).diastolic(invDtls.getDiastolic())
									.familyHistory(invDtls.getFamilyHistory()).hb(invDtls.getHb())
									.height(invDtls.getHeight()).note(invDtls.getCidInvesNote()).ofc(invDtls.getOfc())
									.pefr(invDtls.getPefr()).physicalExam(invDtls.getPhysicalExam())
									.pulse(invDtls.getPulse()).reportType(invDtls.getCidApptUlReportType())
									.respirationRate(invDtls.getRespirationRate()).spo2Level(invDtls.getSpo2Level())
									.systolic(invDtls.getSystolic()).weight(invDtls.getSystolic()).build())
							.collect(Collectors.toList());
				}
				List<ConsultAdviceDTO> consultAdvDTOList = consultAdvDtlsRepository
						.findDetailsByAppID(appointmentDtlsEntity.getAdApptId());

				prescriptionDtlsDTO.setAdviceDtl(consultAdvDTOList);
				prescriptionDtlsDTO.setChiefCompDtls(chiefCompDtlsList);
				prescriptionDtlsDTO.setDiagnosisDtls(diagnosisDtlsList);
				prescriptionDtlsDTO.setInvestigationDtls(investigationList);
				prescriptionDtlsDTO.setMedicationDtls(medicationDtlsList);
				prescriptionDTO.setPrescriptionDtls(prescriptionDtlsDTO);
				logger.info("Size of Consult Adv List ::"+consultAdvDTOList.size());
			} else {
				logger.error("No appointment data found");
				throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
						ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());

			}
		} catch (Exception e) {
			logger.error("Something went wrong" + e);
			e.printStackTrace();
			throw new ConsultationServiceException(ErrorConstants.SOMETHING_WENT_WRONG.getCode(),
					ErrorConstants.SOMETHING_WENT_WRONG.getMessage());
		}
		mainResponseDto.setVersion(appointmentDTO.getVersion());
		mainResponseDto.setResponse(prescriptionDTO);
		mainResponseDto.setStatus(true);
		logger.info("Returning from viewPrescriptionByApptID() of service class");
		return mainResponseDto;
	}

	@Override
	public ConsultationResponseDTO verifyPrescriptionByAppId(PrescriprionVerifyRequestDTO request) {
		logger.info("called verify prescription service");
		ConsultPriscpDtl consultPriscpDtl = consultationPrescriptionRepo.findByAdApptId(request.getAppId());
		AuditConsultPriscpDtl auditConsultPriscpDtl = new AuditConsultPriscpDtl();
		if (consultPriscpDtl != null) {
			logger.info("started saving prescription data in Audit and main consult prescription table");
			auditConsultPriscpDtl.setUserId(userDetails.getUserName());
			auditConsultPriscpDtl.setTimestamp(LocalDateTime.now());
			auditConsultPriscpDtl.setCpdIsPriscpVerify(request.getStatus());
			auditConsultPriscpDtl.setCpdApptIdFk(consultPriscpDtl.getAppointmentDtlsEntity().getAdApptId());
			auditConsultPriscpDtl.setCpdDrUserIdFk(consultPriscpDtl.getDocMstrDtlsEntity().getDmdUserId());
			auditConsultPriscpDtl.setCpdPtUserIdFk(consultPriscpDtl.getPatientRegDtlsEntity().getPrdUserId());
			consultPriscpDtlAuditRepository.save(auditConsultPriscpDtl);
			consultPriscpDtl.setCpdIsPriscpVerify(request.getStatus());
			consultationPrescriptionRepo.save(consultPriscpDtl);
		} else {
			logger.error("No Consult prescription details Entity found for given appointment Id");
			throw new ConsultationServiceException(ErrorConstants.NO_CONSULT_PRESCRIPTION_DETAILS_FOUND.getCode(),
					ErrorConstants.NO_CONSULT_PRESCRIPTION_DETAILS_FOUND.getMessage());
		}
		logger.info("Returning from verifyPrescriptionByAppId() of service class");
		return new ConsultationResponseDTO(verifyPrescriptionResponseMsg);
	}

	@Override
	public ConsultationDTO<ChiefComplaintsDtls> getConsultationChiefComplaint(String appointID) {
		logger.info("Fetch Consultation Chief Complaint");
		if (!EmptyCheckUtility.isNullEmpty(appointID)) {
			logger.info("Validated appointmentId successfully");
			verifyAppointmentMapping(appointID);
			logger.info("Verified appoitment mapping");

			List<ChiefComplaintsDtls> chiefComplaintList = consultCcDtlsRepository.findByCccdApptIdFk(appointID);
			ConsultationDTO<ChiefComplaintsDtls> response = new ConsultationDTO<ChiefComplaintsDtls>();
			response.setAppointID(appointID);
			response.setData(chiefComplaintList);
			response.setTabID("chiefComp");
			logger.info("Mapped data to response");
			logger.info("Size of chief Complaint list"+chiefComplaintList.size());
			logger.info("Returning from getConsultationChiefComplaint() of service class");
			return response;
		} else {
			logger.error("AppointmentId cannot be null or empty");
			throw new ConsultationServiceException(ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getCode(),
					ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getMessage());
		}
	}

	private void verifyAppointmentMapping(String appointID) {
		logger.info("Inside Verify Appointment Mapping");
		int count = 0;
		if (userDetails.getRole().equals(ConsultationConstants.DOCTOR_ROLE.getValue())) {
			count = appointmentDtlsRepository.findCountByAdApptIdAndDmdUserId(appointID, userDetails.getUserName());
		} else if (userDetails.getRole().equals(ConsultationConstants.SCRIBE_ROLE.getValue())) {
			count = appointmentDtlsRepository.findCountByAdApptIdAndAdScrbUserId(appointID, userDetails.getUserName());
		}
		if (count == 0) {
			logger.error(ErrorConstants.NO_APPOINTMENT_MAPPED_WITH_PROVIDED_APPOINTMENTID.getMessage());
			throw new ConsultationServiceException(
					ErrorConstants.NO_APPOINTMENT_MAPPED_WITH_PROVIDED_APPOINTMENTID.getCode(),
					ErrorConstants.NO_APPOINTMENT_MAPPED_WITH_PROVIDED_APPOINTMENTID.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MainResponseDTO<?> saveConsultationMedicationDtls(
			MainRequestDTO<List<ConsultMedicationDtlsDTO>> medicationRequest) {
		logger.info("Inside Save Consultation Medication Dtls");
		MainResponseDTO mainResponseDto = new MainResponseDTO();
		List<ConsultMedicationDtls> medicationList = new ArrayList<ConsultMedicationDtls>();
		List<ConsultMedicationDtlsAudited> auditedMedicationList = new ArrayList<ConsultMedicationDtlsAudited>();
		List<ConsultMedicationDtls> persistedMedicationList = null;
		if (null != medicationRequest.getRequest() && medicationRequest.getRequest().size() > 0) {
			for (ConsultMedicationDtlsDTO consultMedicationDtlsDTO : medicationRequest.getRequest()) {
				ConsultMedicationDtls medicationDetails = consultationMedicationRepository.checkMedicationDtlsExist(
						consultMedicationDtlsDTO.getCmdApptIdFk(),
						consultMedicationDtlsDTO.getCmdDrUserIdFk().toLowerCase(),
						consultMedicationDtlsDTO.getCmdPtUserIdFk().toLowerCase(),
						consultMedicationDtlsDTO.getCmdMedicineType().toLowerCase(),
						consultMedicationDtlsDTO.getCmdMedicineName().toLowerCase());
				if (null == medicationDetails) {
					ConsultMedicationDtls consultMedicationDtlsEntity = new ConsultMedicationDtls();
					BeanUtils.copyProperties(consultMedicationDtlsDTO, consultMedicationDtlsEntity);
					medicationList.add(consultMedicationDtlsEntity);
				}
			}
			try {
				persistedMedicationList = consultationMedicationRepository.saveAll(medicationList);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(" Exception while saving consultation medication details", e);
				throw e;
			}
			// insert entries in audit table.
			logger.info("Inserting entries in audit table");
			for (ConsultMedicationDtls consultMedicationDtls : persistedMedicationList) {
				ConsultMedicationDtlsAudited consultMedicationDtlsAudited = new ConsultMedicationDtlsAudited();
				BeanUtils.copyProperties(consultMedicationDtls, consultMedicationDtlsAudited);
				consultMedicationDtlsAudited.setUserId(consultMedicationDtls.getCmdCreatedBy());
				auditedMedicationList.add(consultMedicationDtlsAudited);
			}

			try {
				consultationMedicationRepositoryAudited.saveAll(auditedMedicationList);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(" Exception while saving audited consultation medication details", e);
				throw e;
			}

		} else {
			logger.error("Medication Request and Input fields should not be null or blank.");
			throw new ConsultationServiceException(ErrorConstants.INVALID_INPUT_DATA.getCode(),
					ErrorConstants.INVALID_INPUT_DATA.getMessage());
		}

		mainResponseDto.setVersion(medicationRequest.getVersion());
		mainResponseDto.setResponse("Medication Details Inserted Successfully..");
		mainResponseDto.setStatus(true);
		logger.info(" method End --> returning response");
		logger.info("Returning from saveConsultationMedicationDtls() of service class");
		return mainResponseDto;
	}

	@Override
	public ConsultationResponseDTO saveConsultationAdvice(ConsultationDTO<ConsultAdviceDTO> request) {
		// TODO Auto-generated method stub
		logger.info("Inside Save Consultation Advice");
		if (!EmptyCheckUtility.isNullEmpty(request.getAppointID())) {
			AppointmentDtlsEntity appointmentDtlsEntity = appointmentDtlsRepository
					.findByAdApptId(request.getAppointID());
			if (appointmentDtlsEntity != null) {

				DocRegDtlsEntity docRegDtls = docRegDtlsRepository
						.findByDrdUserId(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
				if (docRegDtls != null) {
					List<ConsultAdvDtlsEntity> entityList = new ArrayList<ConsultAdvDtlsEntity>();
					List<ConsultAdviceAudtDtlsEntity> audtentityList = new ArrayList<ConsultAdviceAudtDtlsEntity>();
					for (ConsultAdviceDTO dtls : request.getData()) {
						ConsultAdvDtlsEntity entity = new ConsultAdvDtlsEntity();
						entity.setAppointmentDtlsEntity(appointmentDtlsEntity);
						entity.setCadCreatedBy(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
						entity.setCadAdvice(dtls.getAdvice());
						entity.setDocRegDtlsEntity(docRegDtls);
						entity.setCadNote(dtls.getNote());
						entity.setPatientRegDtlsEntity(appointmentDtlsEntity.getPatientRegDtlsEntity());
						entityList.add(entity);
					}
					for (ConsultAdviceDTO dtls : request.getData()) {
						ConsultAdviceAudtDtlsEntity audtentity = new ConsultAdviceAudtDtlsEntity();
						audtentity.setCadApptId(appointmentDtlsEntity.getAdApptId());
						audtentity.setCadCreatedBy(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
						audtentity.setCadAdvice(dtls.getAdvice());
						audtentity.setCadDrUserId(docRegDtls.getDrdUserId());
						audtentity.setCadNote(dtls.getNote());
						audtentity.setCadPtUserId(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdUserId());
						audtentity.setUserid(docRegDtls.getDrdUserId());
						audtentity.setCadOptiVersion(1);
						audtentityList.add(audtentity);
					}
					appointmentDtlsEntity.getConsultAdvDtlsEntities().addAll(entityList);
					consultAdvDtlsRepository.saveAll(entityList);
					logger.info("Saved Consultation AdvDtls into database");
					consultAdviceAuditRepo.saveAll(audtentityList);
					logger.info("Saved Consultation Advice into Audit database");
				} else {
					throw new ConsultationServiceException(
							ErrorConstants.NO_DOCTOR_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getCode(),
							ErrorConstants.NO_DOCTOR_DATA_FOUND_FOR_CURRENT_APPOINTMENT_ID.getMessage());
				}
			} else {
				throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
						ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			}
		} else {
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_ID_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_ID_FOUND.getMessage());
		}
		logger.info("Returning from saveConsultationAdvice() of service class");
		return new ConsultationResponseDTO(adviceResponseMsg);
	}

	@Override
	public ConsultationDTO<ConsultAdviceDTO> getConsultationAdvice(String appointID) {
		logger.info("Fetch Consultation Advice");
		if (appointID != null && !appointID.trim().isEmpty()) {
			List<ConsultAdviceDTO> consultAdviceList = consultAdvDtlsRepository.findDetailsByAppID(appointID);
			ConsultationDTO<ConsultAdviceDTO> response = new ConsultationDTO<ConsultAdviceDTO>();
			response.setAppointID(appointID);
			response.setData(consultAdviceList);
			response.setTabID("ConsultAdvice");
			logger.info("Returning getConsultationAdvice() of service class");
			return response;
		} else {
			throw new ConsultationServiceException(ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getCode(),
					ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getMessage());
		}
	}

	@Override
	public ConsultationResponseDTO savePrescriptionDetails(ConsultPresDTO request) throws IOException {
		logger.info("Inside Save Prescription Details");
		if (!EmptyCheckUtility.isNullEmpty(request.getAppointID())) {
			logger.info("Validated request data successfully");
			AppointmentDtlsEntity appointmentEntity = appointmentDtlsRepository.findByAdApptId(request.getAppointID());
			if (appointmentEntity != null) {
				logger.info("Fetched appointment data for provided appointmentId");

				if (request.getFilePath() != null && new File(request.getFilePath()).exists()) {
					logger.info("Pdf generated successfully");
					AuditConsultPriscpDtl consultPriscpDtlAudit = new AuditConsultPriscpDtl();
					ConsultPriscpDtl consultPriscpDtl = consultationPrescriptionRepo
							.findByAdApptId(appointmentEntity.getAdApptId());
					if (consultPriscpDtl != null) {
						initializeConsultPrescpDtlAuditForExistingEntity(consultPriscpDtlAudit, consultPriscpDtl);
					} else {
						consultPriscpDtl = new ConsultPriscpDtl();
					}
					consultPriscpDtl.setCpdPriscpPath(request.getFilePath());
					consultPriscpDtl.setAppointmentDtlsEntity(appointmentEntity);
					consultPriscpDtl.setPatientRegDtlsEntity(appointmentEntity.getPatientRegDtlsEntity());
					consultPriscpDtl.setDocMstrDtlsEntity(appointmentEntity.getDocMstrDtlsEntity());
					consultPriscpDtl.setCpdIsPriscpVerify("N");
					consultPriscpDtl.setCpdCreatedTmst(LocalDateTime.now());
					consultPriscpDtl.setCpdCreatedBy(userDetails.getUserName());

					if (consultPriscpDtl.getCpdIdPk() == null) {
						initializeConsultPrescpDtlAuditForNewEntity(appointmentEntity, consultPriscpDtlAudit,
								consultPriscpDtl);
					}

					consultPriscpDtlAudit.setUserId(userDetails.getUserName());
					consultPriscpDtlAudit.setTimestamp(LocalDateTime.now());
					logger.info("Saving Consultation priscription dtls into audit database");
					consultPriscpDtlAuditRepository.save(consultPriscpDtlAudit);
					logger.info("Saving Consultation priscription dtls into database");
					consultationPrescriptionRepo.save(consultPriscpDtl);
					auditAppointmentData(appointmentEntity);
					appointmentEntity.setAdApptStatus("C");
					appointmentRepo.save(appointmentEntity);

					logger.info("Updated entry in prescription table");
					logger.info("Mapped data to response");
					return new ConsultationResponseDTO(savePrescriptionResponseMsg);
				} else {
					logger.error(ErrorConstants.FILE_DOES_NOT_EXIST_IN_GIVEN_PATH.getMessage());
					throw new ConsultationServiceException(ErrorConstants.FILE_DOES_NOT_EXIST_IN_GIVEN_PATH.getCode(),
							ErrorConstants.FILE_DOES_NOT_EXIST_IN_GIVEN_PATH.getMessage());
				}
			} else {
				logger.error("No appointment data found for provided appointmentId");
				throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
						ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			}
		} else {
			logger.error(ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getMessage());
			throw new ConsultationServiceException(ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getCode(),
					ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getMessage());
		}
	}

	private String getAddress(String... address) {
		logger.info("Fetch Address");
		String completeAddrs = "";
		for (String addr : address) {
			if (addr != null && addr.trim().length() != 0) {
				if (completeAddrs.equals("")) {
					completeAddrs = addr;
				} else {
					completeAddrs += ", " + addr;
				}
			}
		}
		;
		logger.info("Returning from getAddress() of service class");
		return completeAddrs;
	}

	private void initializeConsultPrescpDtlAuditForNewEntity(AppointmentDtlsEntity appointmentEntity,
			AuditConsultPriscpDtl consultPriscpDtlAudit, ConsultPriscpDtl consultPriscpDtl) {
		logger.info("Inside Initialize Consult Prescp Dtl Audit For New Entity");
		BeanUtils.copyProperties(consultPriscpDtl, consultPriscpDtlAudit);
		consultPriscpDtlAudit.setCpdApptIdFk(appointmentEntity.getAdApptId());
		consultPriscpDtlAudit.setCpdDrUserIdFk(appointmentEntity.getDocMstrDtlsEntity().getDmdUserId());
		consultPriscpDtlAudit.setCpdPtUserIdFk(appointmentEntity.getPatientRegDtlsEntity().getPrdUserId());
	}

	private void initializeConsultPrescpDtlAuditForExistingEntity(AuditConsultPriscpDtl consultPriscpDtlAudit,
			ConsultPriscpDtl consultPriscpDtl) {
		logger.info("Inside Initialize Consult Prescp Dtl Audit For Existing Entity");
		BeanUtils.copyProperties(consultPriscpDtl, consultPriscpDtlAudit);
		consultPriscpDtlAudit.setCpdApptIdFk(consultPriscpDtl.getAppointmentDtlsEntity().getAdApptId());
		consultPriscpDtlAudit
				.setCpdDrUserIdFk(consultPriscpDtl.getAppointmentDtlsEntity().getDocMstrDtlsEntity().getDmdUserId());
		consultPriscpDtlAudit
				.setCpdPtUserIdFk(consultPriscpDtl.getAppointmentDtlsEntity().getPatientRegDtlsEntity().getPrdUserId());
	}

	private List<String> getAdviceList(List<ConsultAdvDtlsEntity> consultAdvList) {
		logger.info("Fetch Advice List");
		List<String> advList = new ArrayList<String>();
		// int count = 1;
		for (ConsultAdvDtlsEntity adv : consultAdvList) {
			advList.add(/* count++ + */ " - " + adv.getCadAdvice());
		}
		logger.info("Size of Advice List"+advList.size());
		logger.info("Returning from getAdviceList() of service class");
		return advList;
	}

	private List<String> getInvestigationList(List<InvestigationDtlsEntity> consultInvsList) {
		logger.info("Fetch Investigation List");
		List<String> invesList = new ArrayList<String>();
		// int count = 1;
		for (InvestigationDtlsEntity inves : consultInvsList) {
			invesList.add(/* count++ + */ " - " + inves.getCidApptUlReportType()
					+ (inves.getCidInvesNote() != null && inves.getCidInvesNote().trim().length() != 0
							? " - " + inves.getCidInvesNote()
							: ""));
		}
		logger.info("Size of Investigation List"+invesList.size());
		logger.info("Returning from getInvestigationList() of service class");
		return invesList;

	}

	private List<String> getDiagnosisList(List<ConsultDiagDtlsEntity> consultDiagList) {
		logger.info("Fetch Diagnosis List");
		List<String> diagList = new ArrayList<String>();
		// int count = 1;
		for (ConsultDiagDtlsEntity diag : consultDiagList) {
			diagList.add(/* count++ + */ " - " + diag.getDiagnosis());
		}
		logger.info("Size of Diagnosis List"+diagList.size());
		logger.info("Returning from getDiagnosisList() of service class");
		return diagList;
	}

	private List<String> getComlainList(List<ConsultCcDtlsEntity> consultCcList) {
		logger.info("Fetch Complain List");
		List<String> compList = new ArrayList<String>();
		// int count = 1;
		for (ConsultCcDtlsEntity cComp : consultCcList) {
			compList.add(/* count++ + */ " - " + cComp.getCcdSymptom());
		}
		logger.info("Size of Complain List"+compList.size());
		logger.info("Returning from getComlainList() of service class");
		return compList;
	}

	private List<ConsultMedicationDtlsDTO> getMedicationList(List<ConsultMedicationDtls> consultMedicationList) {
		logger.info("Fetch Medication List");
		List<ConsultMedicationDtlsDTO> medList = new ArrayList<ConsultMedicationDtlsDTO>();
		int count = 1;
		for (ConsultMedicationDtls med : consultMedicationList) {
			/*
			 * medList.add(count++ + ". " + med.getCmdMedicineName() +
			 * ((med.getCmdMedicineUnit() != null &&
			 * med.getCmdMedicineUnit().trim().length() != 0) ? " " +
			 * med.getCmdMedicineUnit() : "") + " - " + med.getCmdMedicineDoseDtls());
			 */
			ConsultMedicationDtlsDTO medDtls = new ConsultMedicationDtlsDTO();
			medDtls.setCmdMedicineName(count++ + ". " + med.getCmdMedicineName());
			medDtls.setCmdMedicineUnit(med.getCmdMedicineUnit());
			medDtls.setCmdMedicineType(med.getCmdMedicineType());
			medDtls.setCmdMedicineDoseDtls(med.getCmdMedicineDoseDtls());
			medList.add(medDtls);
		}
		logger.info("Size of Medication List"+medList.size());
		logger.info("Returning from getMedicationList() of service class");
		return medList;
	}

	/*
	 * @SuppressWarnings("null")
	 * 
	 * @Override public boolean saveuploadedDocuments(MultipartFile[] inputFiles,
	 * String appointmentId) { String tempFilePath = null; String givenName = "";
	 * String filepath = "";
	 * 
	 * try{ if(documentUserID!="") {
	 * doctorrRegDtlsEntity=registrationRepo.findDoctorDetailsByUserID(
	 * documentUserID); if(null==doctorrRegDtlsEntity) { return
	 * throwExceptionForUserIDNotExists(); } } if(null!=inputFiles) { for
	 * (MultipartFile file : inputFiles) { try { filepath = getFilePathBasedOnOS();
	 * } catch (Exception e) { //throw new
	 * Exception(ErrorConstants.NO_FILE_PATH_FOUND); } if(filepath!=null) {
	 * DoctorDocsDtlEntity doctorDocsDtlEntity=new DoctorDocsDtlEntity(); givenName
	 * =file.getOriginalFilename();
	 * doctorDocsDtlEntity.setDdtDocsName(file.getOriginalFilename());
	 * doctorDocsDtlEntity.setDdtDocsType("BLANK");
	 * doctorDocsDtlEntity.setDdtCreatedBy(doctorrRegDtlsEntity.getDrdUserId());
	 * doctorDocsDtlEntity.setDdtModifiedBy(doctorrRegDtlsEntity.getDrdUserId());
	 * doctorDocsDtlEntity.setDdtDocsRemark("document"); if(documentUserID!="") {
	 * doctorrRegDtlsEntity=registrationRepo.findDoctorDetailsByUserID(
	 * documentUserID); }
	 * doctorDocsDtlEntity.setDoctorRegDtlsEntity(doctorrRegDtlsEntity);
	 * MultipartFile multipartFile = new MockMultipartFile(givenName,
	 * file.getInputStream());
	 * 
	 * createDirectory(AuthConstant.DoctorRegistrationTempDirectory);
	 * if(documentUserID!="") {
	 * createDirectory(AuthConstant.DoctorRegistrationTempDirectory + File.separator
	 * + documentUserID); }else {
	 * createDirectory(AuthConstant.DoctorRegistrationTempDirectory + File.separator
	 * + doctorrRegDtlsEntity.getDrdUserId()); }
	 * 
	 * if(documentUserID!="") { tempFilePath = filepath + File.separator +
	 * AuthConstant.DoctorRegistrationTempDirectory + File.separator +
	 * documentUserID; }else { tempFilePath = filepath + File.separator +
	 * AuthConstant.DoctorRegistrationTempDirectory + File.separator +
	 * doctorrRegDtlsEntity.getDrdUserId(); }
	 * 
	 * BasicUtil.saveDoctorRegistrationFile(tempFilePath, multipartFile);
	 * doctorDocsDtlEntity.setDdtDocsPath(tempFilePath + File.separator +
	 * givenName); try { documentrepo.save(doctorDocsDtlEntity); } catch (Exception
	 * e) { e.printStackTrace(); }
	 * 
	 * } } }
	 * 
	 * }catch(Exception e) { e.printStackTrace(); } return response; }
	 */

	@Override
	public PrescriptionDetailsDTO getDetailsForPrescription(String appointID) throws IOException {
		// TODO Auto-generated method stub
		logger.info("Fetch Details For Prescription");
		if (!EmptyCheckUtility.isNullEmpty(appointID)) {
			logger.info("Validated request data successfully");
			AppointmentDtlsEntity appointmentEntity = appointmentDtlsRepository.findByAdApptId(appointID);
			if (appointmentEntity != null) {
				logger.info("Fetched appointment data for provided appointmentId");
				List<ConsultMedicationDtls> consultMedicationList = consultationMedicationRepository
						.findAllByCmdApptIdFk(appointID);
				List<ConsultCcDtlsEntity> consultCcList = appointmentEntity.getConsultCcDetailsEntity();
				List<ConsultAdvDtlsEntity> consultAdvList = appointmentEntity.getConsultAdvDtlsEntities();
				List<ConsultDiagDtlsEntity> consultDiagList = consultDiagnosisRepo.findByAppointId(appointID);
				List<InvestigationDtlsEntity> consultInvsList = investigationDtlsRepository
						.findByCcidApptIdFk(appointID);

				// String medicationList = String.join("\n",
				// getMedicationList(consultMedicationList));
				List<ConsultMedicationDtlsDTO> medicationList = getMedicationList(consultMedicationList);

				String symptomsList = String.join("\n", getComlainList(consultCcList));

				String diagnosisList = String.join("\n", getDiagnosisList(consultDiagList));

				String investigationList = String.join("\n", getInvestigationList(consultInvsList));

				String adviceList = String.join("\n", getAdviceList(consultAdvList));

				PatientRegDtlsEntity patientDetails = appointmentEntity.getPatientRegDtlsEntity();
				DocMstrDtlsEntity docDetails = appointmentEntity.getDocMstrDtlsEntity();
				Map<String, String> details = new HashMap<String, String>();
				details.put(ConsultationConstants.APPTID.getValue(), appointmentEntity.getAdApptId());
				details.put(ConsultationConstants.APPTDATE.getValue(), appointmentEntity.getAdCreatedTmstmp() != null
						? appointmentEntity.getAdCreatedTmstmp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
						: "");
				details.put(ConsultationConstants.DRNAME.getValue(), docDetails.getDmdDrName());
				details.put(ConsultationConstants.DRSPECIALIZATION.getValue(), docDetails.getDmdSpecialiazation());
				details.put(ConsultationConstants.DRSMCNO.getValue(), docDetails.getDmdSmcNumber());

				details.put(ConsultationConstants.PTNAME.getValue(), patientDetails.getPrdName());
				details.put(ConsultationConstants.AGE.getValue(),
						(patientDetails.getPrdDOB() != null
								? Period.between(patientDetails.getPrdDOB(),
										appointmentEntity.getAdCreatedTmstmp().toLocalDate()).getYears() + " Y"
								: ""));
				details.put(ConsultationConstants.PTMOBILENO.getValue(), patientDetails.getPrdMobileNo());
				details.put(ConsultationConstants.GENDER.getValue(), patientDetails.getPrdGender());
				details.put(ConsultationConstants.PTADDRESS.getValue(), getAddress(patientDetails.getPrdAddress1(),
						patientDetails.getPrdAddress2(), patientDetails.getPrdAddress3()));
				details.put(ConsultationConstants.PTEMAILID.getValue(), patientDetails.getPrdEmail());

				details.put(ConsultationConstants.SYMPTOMS.getValue(), symptomsList);
				details.put(ConsultationConstants.DIAGNOSIS.getValue(), diagnosisList);
				details.put(ConsultationConstants.INVESTIGATION.getValue(), investigationList);
				details.put(ConsultationConstants.ADVICE.getValue(), adviceList);

				logger.info("Prepared data to generate pdf");

				String pdfFilePath = pdfGenerator.generatePDF(details, medicationList);

				if (pdfFilePath != null) {
					logger.info("Pdf generated successfully");
					File file = new File(pdfFilePath);
					FileInputStream fis = new FileInputStream(file);
					byte[] pdfData = new byte[(int) file.length()];
					fis.read(pdfData);
					logger.info("Converted pdf to bye array");
					logger.info("Return response with pdf path");
					fis.close();
					return new PrescriptionDetailsDTO(savePrescriptionResponseMsg, pdfFilePath, pdfData);
				} else {
					logger.error("error while generation pdf");
					throw new ConsultationServiceException(ErrorConstants.PDF_GENERATE_ERROR.getCode(),
							ErrorConstants.PDF_GENERATE_ERROR.getMessage());
				}
			} else {
				logger.error("No appointment data found for provided appointmentId");
				throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
						ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			}
		} else {
			logger.error(ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getMessage());
			throw new ConsultationServiceException(ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getCode(),
					ErrorConstants.APPOINTMENT_ID_NOT_FOUND.getMessage());
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public MainResponseDTO<?> getPatientMedicationDtls(MainRequestDTO<ConsultMedicationDtlsDTO> medicationRequest) {
		logger.info("Fetch Patient Medication dtls");
		List<ConsultMedicationDtlsDTO> consultMedicationDtlsDTOList = new ArrayList<ConsultMedicationDtlsDTO>();
		MainResponseDTO response = new MainResponseDTO();
		List<ConsultMedicationDtls> medicationDetails = null;
		if (null != medicationRequest && null != medicationRequest.getRequest().getCmdApptIdFk()
				&& !medicationRequest.getRequest().getCmdApptIdFk().isEmpty()
				&& null != medicationRequest.getRequest().getCmdDrUserIdFk()
				&& !medicationRequest.getRequest().getCmdDrUserIdFk().isEmpty()
				&& null != medicationRequest.getRequest().getCmdPtUserIdFk()
				&& !medicationRequest.getRequest().getCmdPtUserIdFk().isEmpty()) {
			try {
				medicationDetails = consultationMedicationRepository.findMedicationDetails(
						medicationRequest.getRequest().getCmdApptIdFk(),
						medicationRequest.getRequest().getCmdDrUserIdFk().toLowerCase(),
						medicationRequest.getRequest().getCmdPtUserIdFk().toLowerCase());
				logger.info("Size of Medication dtls"+medicationDetails.size());
				if (null != medicationDetails && medicationDetails.size() > 0) {
					for (ConsultMedicationDtls consultMedicationDtls : medicationDetails) {
						ConsultMedicationDtlsDTO consultMedicationDtlsDTO = new ConsultMedicationDtlsDTO();
						BeanUtils.copyProperties(consultMedicationDtls, consultMedicationDtlsDTO);
						consultMedicationDtlsDTOList.add(consultMedicationDtlsDTO);
					}

				} else {
					logger.info("No Medication Data found for given appointment id");
					throw new ConsultationServiceException(ErrorConstants.NO_MEDICATION_DATA_FOUND_FOR_APPTID.getCode(),
							ErrorConstants.NO_MEDICATION_DATA_FOUND_FOR_APPTID.getMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(" Exception while fetching consultation medication details", e);
				throw e;
			}
		} else {
			logger.error(
					"Medication Request and Input fields (appointmentId,drUserId,ptUserId) should not be null or blank.");
			throw new ConsultationServiceException(ErrorConstants.INVALID_INPUT_DATA.getCode(),
					ErrorConstants.INVALID_INPUT_DATA.getMessage());
		}

		response.setStatus(true);
		response.setResponse(consultMedicationDtlsDTOList);
		logger.info("Returning from getPatientMedicationDtls() of service class");
		return response;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MainResponseDTO<?> saveConsultationDtls(MainRequestDTO<ConsultationDtlDTO> consultationRequest) {
		logger.info("Inside Save Consultation Dtls");
		/*
		 * if(null!=consultationRequest.getRequest().getHandwrittenPrescription()) {
		 * if(consultationRequest.getRequest().getHandwrittenPrescription().length()>
		 * Long.parseLong(documentUploadSizePrescription)) { throw new
		 * ConsultationServiceException(ErrorConstants.SOMETHING_WENT_WRONG.getCode(),
		 * ErrorConstants.SIZE_PRESCRIPTION.getMessage()); } }
		 */
		MainResponseDTO mainResponseDto = new MainResponseDTO();
		String message = "";
		String dmdDrName = null;
		String prdName = null;
		if (null != consultationRequest.getRequest()) {
			 dmdDrName = appointmentDtlsRepository.findByAdApptId(consultationRequest.getRequest().getCtApptId())
					.getDocMstrDtlsEntity().getDmdDrName();
			 prdName = appointmentDtlsRepository.findByAdApptId(consultationRequest.getRequest().getCtApptId())
					.getPatientRegDtlsEntity().getPrdName();
			 if(dmdDrName.contains(" ") || prdName.contains(" ") ) {
				 dmdDrName= dmdDrName.trim().replaceAll("\\s+", "_");
				 prdName= prdName.trim().replaceAll("\\s+", "_");
			}
			DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
			String formattedDate = timeStampPattern.format(java.time.LocalDateTime.now());
			File handwrittenPrescriptionFile = new File(
					prescriptionPath + pathSeperator + formattedDate + "_" + dmdDrName + "_" + prdName + ".pdf");
			boolean isPrescriptionExist = handwrittenPrescriptionFile.exists();
			if ((isPrescriptionExist == false && null != consultationRequest.getRequest().getHandwrittenPrescription()
					&& !consultationRequest.getRequest().getHandwrittenPrescription().isEmpty())
					|| isPrescriptionExist == true) {
				if (null != consultationRequest.getRequest().getHandwrittenPrescription()
						&& !consultationRequest.getRequest().getHandwrittenPrescription().isEmpty()) {
					// upload handwritten prescription to given location.
					String filePath = prescriptionPath + pathSeperator + formattedDate + "_" + dmdDrName + "_" + prdName
							+ ".pdf";
					try {
						Files.deleteIfExists(Paths.get(filePath));
						saveConsultationDetails(consultationRequest, filePath);
					} catch (IOException e1) {
						logger.error("Exception while deleting consultation details");
						e1.printStackTrace();
						throw new ConsultationServiceException(ErrorConstants.SOMETHING_WENT_WRONG.getCode(),
								ErrorConstants.SOMETHING_WENT_WRONG.getMessage());
					}
					String prescriptionFileInfo[] = consultationRequest.getRequest().getHandwrittenPrescription()
							.split(",");
					String prescriptionFileType = prescriptionFileInfo[0]
							.substring(prescriptionFileInfo[0].indexOf("/") + 1, prescriptionFileInfo[0].indexOf(";"));
					byte[] data = DatatypeConverter.parseBase64Binary(prescriptionFileInfo[1].trim());
					uploadHandwrittenPrescription(filePath, prescriptionFileType, data);
					message = "Prescription uploaded successfully..";
					logger.info("Prescription uploaded successfully..");
				} else {
					saveConsultationDetails(consultationRequest, null);
					message = "consultation Details Inserted Successfully..";
					logger.info("consultation Details Inserted Successfully..");
				}
			} else {
				saveConsultationDetails(consultationRequest, null);
				message = "consultation Details Inserted Successfully..";
				logger.info("consultation Details Inserted Successfully..");
			}
		} else {
			logger.error("Consultation Request and Input fields should not be null or blank.");
			throw new ConsultationServiceException(ErrorConstants.INVALID_INPUT_DATA.getCode(),
					ErrorConstants.INVALID_INPUT_DATA.getMessage());
		}

		mainResponseDto.setVersion(consultationRequest.getVersion());
		mainResponseDto.setResponse(message);
		mainResponseDto.setStatus(true);
		logger.info("Returning from saveConsultationDtls() of service class");
		return mainResponseDto;
	}

	@Override
	public PrescriptionDetailsDTO getPrescriptionDetails(ConsultationDtlDTO consultationRequest) throws IOException {
		logger.info("Fetch Prescription Details");
		String pdfFilePath = null;
		if (null != consultationRequest) {
			logger.info("Validated request data successfully");
			AppointmentDtlsEntity appointmentEntity = appointmentDtlsRepository
					.findByAdApptId(consultationRequest.getCtApptId());
			if (appointmentEntity != null) {
				logger.info("Fetched appointment data for provided appointmentId");
				ConsultationDtl consultationDtl = consultationDtlsRepository.checkConsultationDtlsExist(
						consultationRequest.getCtApptId(), consultationRequest.getCtDoctorId().toLowerCase(),
						consultationRequest.getCtPatientId().toLowerCase());
				if (null == consultationDtl) {
					logger.error("No consultation details found for provided appointmentId,doctorId,patientId");
					throw new ConsultationServiceException(ErrorConstants.NO_CONSULTATION_DATA_FOUND.getCode(),
							ErrorConstants.NO_CONSULTATION_DATA_FOUND.getMessage());
				}
				if (null == consultationDtl.getCtPrescriptionPath()
						|| consultationDtl.getCtPrescriptionPath().isEmpty()) {
					String medicationList = consultationDtl.getCtMedication();
					String symptomsList = consultationDtl.getCtChiefComplaints();
					String diagnosisList = consultationDtl.getCtDiagnosis();
					String adviceList = consultationDtl.getCtAdvice();

					PatientRegDtlsEntity patientDetails = appointmentEntity.getPatientRegDtlsEntity();
					DocMstrDtlsEntity docDetails = appointmentEntity.getDocMstrDtlsEntity();
					Map<String, String> details = new HashMap<String, String>();
					details.put(ConsultationConstants.APPTID.getValue(), appointmentEntity.getAdApptId());
					details.put(ConsultationConstants.APPTDATE.getValue(),
							appointmentEntity.getAdCreatedTmstmp() != null ? appointmentEntity.getAdCreatedTmstmp()
									.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "");
					details.put(ConsultationConstants.DRNAME.getValue(), docDetails.getDmdDrName());
					details.put(ConsultationConstants.DRSPECIALIZATION.getValue(), docDetails.getDmdSpecialiazation());
					details.put(ConsultationConstants.DRSMCNO.getValue(), docDetails.getDmdSmcNumber());
					details.put(ConsultationConstants.DRMCINO.getValue(), docDetails.getDmdMciNumber());
					details.put(ConsultationConstants.DRMOBILENO.getValue(),
							String.valueOf(docDetails.getDmdMobileNo()));
					details.put(ConsultationConstants.DREMAILID.getValue(),
							null != docDetails.getDmdEmail() ? docDetails.getDmdEmail() : "");

					details.put(ConsultationConstants.PTNAME.getValue(), patientDetails.getPrdName());
					details.put(ConsultationConstants.AGE.getValue(),
							(patientDetails.getPrdDOB() != null
									? Period.between(patientDetails.getPrdDOB(),
											appointmentEntity.getAdCreatedTmstmp().toLocalDate()).getYears() + " Y"
									: ""));
					details.put(ConsultationConstants.PTMOBILENO.getValue(), patientDetails.getPrdMobileNo());
					details.put(ConsultationConstants.GENDER.getValue(), patientDetails.getPrdGender());
					details.put(ConsultationConstants.PTADDRESS.getValue(), getAddress(patientDetails.getPrdAddress1(),
							patientDetails.getPrdAddress2(), patientDetails.getPrdAddress3()));
					details.put(ConsultationConstants.PTEMAILID.getValue(), patientDetails.getPrdEmail());

					details.put(ConsultationConstants.SYMPTOMS.getValue(), symptomsList);
					details.put(ConsultationConstants.DIAGNOSIS.getValue(), diagnosisList);
					details.put(ConsultationConstants.ADVICE.getValue(), adviceList);
					details.put(ConsultationConstants.MEDICATION.getValue(), medicationList);

					logger.info("Prepared data to generate pdf");

					// if doctors own template is available use that template or use default
					// template.
					PrescriptionTemplateDetails prescriptionTemplateDetails = prescriptionTemplateRepo
							.checkIfPrescriptionTempAvailable(consultationRequest.getCtDoctorId().toUpperCase());
					if (null != prescriptionTemplateDetails) {
						pdfFilePath = pdfGenerator.generatePrescriptionPDFAsPerDoctorTemplate(details,
								prescriptionTemplateDetails.getPtdPrescriptionTemplatePath());
					} else {
						pdfFilePath = pdfGenerator.generateDefaultPrescriptionPDF(details);
					}
				} else {// for handwritten prescription.
					pdfFilePath = consultationDtl.getCtPrescriptionPath();
				}

				if(pdfFilePath.contains(" ")) {
					pdfFilePath= pdfFilePath.trim().replaceAll("\\s+", "_");	 
				}
				
				if (pdfFilePath != null) {
					logger.info("Pdf generated successfully");
					File file = new File(pdfFilePath);
					FileInputStream fis = new FileInputStream(file);
					byte[] pdfData = new byte[(int) file.length()];
					fis.read(pdfData);
					logger.info("Converted pdf to bye array");
					logger.info("Return response with pdf path");
					fis.close();
					return new PrescriptionDetailsDTO(savePrescriptionResponseMsg, pdfFilePath, pdfData);
				} else {
					logger.error("error while generation pdf");
					throw new ConsultationServiceException(ErrorConstants.PDF_GENERATE_ERROR.getCode(),
							ErrorConstants.PDF_GENERATE_ERROR.getMessage());
				}
			} else {
				logger.error("No appointment data found for provided appointmentId");
				throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
						ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			}
		} else {
			logger.error("Consultation request should not be null");
			throw new ConsultationServiceException(ErrorConstants.INVALID_INPUT_DATA.getCode(),
					ErrorConstants.INVALID_INPUT_DATA.getMessage());
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public ConsultationResponseDTO saveConsultationPatientReports(
			@Valid MainRequestDTO<PatientDocumentsDtlsDTO<DocumentReportDTO>> request) throws IOException {
		logger.info("Inside Save Consultation Patient Reports");
		String temppath = null;
		AppointmentDtlsEntity appointmentDtlsEntity = null;
		PatientDocumentsDtlsDTO documentsDtlsDTO = request.getRequest();
		PatientReportUploadDtlsEntity patientReportUploadDtlsEntity = null;
		PatientReportUploadEntity patientReportUploadEntity = null;
		try {
			File byteStorePath = null;
			byteStorePath = new File(uploadedPath);
			if (null != documentsDtlsDTO.getApptID() && !documentsDtlsDTO.getApptID().isEmpty()) {
				appointmentDtlsEntity = appointmentDtlsRepository.findByAdApptId(documentsDtlsDTO.getApptID());
			}
			if (appointmentDtlsEntity != null) {
				PatientRegDtlsEntity patientDtls = patientRegDtlsRepo.getPatientRegDtls(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdUserId());
				for (int i = 0; i < request.getRequest().getDocument().size(); i++) {
					if (request.getRequest().getDocument().get(i).getFiles() != "") {
						if (request.getRequest().getDocument().get(i).getFiles().length() < Long
								.parseLong(documentUploadSize)) {
							String[] strings = request.getRequest().getDocument().get(i).getFiles().split(",");
							byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
							createDirectory(ConsultationConstants.PATIENTREPORTTEMPDIRECTORY.getValue());
							createDirectory(ConsultationConstants.PATIENTREPORTTEMPDIRECTORY.getValue() + File.separator
									+ appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdUserId());
							String[] text = strings[0].split(":");
							String[] givenname = text[1].split(";");
							String[] filename = givenname[0].split("/");
							logger.info("get path of file and save file to particular path");
							temppath = byteStorePath + File.separator
									+ ConsultationConstants.PATIENTREPORTTEMPDIRECTORY.getValue() + File.separator
									+ appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdUserId();
							String path = temppath + File.separator + filename[0];
							File file = new File(path);
							if (path != null) {
								patientReportUploadDtlsEntity = new PatientReportUploadDtlsEntity();
								patientReportUploadDtlsEntity.setAppointmentDtlsEntity(appointmentDtlsEntity);
								patientReportUploadDtlsEntity.setReportName(filename[0]);
								patientReportUploadDtlsEntity.setReportType(filename[1]);
								patientReportUploadDtlsEntity.setPath(path);
								patientReportUploadDtlsEntity.setUlNote("blank");
								patientReportUploadDtlsEntity.setOtherReportType(filename[1]);
								patientReportUploadDtlsEntity = patientReportUploadDtlsRepository
										.save(patientReportUploadDtlsEntity);
								
								patientReportUploadEntity = new PatientReportUploadEntity();
								patientReportUploadEntity.setPtUserId(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdUserId());
								patientReportUploadEntity.setReportType(filename[1]);
								patientReportUploadEntity.setReportName(filename[0]);
								patientReportUploadEntity.setReportPath(path);
								patientReportUploadEntity.setUploadDate(LocalDateTime.now());
								patientReportUploadEntity.setUploadedBy(patientDtls.getPrdName());
								patientReportUploadRepo.save(patientReportUploadEntity);
							}
							try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
								logger.info("write data to file");
								if (patientReportUploadDtlsEntity != null) {
									outputStream.write(data);
								}
							} catch (IOException e) {
								throw new ConsultationServiceException(ErrorConstants.File_WRITE.getCode(),
										ErrorConstants.File_WRITE.getMessage());
							}
						} else {
							throw new ConsultationServiceException(ErrorConstants.SIZE_LIMIT.getCode(),
									ErrorConstants.SIZE_LIMIT.getMessage());
						}
					}
				}
			} else {
				throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
						ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			}

		} catch (ConsultationServiceException ce) {
			throw ce;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConsultationServiceException(ErrorConstants.SOMETHING_WENT_WRONG.getCode(),
					ErrorConstants.SOMETHING_WENT_WRONG.getMessage());
		}
		logger.info("Returning from saveConsultationPatientReports() of service class");
		return new ConsultationResponseDTO(savePatientReportResponseMsg);
	}

	public String getFilePathBasedOnOS() throws IOException {
		logger.info("Fetch File Path Based On OS");
		logger.info("Returning from getFilePathBasedOnOS() of service class");
		return uploadedPath;
	}

	@Override
	public ConsultationDtlDTO getConsultationDetails(ConsultationDtlDTO consultationRequest) throws IOException {
		logger.info("Fetch Consultation Details");
		ConsultationDtl consultationDtls = null;
		ConsultationDtlDTO consultationDtlsDTO = new ConsultationDtlDTO();
		if (null != consultationRequest) {
			try {
				consultationDtls = consultationDtlsRepository.checkConsultationDtlsExist(
						consultationRequest.getCtApptId(), consultationRequest.getCtDoctorId().toLowerCase(),
						consultationRequest.getCtPatientId().toLowerCase());
				if (null == consultationDtls) {
					logger.error("No consultaton data found.");
					throw new ConsultationServiceException(ErrorConstants.NO_CONSULTATION_DATA_FOUND.getCode(),
							ErrorConstants.NO_CONSULTATION_DATA_FOUND.getMessage());
				}
				BeanUtils.copyProperties(consultationDtls, consultationDtlsDTO);
			} catch (ConsultationServiceException ce) {
				throw ce;
			} catch (Exception e) {
				logger.error("Exception while getting consultaton details --> ");
				e.printStackTrace();
				throw e;
			}
		} else {
			logger.error("Request or it's input parameters should not be null or empty.");
			throw new ConsultationServiceException(ErrorConstants.INVALID_INPUT_DATA.getCode(),
					ErrorConstants.INVALID_INPUT_DATA.getMessage());
		}

		logger.info("Returning from getConsultationDetails() of service class");
		return consultationDtlsDTO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public MainResponseDTO<?> updateConsultationStatus(MainRequestDTO<ConsultationDtlDTO> consultationRequest)
			throws IOException {
		logger.info("Inside Update Consultation Status");
		ConsultationDtl consultationDtls = null;
		ConsultationDtl consultationDtlsUpdated = new ConsultationDtl();
		MainResponseDTO mainResponseDto = new MainResponseDTO();
		TemplateDtls details = new TemplateDtls();
		if (null != consultationRequest) {
			consultationDtls = consultationDtlsRepository.checkConsultationDtlsExist(
					consultationRequest.getRequest().getCtApptId(),
					consultationRequest.getRequest().getCtDoctorId().toLowerCase(),
					consultationRequest.getRequest().getCtPatientId().toLowerCase());
			AppointmentDtlsEntity appointmentEntity = appointmentDtlsRepository
					.findByAdApptId(consultationRequest.getRequest().getCtApptId());
			if (appointmentEntity != null) {
				auditAppointmentData(appointmentEntity);
				try {
					appointmentEntity.setAdApptStatus("C");
					if (appointmentEntity.getAdApptStartTime() != null) {
						appointmentEntity.setAdApptEndTime(
								java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
					}

					logger.info("start time : {}", appointmentEntity.getAdApptStartTime());
					logger.info("end time : {}", appointmentEntity.getAdApptEndTime());
					if (appointmentEntity.getAdConsultType().equalsIgnoreCase("in-clinic")) {
						logger.info("inside if block");
						if (appointmentEntity.getAdApptStartTime() != null) {
							LocalTime startTime = java.time.LocalTime.parse(appointmentEntity.getAdApptStartTime());
							LocalTime endTime = java.time.LocalTime.parse(appointmentEntity.getAdApptEndTime());
							long hours = ChronoUnit.HOURS.between(startTime, endTime);
							long minutes = ChronoUnit.MINUTES.between(startTime, endTime) % 60;
							appointmentEntity.setAdApptTotalTime(hours + ":" + minutes);
						}
					} else {
						logger.info("inside else block");
						if(appointmentEntity.getAdApptStartTime()!=null&&appointmentEntity.getAdApptEndTime()!=null) {
						LocalTime startTime = java.time.LocalTime.parse(appointmentEntity.getAdApptStartTime());
						LocalTime endTime = java.time.LocalTime.parse(appointmentEntity.getAdApptEndTime());
						long hours = ChronoUnit.HOURS.between(startTime, endTime);
						long minutes = ChronoUnit.MINUTES.between(startTime, endTime) % 60;
						appointmentEntity.setAdApptTotalTime(hours + ":" + minutes);
						}
					}
					logger.info("Saving appointment dtls into database");
					appointmentRepo.save(appointmentEntity);
				} catch (Exception e) {
					logger.error("Exception while updating staus of appointment details. {}", e.getMessage());
					throw new ConsultationServiceException(ErrorConstants.PRESCRIPTION_UPLOAD_ISSUE.getCode(),
							ErrorConstants.PRESCRIPTION_UPLOAD_ISSUE.getMessage());
					/*
					 * e.printStackTrace(); throw e;
					 */
				}
			} else {
				logger.error(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
				throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
						ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			}
			if (null != consultationDtls) {
				try {
					BeanUtils.copyProperties(consultationDtls, consultationDtlsUpdated);
					consultationDtlsUpdated
							.setCtAppointmentStatus(consultationRequest.getRequest().getCtAppointmentStatus());
					consultationDtlsUpdated
							.setCtPrescriptionPath(consultationRequest.getRequest().getCtPrescriptionPath());
					logger.info("Saving consultation dtls into database");
					consultationDtlsRepository.save(consultationDtlsUpdated);	
				} catch (Exception e) {
					logger.error("Exception while updating staus of consultaton.");
					// e.printStackTrace();
					// throw e;
					throw new ConsultationServiceException(
							ErrorConstants.ERROR_UPLOADING_PRESCRIPTION_TEMPLATE.getCode(),
							ErrorConstants.ERROR_UPLOADING_PRESCRIPTION_TEMPLATE.getMessage());
				}
			}
			
			if(consultationRequest != null ) {
				try {
					PatientCareContextEntity patientCareContextEntity = new PatientCareContextEntity(); 
					
					String patientId = consultationRequest.getRequest().getCtPatientId();
					String apptId = consultationRequest.getRequest().getCtApptId();
					
					PatientRegDtlsEntity patientRegDtlsEntity= patientRegDtlsRepo.getPatientRegDtls(patientId);
					if(patientRegDtlsEntity !=null) {
					
					patientCareContextEntity.setCareContextsId(apptId);
				 // patientCareContextEntity.setAadhaarNo();
					patientCareContextEntity.setDisplayName(appointmentEntity.getAdSymptoms());
					patientCareContextEntity.setHealthId(patientRegDtlsEntity.getPrdHealthId());
					patientCareContextEntity.setHealthNo(patientRegDtlsEntity.getPrdHealthNo());
					patientCareContextEntity.setMobileNo(patientRegDtlsEntity.getPrdMobileNo());
					patientCareContextEntity.setPatientId(patientId);
					patientCareContextEntity.setPatientName(patientRegDtlsEntity.getPrdName());
					
					patientCareContextRepo.save(patientCareContextEntity);
					}
				}catch(Exception e) {
					logger.error("Exception while updating data of in patient care context table.");
					
				}
				
			}

			// send prescription to patient in email with attachment.
			details.setPatientName(appointmentEntity.getPatientRegDtlsEntity().getPrdName());
			details.setDoctorName(appointmentEntity.getDocMstrDtlsEntity().getDmdDrName());
			details.setPtEmailId(appointmentEntity.getPatientRegDtlsEntity().getPrdEmail());
			details.setDocEmailId(appointmentEntity.getDocMstrDtlsEntity().getDmdEmail());
			details.setAttachmentPath(consultationRequest.getRequest().getCtPrescriptionPath());
			details.setTemplateType("Consultation_Success");
			// details.setSendType("EMAIL");
			// Added by swati s to send sms
			details.setSendType("both");
			details.setPatientName(appointmentEntity.getPatientRegDtlsEntity().getPrdName());
			details.setPtMobileNo(appointmentEntity.getPatientRegDtlsEntity().getPrdMobileNo());
			details.setDocMobileNo(String.valueOf(appointmentEntity.getDocMstrDtlsEntity().getDmdMobileNo()));
			// Added by swati s to send sms end
			sendPrescriptionOnEmail(details);
			// sending prescription notification to patients
			logger.info("sending prescription notification to patients");
			sendPrescriptionNotificationOnSMS(appointmentEntity);
		} else {
			logger.error("Request or it's input parameters should not be null or empty.");
			throw new ConsultationServiceException(ErrorConstants.INVALID_INPUT_DATA.getCode(),
					ErrorConstants.INVALID_INPUT_DATA.getMessage());
		}

		mainResponseDto.setVersion(consultationRequest.getVersion());
		mainResponseDto.setResponse("Consultation Details updated Successfully..");
		mainResponseDto.setStatus(true);
		logger.info("Returning from updateConsultationStatus() of service class");
		return mainResponseDto;
	}

	private void sendPrescriptionNotificationOnSMS(AppointmentDtlsEntity appointmentEntity) {
		logger.info("Inside sendPrescriptionNotificationOnSMS method");
		MainRequestDTO<TemplateDtls> mainRequest = new MainRequestDTO<TemplateDtls>();
		TemplateDtls details = new TemplateDtls();
		details.setDoctorName(appointmentEntity.getDocMstrDtlsEntity().getDmdDrName());
		details.setMobileNo(appointmentEntity.getPatientRegDtlsEntity().getPrdMobileNo());
		details.setTemplateType("Notification_Download_Prescription_Patient");
		details.setSendType("SMS");
		try {
			mainRequest.setRequest(details);
			HttpEntity<MainRequestDTO<TemplateDtls>> requestEntity = new HttpEntity<MainRequestDTO<TemplateDtls>>(
					mainRequest);
			ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>>() {
			};
			logger.info("calling Otp manager service sms Api");
			ResponseEntity<MainResponseDTO<OtpResponseDTO>> response = template.exchange(notificationSMSURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);
			logger.info("sendPrescriptionNotificationOnSMS method excecution completed");
		} catch (Exception e) {
			logger.error("Exception while calling sms API : method sendNotificationOnSMS()");
			e.printStackTrace();
		}
	}

	/**
	 * @param appointmentDtlsEntity
	 * 
	 *                              Method changed by girishk.
	 */
	private void auditAppointmentData(AppointmentDtlsEntity appointmentDtlsEntity) {
		AppointmentDtlsAuditEntity appointmentDtlsAuditEntity = new AppointmentDtlsAuditEntity();
		appointmentDtlsAuditEntity.setAdIdPk(appointmentDtlsEntity.getAdIdPk());
		appointmentDtlsAuditEntity.setAdApptId(appointmentDtlsEntity.getAdApptId());
		appointmentDtlsAuditEntity.setAdDrUserIdFk(appointmentDtlsEntity.getDocMstrDtlsEntity().getDmdUserId());
		appointmentDtlsAuditEntity.setAdPtUserIdFk(appointmentDtlsEntity.getPatientRegDtlsEntity().getPrdUserId());
		appointmentDtlsAuditEntity.setAdApptDateFk(appointmentDtlsEntity.getAdApptDateFk());
		appointmentDtlsAuditEntity.setAdApptSlotFk(appointmentDtlsEntity.getAdApptSlotFk());
		appointmentDtlsAuditEntity.setAdApptBookedFor(appointmentDtlsEntity.getAdApptBookedFor());
		appointmentDtlsAuditEntity.setAdIsbooked(appointmentDtlsEntity.getAdIsbooked());
		appointmentDtlsAuditEntity.setAdApptStatus(appointmentDtlsEntity.getAdApptStatus());
		appointmentDtlsAuditEntity.setAdPmtTransIdFk(appointmentDtlsEntity.getPaymentDtlsEntity().getPdPmtTransId());
		appointmentDtlsAuditEntity.setApCancelReason(appointmentDtlsEntity.getApCancelReason());
		appointmentDtlsAuditEntity.setApCancelDate(appointmentDtlsEntity.getApCancelDate());
		appointmentDtlsAuditEntity.setAdCreatedBy(appointmentDtlsEntity.getAdCreatedBy());
		appointmentDtlsAuditEntity.setAdCreatedTmstmp(appointmentDtlsEntity.getAdCreatedTmstmp());
		appointmentDtlsAuditEntity.setAdModifiedBy(appointmentDtlsEntity.getAdModifiedBy());
		appointmentDtlsAuditEntity.setAdModifiedTmstmp(appointmentDtlsEntity.getAdModifiedTmstmp());
		appointmentDtlsAuditEntity.setAdOptiVersion(appointmentDtlsEntity.getAdOptiVersion());
		appointmentDtlsAuditEntity.setUserId(userDetails.getUserName());
		appointmentDtlsAuditEntity.setTimestamp(LocalDateTime.now());
		try {
			logger.info("Saving appointment Dtls Audit Entity into database");
			appointmentDtlsAuditRepository.save(appointmentDtlsAuditEntity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" Exception while saving audited appointment details --> ");
			throw e;
		}

	}

	public void createDirectory(String folder_name) throws IOException {
		logger.info("Inside Create Directory method");
		String directoryFilePath = getFilePathBasedOnOS() + "/" + folder_name;
		File file = new File(directoryFilePath);
		if (!file.exists()) {
			try {
				file.mkdir();
			} catch (Exception e) {
			}
		} else {
		}
	}

	@Override
	public Long getCountOfConsultation() {
		logger.info("Fetch Count Of Consultation");
		Long count = 0L;
		try {
			count = consultationDtlsRepository.getCountOfConsultation();
		} catch (Exception e) {
			logger.error("Exception while getting count of consultation.");
			e.printStackTrace();
		}
		logger.info("Returning from getCountOfConsultation() of service class");
		return count;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public MainResponseDTO<?> uploadPrescriptionTemplate(MainRequestDTO<ConsultationDtlDTO> prescriptionUploadRequest) {
		logger.info("Inside Upload Prescription Template");
		MainResponseDTO mainResponseDto = new MainResponseDTO();
		PrescriptionTemplateDetails prescriptionTemplateDetail = prescriptionTemplateRepo
				.checkIfPrescriptionTempAvailable(prescriptionUploadRequest.getRequest().getCtDoctorId().toUpperCase());
		if (null == prescriptionTemplateDetail) // if template is previously not added then only add.
		{
			String templatePath = prescriptionPath + pathSeperator + "templates" + pathSeperator
					+ prescriptionUploadRequest.getRequest().getCtDoctorId() + pathSeperator;
			if (null != prescriptionUploadRequest.getRequest().getTemplateHeader()
					&& !prescriptionUploadRequest.getRequest().getTemplateHeader().isEmpty()) {
				String[] imageInfo = prescriptionUploadRequest.getRequest().getTemplateHeader().split(",");
				String extension = imageInfo[0].substring(imageInfo[0].indexOf("/") + 1, imageInfo[0].indexOf(";"));
				uploadPrescriptionImages(imageInfo[1], templatePath, "header." + extension);// upload header image
			}
			if (null != prescriptionUploadRequest.getRequest().getTemplateFooter()
					&& !prescriptionUploadRequest.getRequest().getTemplateFooter().isEmpty()) {
				String[] imageInfo = prescriptionUploadRequest.getRequest().getTemplateFooter().split(",");
				String extension = imageInfo[0].substring(imageInfo[0].indexOf("/") + 1, imageInfo[0].indexOf(";"));
				uploadPrescriptionImages(imageInfo[1], templatePath, "footer." + extension);// upload footer image
			}

			try {
				PrescriptionTemplateDetails prescriptionTemplateDetails = new PrescriptionTemplateDetails();
				prescriptionTemplateDetails.setPtdDoctorId(prescriptionUploadRequest.getRequest().getCtDoctorId());
				prescriptionTemplateDetails.setPtdPrescriptionTemplatePath(templatePath);
				prescriptionTemplateDetails.setPtdCreatedBy(prescriptionUploadRequest.getRequest().getCtDoctorId());
				logger.info("Saving Prescription template details into database");
				prescriptionTemplateRepo.save(prescriptionTemplateDetails);
				
			} catch (Exception e) {
				logger.error(" Exception while saving uploaded prescription template details --> ");
				e.printStackTrace();
				throw e;
			}
		}
		mainResponseDto.setVersion(prescriptionUploadRequest.getVersion());
		mainResponseDto.setResponse("Prescription Template uploaded successfully..");
		mainResponseDto.setStatus(true);
		logger.info("Returning from uploadPrescriptionTemplate() of service class");
		return mainResponseDto;
	}

	/**
	 * Added by girishk to upload doctors own prescription template to specified
	 * location. This is used if doctor want to generate prescription as per his own
	 * template.
	 */
	private void uploadPrescriptionImages(String templateImage, String templatePath, String templateName) {
		logger.info("Inside Upload Prescription Images");
		File directory = new File(templatePath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		byte[] data = DatatypeConverter.parseBase64Binary(templateImage);
		File file = new File(templatePath + templateName);
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			logger.info("write data to file");
			outputStream.write(data);
		} catch (IOException e) {
			logger.error("Exception while uploading doctors own prescription template.");
			e.printStackTrace();
			throw new ConsultationServiceException(ErrorConstants.ERROR_UPLOADING_PRESCRIPTION_TEMPLATE.getCode(),
					ErrorConstants.ERROR_UPLOADING_PRESCRIPTION_TEMPLATE.getMessage());
		}
	}

	@Override
	public MainResponseDTO<List<PatientReportDTO>> getPatientReportDetails(MainRequestDTO<AppointmentDTO> request) {
		// TODO Auto-generated method stub
		logger.info("Fetch Patient Report Details");
		String finalResponse = null;
		MainResponseDTO<List<PatientReportDTO>> responseWrapper = new MainResponseDTO<List<PatientReportDTO>>();
		List<PatientReportUploadDtlsEntity> patientReportUploadDtlsEntityList = patientReportUploadDtlsRepository
				.findByAurdApptIdFk(request.getRequest().getAppointmentID());
		if (null != patientReportUploadDtlsEntityList && patientReportUploadDtlsEntityList.size() > 0) {
			List<PatientReportDTO> list = new ArrayList<PatientReportDTO>();
			try {
				for (int i = 0; i < patientReportUploadDtlsEntityList.size(); i++) {
					PatientReportDTO response = new PatientReportDTO();
					try {
						finalResponse = new String(Base64.encodeBase64(
								Files.readAllBytes(Paths.get(patientReportUploadDtlsEntityList.get(i).getPath()))));
					} catch (ConsultationServiceException e) {
						throw new ConsultationServiceException(ErrorConstants.NO_FILE_PRESENT.getCode(),
								ErrorConstants.NO_FILE_PRESENT.getMessage());
					}
					response.setReportname(patientReportUploadDtlsEntityList.get(i).getReportName());
					response.setReportpath(patientReportUploadDtlsEntityList.get(i).getPath());
					response.setReport(finalResponse);
					response.setReporttype(patientReportUploadDtlsEntityList.get(i).getReportType());
					response.setMimetype(URLConnection
							.guessContentTypeFromName(patientReportUploadDtlsEntityList.get(i).getReportName()));
					list.add(response);
				}
				logger.info("Size of Patient Report"+list.size());
				responseWrapper.setResponse(list);
				responseWrapper.setStatus(true);

			} catch (Throwable e) {
				e.printStackTrace();
				throw new ConsultationServiceException(ErrorConstants.SOMETHING_WENT_WRONG.getCode(),
						ErrorConstants.SOMETHING_WENT_WRONG.getMessage());
			}
		} else {
			throw new ConsultationServiceException(ErrorConstants.NO_PATIENT_REPORT_DETAILS_FOUND.getCode(),
					ErrorConstants.NO_PATIENT_REPORT_DETAILS_FOUND.getMessage());
		}
		logger.info("Returning from getPatientReportDetails() of service class");
		return responseWrapper;
	}

	private void saveConsultationDetails(MainRequestDTO<ConsultationDtlDTO> consultationRequest,
			String prescriptionPath) {
		logger.info("Inside Save Consultation Details method");
		ConsultationDtl persistedConsultationDetails = null;
		ConsultationDtl consultationDtlsEntity = new ConsultationDtl();
		try {
			ConsultationDtl consultationDtls = consultationDtlsRepository.checkConsultationDtlsExist(
					consultationRequest.getRequest().getCtApptId(),
					consultationRequest.getRequest().getCtDoctorId().toLowerCase(),
					consultationRequest.getRequest().getCtPatientId().toLowerCase());
			if (null != consultationDtls) {
				BeanUtils.copyProperties(consultationDtls, consultationDtlsEntity);
				consultationDtlsEntity.setCtAdvice(consultationRequest.getRequest().getCtAdvice());
				consultationDtlsEntity.setCtChiefComplaints(consultationRequest.getRequest().getCtChiefComplaints());
				consultationDtlsEntity.setCtDiagnosis(consultationRequest.getRequest().getCtDiagnosis());
				consultationDtlsEntity.setCtMedication(consultationRequest.getRequest().getCtMedication());
				consultationDtlsEntity.setCtModifiedTmstmp(LocalDateTime.now());
			} else {
				BeanUtils.copyProperties(consultationRequest.getRequest(), consultationDtlsEntity);
				consultationDtlsEntity.setCtCreatedTmstmp(LocalDateTime.now());
				consultationDtlsEntity.setCtModifiedTmstmp(LocalDateTime.now());
				consultationDtlsEntity.setCtCreatedBy(consultationDtlsEntity.getCtDoctorId());
				consultationDtlsEntity.setCtModifiedBy(consultationDtlsEntity.getCtDoctorId());
				consultationDtlsEntity.setCtAppointmentStatus("pending");
			}
			consultationDtlsEntity.setCtPrescriptionPath(prescriptionPath);
			logger.info("Saving Consultation Details into database");
			persistedConsultationDetails = consultationDtlsRepository.save(consultationDtlsEntity);
			logger.info("Saving Priscription Document into database");
			if(prescriptionPath != null) {
				
			File fileName= new File(prescriptionPath);
			PatientRegDtlsEntity patientDtls = patientRegDtlsRepo.getPatientRegDtls(consultationDtlsEntity.getCtPatientId());
			PatientReportUploadEntity patientReportUploadEntity = new PatientReportUploadEntity();
			patientReportUploadEntity.setPtUserId(consultationDtlsEntity.getCtPatientId());
			patientReportUploadEntity.setReportType("Prescription");
			patientReportUploadEntity.setReportName(fileName.getName());
			patientReportUploadEntity.setReportPath(prescriptionPath);
			patientReportUploadEntity.setUploadDate(LocalDateTime.now());
			patientReportUploadEntity.setUploadedBy(patientDtls.getPrdName());
			patientReportUploadRepo.save(patientReportUploadEntity);
			
			}
			
		} catch (Exception e) {
			logger.error(" Exception while saving consultation details --> ");
			e.printStackTrace();
			throw e;
		}
		// insert entries in audit table.
		try {
			AuditConsultationDtl consultationDtlsAudited = new AuditConsultationDtl();
			BeanUtils.copyProperties(persistedConsultationDetails, consultationDtlsAudited);
			consultationDtlsAudited.setUserId(persistedConsultationDetails.getCtCreatedBy());
			logger.info("Saving Consultation Details into audit table");
			consultationDtlsAuditedRepository.save(consultationDtlsAudited);
		} catch (Exception e) {
			logger.error(" Exception while saving audited consultation details --> ");
			e.printStackTrace();
			throw e;
		}
	}

	private void uploadHandwrittenPrescription(String filePath, String fileType, byte[] data) {
		logger.info("Inside upload Handwritten Prescription method");
		PdfWriter writer = null;
		PdfDocument pdfDoc = null;
		Document document = null;
		if (fileType.equalsIgnoreCase("pdf")) {

			File file = new File(filePath);
			try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
				logger.info("write data to file");
				outputStream.write(data);
			} catch (IOException e) {
				logger.error("Exception while uploading handwritten prescription.");
				e.printStackTrace();
				throw new ConsultationServiceException(
						ErrorConstants.ERROR_UPLOADING_HANDWRITTEN_PRESCRIPTION.getCode(),
						ErrorConstants.ERROR_UPLOADING_HANDWRITTEN_PRESCRIPTION.getMessage());
			}
		} else { // image file to convert to PDF.
			try {
				logger.info("Convert Image file into PDF");
				writer = new PdfWriter(filePath);
				pdfDoc = new PdfDocument(writer);
				document = new Document(pdfDoc);

				ImageData prescriptionData = ImageDataFactory.create(data);
				Image prescriptionImage = new Image(prescriptionData);
				prescriptionImage.setAutoScale(true);
				document.add(prescriptionImage);
			} catch (Exception e) {
				logger.error("Exception while uploading handwritten prescription.");
				e.printStackTrace();
				throw new ConsultationServiceException(
						ErrorConstants.ERROR_UPLOADING_HANDWRITTEN_PRESCRIPTION.getCode(),
						ErrorConstants.ERROR_UPLOADING_HANDWRITTEN_PRESCRIPTION.getMessage());
			} finally {
				try {
					document.close();
					pdfDoc.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void sendPrescriptionOnEmail(TemplateDtls details) {
		logger.info("Inside Send Prescription On Email method");
		MainRequestDTO<TemplateDtls> mainRequest = new MainRequestDTO<TemplateDtls>();
		try {
			mainRequest.setRequest(details);
			HttpEntity<MainRequestDTO<TemplateDtls>> requestEntity = new HttpEntity<MainRequestDTO<TemplateDtls>>(
					mainRequest);
			ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>>() {
			};
			ResponseEntity<MainResponseDTO<OtpResponseDTO>> response = template.exchange(prescriptionEmailURL,
					HttpMethod.POST, requestEntity, parameterizedResponse);
		} catch (Exception e) {
			logger.error("Exception while calling email API : method sendPrescriptionOnEmail()");
			e.printStackTrace();
		}
	}
}
