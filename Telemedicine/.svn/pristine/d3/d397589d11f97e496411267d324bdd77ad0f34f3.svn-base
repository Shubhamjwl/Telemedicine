package com.nsdl.appointment.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nsdl.appointment.aspect.LoggingClientInfo;
import com.nsdl.appointment.constants.AppointmentConstants;
import com.nsdl.appointment.constants.AppointmentErrorCodes;
import com.nsdl.appointment.dto.ApptDetailsForScribe;
import com.nsdl.appointment.dto.ApptDtlsDto;
import com.nsdl.appointment.dto.AssignScribeToApptRequest;
import com.nsdl.appointment.dto.AssignedScribeStatus;
import com.nsdl.appointment.dto.AssignedScribeToApptResponse;
import com.nsdl.appointment.dto.CancelAppointmentRequest;
import com.nsdl.appointment.dto.CancelAppointmentResponse;
import com.nsdl.appointment.dto.DoctorDTO;
import com.nsdl.appointment.dto.DoctorSearchRequest;
import com.nsdl.appointment.dto.DoctorSlotRequest;
import com.nsdl.appointment.dto.MainRequestDTO;
import com.nsdl.appointment.dto.MainResponseDTO;
import com.nsdl.appointment.dto.OtpResponseDTO;
import com.nsdl.appointment.dto.PatientDetailsDTO;
import com.nsdl.appointment.dto.PatientReview;
import com.nsdl.appointment.dto.RescheduleApptDTO;
import com.nsdl.appointment.dto.SaveAppDetailsRequest;
import com.nsdl.appointment.dto.SaveAppDetailsResponse;
import com.nsdl.appointment.dto.ScribeAppptDetailsDTO;
import com.nsdl.appointment.dto.SlotDetails;
import com.nsdl.appointment.dto.StatusResponse;
import com.nsdl.appointment.dto.TemplateDtls;
import com.nsdl.appointment.dto.UserDTO;
import com.nsdl.appointment.dto.UsrOtpEmailVerifyDTO;
import com.nsdl.appointment.dto.UsrOtpEmailVerifyResponseDTO;
import com.nsdl.appointment.entity.AppointmentDtlsEntity;
import com.nsdl.appointment.entity.AppointmentSeqEntity;
import com.nsdl.appointment.entity.ApptDrScrbAssignDtl;
import com.nsdl.appointment.entity.DocMstrDtlsEntity;
import com.nsdl.appointment.entity.DocSlotDtlsEntity;
import com.nsdl.appointment.entity.LoginUserEntity;
import com.nsdl.appointment.entity.PatientRegDtlsEntity;
import com.nsdl.appointment.entity.PatientRevDtlsEntity;
import com.nsdl.appointment.entity.PaymentDtlsEntity;
import com.nsdl.appointment.entity.ScribeRegEntity;
import com.nsdl.appointment.exception.RegistrationException;
import com.nsdl.appointment.repository.AppointmentDrScrbAssignDtlsReposotory;
import com.nsdl.appointment.repository.AppointmentDtlsRepository;
import com.nsdl.appointment.repository.AppointmentSeqRepository;
import com.nsdl.appointment.repository.DocMstrDtlsRepository;
import com.nsdl.appointment.repository.DocSlotDtlsRepository;
import com.nsdl.appointment.repository.LoginUserRepo;
import com.nsdl.appointment.repository.PatientRegDtlsRepository;
import com.nsdl.appointment.repository.PaymentDtlsRepository;
import com.nsdl.appointment.service.AppointmentService;
import com.nsdl.appointment.service.AuditService;
import com.nsdl.appointment.utils.AlphabetEncoder;
import com.nsdl.appointment.utils.EmptyCheckUtils;

@Service
@LoggingClientInfo
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private DocMstrDtlsRepository docMstrDtlsRepository;

	@Autowired
	private DocSlotDtlsRepository docSlotDtlsRepository;

	@Autowired
	private PatientRegDtlsRepository patientRegDtlsRepository;

	@Autowired
	private PaymentDtlsRepository paymentDtlsRepository;

	@Autowired
	private AppointmentDtlsRepository appointmentDtlsRepository;

	@Autowired
	private LoginUserRepo loginUserRepo;

	@Autowired
	private AuditService auditService;

	@Autowired
	private UserDTO userDetails;

	@Autowired
	AppointmentDrScrbAssignDtlsReposotory apptDrScrbAssignDtlsReposotory;

	@Autowired
	AppointmentSeqRepository seqRepository;

	@Value("${smsemail.notification.url}")
	private String emailNotificationURL;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Override
	public List<DoctorDTO> getListOfDoctorBySearch(DoctorSearchRequest doctorSearchRequest) {
		// TODO Auto-generated method stub
		List<DoctorDTO> doctorDetails = new ArrayList<>();
		List<DocMstrDtlsEntity> doctorsList=null;
		if (userDetails.getRole().equalsIgnoreCase(AppointmentConstants.PATIENT_ROLE) && doctorSearchRequest.isCloseDrGrp()) {
			doctorsList=docMstrDtlsRepository.getDrList(userDetails.getUserName());
			
		}else {
				doctorsList = findByLikeAndBetweenCriteria(doctorSearchRequest.getDoctorName(),
					doctorSearchRequest.getSpeciality(), doctorSearchRequest.getGender(),
					doctorSearchRequest.getConsultFee(),doctorSearchRequest.getDoctorId());
		}
		logger.info("Fetched doctor master details from database");

		for (DocMstrDtlsEntity doctor : doctorsList) {
			LoginUserEntity user = loginUserRepo.findByUserId(doctor.getDmdUserId());
			if (user == null || !user.getIsActive()) {
				continue;
			}

			DoctorDTO doctorDTO = new DoctorDTO();
			doctorDTO.setDoctorId(doctor.getDmdUserId());
			doctorDTO.setDoctorName(doctor.getDmdDrName());
			doctorDTO.setSpeciality(doctor.getDmdSpecialiazation());
			doctorDTO.setConsultFee(doctor.getDmdConsulFee());
			doctorDTO.setDrProfilePhoto(doctor.getDmdPhotoPath());

			List<DocSlotDtlsEntity> docSlotDtlsEntities = new ArrayList<>();
			if (doctorSearchRequest.getAvailabilityStartDate() != null
					&& doctorSearchRequest.getAvailabilityEndDate() != null) {
				docSlotDtlsEntities = docSlotDtlsRepository.findAllDocSlotDtls(doctor.getDmdUserId(),
						doctorSearchRequest.getAvailabilityStartDate(), doctorSearchRequest.getAvailabilityEndDate());
			} else {
				docSlotDtlsEntities = docSlotDtlsRepository.findAllDocSlotDtlsWithoutDate(doctor.getDmdUserId());
			}

			List<SlotDetails> slotDetails = new ArrayList<>();
			for (DocSlotDtlsEntity docSlotDtlsEntity : docSlotDtlsEntities) {
				slotSetter(doctorDTO, slotDetails, docSlotDtlsEntity);
			}
			doctorDTO.setSlotDetails(slotDetails);

			PatientReview patientReview = new PatientReview();
			patientReview.setReviewRating(doctor.getPatientRevDtlsEntities().stream()
					.collect(Collectors.averagingDouble(PatientRevDtlsEntity::getPrdRating)));
			patientReview.setPatientStoryCnt(doctor.getPatientRevDtlsEntities().stream().count());

			doctorDTO.setPatientReview(patientReview);
			doctorDetails.add(doctorDTO);
		}

		if (doctorDetails.size() == 0) {
			logger.error("No Doctor Data found");
			throw new RegistrationException(AppointmentErrorCodes.DOCTORS_NOT_AVAILABLE.getErrorCode(),
					AppointmentErrorCodes.DOCTORS_NOT_AVAILABLE.getErrorMessage());
		}
		return doctorDetails;
	}

	private void slotSetter(DoctorDTO doctorDTO, List<SlotDetails> slotDetails, DocSlotDtlsEntity docSlotDtlsEntity) {

		Date slotDateTime = getDateTime(docSlotDtlsEntity.getDsdSlotDate(), docSlotDtlsEntity.getDsdSlot());

		logger.info("setting slot details for doctor Id: {}", docSlotDtlsEntity.getDsdDrUserIdFk());
		if (slotDateTime.after(new Date())) {
			SlotDetails details = new SlotDetails();
			DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
			details.setSlotDate(outputFormatter.format(docSlotDtlsEntity.getDsdSlotDate()));
			details.setSlotTime(docSlotDtlsEntity.getDsdSlot());
			slotDetails.add(details);

			if (doctorDTO.getAvailability() == null) {
				if (getZeroTimeDate(docSlotDtlsEntity.getDsdSlotDate()).compareTo(getZeroTimeDate(new Date())) == 0) {
					doctorDTO.setAvailability(AppointmentConstants.TODAY);
				} else {
					Calendar calender = Calendar.getInstance();
					calender.add(Calendar.DAY_OF_YEAR, 1);
					calender.set(Calendar.HOUR_OF_DAY, 0);
					calender.set(Calendar.MINUTE, 0);
					calender.set(Calendar.SECOND, 0);
					calender.set(Calendar.MILLISECOND, 0);
					Date tomorrowDate = calender.getTime();
					if (getZeroTimeDate(docSlotDtlsEntity.getDsdSlotDate()).compareTo((tomorrowDate)) == 0) {
						doctorDTO.setAvailability(AppointmentConstants.TOMORROW);
					} else {
						doctorDTO.setAvailability(outputFormatter.format(docSlotDtlsEntity.getDsdSlotDate()));
					}
				}
			}
		}

	}

	@SuppressWarnings("serial")
	public List<DocMstrDtlsEntity> findByLikeAndBetweenCriteria(String name, String specialization, String gender,
			int[] consultFee,String docId) {
		return docMstrDtlsRepository.findAll(new Specification<DocMstrDtlsEntity>() {
			@Override
			public Predicate toPredicate(Root<DocMstrDtlsEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				if(docId!=null && docId!="") {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.equal(root.get("dmdUserId"),  docId.toUpperCase())));
				}
				if (name != null) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("dmdDrName"), "%" + name.toUpperCase() + "%")));
				}
				if (specialization != null) {
					predicates.add(
							//criteriaBuilder.and(criteriaBuilder.equal(root.get("dmdSpecialiazation"), specialization)));
							criteriaBuilder.and(criteriaBuilder.like(
								    criteriaBuilder.upper(root.get("dmdSpecialiazation")), 
								    "%"+specialization.toUpperCase()+"%")));
				}
				if (gender != null) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("dmdGender"), gender)));
				}
				if (consultFee != null) {
					predicates.add(criteriaBuilder.between(root.get("dmdConsulFee"), consultFee[0], consultFee[1]));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}

	@Override
	public List<SlotDetails> getAvailableSlotListByDoctor(DoctorSlotRequest doctorSlotRequest) {
		// TODO Auto-generated method stub
		List<SlotDetails> slotDetails = new ArrayList<>();

		List<DocSlotDtlsEntity> docSlotDtlsEntities = docSlotDtlsRepository
				.findDocSlotDtlsByDate(doctorSlotRequest.getDoctorUserId(), doctorSlotRequest.getSlotDate());

		logger.info("Fetching slot details from database");
		for (DocSlotDtlsEntity docSlotDtlsEntity : docSlotDtlsEntities) {

			Date slotDateTime = getDateTime(docSlotDtlsEntity.getDsdSlotDate(), docSlotDtlsEntity.getDsdSlot());

			if (slotDateTime.after(new Date())) {
				SlotDetails details = new SlotDetails();
				DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
				details.setSlotDate(outputFormatter.format(docSlotDtlsEntity.getDsdSlotDate()));
				details.setSlotTime(docSlotDtlsEntity.getDsdSlot());
				slotDetails.add(details);
			}
		}

		if (slotDetails.size() == 0) {
			logger.error("No slot Data found");
			throw new RegistrationException(AppointmentErrorCodes.SLOT_DETAILS_NOT_AVAILABLE.getErrorCode(),
					AppointmentErrorCodes.SLOT_DETAILS_NOT_AVAILABLE.getErrorMessage());
		}
		return slotDetails;
	}

	private static Date getDateTime(Date date, String time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.substring(0, 2)));
		calendar.set(Calendar.MINUTE, Integer.parseInt(time.substring(3, 5)));
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	private static Date getZeroTimeDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		return date;
	}

	@Override
	public PatientDetailsDTO getPatientDetails(String patientId) {
		// TODO Auto-generated method stub
		PatientDetailsDTO patientDetailsDTO = new PatientDetailsDTO();

		PatientRegDtlsEntity patientDetails = patientRegDtlsRepository.findByPrdUserId(patientId);

		logger.info("Fetching patient details from database for patient Id: {}", patientId);
		if (patientDetails == null) {
			logger.error("No patient Data found");
			throw new RegistrationException(AppointmentErrorCodes.PATIENT_DETAILS_NOT_AVAILABLE.getErrorCode(),
					AppointmentErrorCodes.PATIENT_DETAILS_NOT_AVAILABLE.getErrorMessage());
		}
		patientDetailsDTO.setPatientFullName(patientDetails.getPrdPtName());
		patientDetailsDTO.setMobileNumber(patientDetails.getPrdMobileNo());
		patientDetailsDTO.setEmailId(patientDetails.getPrdEmail());
		patientDetailsDTO.setPtProfilePhoto(patientDetails.getPrdPhotoPath());

		return patientDetailsDTO;
	}

	@Override
	@Transactional
	public SaveAppDetailsResponse saveAppointmentDetails(SaveAppDetailsRequest appointmentRequest) {
		// TODO Auto-generated method stub
		SaveAppDetailsResponse appointmentResponse = new SaveAppDetailsResponse();
		Date slotDateTime = getDateTime(appointmentRequest.getAppointmentDetails().getAppointmentDate(),
				appointmentRequest.getAppointmentDetails().getAppointmentSlot());

		if (slotDateTime.before(new Date())) {
			logger.error("Can't use past date for booking");
			logger.error("request date and time : {}", slotDateTime);
			logger.error("current date : {}", new Date());
			throw new RegistrationException(AppointmentErrorCodes.PAST_DATE_TIME_CANNOT_BOOKED.getErrorCode(),
					AppointmentErrorCodes.PAST_DATE_TIME_CANNOT_BOOKED.getErrorMessage());
		}
		//Payment details removed.
		PaymentDtlsEntity paymentDtlsEntity = paymentDtlsRepository
				.findByPdPmtTransId(appointmentRequest.getTransactionID());
		if (paymentDtlsEntity == null) {
			logger.error("No payment details found for tarnsactionId: {}", appointmentRequest.getTransactionID());
			throw new RegistrationException(AppointmentErrorCodes.PAYMENT_DETAILS_NOT_PRESENT.getErrorCode(),
					AppointmentErrorCodes.PAYMENT_DETAILS_NOT_PRESENT.getErrorMessage());
		}

		DocMstrDtlsEntity docMstrDtlsEntity = docMstrDtlsRepository.findByDmdUserId(appointmentRequest.getDrRegID());
		if (docMstrDtlsEntity == null) {
			logger.error("No doctor details found for doctor Id: {}", appointmentRequest.getDrRegID());
			throw new RegistrationException(AppointmentErrorCodes.DOCTOR_DETAILS_NOT_PRESENT.getErrorCode(),
					AppointmentErrorCodes.DOCTOR_DETAILS_NOT_PRESENT.getErrorMessage());
		}
		PatientRegDtlsEntity patientRegDtlsEntity=null;
		if(userDetails.getRole().equalsIgnoreCase(AppointmentConstants.DOCTOR_ROLE) || userDetails.getRole().equalsIgnoreCase(AppointmentConstants.SCRIBE_ROLE)) {
			 patientRegDtlsEntity = patientRegDtlsRepository.findByPrdUserId(appointmentRequest.getPtRegID());
		}
		else
		{
			 patientRegDtlsEntity = patientRegDtlsRepository.findByPrdUserId(userDetails.getUserName());
		}
		
		if (patientRegDtlsEntity == null) {
			logger.error("No patinet details found for patient Id: {}", appointmentRequest.getPtRegID());
			throw new RegistrationException(AppointmentErrorCodes.PATIENT_DETAILS_NOT_AVAILABLE.getErrorCode(),
					AppointmentErrorCodes.PATIENT_DETAILS_NOT_AVAILABLE.getErrorMessage());
		}
		String scribeId = null;
		if(docMstrDtlsEntity.getScrbRegDtls()!=null && docMstrDtlsEntity.getScrbRegDtls().size()>0) {
		
			for(ScribeRegEntity details:docMstrDtlsEntity.getScrbRegDtls()) {
				if(details.getIsDefaultScribe()!=null && details.getIsDefaultScribe().equalsIgnoreCase("Y")) {
					scribeId =details.getScrbUserID();
					break;
				}
			}
			ApptDrScrbAssignDtl apptDrScrbAssignDtl = apptDrScrbAssignDtlsReposotory.findByDrUserIdFkAndDate(
					appointmentRequest.getDrRegID(), appointmentRequest.getAppointmentDetails().getAppointmentDate());
				scribeId = apptDrScrbAssignDtl == null ? scribeId : apptDrScrbAssignDtl.getScribeUserIdFk();
		}
		 
		AppointmentDtlsEntity appointmentDtlsEntity = new AppointmentDtlsEntity();
		appointmentDtlsEntity.setAdScrbUserId(scribeId);
		appointmentDtlsEntity.setAdApptId(generateAppointmentId());
		appointmentDtlsEntity.setDocMstrDtlsEntity(docMstrDtlsEntity);

		if (appointmentRequest.getBookForSomeoneElse().equalsIgnoreCase("y")) {
			appointmentDtlsEntity.setAdApptBookedFor(appointmentRequest.getPatientName());
			appointmentDtlsEntity.setAdPatientEmail(appointmentRequest.getPatientEmail());
			appointmentDtlsEntity.setAdPatientMobileNo(appointmentRequest.getPatientMNO());
		}
		appointmentDtlsEntity.setPatientRegDtlsEntity(patientRegDtlsEntity);
		appointmentDtlsEntity.setAdApptSlotFk(appointmentRequest.getAppointmentDetails().getAppointmentSlot());
		appointmentDtlsEntity.setAdApptDateFk(appointmentRequest.getAppointmentDetails().getAppointmentDate());
		if(appointmentRequest.getBookForSomeoneElse().equalsIgnoreCase("y"))
		{
			appointmentDtlsEntity.setAdIsbooked(true);
		}else
		{
			appointmentDtlsEntity.setAdApptBookedFor(appointmentRequest.getPatientName());
			appointmentDtlsEntity.setAdIsbooked(false);
		}
		appointmentDtlsEntity.setAdApptStatus(AppointmentConstants.SCHEDULED);
		appointmentDtlsEntity.setPaymentDtlsEntity(paymentDtlsEntity);
		appointmentDtlsEntity.setAdCreatedBy(userDetails.getUserName());
		appointmentDtlsEntity.setAdCreatedTmstmp(LocalDateTime.now());
		AppointmentDtlsEntity appointmentDetails = appointmentDtlsRepository.save(appointmentDtlsEntity);
		logger.info("Appointment Booked Successfully");
		auditService.auditAppointmentData(appointmentDetails);
		appointmentResponse.setAppointmentID(appointmentDetails.getAdApptId());
		appointmentResponse.setInfo(AppointmentConstants.APPOINTMENT_SAVED_SUCCESSFULLY);
		//Call SMS email URL
		sendSMSEmailNotification(appointmentDtlsEntity,docMstrDtlsEntity,patientRegDtlsEntity,"appointment_success");
		return appointmentResponse;
	}

	private synchronized String generateAppointmentId() {

		List<AppointmentSeqEntity>seqList=seqRepository.findAll();
		if(seqList.size()>0) {
			AppointmentSeqEntity appointmentSeqEntity=seqList.get(0);
			String apptId=AlphabetEncoder.generateAppointmentId(appointmentSeqEntity.getSeq().intValue());
			appointmentSeqEntity.setSeq(appointmentSeqEntity.getSeq()+1);
			seqRepository.save(appointmentSeqEntity);
			return apptId;
		}
		else {

			SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss"); 
			String datetime = ft.format(new Date()); 
			String randomTwoDigitNumber = String.valueOf(new Random().nextInt(90) + 10);
			return datetime.concat(randomTwoDigitNumber);
		}
	}

	@SuppressWarnings("unused")
	@Override
	public CancelAppointmentResponse cancelAppointment(CancelAppointmentRequest cancelAppointmentRequest) {

		CancelAppointmentResponse appointmentResponse = new CancelAppointmentResponse();
		AppointmentDtlsEntity UpdatedAppointmentDtlsEntity=null;
		AppointmentDtlsEntity appointmentDtlsEntity = appointmentDtlsRepository
				.findByAdApptId(cancelAppointmentRequest.getApptId());
		
		
		Date slotDateTime = getDateTime(appointmentDtlsEntity.getAdApptDateFk(),
				appointmentDtlsEntity.getAdApptSlotFk());

		if (slotDateTime.before(new Date())) {
			logger.error("Sorry, you can not take past date to reschedule appointment");
			throw new RegistrationException(AppointmentErrorCodes.PAST_DATE_TIME_CANNOT_CANCELLED.getErrorCode(),
					AppointmentErrorCodes.PAST_DATE_TIME_CANNOT_CANCELLED.getErrorMessage());
		}
		
		if (appointmentDtlsEntity != null) {
			logger.info("Fetched appointment details");
			/*if (appointmentDtlsEntity.getAdApptDateFk().before(new Date())) {
				logger.error(AppointmentErrorCodes.PAST_DATE_TIME_CANNOT_CANCELLED.getErrorMessage());
				throw new RegistrationException(AppointmentErrorCodes.PAST_DATE_TIME_CANNOT_CANCELLED.getErrorCode(),
						AppointmentErrorCodes.PAST_DATE_TIME_CANNOT_CANCELLED.getErrorMessage());
			}*/
			logger.info("Updated appointment status to cancel");
			appointmentDtlsEntity.setAdApptStatus(AppointmentConstants.CANCEL);
			appointmentDtlsEntity.setApCancelReason(cancelAppointmentRequest.getReason());
			appointmentDtlsEntity.setApCancelDate(null);
			appointmentDtlsEntity.setAdModifiedBy(userDetails.getUserName());
			appointmentDtlsEntity.setAdModifiedTmstmp(LocalDateTime.now());
		    UpdatedAppointmentDtlsEntity = appointmentDtlsRepository.save(appointmentDtlsEntity);
		    auditService.auditAppointmentData(appointmentDtlsEntity);
		}else
		{
			throw new RegistrationException(AppointmentErrorCodes.NO_APPOINTMENT_FOUND.getErrorCode(),
					AppointmentErrorCodes.NO_APPOINTMENT_FOUND.getErrorMessage());
		}
		if(null!=UpdatedAppointmentDtlsEntity)
		{
			sendSMSEmailNotification(appointmentDtlsEntity, appointmentDtlsEntity.getDocMstrDtlsEntity(), appointmentDtlsEntity.getPatientRegDtlsEntity(), "Appointment_Fail");
			appointmentResponse.setAppointmentID(UpdatedAppointmentDtlsEntity.getAdApptId());
			appointmentResponse.setInfo(AppointmentConstants.APPOINTMENT_CANCELLED_SUCCESSFULLY);
		}else
		{
			throw new RegistrationException(AppointmentErrorCodes.REG_EXCEP.getErrorCode(),
					AppointmentErrorCodes.REG_EXCEP.getErrorMessage());
		}
		return appointmentResponse;
	}

	@Override
	public ApptDtlsDto getAppointmentDetails(String apptId) {
		AppointmentDtlsEntity appointmentDtls = null;
		if (userDetails.getRole().equals(AppointmentConstants.PATIENT_ROLE)) {
			logger.info("Fetching appointment details for patient role");
			appointmentDtls = appointmentDtlsRepository.findByAdApptIdAndPrdUserId(apptId, userDetails.getUserName());
		} else if (userDetails.getRole().equals(AppointmentConstants.DOCTOR_ROLE)) {
			logger.info("Fetching appointment details for doctor role");
			appointmentDtls = appointmentDtlsRepository.findByAdApptIdAndDmdUserId(apptId, userDetails.getUserName());
		}
		if (appointmentDtls != null) {
			logger.info("Initializing response object");
			ApptDtlsDto response = new ApptDtlsDto();
			response.setAppointmentId(apptId);
			response.setBookedFor(appointmentDtls.getAdApptBookedFor());
			response.setDateTime(
					(appointmentDtls.getAdApptDateFk() + appointmentDtls.getAdApptSlotFk()).replace("00:00:00.0", ""));
			response.setDrName(appointmentDtls.getDocMstrDtlsEntity().getDmdDrName());
			response.setSpecialization(appointmentDtls.getDocMstrDtlsEntity().getDmdSpecialiazation());
			return response;
		} else {
			throw new RegistrationException(
					AppointmentErrorCodes.NO_APPOINTMENT_REGISTERED_FOR_CURRENT_ID.getErrorCode(),
					AppointmentErrorCodes.NO_APPOINTMENT_REGISTERED_FOR_CURRENT_ID.getErrorMessage());
		}

	}

	@Override
	public SaveAppDetailsResponse saveReschdulesAppDetails(RescheduleApptDTO appointmentRequest) {
		// TODO Auto-generated method stub
		SaveAppDetailsResponse appointmentResponse = new SaveAppDetailsResponse();
		AppointmentDtlsEntity appointmentDtls = new AppointmentDtlsEntity();
		Date slotDateTime = getDateTime(appointmentRequest.getAppointmentDetails().getReschduleappDate(),
				appointmentRequest.getAppointmentDetails().getReschduleappSlot());

		if (slotDateTime.before(new Date())) {
			logger.error("Sorry, you can not take past date to reschedule appointment");
			throw new RegistrationException(AppointmentErrorCodes.PAST_DATE_TIME_RESCHDULED_BOOKED.getErrorCode(),
					AppointmentErrorCodes.PAST_DATE_TIME_RESCHDULED_BOOKED.getErrorMessage());
		}
		logger.info("get previous Appt details with appID");
		appointmentDtls = appointmentDtlsRepository.findByAdApptId(appointmentRequest.getAppID(),
					userDetails.getUserName());
		if(null!=appointmentDtls)
		{	
			appointmentDtls.setAdApptSlotFk(appointmentRequest.getAppointmentDetails().getReschduleappSlot());
			appointmentDtls.setAdApptDateFk(appointmentRequest.getAppointmentDetails().getReschduleappDate());
			appointmentDtls.setAdApptStatus(AppointmentConstants.RESCHEDULED);
			appointmentDtls.setAdModifiedBy(userDetails.getUserName());
			appointmentDtls.setAdModifiedTmstmp(LocalDateTime.now());
			AppointmentDtlsEntity appointmentDetails = appointmentDtlsRepository.save(appointmentDtls);
			logger.info("Saving Appointment Rescheduled Details");
			auditService.auditAppointmentData(appointmentDetails);
		
		}else{
			logger.error("Appointment details are not present");
			throw new RegistrationException(AppointmentErrorCodes.NO_APPOINTMENT_FOUND.getErrorCode(),
					AppointmentErrorCodes.NO_APPOINTMENT_FOUND.getErrorMessage());
		}
		appointmentResponse.setAppointmentID(appointmentDtls.getAdApptId());
		appointmentResponse.setInfo(AppointmentConstants.APPOINTMENT_RESCHDULE_SUCCESSFULLY);
		logger.info("bind response successfully");
		//send Notification to users.
		sendSMSEmailNotification(appointmentDtls,appointmentDtls.getDocMstrDtlsEntity(),appointmentDtls.getPatientRegDtlsEntity(),"reschedule");
		return appointmentResponse;

	}

	@Override
	public StatusResponse assignScribeToApptByDoctor(AssignScribeToApptRequest request) {

		try {
			ApptDrScrbAssignDtl scrbAssignDtl =null;
			scrbAssignDtl = apptDrScrbAssignDtlsReposotory.findByDrUserIdFkAndDate(
					userDetails.getUserName(), request.getAssignDate());
			if(scrbAssignDtl!=null) {
				scrbAssignDtl.setModifiedBy(userDetails.getUserName());
				scrbAssignDtl.setModifiedTmstmp(LocalDateTime.now());
				scrbAssignDtl.setDate(request.getAssignDate());
				scrbAssignDtl.setDrUserIdFk(userDetails.getUserName());
				scrbAssignDtl.setIsactive(true);
				scrbAssignDtl.setScribeUserIdFk(request.getScribeID().trim().toUpperCase());
			}else {
				scrbAssignDtl = new ApptDrScrbAssignDtl();
				scrbAssignDtl.setCreatedBy(userDetails.getUserName());
				scrbAssignDtl.setCreatedTmstmp(LocalDateTime.now());
				scrbAssignDtl.setDate(request.getAssignDate());
				scrbAssignDtl.setDrUserIdFk(userDetails.getUserName());
				scrbAssignDtl.setIsactive(true);
				scrbAssignDtl.setScribeUserIdFk(request.getScribeID().trim().toUpperCase());
			}
			apptDrScrbAssignDtlsReposotory.save(scrbAssignDtl);
			List<AppointmentDtlsEntity> apptList=appointmentDtlsRepository.findByAdApptDateFkAndDmdUserId(request.getAssignDate(), userDetails.getUserName());
			for (AppointmentDtlsEntity appointmentDtlsEntity : apptList) {
				appointmentDtlsEntity.setAdScrbUserId(request.getScribeID().trim().toUpperCase());
				appointmentDtlsEntity.setAdModifiedBy(userDetails.getUserName());
				appointmentDtlsEntity.setAdModifiedTmstmp(LocalDateTime.now());
				appointmentDtlsRepository.save(appointmentDtlsEntity);
			}
		} catch (DataIntegrityViolationException e) {
			if (e.getMessage().contains("fk_scribe_appt_dr_scrb_assign")) {
				logger.error("Scribe details not found for  Scribe Id: {}", request.getScribeID());
				throw new RegistrationException(AppointmentErrorCodes.SCRIBE_DETAILS_NOT_PRESENT.getErrorCode(),
						AppointmentErrorCodes.SCRIBE_DETAILS_NOT_PRESENT.getErrorMessage());
			} else if (e.getMessage().contains("fk_dr_appt_dr_scrb_assign")) {
				logger.error("Doctor details not found for  Doctor Id: {}", userDetails.getUserName());
				throw new RegistrationException(AppointmentErrorCodes.DOCTOR_DETAILS_NOT_PRESENT.getErrorCode(),
						AppointmentErrorCodes.DOCTOR_DETAILS_NOT_PRESENT.getErrorMessage());
			} else {
				logger.error(
						"Doctor Scribe appointment already available for Scribe Id: {} and Doctor Id: {} and date :{}",
						request.getScribeID(), userDetails.getUserName(), request.getAssignDate());
				throw new RegistrationException(AppointmentErrorCodes.DOCTOR_SCRIBE_APPOINTMENT_PRESENT.getErrorCode(),
						AppointmentErrorCodes.DOCTOR_SCRIBE_APPOINTMENT_PRESENT.getErrorMessage());
			}
		} catch (Exception e) {
			logger.error(AppointmentErrorCodes.INTERNAL_SERVER_ERROR.getErrorMessage(), e);
			throw new RegistrationException(AppointmentErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode(),
					AppointmentErrorCodes.INTERNAL_SERVER_ERROR.getErrorMessage());
		}
		return StatusResponse.builder().info(AppointmentConstants.SCRIBE_ASSIGNED_SUCCESSFULLY).build();

	}

	@Override
	public AssignedScribeToApptResponse assignedApptListToScribe(String scribeId) {

		AssignedScribeToApptResponse response = new AssignedScribeToApptResponse();

		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);

		List<AppointmentDtlsEntity> appointmentList = appointmentDtlsRepository
				.findByAdScrbUserIdAndAdApptDateFk(scribeId, today, tomorrow);

		if (!EmptyCheckUtils.isNullEmpty(appointmentList)) {
			logger.info("Fetching Scribe Details");

			List<ApptDetailsForScribe> todayAppointmentList = new ArrayList<>();
			List<ApptDetailsForScribe> tomorrowAppointmentList = new ArrayList<>();
			DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
			for (AppointmentDtlsEntity appointment : appointmentList) {
				if (appointment.getAdApptDateFk().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.equals(today)) {
					todayAppointmentList.add(getScribeAppointmentDetails(outputFormatter, appointment));
				} else if (appointment.getAdApptDateFk().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						.equals(tomorrow)) {
					tomorrowAppointmentList.add(getScribeAppointmentDetails(outputFormatter, appointment));
				}
			}
			Map<String, List<ApptDetailsForScribe>> scribeDetails = new LinkedHashMap<String, List<ApptDetailsForScribe>>();
			scribeDetails.put(AppointmentConstants.TODAY_FOR_SCRIBE, todayAppointmentList);
			scribeDetails.put(AppointmentConstants.TOMORROW_FOR_SCRIBE, tomorrowAppointmentList);

			response.setScribeID(scribeId);
			response.setScribeDetails(scribeDetails);

		} else {
			logger.error("No appointment found for Scribe Id: {}", scribeId);
			throw new RegistrationException(AppointmentErrorCodes.NO_APPOINTMENT_FOUND_FOR_SCRIBE_ID.getErrorCode(),
					AppointmentErrorCodes.NO_APPOINTMENT_FOUND_FOR_SCRIBE_ID.getErrorMessage());
		}
		return response;
	}

	private ApptDetailsForScribe getScribeAppointmentDetails(DateFormat outputFormatter,
			AppointmentDtlsEntity appointment) {
		return ApptDetailsForScribe.builder().appointmentID(appointment.getAdApptId())
				.appointmentTime(appointment.getAdApptSlotFk())
				.apptDate(outputFormatter.format(appointment.getAdApptDateFk()))
				.drRegID(appointment.getDocMstrDtlsEntity().getDmdUserId())
				.patientName(appointment.getPatientRegDtlsEntity().getPrdPtName())
				.patientRegId(appointment.getPatientRegDtlsEntity().getPrdUserId())
				.doctorName(appointment.getDocMstrDtlsEntity().getDmdDrName())
				.docProfilePhoto(appointment.getDocMstrDtlsEntity().getDmdPhotoPath())
				.ptEmail(appointment.getPatientRegDtlsEntity().getPrdEmail())
				.ptMNo(appointment.getPatientRegDtlsEntity().getPrdMobileNo())
				.status(findAppointmentStatus(appointment.getAdApptStatus())).build();
	}

	@Override
	public AssignedScribeStatus isScribeAssignedToAppt(MainRequestDTO<ScribeAppptDetailsDTO> request) {
		// TODO Auto-generated method stub
		AppointmentDtlsEntity appointmentDtls = appointmentDtlsRepository.findByAdApptId(request.getRequest().getAppointmentID());
		if (appointmentDtls != null) {
			if (appointmentDtls.getAdScrbUserId() != null) {
				return AssignedScribeStatus.builder().assignedStatus(true).build();
			} else {
				return AssignedScribeStatus.builder().assignedStatus(false).build();
			}
		} else {
			throw new RegistrationException(
					AppointmentErrorCodes.NO_APPOINTMENT_REGISTERED_FOR_CURRENT_ID.getErrorCode(),
					AppointmentErrorCodes.NO_APPOINTMENT_REGISTERED_FOR_CURRENT_ID.getErrorMessage());
		}
	}

	private String findAppointmentStatus(String status) {
		switch (status) {
		case "S":
			return "Scheduled";
		case "C":
			return "Completed";
		case "X":
			return "Cancel";
		case "R":
			return "Resheduled";
		default:
			return null;
		}
	}
	private MainResponseDTO<UsrOtpEmailVerifyResponseDTO> sendSMSEmailNotification(AppointmentDtlsEntity appointmentDtlsEntity,
			DocMstrDtlsEntity docMstrDtlsEntity, PatientRegDtlsEntity patientRegDtlsEntity, String flagchk) {
		// TODO Auto-generated method stub
		MainRequestDTO<UsrOtpEmailVerifyDTO> mainRequest = new MainRequestDTO<>();
		UsrOtpEmailVerifyDTO usrOtpEmailVerifyDTO=new UsrOtpEmailVerifyDTO();
		usrOtpEmailVerifyDTO.setUserId("");
		Date date = appointmentDtlsEntity.getAdApptDateFk();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
		String strDate = dateFormat.format(date);  
		usrOtpEmailVerifyDTO.setAppointmentDate(strDate);
		usrOtpEmailVerifyDTO.setAppointmentTime(appointmentDtlsEntity.getAdApptSlotFk());
		usrOtpEmailVerifyDTO.setDocEmailId(docMstrDtlsEntity.getDmdEmail());
		usrOtpEmailVerifyDTO.setDocMobileNo(docMstrDtlsEntity.getDmdMobileNo());
		usrOtpEmailVerifyDTO.setPatientName(patientRegDtlsEntity.getPrdPtName());
		usrOtpEmailVerifyDTO.setPtEmailId(patientRegDtlsEntity.getPrdEmail());
		usrOtpEmailVerifyDTO.setPtMobileNo(patientRegDtlsEntity.getPrdMobileNo());
		usrOtpEmailVerifyDTO.setSendType(null != docMstrDtlsEntity.getDmdEmail() && !docMstrDtlsEntity.getDmdEmail().isEmpty() ? "both" : "sms");//changed by girishk
		if(flagchk.equalsIgnoreCase("reschedule"))
		{
			usrOtpEmailVerifyDTO.setTemplateType("reschedule");
		}else if(flagchk.equalsIgnoreCase("appointment_success"))
		{
			usrOtpEmailVerifyDTO.setTemplateType("appointment_success");
		}else
		{
			usrOtpEmailVerifyDTO.setTemplateType("Appointment_Fail");
		}
		usrOtpEmailVerifyDTO.setDoctorName(docMstrDtlsEntity.getDmdDrName());
		mainRequest.setRequest(usrOtpEmailVerifyDTO);
		HttpEntity<MainRequestDTO<UsrOtpEmailVerifyDTO>> requestEntity = new HttpEntity<MainRequestDTO<UsrOtpEmailVerifyDTO>>(mainRequest);
		ParameterizedTypeReference<MainResponseDTO<UsrOtpEmailVerifyResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<UsrOtpEmailVerifyResponseDTO>>() {
		};
		ResponseEntity<MainResponseDTO<UsrOtpEmailVerifyResponseDTO>> response = restTemplate.exchange(emailNotificationURL, HttpMethod.POST, requestEntity, parameterizedResponse);
		return response.getBody();
	}

	@Override
	public List<ApptDtlsDto> getPatientCompleteAppointmentDetails(String ptUserId) {
		List<AppointmentDtlsEntity> entityList = null;
		List<ApptDtlsDto> responseList = new ArrayList<>();
		ApptDtlsDto apptDtlsDto = new ApptDtlsDto();
		if(ptUserId==null && ptUserId.isEmpty()) {
			throw new RegistrationException(AppointmentErrorCodes.INVALID_PATIENT_ID.getErrorCode(),
					AppointmentErrorCodes.INVALID_PATIENT_ID.getErrorMessage());
		}
		entityList = appointmentDtlsRepository.findByAdPtUserId(ptUserId);
		if(EmptyCheckUtils.isNullEmpty(entityList)) {
			throw new RegistrationException(AppointmentErrorCodes.NO_APPOINTMENT_FOUND_FOR_PATIENT_ID.getErrorCode(),
					AppointmentErrorCodes.NO_APPOINTMENT_FOUND_FOR_PATIENT_ID.getErrorMessage());
		}
		for(AppointmentDtlsEntity entity : entityList) {
			apptDtlsDto.setAppointmentId(entity.getAdApptId());
			apptDtlsDto.setBookedFor(entity.getAdApptBookedFor());
			apptDtlsDto.setDateTime(
					(entity.getAdApptDateFk() + entity.getAdApptSlotFk()).replace("00:00:00.0", ""));
			apptDtlsDto.setDrName(entity.getDocMstrDtlsEntity().getDmdDrName());
			apptDtlsDto.setSpecialization(entity.getDocMstrDtlsEntity().getDmdSpecialiazation());
			responseList.add(apptDtlsDto);
		}
		return responseList;
	}
	
	@Override
	public void sendNotificationBeforeAppointmentStart() {
		try {
			LocalDate today = LocalDate.now();
			List<AppointmentDtlsEntity> entityList = appointmentDtlsRepository.sendNotificationBeforeAppointmentStart(today);
			//send SMS to patient before appointment start
			if(null != entityList && entityList.size() > 0) {
				for (AppointmentDtlsEntity appointmentEntity : entityList) {
					TemplateDtls details = new TemplateDtls();
					details.setPatientName(appointmentEntity.getPatientRegDtlsEntity().getPrdPtName());
					details.setDoctorName(appointmentEntity.getDocMstrDtlsEntity().getDmdDrName());
					details.setPtMobileNo(appointmentEntity.getPatientRegDtlsEntity().getPrdMobileNo());
					details.setDocMobileNo(String.valueOf(appointmentEntity.getDocMstrDtlsEntity().getDmdMobileNo()));
					details.setTemplateType("Notify_Before_Appointment_Patient_SMS");
					details.setSendType("sms");
					sendNotificationOnSMS(details);
				}
			}
		}catch(Exception e) {
			logger.error("Exception while getting appointments to send notifiaction before started");
			e.printStackTrace();
		}
	}
	
	private void sendNotificationOnSMS(TemplateDtls details){
		MainRequestDTO<TemplateDtls> mainRequest  = new MainRequestDTO<TemplateDtls>();
		try {
			mainRequest.setRequest(details);
			HttpEntity<MainRequestDTO<TemplateDtls>> requestEntity = new HttpEntity<MainRequestDTO<TemplateDtls>>(
					mainRequest);
			ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>> parameterizedResponse = new ParameterizedTypeReference<MainResponseDTO<OtpResponseDTO>>() {
			};
			ResponseEntity<MainResponseDTO<OtpResponseDTO>> response = restTemplate.exchange(emailNotificationURL,HttpMethod.POST, requestEntity, parameterizedResponse);
		}catch(Exception e) {
			logger.error("Exception while calling email API : method sendNotificationOnSMS()");
	        e.printStackTrace();
		}
	}

}
