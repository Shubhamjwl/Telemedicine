package com.nsdl.telemedicine.consultancy.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsdl.telemedicine.consultancy.constant.ConsultationConstants;
import com.nsdl.telemedicine.consultancy.constant.ErrorConstants;
import com.nsdl.telemedicine.consultancy.dto.AppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.DrAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.PtAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.UserDTO;
import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.DocMstrDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.exception.ConsultationServiceException;
import com.nsdl.telemedicine.consultancy.logger.LoggingClientInfo;
import com.nsdl.telemedicine.consultancy.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.consultancy.service.AppointmentService;
import com.nsdl.telemedicine.consultancy.utility.EmptyCheckUtility;


@Service
@Transactional
@LoggingClientInfo
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentDtlsRepository appointmentDtlsRepository;

	@Autowired
	private UserDTO userDTO;

	private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

	@Override
	public PtAppointmentDTO getAppointmentListByPatientID() {

		PtAppointmentDTO response = new PtAppointmentDTO();

		List<AppointmentDtlsEntity> appointmentList = appointmentDtlsRepository
				.findByPrdUserIdOrderByAdApptDateFkAdApptSlotFk(userDTO.getUserName());
		logger.info("Fetched appointment details");

		if (!EmptyCheckUtility.isNullEmpty(appointmentList)) {
			PatientRegDtlsEntity patientRegDtls = appointmentList.get(0).getPatientRegDtlsEntity();
			List<AppointmentDTO> appointmentDtoList = new ArrayList<AppointmentDTO>();
			for (AppointmentDtlsEntity appointment : appointmentList) {
				AppointmentDTO aptDtl = AppointmentDTO.builder().appointmentID(appointment.getAdApptId())
						.appointmentDate(appointment.getAdApptDateFk().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
						.appointmentTime(appointment.getAdApptSlotFk())
						.doctorSpeciality(appointment.getDocMstrDtlsEntity().getDmdSpecialiazation())
						.doctorName(appointment.getDocMstrDtlsEntity().getDmdDrName())
						.profilePhoto(appointment.getDocMstrDtlsEntity().getProfilePhoto())
						.doctorUserId(appointment.getDocMstrDtlsEntity().getDmdUserId())
						.patientName(appointment.getAdApptBookedFor()).patientRegId(patientRegDtls.getPrdUserId())
						.status(getStatusFromStatusCode(appointment.getAdApptStatus())).build();
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
		return response;
	}

	@Override
	public DrAppointmentDTO getAppointmentListByDrID(String apptDate) {

		DrAppointmentDTO response = new DrAppointmentDTO();

		List<AppointmentDtlsEntity> appointmentList = null;
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		LocalDate appointmentDate=null;
		try {
			if(apptDate!=null && apptDate!="")
				appointmentDate=LocalDate.parse(apptDate);
		}
		catch(Exception e) {
			logger.error(ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getMessage());
			throw new ConsultationServiceException(ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getCode(),
					ErrorConstants.WRONG_APPOINTMENT_DATE_FORMAT.getMessage());
		}
		if (userDTO.getRole().equals(ConsultationConstants.SCRIBE_ROLE.getValue())) {
			
			if(appointmentDate!=null) {
				appointmentList = appointmentDtlsRepository
						.findByAdApptDateFkAndAdScrbUserIdAndOrderByAdApptDateFkAdApptSlotFk(appointmentDate,
								userDTO.getUserName());
			}
			else {
			appointmentList = appointmentDtlsRepository
					.findByAdApptDateFkAndAdScrbUserIdAndOrderByAdApptDateFkAdApptSlotFk(today, tomorrow,
							userDTO.getUserName());
			}
		} else if (userDTO.getRole().equals(ConsultationConstants.DOCTOR_ROLE.getValue())) {
			
			if(appointmentDate!=null) {
				appointmentList = appointmentDtlsRepository
						.findByAdApptDateFkAndDmdUserIdAndOrderByAdApptDateFkAdApptSlotFk(appointmentDate,
								userDTO.getUserName());
			}
			else {
			appointmentList = appointmentDtlsRepository
					.findByAdApptDateFkAndDmdUserIdAndOrderByAdApptDateFkAdApptSlotFk(today, tomorrow,
							userDTO.getUserName());
			}
		}
		logger.info("Fetched Appointment data");
		if (!EmptyCheckUtility.isNullEmpty(appointmentList)) {
			DocMstrDtlsEntity docMstrDtls = appointmentList.get(0).getDocMstrDtlsEntity();
			List<AppointmentDTO> todayAppointmentList = new ArrayList<AppointmentDTO>();
			List<AppointmentDTO> tomorrowAppointmentList = new ArrayList<AppointmentDTO>();
			for (AppointmentDtlsEntity appointment : appointmentList) {
				if (appointment.getAdApptDateFk().equals(today)) {
					AppointmentDTO aptDtl = buildAppointmentDto(docMstrDtls, appointment);
					todayAppointmentList.add(aptDtl);
				} else if (appointment.getAdApptDateFk().equals(tomorrow)) {
					AppointmentDTO aptDtl = buildAppointmentDto(docMstrDtls, appointment);
					tomorrowAppointmentList.add(aptDtl);
				}else {
					AppointmentDTO aptDtl = buildAppointmentDto(docMstrDtls, appointment);
					todayAppointmentList.add(aptDtl);
				}
			}
			Map<String, List<?>> apptDtls = new LinkedHashMap<String, List<?>>();
			Map<String, Map<String, List<?>>>apptByTime=new HashMap<>();
			Map<String, List<?>> apptList=getApptListByTime(todayAppointmentList,apptDtls);
			apptByTime.put(ConsultationConstants.TODAY.getValue(), apptList);
			apptDtls = new LinkedHashMap<String, List<?>>();
			apptList=getApptListByTime(tomorrowAppointmentList,apptDtls);
			apptByTime.put(ConsultationConstants.TOMORROW.getValue(), apptList);
			response.setApptDtls(apptByTime);
			logger.info("Mapped consultation data to response");
			response.setDrRegID(docMstrDtls.getDmdUserId());
		} else {
			logger.error(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
					ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
		}
		return response;
	}

	private Map<String, List<?>> getApptListByTime(List<AppointmentDTO> todayAppointmentList,
			Map<String, List<?>> apptDtls) {
		
		int startTime=0;
		int endTime=0;
		List<AppointmentDTO> apptList=new ArrayList<AppointmentDTO>();
		Set<String> timeList=new LinkedHashSet<String>();
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
			
			if(startDateTime.getHour()>=startTime && (endDateTime.getHour()<endTime||(endDateTime.getHour()==endTime && endDateTime.getMinute()<=0))) {
				appointmentDTO.setSlot(convertTime24To12Format((startTime<10?"0"+startTime:startTime)+":00")+"-"+convertTime24To12Format((endTime<10?"0"+endTime:endTime)+":00"));
				timeList.add(appointmentDTO.getSlot());
				apptList.add(appointmentDTO);
			}
			else
			{
				apptDtls.put(convertTime24To12Format((startTime<10?"0"+startTime:startTime)+":00")+"-"+convertTime24To12Format((endTime<10?"0"+endTime:endTime)+":00"), apptList);
				apptList=new ArrayList<AppointmentDTO>();
				startTime=Integer.parseInt(appointmentDTO.getAppointmentTime().split("-")[0].split(":")[0]);
				endTime=startTime+1;
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
		return apptDtls;
	}

	private AppointmentDTO buildAppointmentDto(DocMstrDtlsEntity docMstrDtls, AppointmentDtlsEntity appointment) {
		return AppointmentDTO.builder().appointmentID(appointment.getAdApptId())
				.appointmentDate(appointment.getAdApptDateFk().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
				.appointmentTime(appointment.getAdApptSlotFk()).doctorName(docMstrDtls.getDmdDrName())
				.doctorUserId(docMstrDtls.getDmdUserId()).doctorSpeciality(docMstrDtls.getDmdSpecialiazation())
				.patientRegId(appointment.getPatientRegDtlsEntity().getPrdUserId())
				.patientName(appointment.getPatientRegDtlsEntity().getPrdName())
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

}
