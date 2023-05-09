package com.nsdl.telemedicine.consultancy.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsdl.telemedicine.consultancy.constant.ConsultationConstants;
import com.nsdl.telemedicine.consultancy.constant.ErrorConstants;
import com.nsdl.telemedicine.consultancy.dto.AppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.DrAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.MainRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.PtAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.TokenDTO;
import com.nsdl.telemedicine.consultancy.dto.UserDTO;
import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultationDtl;
import com.nsdl.telemedicine.consultancy.entity.DocMstrDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.DocSlotDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.exception.ConsultationServiceException;
import com.nsdl.telemedicine.consultancy.logger.LoggingClientInfo;
import com.nsdl.telemedicine.consultancy.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.DocSlotDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ReceptionistDtlsRepository;
import com.nsdl.telemedicine.consultancy.service.AppointmentService;
import com.nsdl.telemedicine.consultancy.utility.EmptyCheckUtility;
import com.nsdl.telemedicine.consultancy.utility.TokenValidator;


@Service
@Transactional
@LoggingClientInfo
public class AppointmentServiceImpl implements AppointmentService {
	
	@Autowired
	private AppointmentDtlsRepository appointmentDtlsRepository;
	
	@Autowired
	DocSlotDtlsRepository drSlotRepository;

	@Autowired
	private UserDTO userDTO;
	
	@Autowired
	ConsultationDtlsRepository consultationDtlsRepository;
	
	@Autowired
	ReceptionistDtlsRepository receptionistDtlsRepository;
	
	@Value("${consultation.manager.service.prescription.path}")
	private String prescriptionPath;

	@Value("${consultation.manager.service.path.seperator}")
	private String pathSeperator;
	
	@Value("${convenience.charge}")
	private Integer convenienceCharge;

	private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	
	@Value("${auth.jwt.base}")
	private String base;

	@Autowired
	private TokenValidator tokenValidator;
	
	@Override
	public PtAppointmentDTO getAppointmentListByPatientID() {

		PtAppointmentDTO response = new PtAppointmentDTO();

		List<AppointmentDtlsEntity> appointmentList = appointmentDtlsRepository
				.findByPrdUserIdOrderByAdApptDateFkAdApptSlotFk(userDTO.getUserName());
		
		logger.info("Fetch appointment details by patient ID");

		if (!EmptyCheckUtility.isNullEmpty(appointmentList)) {
			logger.info("Count of appointmentList::"+ appointmentList.size());
			PatientRegDtlsEntity patientRegDtls = appointmentList.get(0).getPatientRegDtlsEntity();
			List<AppointmentDTO> appointmentDtoList = new ArrayList<AppointmentDTO>();
			String filePath = prescriptionPath + pathSeperator;
			for (AppointmentDtlsEntity appointment : appointmentList) {
				AppointmentDTO aptDtl = AppointmentDTO.builder().appointmentID(appointment.getAdApptId())
						.appointmentDate(appointment.getAdApptDateFk().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
						.appointmentTime(appointment.getAdApptSlotFk())
						.doctorSpeciality(appointment.getDocMstrDtlsEntity().getDmdSpecialiazation())
						.doctorName(appointment.getDocMstrDtlsEntity().getDmdDrName())
						.profilePhoto(appointment.getDocMstrDtlsEntity().getProfilePhoto())
						.doctorUserId(appointment.getDocMstrDtlsEntity().getDmdUserId())
						.patientName(appointment.getAdApptBookedFor()).patientRegId(patientRegDtls.getPrdUserId())
						.slotType(appointment.getAdConsultType())
						.doctorConsulFee(appointment.getDocMstrDtlsEntity().getDmdConsulFee())
						.status(getStatusFromStatusCode(appointment.getAdApptStatus())).build();
				if(appointment.getAdApptStatus().equalsIgnoreCase("C")) {
					logger.info("Appointment AdAppStatus is = C");
					List<ConsultationDtl> consultationDtlList= consultationDtlsRepository.findByCtApptId(appointment.getAdApptId());
					if (consultationDtlList.size() > 0) {
						logger.info("Count of consultationDtlList::"+ consultationDtlList.size());
						aptDtl.setChiefComplaints(consultationDtlList.get(0).getCtChiefComplaints());
						aptDtl.setPath(consultationDtlList.get(0).getCtPrescriptionPath());
					}
				//aptDtl.setPath(filePath + appointment.getAdApptId() +".pdf");
				}
				appointmentDtoList.add(aptDtl);
			}
			response.setApptDtls(appointmentDtoList);
			response.setPtRegID(patientRegDtls.getPrdUserId());
			logger.info("Mapped appointment data to response");
		} else {
			logger.error(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
		}
		logger.info("Returning from getAppointmentListByPatientID() of service class");
		return response;
	}

	@Override
	public DrAppointmentDTO getAppointmentListByDrID(String apptDate) {
		logger.info("Fetch appointment list by docter ID");
		DrAppointmentDTO response = new DrAppointmentDTO();

		List<AppointmentDtlsEntity> appointmentList = null;
		List<DocSlotDtlsEntity> slotList = null;
		DocMstrDtlsEntity docMstrDtls =null;
		String druserid=null;
		List<AppointmentDTO> todayAppointmentList = new ArrayList<AppointmentDTO>();
		//LocalDate today = LocalDate.now();
		//LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate appointmentDate=null;
		try {
			if(apptDate!=null && apptDate!="")
				appointmentDate=LocalDate.parse(apptDate);
			else
				appointmentDate= LocalDate.now();
		}
		catch(Exception e) {
			logger.error(ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getMessage());
			throw new ConsultationServiceException(ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getCode(),
					ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getMessage());
		}
		if (userDTO.getRole().equals(ConsultationConstants.SCRIBE_ROLE.getValue())) {
			
			logger.info("Consultation role is Scribe");
			if(appointmentDate!=null) {
				appointmentList = appointmentDtlsRepository
						.findByAdApptDateFkAndAdScrbUserIdAndOrderByAdApptDateFkAdApptSlotFk(appointmentDate,
								userDTO.getUserName());
				logger.info("Size of Scribe Appointment details :: " + appointmentList.size());
				List<String> docRegIdList=drSlotRepository.getDocRegId(userDTO.getUserName());
				if(docRegIdList.size()>0)
				{
					slotList=drSlotRepository.findDocSlotDtlsByDate(String.valueOf(docRegIdList.get(0)), appointmentDate);
					logger.info("Size of scribe slot list :: " + slotList.size());
				}
				
			}
			/*
			 * else { appointmentList = appointmentDtlsRepository
			 * .findByAdApptDateFkAndAdScrbUserIdAndOrderByAdApptDateFkAdApptSlotFk(today,
			 * tomorrow, userDTO.getUserName()); }
			 */
		} else if (userDTO.getRole().equals(ConsultationConstants.DOCTOR_ROLE.getValue())) {
			logger.info("Consultation role is Doctor");
			if(appointmentDate!=null) {
				appointmentList = appointmentDtlsRepository
						.findByAdApptDateFkAndDmdUserIdAndOrderByAdApptDateFkAdApptSlotFk(appointmentDate,
								userDTO.getUserName());
				logger.info("Size of Docter Appointment details :: " + appointmentList.size());
				slotList=drSlotRepository.findDocSlotDtlsByDate(userDTO.getUserName(), appointmentDate);
				logger.info("Size of docter slot list :: " + slotList.size());
			}
			
			/*
			 * else { appointmentList = appointmentDtlsRepository
			 * .findByAdApptDateFkAndDmdUserIdAndOrderByAdApptDateFkAdApptSlotFk(today,
			 * tomorrow, userDTO.getUserName()); }
			 */
		}
		else if (userDTO.getRole().equals(ConsultationConstants.RECEPT_ROLE.getValue())) {
			logger.info("Consultation role is RECEPTIONIST");
			/*
			 * ReceptionistDtlsEntity receptionistDtlsEntity=new ReceptionistDtlsEntity();
			 * receptionistDtlsEntity=receptionistDtlsRepository.findByReceptUserId(userDTO.
			 * getUserName()); if(null!=receptionistDtlsEntity) {
			 * druserid=receptionistDtlsEntity.getRrdDrUserIdFk(); }
			 * if(appointmentDate!=null) { appointmentList = appointmentDtlsRepository.
			 * findByAdApptDateFkAndReceptUserIdAndOrderByAdApptDateFkAdApptSlotFk(
			 * appointmentDate, userDTO.getUserName()); if(null!=druserid) {
			 * slotList=drSlotRepository.findDocSlotDtlsByDate(druserid, appointmentDate); }
			 * }
			 */
			
			List<String> docRegIdList=drSlotRepository.getDocRegIdByReceptionistId(userDTO.getUserName());
			if(docRegIdList.size()>0)
			{
				appointmentList = appointmentDtlsRepository
						.findByAdApptDateFkAndDmdUserIdAndOrderByAdApptDateFkAdApptSlotFk(appointmentDate,
								String.valueOf(docRegIdList.get(0)));
				logger.info("Size of RECEPTIONIST Appointment details :: " + appointmentList.size());
				slotList=drSlotRepository.findDocSlotDtlsByDate(String.valueOf(docRegIdList.get(0)), appointmentDate);
				logger.info("Size of RECEPTIONIST slot list :: " + slotList.size());
			}
		}
		logger.info("Fetched Appointment data");
		if (!EmptyCheckUtility.isNullEmpty(appointmentList)) {
			docMstrDtls = appointmentList.get(0).getDocMstrDtlsEntity();
			//List<AppointmentDTO> tomorrowAppointmentList = new ArrayList<AppointmentDTO>();
			for (AppointmentDtlsEntity appointment : appointmentList) {
				/*
				 * if (appointment.getAdApptDateFk().equals(today)) { AppointmentDTO aptDtl =
				 * buildAppointmentDto(docMstrDtls, appointment);
				 * todayAppointmentList.add(aptDtl); } else if
				 * (appointment.getAdApptDateFk().equals(tomorrow)) { AppointmentDTO aptDtl =
				 * buildAppointmentDto(docMstrDtls, appointment);
				 * tomorrowAppointmentList.add(aptDtl); }else {
				 */
					AppointmentDTO aptDtl = buildAppointmentDto(docMstrDtls, appointment);
					todayAppointmentList.add(aptDtl);
				/* } */
			}
		}
			if (!EmptyCheckUtility.isNullEmpty(slotList)) {
				docMstrDtls = slotList.get(0).getDocMstrDtlsEntity();
				for (DocSlotDtlsEntity slotDtls : slotList) {
						AppointmentDTO aptDtl = buildAppointmentDto(docMstrDtls, slotDtls);
						todayAppointmentList.add(aptDtl);
				}
			
			}
			Collections.sort(todayAppointmentList);	
			  Map<String, List<?>> apptDtls = new LinkedHashMap<String, List<?>>();
			  Map<String, Map<String, List<?>>>apptByTime=new HashMap<>(); Map<String,
			  List<?>> apptList=getApptListByTime(todayAppointmentList,apptDtls);
			  logger.info("Size od appointment list by time ::" + apptList.size());
			  apptByTime.put(ConsultationConstants.TODAY.getValue(), apptList); 
			  //apptDtls = new LinkedHashMap<String, List<?>>();
			  //apptList=getApptListByTime(tomorrowAppointmentList,apptDtls);
			  //apptByTime.put(ConsultationConstants.TOMORROW.getValue(), apptList);
			  response.setApptDtls(apptByTime);
			  if(null!=docMstrDtls)
			  {
				  response.setDoctorConsultFee(docMstrDtls.getDmdConsulFee());
				//added convenience Charge in amount
				  response.setConvenienceCharge(convenienceCharge);
				  response.setDrMobileNumber(docMstrDtls.getDmdMobileNo());
				  
			  }
			 
			//  response.set
			 
		//	Map<String, List<AppointmentDTO>> apptDtls = new LinkedHashMap<String, List<AppointmentDTO>>();
			//apptDtls.put(ConsultationConstants.TODAY.getValue(), todayAppointmentList);
			//apptDtls.put(ConsultationConstants.TOMORROW.getValue(), tomorrowAppointmentList);
		//	response.setApptDtls(apptDtls);
			logger.info("Mapped consultation data to response");
			if(null!=docMstrDtls)
			{
				if(null!=docMstrDtls.getDmdUserId())
				{
					response.setDrRegID(docMstrDtls.getDmdUserId());
				}
			}/*else if(null!=druserid)
			{
				response.setDrRegID(druserid);
			}*/
			
			if(todayAppointmentList.size()==0) {
			logger.error(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
		}
			logger.info("Returning from getAppointmentListByDrID() of service class");
		return response;
	}

	private AppointmentDTO buildAppointmentDto(DocMstrDtlsEntity docMstrDtls, DocSlotDtlsEntity slotDtls) {
		return AppointmentDTO.builder().
				appointmentDate(sdf.format(slotDtls.getDsdSlotDate()))
				.slotType(slotDtls.getDsdSlotType())
				.appointmentTime(slotDtls.getDsdSlot()).doctorName(docMstrDtls.getDmdDrName())
				.doctorUserId(docMstrDtls.getDmdUserId()).doctorSpeciality(docMstrDtls.getDmdSpecialiazation())
				.status("Available").build();
	}

	private Map<String, List<?>> getApptListByTime(List<AppointmentDTO> todayAppointmentList,
			Map<String, List<?>> apptDtls) {
		logger.info("Fetch appointment list by time");
		int startTime=0;
		int endTime=0;
		List<AppointmentDTO> apptList=new ArrayList<AppointmentDTO>();
		Set<String> timeList=new LinkedHashSet<String>();
		logger.info("Size of todays appointment list ::"+todayAppointmentList.size());
		for (AppointmentDTO appointmentDTO : todayAppointmentList) {
			
			if(startTime==0 && endTime==0) {
				startTime=Integer.parseInt(appointmentDTO.getAppointmentTime().split("-")[0].split(":")[0]);
				endTime=startTime+1;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime startDateTime = LocalDateTime.parse(appointmentDTO.getAppointmentDate()+" 00:00:00", formatter);
			startDateTime=startDateTime.withHour(Integer.parseInt(appointmentDTO.getAppointmentTime().split("-")[0].split(":")[0]));
			startDateTime=startDateTime.withMinute(Integer.parseInt(appointmentDTO.getAppointmentTime().split("-")[0].split(":")[1]));
			LocalDateTime endDateTime=startDateTime;
			endDateTime=endDateTime.withHour(Integer.parseInt(appointmentDTO.getAppointmentTime().split("-")[1].split(":")[0]));
			endDateTime=endDateTime.withMinute(Integer.parseInt(appointmentDTO.getAppointmentTime().split("-")[1].split(":")[1]));
			
			if(startDateTime.getHour()>=startTime && (endDateTime.getHour()<=endTime) && (startDateTime.getHour()<=startTime && startDateTime.getHour()<=endTime)) {
				appointmentDTO.setSlot(convertTime24To12Format((startTime<10?"0"+startTime:startTime)+":00")+"-"+convertTime24To12Format((endTime<10?"0"+endTime:endTime)+":00"));
				timeList.add(appointmentDTO.getSlot());
				apptList.add(appointmentDTO);
			}
			else
			{
				apptDtls.put(convertTime24To12Format((startTime<10?"0"+startTime:startTime)+":00")+"-"+convertTime24To12Format((endTime<10?"0"+endTime:endTime)+":00"), apptList);
				apptList=new ArrayList<AppointmentDTO>();
				
				startTime=Integer.parseInt(appointmentDTO.getAppointmentTime().split("-")[0].split(":")[0]);
				endTime=Integer.parseInt(appointmentDTO.getAppointmentTime().split("-")[1].split(":")[0]);
				if(startTime==endTime) {
					endTime=endTime+1;
				}
				//if(endTime> startTime)
				if(startDateTime.getHour()>=startTime && endDateTime.getHour()<=endTime) {
					appointmentDTO.setSlot(convertTime24To12Format((startTime<10?"0"+startTime:startTime)+":00")+"-"+convertTime24To12Format((endTime<10?"0"+endTime:endTime)+":00"));
					timeList.add(appointmentDTO.getSlot());
					apptList.add(appointmentDTO);
				}
			}
			
		}
		if(todayAppointmentList.size()>0)
		apptDtls.put(convertTime24To12Format((startTime<10?"0"+startTime:startTime)+":00")+"-"+convertTime24To12Format((endTime<10?"0"+endTime:endTime)+":00"), apptList);
		apptDtls.put("slots",new ArrayList<>(timeList));
		logger.info("Returning from getApptListByTime() of service class");
		return apptDtls;
		
	}

	private AppointmentDTO buildAppointmentDto(DocMstrDtlsEntity docMstrDtls, AppointmentDtlsEntity appointment) {
		return AppointmentDTO.builder().appointmentID(appointment.getAdApptId())
				.appointmentDate(appointment.getAdApptDateFk().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
				.appointmentTime(appointment.getAdApptSlotFk()).doctorName(docMstrDtls.getDmdDrName())
				.doctorUserId(docMstrDtls.getDmdUserId()).doctorSpeciality(docMstrDtls.getDmdSpecialiazation())
				.patientRegId(appointment.getPatientRegDtlsEntity().getPrdUserId())
				.patientName(appointment.getPatientRegDtlsEntity().getPrdName())
				.doctorConsulFee(docMstrDtls.getDmdConsulFee())
				.slotType(appointment.getAdConsultType())
				.status(getStatusFromStatusCode(appointment.getAdApptStatus())).build();
	}

	private String getStatusFromStatusCode(String adApptStatus) {
		
		switch (adApptStatus) {
		case "S":
			return ConsultationConstants.SCHEDULED.getValue();
		case "C":
			return ConsultationConstants.COMPLETED.getValue();
		case "X":
			return ConsultationConstants.CANCEL.getValue();
		case "R":
			return ConsultationConstants.RESHEDULED.getValue();
		case "SCHEDULEWP":
			return ConsultationConstants.SCHEDULEWP.getValue();
		default:
			return ConsultationConstants.SCHEDULED.getValue();
		}
		
	}

	private void validateRequestString(String request) {
		if (EmptyCheckUtility.isNullEmpty(request)) {
			logger.error("Provided userId is null or empty");
			throw new ConsultationServiceException(ErrorConstants.INVALID_INPUT_DATA.getCode(),
					ErrorConstants.INVALID_INPUT_DATA.getMessage());
		}
	}
	
	public String convertTime24To12Format(String str) 
	{ 
		String output="";
	// Get Hours 
	    int h1 = (int)str.charAt(0) - '0'; 
	    int h2 = (int)str.charAt(1)- '0'; 
	  
	    int hh = h1 * 10 + h2; 
	  
	    // Finding out the Meridien of time 
	    // ie. AM or PM 
	    String Meridien; 
	    if (hh < 12) { 
	        Meridien = "AM"; 
	    } 
	    else
	        Meridien = "PM"; 
	  
	    hh %= 12; 
	  
	    // Handle 00 and 12 case separately 
	    if (hh == 0) { 
	        //System.out.print("12"); 
	    	output=output+"12";
	        // Printing minutes and seconds 
	        for (int i = 2; i < 5; ++i) { 
	        //System.out.print(str.charAt(i));
	        	output=output+str.charAt(i);
	        } 
	    } 
	    else { 
	        //System.out.print(hh);
	    	output=output+hh;
	        // Printing minutes and seconds 
	        for (int i = 2; i < 5; ++i) { 
	       // System.out.print(str.charAt(i));
	        	output=output+str.charAt(i);
	        } 
	    } 
	  
	    // After time is printed 
	    // cout Meridien 
	//System.out.println(" "+Meridien);
	    return output=output+" "+Meridien;
	}

	@Override
	public DrAppointmentDTO getAppointmentListForDrID(MainRequestDTO<TokenDTO> request) {
		logger.info("Fetch appointment list for docter ID");
		// TODO Auto-generated method stub
		String druserid=gettokenDetails(request.getRequest().getToken());
		String apptDate=request.getRequest().getApptDate();
		DrAppointmentDTO response = new DrAppointmentDTO();
		List<AppointmentDtlsEntity> appointmentList = null;
		List<DocSlotDtlsEntity> slotList = null;
		DocMstrDtlsEntity docMstrDtls =null;
		List<AppointmentDTO> todayAppointmentList = new ArrayList<AppointmentDTO>();
		LocalDate appointmentDate=null;
		try {
			if(apptDate!=null && apptDate!="")
				appointmentDate=LocalDate.parse(apptDate);
			else
				appointmentDate= LocalDate.now();
		}
		catch(Exception e) {
			logger.error(ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getMessage());
			throw new ConsultationServiceException(ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getCode(),
					ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getMessage());
		}
			if(appointmentDate!=null) {
				appointmentList = appointmentDtlsRepository
						.findByAdApptDateFkAndDmdUserIdAndOrderByAdApptDateFkAdApptSlotFk(appointmentDate,
								druserid);
				logger.info("Size of Appointment details :: " + appointmentList.size());
				slotList=drSlotRepository.findDocSlotDtlsByDate(druserid, appointmentDate);
				logger.info("Size of slot list :: " + slotList.size());
			}
			
		
		logger.info("Fetched Appointment data");
		if (!EmptyCheckUtility.isNullEmpty(appointmentList)) {
			docMstrDtls = appointmentList.get(0).getDocMstrDtlsEntity();
			//List<AppointmentDTO> tomorrowAppointmentList = new ArrayList<AppointmentDTO>();
			for (AppointmentDtlsEntity appointment : appointmentList) {
				/*
				 * if (appointment.getAdApptDateFk().equals(today)) { AppointmentDTO aptDtl =
				 * buildAppointmentDto(docMstrDtls, appointment);
				 * todayAppointmentList.add(aptDtl); } else if
				 * (appointment.getAdApptDateFk().equals(tomorrow)) { AppointmentDTO aptDtl =
				 * buildAppointmentDto(docMstrDtls, appointment);
				 * tomorrowAppointmentList.add(aptDtl); }else {
				 */
					AppointmentDTO aptDtl = buildAppointmentDto(docMstrDtls, appointment);
					todayAppointmentList.add(aptDtl);
				/* } */
			}
		}
			if (!EmptyCheckUtility.isNullEmpty(slotList)) {
				docMstrDtls = slotList.get(0).getDocMstrDtlsEntity();
				for (DocSlotDtlsEntity slotDtls : slotList) {
						AppointmentDTO aptDtl = buildAppointmentDto(docMstrDtls, slotDtls);
						todayAppointmentList.add(aptDtl);
				}
			
			}
			Collections.sort(todayAppointmentList);	
			  Map<String, List<?>> apptDtls = new LinkedHashMap<String, List<?>>();
			  Map<String, Map<String, List<?>>>apptByTime=new HashMap<>(); Map<String,
			  List<?>> apptList=getApptListByTime(todayAppointmentList,apptDtls);
			  logger.info("Size od appointment list by time ::" + apptList.size());
			  apptByTime.put(ConsultationConstants.TODAY.getValue(), apptList); 
			  response.setApptDtls(apptByTime);
			  response.setDoctorConsultFee(docMstrDtls.getDmdConsulFee());
			logger.info("Mapped consultation data to response");
			if(null!=docMstrDtls)
			{
				if(null!=docMstrDtls.getDmdUserId())
				{
					logger.info("Doctor user ID"+docMstrDtls.getDmdUserId());
					response.setDrRegID(docMstrDtls.getDmdUserId());
					response.setDoctorConsultFee(docMstrDtls.getDmdConsulFee());
					response.setDrName(docMstrDtls.getDmdDrName());
					response.setDrSpecilization(docMstrDtls.getDmdSpecialiazation());
					response.setDrProfilePath(docMstrDtls.getProfilePhoto());
				}
			}
			if(todayAppointmentList.size()==0) {
			logger.error(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
		}
			logger.info("Returning from getAppointmentListForDrID() of service class");
		return response;
	} 
	
	private String gettokenDetails(String token)
	{
		String userName=tokenValidator.getUsernameFromToken(token);
		//String role=tokenValidator.getRoleFromToken(token.replace(base, ""));
		return userName;
	}

	@Override
	public DrAppointmentDTO getScheduledAndRescheduledAppointments(String apptDate) {
        logger.info("Fetch appointment list by docter ID");
		DrAppointmentDTO response = new DrAppointmentDTO();
		List<AppointmentDtlsEntity> appointmentList = null;
		List<DocSlotDtlsEntity> slotList = null;
		DocMstrDtlsEntity docMstrDtls = null;
		List<AppointmentDTO> todayAppointmentList = new ArrayList<AppointmentDTO>();
		LocalDate appointmentDate = null;
		try {
			if (apptDate != null && apptDate != "")
				appointmentDate = LocalDate.parse(apptDate);
			else
				appointmentDate = LocalDate.now();
		} catch (Exception e) {
			logger.error(ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getMessage());
			throw new ConsultationServiceException(ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getCode(),
					ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getMessage());
		}
		if (userDTO.getRole().equals(ConsultationConstants.DOCTOR_ROLE.getValue())) {
			logger.info("Consultation role is Doctor");
			if (appointmentDate != null) {
				appointmentList = appointmentDtlsRepository
						.findByAdApptDateFkAndDmdUserIdAndSceduledAndResceduled(appointmentDate, userDTO.getUserName());
				logger.info("Size of Docter Appointment details :: " + appointmentList.size());
				slotList = drSlotRepository.findDocSlotDtlsByStatus(userDTO.getUserName(), appointmentDate);
				logger.info("Size of docter slot list :: " + slotList.size());
			}

		}
		logger.info("Fetched Appointment data");
		if (!EmptyCheckUtility.isNullEmpty(appointmentList)) {
			docMstrDtls = appointmentList.get(0).getDocMstrDtlsEntity();
			for (AppointmentDtlsEntity appointment : appointmentList) {
				Date slotDateTime = getDateTime(java.sql.Date.valueOf(appointment.getAdApptDateFk()),
						appointment.getAdApptSlotFk());

				if (slotDateTime.after(getAfterTime(new Date()))) {

					AppointmentDTO aptDtl = buildAppointmentDto(docMstrDtls, appointment);
					 todayAppointmentList.add(aptDtl);
				}
			}
		}
		if (!EmptyCheckUtility.isNullEmpty(slotList)) {
			docMstrDtls = slotList.get(0).getDocMstrDtlsEntity();
			for (DocSlotDtlsEntity slotDtls : slotList) {
				Date slotDateTime = getDateTime(slotDtls.getDsdSlotDate(), slotDtls.getDsdSlot());
				if (slotDateTime.after(getAfterTime(new Date()))) {
					AppointmentDTO aptDtl = buildAppointmentDto(docMstrDtls, slotDtls);
					//todayAppointmentList.add(aptDtl);
				}
			}

		}
		Collections.sort(todayAppointmentList);
		Map<String, List<?>> apptDtls = new LinkedHashMap<String, List<?>>();
		Map<String, Map<String, List<?>>> apptByTime = new HashMap<>();
		Map<String, List<?>> apptList = getApptListByTime(todayAppointmentList, apptDtls);
		logger.info("Size od appointment list by time ::" + apptList.size());
		apptByTime.put(ConsultationConstants.TODAY.getValue(), apptList);
		response.setApptDtls(apptByTime);
		if (null != docMstrDtls) {
			response.setDoctorConsultFee(docMstrDtls.getDmdConsulFee());
			// added convenience Charge in amount
			response.setConvenienceCharge(convenienceCharge);
			response.setDrMobileNumber(docMstrDtls.getDmdMobileNo());

		}
		logger.info("Mapped consultation data to response");
		if (null != docMstrDtls) {
			if (null != docMstrDtls.getDmdUserId()) {
				response.setDrRegID(docMstrDtls.getDmdUserId());
			}
		}
		if (todayAppointmentList.size() == 0) {
			logger.error(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
		}
		logger.info("Returning from getAppointmentListByDrID() of service class");
		return response;
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

	public static Date getAfterTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, 30);
		Date dateTime = cal.getTime();
		return dateTime;
	}

	@Override
	public PtAppointmentDTO getScheduledAndRescheduledAppointmentByPatientId() {
		PtAppointmentDTO response = new PtAppointmentDTO();

		List<AppointmentDtlsEntity> appointmentList = appointmentDtlsRepository
				.findByPrdUserIdAndScheduledAndRescheuled(userDTO.getUserName());
		logger.info("Fetch appointment details by patient ID");

		PatientRegDtlsEntity patientRegDtls = appointmentList.get(0).getPatientRegDtlsEntity();
		List<AppointmentDTO> appointmentDtoList = new ArrayList<AppointmentDTO>();
		for (AppointmentDtlsEntity appointment : appointmentList) {

			Date slotDateTime = getDateTime(java.sql.Date.valueOf(appointment.getAdApptDateFk()),
					appointment.getAdApptSlotFk());
			if (slotDateTime.after(getAfterTime(new Date()))) {
				AppointmentDTO aptDtl = AppointmentDTO.builder().appointmentID(appointment.getAdApptId())
						.appointmentDate(
								appointment.getAdApptDateFk().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
						.appointmentTime(appointment.getAdApptSlotFk())
						.doctorSpeciality(appointment.getDocMstrDtlsEntity().getDmdSpecialiazation())
						.doctorName(appointment.getDocMstrDtlsEntity().getDmdDrName())
						.profilePhoto(appointment.getDocMstrDtlsEntity().getProfilePhoto())
						.doctorUserId(appointment.getDocMstrDtlsEntity().getDmdUserId())
						.patientName(appointment.getAdApptBookedFor()).patientRegId(patientRegDtls.getPrdUserId())
						.slotType(appointment.getAdConsultType())
						.doctorConsulFee(appointment.getDocMstrDtlsEntity().getDmdConsulFee())
						.status(getStatusFromStatusCode(appointment.getAdApptStatus())).build();
				appointmentDtoList.add(aptDtl);
			}
		}
		logger.info("Count of appointmentList::" + appointmentDtoList.size());
		response.setApptDtls(appointmentDtoList);
		response.setPtRegID(patientRegDtls.getPrdUserId());
		logger.info("Mapped appointment data to response");
		if (EmptyCheckUtility.isNullEmpty(appointmentDtoList)) {
			logger.error(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
		}
		logger.info("Returning from getScheduledAndRescheduledAppointmentByPatientId() of service class");
		return response;
	}
}