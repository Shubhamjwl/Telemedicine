package com.nsdl.telemedicine.slot.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.slot.constant.ErrorConstants;
import com.nsdl.telemedicine.slot.constant.SlotConstant;
import com.nsdl.telemedicine.slot.dto.AvailableSlotReqDto;
import com.nsdl.telemedicine.slot.dto.AvailableSlotResDto;
import com.nsdl.telemedicine.slot.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.slot.dto.HolidayReqtDto;
import com.nsdl.telemedicine.slot.dto.MainRequestDTO;
import com.nsdl.telemedicine.slot.dto.MainResponseDTO;
import com.nsdl.telemedicine.slot.dto.SaveSlotRequestDTO;
import com.nsdl.telemedicine.slot.dto.ScribeDTO;
import com.nsdl.telemedicine.slot.dto.SlotDto;
import com.nsdl.telemedicine.slot.dto.SlotReqtDto;
import com.nsdl.telemedicine.slot.dto.SlotResponseDto;
import com.nsdl.telemedicine.slot.dto.UserDTO;
import com.nsdl.telemedicine.slot.dto.ViewSlotRequestDTO;
import com.nsdl.telemedicine.slot.dto.WorkingTime;
import com.nsdl.telemedicine.slot.entity.AudDocSlotDtlsEntity;
import com.nsdl.telemedicine.slot.entity.AudDrSlotMstr;
import com.nsdl.telemedicine.slot.entity.DocSlotDtlsEntity;
import com.nsdl.telemedicine.slot.entity.DrSlotMstr;
import com.nsdl.telemedicine.slot.entity.HolidayEntity;
import com.nsdl.telemedicine.slot.logger.LoggingClientInfo;
import com.nsdl.telemedicine.slot.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.slot.repository.AudDrSlotMstrRepository;
import com.nsdl.telemedicine.slot.repository.AudRepo;
import com.nsdl.telemedicine.slot.repository.DrSlotMstrRepository;
import com.nsdl.telemedicine.slot.repository.HolidayRepository;
import com.nsdl.telemedicine.slot.repository.SlotRepo;
import com.nsdl.telemedicine.slot.service.SlotManagementService;
import com.nsdl.telemedicine.slot.utility.CommonValidationUtil;
import com.nsdl.telemedicine.slot.utility.DateUtils;
import com.nsdl.telemedicine.slot.utility.SlotUtility;

@Service
@Transactional
@LoggingClientInfo
public class SlotManagementServiceImpl extends SlotConstant implements SlotManagementService {
	@Autowired
	SlotRepo masterRepo;

	@Autowired
	AudRepo autidRepo; 
	
	@Autowired
	CommonValidationUtil validater;
	
	@Autowired
	private UserDTO userDetails;
	
	@Autowired
	DrSlotMstrRepository slotMstrRepository;
	
	@Autowired
	AudDrSlotMstrRepository audSlotMstrRepository;

	@Autowired
	HolidayRepository holidayRepository; 
	
	@Autowired
	AppointmentDtlsRepository appointmentDtlsRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(SlotManagementServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public MainResponseDTO<List<SaveSlotRequestDTO>> viewSlot(MainRequestDTO<ViewSlotRequestDTO> masterRequest) {
		MainResponseDTO<List<SaveSlotRequestDTO>> responseDTO=null;
		List<ExceptionJSONInfoDTO> errorList=null;
		List<SaveSlotRequestDTO> slotDtoList=new ArrayList<>();
		String userId="";
		try {
			responseDTO = (MainResponseDTO<List<SaveSlotRequestDTO>>) SlotUtility.getMainResponseDto(masterRequest);
			Date slotDate=DateUtils.getDateFromDateString(masterRequest.getRequest().getSlotDate(), SLOT_DATE_FORMAT);
			if(slotDate==null) {
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_SLOT_DATE.getCode(), ErrorConstants.INVALID_SLOT_DATE.getMessage());
				logger.error(ErrorConstants.INVALID_SLOT_DATE.getMessage());
			}
			if(errorList!=null) {
				responseDTO.setStatus(false);
				responseDTO.setErrors(errorList);
				return responseDTO;
			}
			if(userDetails.getRole().equalsIgnoreCase(SCRIBE_ROLE)) {
				userId=masterRequest.getRequest().getDrRegID().trim().toUpperCase();
				
			}
			else {
				userId=userDetails.getUserName();
			}
			logger.info("Fetching records from DB");
			List<DocSlotDtlsEntity> slotList=masterRepo.getSlotDetails(userId,slotDate);
			logger.info("Records fetched from DB");
			for (DocSlotDtlsEntity slotDtls : slotList) {
				
				SaveSlotRequestDTO dto=new SaveSlotRequestDTO();
				dto.setDrRegID(slotDtls.getDrUserIdFk());
				dto.setSlotDate(slotDtls.getSlotDate().toString());
				dto.setSlotTime(slotDtls.getSlot());
				dto.setIsActive(String.valueOf(slotDtls.getIsactive()));
				dto.setConsultAmount(String.valueOf(slotDtls.getConsulFee()));
				slotDtoList.add(dto);

			}
		}catch(Exception e) {
			logger.error(ErrorConstants.SOMETHING_WENT_WRONG.getMessage(),e);
			throw e;
		}
		responseDTO.setStatus(true);
		responseDTO.setResponse(slotDtoList);
		return responseDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MainResponseDTO<String> modifySlot(MainRequestDTO<List<SaveSlotRequestDTO>> masterRequest) {
		MainResponseDTO<String> responseDTO=null;
		List<ExceptionJSONInfoDTO> errorList=null;
		try {
			responseDTO = (MainResponseDTO<String>) SlotUtility.getMainResponseDto(masterRequest);
			for (SaveSlotRequestDTO request : masterRequest.getRequest()) {

				Date slotDate=DateUtils.getDateFromDateString(request.getSlotDate(), SLOT_DATE_FORMAT);
				String slotTime=request.getSlotTime();
				String consultAmount=request.getConsultAmount();
				String isActive=request.getIsActive();
				String userId="";
				if(isActive==null || isActive.isEmpty() || (!isActive.equalsIgnoreCase(TRUE) && !isActive.equalsIgnoreCase(FALSE))) {
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_IS_ACTIVE.getCode(), ErrorConstants.INVALID_IS_ACTIVE.getMessage() +isActive);
					logger.error(ErrorConstants.INVALID_IS_ACTIVE.getMessage());
				}
				if(slotDate==null) {
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_SLOT_DATE.getCode(), ErrorConstants.INVALID_SLOT_DATE.getMessage()+request.getSlotDate());
					logger.error(ErrorConstants.INVALID_SLOT_DATE.getMessage());
					continue;
				}
				if(!validater.validateSlotTime(slotTime)) {
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_SLOT_TIME.getCode(), ErrorConstants.INVALID_SLOT_TIME.getMessage() +slotTime);
					logger.error(ErrorConstants.INVALID_SLOT_TIME.getMessage());
					continue;
				}
				if(!validater.validateNumber(consultAmount)) {
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_CONSULT_AMOUNT.getCode(), ErrorConstants.INVALID_CONSULT_AMOUNT.getMessage() +consultAmount);
					logger.error(ErrorConstants.INVALID_CONSULT_AMOUNT.getMessage());
					continue;
				}
				if(userDetails.getRole().equalsIgnoreCase(SCRIBE_ROLE)) {
					userId=request.getDrRegID().trim().toUpperCase();
					
				}
				else {
					userId=userDetails.getUserName();
				}
				logger.info("Fetching records from DB");
				 List<DocSlotDtlsEntity> slotList=masterRepo.getSlotDetailsByDocAndDate(userId,slotDate,slotTime);
				 logger.info("Records fetched from DB");
				if(slotList!=null && slotList.size()>0) {
					DocSlotDtlsEntity slotDtlsEntity=slotList.get(0);
					slotDtlsEntity.setConsulFee(Integer.parseInt(consultAmount));
					slotDtlsEntity.setModifiedBy(userId);
					slotDtlsEntity.setModifiedTmstmp(DateUtils.getCurrentLocalDateTime());
					slotDtlsEntity.setSlot(slotTime);
					slotDtlsEntity.setIsactive(Boolean.valueOf(isActive));
					try {
						AudDocSlotDtlsEntity audDocSlotDtlsEntity=new AudDocSlotDtlsEntity(slotDtlsEntity, userId);
						logger.info("Saving audit to DB");
						autidRepo.save(audDocSlotDtlsEntity);
						logger.info("Audit saveto DB");
						logger.info("Updating slot to DB");
						masterRepo.save(slotDtlsEntity);
						logger.info("Updating slot to DB completed");
					} 
					catch (DataIntegrityViolationException e) {
						errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_SLOT_TIME_AVAILABLE.getCode(), ErrorConstants.INVALID_SLOT_TIME_AVAILABLE.getMessage()+slotTime);
						logger.error(ErrorConstants.INVALID_SLOT_TIME_AVAILABLE.getMessage(),e);
						continue;
					}
					catch (Exception e) {
						logger.error(ErrorConstants.SOMETHING_WENT_WRONG.getMessage(),e);
						throw e;

					}

				}
				else
				{
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.NO_DETAILS_FOUND.getCode(), ErrorConstants.NO_DETAILS_FOUND.getMessage()+slotTime);
					logger.info(ErrorConstants.NO_DETAILS_FOUND.getMessage()+slotTime);
					continue;
				}
			}
		}
		catch(Exception e) {
			logger.error(ErrorConstants.SOMETHING_WENT_WRONG.getMessage(),e);
			throw e;
		}
		if(errorList!=null) {
			responseDTO.setStatus(false);
			responseDTO.setErrors(errorList);
			if(masterRequest.getRequest().size()==errorList.size()) {
				responseDTO.setResponse(ErrorConstants.FAIL_TO_UPDATE.getMessage());
				logger.error(ErrorConstants.FAIL_TO_UPDATE.getMessage());
			}
			else {
				responseDTO.setResponse("info: "+errorList.size()+" Records are failure out of "+masterRequest.getRequest().size()+". Please refer error details !");
				logger.error("info: "+errorList.size()+" Records are failure out of "+masterRequest.getRequest().size()+". Please refer error details !");	
			}
		}else {
			responseDTO.setStatus(true);
			responseDTO.setResponse(ErrorConstants.SLOT_UPDATE.getMessage());
			logger.info(ErrorConstants.SLOT_UPDATE.getMessage());
		}
		return responseDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MainResponseDTO<String> getDocRegId(MainRequestDTO<ScribeDTO> masterRequest) {
		MainResponseDTO<String> responseDTO=null;
		List<ExceptionJSONInfoDTO> errorList=null;
		try {
			responseDTO = (MainResponseDTO<String>) SlotUtility.getMainResponseDto(masterRequest);
			 logger.info("Fetching records from DB");
			List<Object[]> docRegIdList=masterRepo.getDocRegId(userDetails.getUserName());
			logger.info("Records fetched from DB");
			
			if(docRegIdList.size()>0)
			{
				responseDTO.setStatus(true);
				responseDTO.setResponse(String.valueOf(docRegIdList.get(0)[0]));
				return responseDTO;
				
			}
			else
			{
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.DR_DETAILS_NOT_FOUND.getCode(), ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
				logger.error(ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
				responseDTO.setStatus(false);
				responseDTO.setErrors(errorList);
				return responseDTO;
			}
			 
		}catch(Exception e) {
			logger.error(ErrorConstants.SOMETHING_WENT_WRONG.getMessage(),e);
			throw e;
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public MainResponseDTO<String> saveSlot(MainRequestDTO<SlotReqtDto> masterRequest) {
		MainResponseDTO<String> responseDTO = (MainResponseDTO<String>) SlotUtility.getMainResponseDto(masterRequest);
		List< SlotResponseDto > slots = new ArrayList<SlotResponseDto>() ;
		LocalDateTime start = null;
		List<ExceptionJSONInfoDTO> errorList=null;
		LocalDateTime stop = null;
		SlotReqtDto req=masterRequest.getRequest();
		DrSlotMstr drSlotMstr=null;
		SimpleDateFormat sdf = new SimpleDateFormat(SLOT_FORMAT);
		DateTimeFormatter format=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int fromMonth=0;
		int toMonth=0;
		try {
			
			if(req.getRepetForMonths()<=0) {
				req.setRepetForMonths(1);
			}
			if(req.getSlotDuration()<=0) {
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_SLOT_DURATION.getCode(), ErrorConstants.INVALID_SLOT_DURATION.getMessage());
				logger.error(ErrorConstants.INVALID_SLOT_DURATION.getMessage());				
			}
			if(req.getSlotWorkingDays().length<=0) {
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_WORKING_DAYS.getCode(), ErrorConstants.INVALID_WORKING_DAYS.getMessage());
				logger.error(ErrorConstants.INVALID_WORKING_DAYS.getMessage());
			}
			if(req.getWorkingTime().size()<=0) {
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_WORKING_TIME.getCode(), ErrorConstants.INVALID_WORKING_TIME.getMessage());
				logger.error(ErrorConstants.INVALID_WORKING_TIME.getMessage());
			}
			if(errorList!=null) {
				responseDTO.setStatus(false);
				responseDTO.setErrors(errorList);
				responseDTO.setResponse(ErrorConstants.FAIL_TO_SAVE_RECORD.getMessage());
				logger.error(ErrorConstants.FAIL_TO_SAVE_RECORD.getMessage());
				return responseDTO;
			}
			String userId="";
			if(userDetails.getRole().equalsIgnoreCase(SCRIBE_ROLE)) {
				List<Object[]> docRegIdList=masterRepo.getDocRegId(userDetails.getUserName());
				if(docRegIdList.size()>0) {
					userId=String.valueOf(docRegIdList.get(0)[0]).trim().toUpperCase();
				}
				else
				{
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.DR_DETAILS_NOT_FOUND.getCode(), ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
					logger.error(ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
					responseDTO.setStatus(false);
					responseDTO.setErrors(errorList);
					return responseDTO;
				}
				
			}
			else {
				userId=userDetails.getUserName();
			}
			start=DateUtils.getCurrentLocalDateTime();
			stop=DateUtils.getCurrentLocalDateTime();
			boolean flag=true;
			String completeTime="";
			Map<String,String>daysTime=new HashMap<String,String>();
			for (int i=1;i<=req.getRepetForMonths();i++) {
				YearMonth yearMonthObject = YearMonth.of(start.getYear(), start.getMonthValue());
				int daysInMonth = yearMonthObject.lengthOfMonth();
				if(i==1)
					fromMonth=start.getMonthValue();
				if(i==req.getRepetForMonths())
					toMonth=start.getMonthValue();
				for(int j=start.getDayOfMonth();j<=daysInMonth;j++)
				{
					SlotResponseDto dto=new SlotResponseDto();;
					start=start.withDayOfMonth(j);
					stop=stop.withDayOfMonth(j);
					String dayOfWeek=start.getDayOfWeek().name();
					for (String days : req.getSlotWorkingDays()) {
						if(dayOfWeek.contains(days.toUpperCase())){
							HolidayEntity holiday=holidayRepository.findByDrUserIdFkAndHolidayDate(userId, start.toLocalDate()) ;
							if(holiday!=null && holiday.getIsactive()!=null && holiday.getIsactive()==true)
								flag=false;
								
							for (WorkingTime workingTime : req.getWorkingTime()) {

								String [] time=workingTime.getStartTime().split(":");
								if(completeTime!="")
									completeTime=completeTime+","+workingTime.getStartTime()+"-"+workingTime.getEndTime();
								else
									completeTime=workingTime.getStartTime()+"-"+workingTime.getEndTime();
								start=start.withHour(Integer.parseInt(time[0])).withMinute(Integer.parseInt(time[1]));
								time=workingTime.getEndTime().split(":");
								stop=stop.withHour(Integer.parseInt(time[0])).withMinute(Integer.parseInt(time[1]));
								int slotDuration=req.getSlotDuration();	
								LocalDateTime ldt = start;
								ZonedDateTime zdt;
								dto.setSlotDate(ldt.format(format));
								dto.setDayOfWeek(days);
								while (ldt.isBefore(stop)) {
									zdt = ldt.atZone(ZoneId.systemDefault());
									Date startTime = Date.from(zdt.toInstant());
									ldt = ldt.plusMinutes(slotDuration) ;
									zdt = ldt.atZone(ZoneId.systemDefault());
									Date endTime = Date.from(zdt.toInstant());
									SlotDto slot=new SlotDto();
									slot.setSlot(sdf.format(startTime) +"-"+sdf.format(endTime));
									dto.getSlots().add(slot);
									DocSlotDtlsEntity slotDtlsEntity=new DocSlotDtlsEntity();
									slotDtlsEntity.setConsulFee(req.getConsultAmount());
									slotDtlsEntity.setCreatedBy(userDetails.getUserName());
									slotDtlsEntity.setCreatedTmstmp(LocalDateTime.now());
									slotDtlsEntity.setDrUserIdFk(userId);
									slotDtlsEntity.setIsactive(flag);
									slotDtlsEntity.setSlot(slot.getSlot());
									slotDtlsEntity.setSlotDate(DateUtils.getDateFromDateString(start.toLocalDate().toString(), SLOT_DATE_FORMAT));
									AudDocSlotDtlsEntity audDocSlotDtlsEntity=new AudDocSlotDtlsEntity(slotDtlsEntity, userDetails.getUserName());
	 									logger.info("Saving audit details");
										autidRepo.save(audDocSlotDtlsEntity);
										logger.info("Audit details save");
										logger.info("Saving slot details");
										masterRepo.save(slotDtlsEntity);
										logger.info("Slot details save");
									
								}
							}
							daysTime.put(days, completeTime+"#"+req.getSlotDuration()+"#");
							slots.add(dto) ;
							completeTime="";
						}
					}
				}
				start=start.with(TemporalAdjusters.firstDayOfNextMonth());;
				stop=start;
			}
			
			if(daysTime.size()>0) {
				drSlotMstr=slotMstrRepository.findByDrUserIdFk(userId) ;
				if(drSlotMstr==null) {
					drSlotMstr=new DrSlotMstr();
					drSlotMstr.setCreatedBy(userDetails.getUserName());
					drSlotMstr.setCreatedTmstmp(LocalDateTime.now());	
				}else
				{
					drSlotMstr.setModifiedBy(userDetails.getUserName());
					drSlotMstr.setModifiedTmstmp(LocalDateTime.now());
				}
				for (Map.Entry<String,String> entry : daysTime.entrySet()) {
					drSlotMstr=getDrSlotMstr(drSlotMstr, entry.getKey(),entry.getValue());
				}
				
				drSlotMstr.setAutoRep(req.isAutoRep());
				drSlotMstr.setDrUserIdFk(userId);
				drSlotMstr.setFromMonth(fromMonth);
				drSlotMstr.setIsactive(true);
				drSlotMstr.setRepForMonth(req.getRepetForMonths());
				drSlotMstr.setSlotDuration(req.getSlotDuration());
				drSlotMstr.setToMonth(toMonth);
				logger.info("Saving slot master details");
				slotMstrRepository.save(drSlotMstr);
				logger.info("Slot master details saved");
				logger.info("Saving slot master details audit");
				audSlotMstrRepository.save(new AudDrSlotMstr(drSlotMstr,userDetails.getUserName()));
				logger.info("Slot master details audit saved");
			}
			
 		}catch(Exception e) {
			logger.error(ErrorConstants.SOMETHING_WENT_WRONG.getMessage(),e);
			throw e;
		}
		responseDTO.setStatus(true);
		responseDTO.setResponse(ErrorConstants.SLOT_SAVE.getMessage());
		return responseDTO;
	}
	
private DrSlotMstr getDrSlotMstr(DrSlotMstr drSlotMstr,String days, String completeTime) {
		
		if(days.equalsIgnoreCase("mon")) {
			drSlotMstr.setMon(true);
			drSlotMstr.setMonTime(drSlotMstr.getMonTime()==null?completeTime:drSlotMstr.getMonTime()+completeTime);
		}else if(days.equalsIgnoreCase("tue")) {
			drSlotMstr.setTue(true);
			drSlotMstr.setTueTime(drSlotMstr.getTueTime()==null?completeTime:drSlotMstr.getTueTime()+completeTime);
		}else if(days.equalsIgnoreCase("wed")) {
			drSlotMstr.setWed(true);
			drSlotMstr.setWedTime(drSlotMstr.getWedTime()==null?completeTime:drSlotMstr.getWedTime()+completeTime);
		}else if(days.equalsIgnoreCase("thu")) {
			drSlotMstr.setThu(true);
			drSlotMstr.setThuTime(drSlotMstr.getThuTime()==null?completeTime:drSlotMstr.getThuTime()+completeTime);
		}else if(days.equalsIgnoreCase("fri")) {
			drSlotMstr.setFri(true);
			drSlotMstr.setFriTime(drSlotMstr.getFriTime()==null?completeTime:drSlotMstr.getFriTime()+completeTime);
		}else if(days.equalsIgnoreCase("sat")) {
			drSlotMstr.setSat(true);
			drSlotMstr.setSatTime(drSlotMstr.getSatTime()==null?completeTime:drSlotMstr.getSatTime()+completeTime);
		}else if(days.equalsIgnoreCase("sun")) {
			drSlotMstr.setSun(true);
			drSlotMstr.setSunTime(drSlotMstr.getSunTime()==null?completeTime:drSlotMstr.getSunTime()+completeTime);
		}
		return drSlotMstr;
	}
	@SuppressWarnings("unchecked")
	@Override
	public MainResponseDTO<List<String>> getSlotCreatedDays(MainRequestDTO<?> masterRequest) {
		MainResponseDTO<List<String>> responseDTO=null;
		List<ExceptionJSONInfoDTO> errorList=null;
		List<String> daysList=new ArrayList<String>();
		try {
			responseDTO = (MainResponseDTO<List<String>>) SlotUtility.getMainResponseDto(masterRequest);
			String userId="";
			if(userDetails.getRole().equalsIgnoreCase(SCRIBE_ROLE)) {
				List<Object[]> docRegIdList=masterRepo.getDocRegId(userDetails.getUserName());
				if(docRegIdList.size()>0) {
					userId=String.valueOf(docRegIdList.get(0)[0]).trim().toUpperCase();
				}
				else
				{
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.DR_DETAILS_NOT_FOUND.getCode(), ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
					logger.error(ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
					responseDTO.setStatus(false);
					responseDTO.setErrors(errorList);
					return responseDTO;
				}
				
			}
			else {
				userId=userDetails.getUserName();
			}
			logger.info("Fetching records from DB");
			DrSlotMstr drSlotMstr=slotMstrRepository.getSlotCreatedDays(userId,LocalDate.now().getMonthValue()) ;
			logger.info("Records fetched from DB");

			if(drSlotMstr!=null)
			{
				if(drSlotMstr.getMon())	{
					daysList.add("Mon");
				}if(drSlotMstr.getTue()){
					daysList.add("Tue");
				}if(drSlotMstr.getWed()){
					daysList.add("Wed");
				}if(drSlotMstr.getThu()){
					daysList.add("Thu");
				}if(drSlotMstr.getFri()){
					daysList.add("Fri");
				}if(drSlotMstr.getSat()){
					daysList.add("Sat");
				}if(drSlotMstr.getSun()){
					daysList.add("Sun");
				}
				responseDTO.setStatus(true);
				responseDTO.setResponse(daysList);
				return responseDTO;

			}
			else
			{
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.SLOT_DETAILS_NOT_FOUND.getCode(), ErrorConstants.SLOT_DETAILS_NOT_FOUND.getMessage());
				logger.error(ErrorConstants.SLOT_DETAILS_NOT_FOUND.getMessage());
				responseDTO.setStatus(false);
				responseDTO.setErrors(errorList);
				return responseDTO;
			}

		}catch(Exception e) {
			logger.error(ErrorConstants.SOMETHING_WENT_WRONG.getMessage(),e);
			throw e;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public MainResponseDTO<List<AvailableSlotResDto>> getAvailableSlotByMonth(
			MainRequestDTO<AvailableSlotReqDto> masterRequest) {
		MainResponseDTO<List<AvailableSlotResDto>> responseDTO=null;
		List<ExceptionJSONInfoDTO> errorList=null;
		List<AvailableSlotResDto> availableSlotList=new ArrayList<AvailableSlotResDto>();
		List<SlotDto> slotDtoList=new ArrayList<SlotDto>();
		try {
			responseDTO = (MainResponseDTO<List<AvailableSlotResDto>>) SlotUtility.getMainResponseDto(masterRequest);
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
				  sdf.parse(masterRequest.getRequest().getMonth());
			} catch (Exception e) {
				 
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.INVALID_YEAR_MONTH_FORMAT.getCode(), ErrorConstants.INVALID_YEAR_MONTH_FORMAT.getMessage());
				logger.error(ErrorConstants.INVALID_YEAR_MONTH_FORMAT.getMessage());
				responseDTO.setStatus(false);
				responseDTO.setErrors(errorList);
				return responseDTO;
			}
			String[] array=masterRequest.getRequest().getMonth().split("-");
			int year=Integer.parseInt(array[0]);
			int month=Integer.parseInt(array[1]);
			String userId="";
			if(userDetails.getRole().equalsIgnoreCase(SCRIBE_ROLE)) {
				List<Object[]> docRegIdList=masterRepo.getDocRegId(userDetails.getUserName());
				if(docRegIdList.size()>0) {
					userId=String.valueOf(docRegIdList.get(0)[0]).trim().toUpperCase();
				}
				else
				{
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.DR_DETAILS_NOT_FOUND.getCode(), ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
					logger.error(ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
					responseDTO.setStatus(false);
					responseDTO.setErrors(errorList);
					return responseDTO;
				}
				
			}
			else {
				userId=userDetails.getUserName();
			}
			logger.info("Fetching records from DB");
			List<DocSlotDtlsEntity> drSlotDtlsList=masterRepo.getAvailableSlotByMonth(userId,year,month) ;
			logger.info("Records fetched from DB");
			AvailableSlotResDto availableSlotDto=new AvailableSlotResDto();
			DateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
			Date today = new Date();
			Date date = null;
			int i=0;
			int noOfBookedSlot=0;
			boolean slotFound=false;
			for (DocSlotDtlsEntity docSlotDtlsEntity : drSlotDtlsList) {
				SlotDto dto=new SlotDto();
				i++;
				if(formatter.parse(formatter.format(docSlotDtlsEntity.getSlotDate())).equals(formatter.parse(formatter.format(today))))
				{
					Date slotDateTime = getDateTime(docSlotDtlsEntity.getSlotDate(), docSlotDtlsEntity.getSlot());
					if (slotDateTime.after(new Date())) {
						slotFound=true;
						dto.setSlot(docSlotDtlsEntity.getSlot());
						slotDtoList.add(dto);
					}
					
				}
				else {
					if(date==null) {
						date = docSlotDtlsEntity.getSlotDate();
						if(slotFound) {
							availableSlotDto.setSlotDate(today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
							availableSlotDto.setNumberOfSlot(slotDtoList.size());
							noOfBookedSlot=appointmentDtlsRepository.getBookedSlot(userId,availableSlotDto.getSlotDate()).size();
							availableSlotDto.setNoOfBookedSlot(noOfBookedSlot);
							availableSlotDto.setSlots(slotDtoList);
							availableSlotList.add(availableSlotDto);
							slotDtoList=new ArrayList<SlotDto>();
							availableSlotDto=new AvailableSlotResDto();
							dto=new SlotDto();
						}
					}
					if(formatter.parse(formatter.format(docSlotDtlsEntity.getSlotDate())).equals(formatter.parse(formatter.format(date)))) {
						
						dto.setSlot(docSlotDtlsEntity.getSlot());
						slotDtoList.add(dto);
					}
					else {
						availableSlotDto.setSlotDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
						date = docSlotDtlsEntity.getSlotDate();
						availableSlotDto.setNumberOfSlot(slotDtoList.size());
						noOfBookedSlot=appointmentDtlsRepository.getBookedSlot(userId,availableSlotDto.getSlotDate()).size();
						availableSlotDto.setNoOfBookedSlot(noOfBookedSlot);
						availableSlotDto.setSlots(slotDtoList);
						availableSlotList.add(availableSlotDto);
						slotDtoList=new ArrayList<SlotDto>();
						availableSlotDto=new AvailableSlotResDto();
						dto=new SlotDto();
						dto.setSlot(docSlotDtlsEntity.getSlot());
						slotDtoList.add(dto);
					}
					
				}
				
			}
			if(i>0 && drSlotDtlsList.size()==i) {
				availableSlotDto.setSlotDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				availableSlotDto.setNumberOfSlot(slotDtoList.size());
				noOfBookedSlot=appointmentDtlsRepository.getBookedSlot(userId,availableSlotDto.getSlotDate()).size();
				availableSlotDto.setNoOfBookedSlot(noOfBookedSlot);
				availableSlotDto.setSlots(slotDtoList);
				availableSlotList.add(availableSlotDto);
			}
			
			 List<HolidayEntity> holidayList=holidayRepository.getHolidayList(userId, month, year);
			for (HolidayEntity holidayEntity : holidayList) {
				availableSlotDto=new AvailableSlotResDto();
				availableSlotDto.setHoliday(true);
				availableSlotDto.setNumberOfSlot(0);
				availableSlotDto.setSlotDate(holidayEntity.getHolidayDate());
				availableSlotDto.setSlots(null);
				availableSlotList.add(availableSlotDto);
			}
			if(holidayList.size()>0)
				Collections.sort(availableSlotList);
			if(drSlotDtlsList.size()>0 || availableSlotList.size()>0)
			{
				responseDTO.setStatus(true);
				responseDTO.setResponse(availableSlotList);
				return responseDTO;

			}
			else
			{
				errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.SLOT_DETAILS_NOT_FOUND.getCode(), ErrorConstants.SLOT_DETAILS_NOT_FOUND.getMessage());
				logger.error(ErrorConstants.SLOT_DETAILS_NOT_FOUND.getMessage());
				responseDTO.setStatus(false);
				responseDTO.setErrors(errorList);
				return responseDTO;
			}

		}catch(Exception e) {
			logger.error(ErrorConstants.SOMETHING_WENT_WRONG.getMessage(),e);
		}
		return responseDTO;

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

	@SuppressWarnings("unchecked")
	@Override
	public MainResponseDTO<String> holidayManagement(MainRequestDTO<HolidayReqtDto> masterRequest) {

		MainResponseDTO<String> responseDTO=null;
		List<ExceptionJSONInfoDTO> errorList=null;
		List<DocSlotDtlsEntity> slotList=null;
		String holidayMark="System is able to mark holidy on ";
		String holidayNotMark="Not able to mark holidy on ";
		boolean notMark=false;
		boolean mark=false;
		try {
			responseDTO = (MainResponseDTO<String>) SlotUtility.getMainResponseDto(masterRequest);
			String userId="";
			if(userDetails.getRole().equalsIgnoreCase(SCRIBE_ROLE)) {
				List<Object[]> docRegIdList=masterRepo.getDocRegId(userDetails.getUserName());
				if(docRegIdList.size()>0) {
					userId=String.valueOf(docRegIdList.get(0)[0]).trim().toUpperCase();
				}
				else
				{
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.DR_DETAILS_NOT_FOUND.getCode(), ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
					logger.error(ErrorConstants.DR_DETAILS_NOT_FOUND.getMessage());
					responseDTO.setStatus(false);
					responseDTO.setErrors(errorList);
					return responseDTO;
				}

			}
			else {
				userId=userDetails.getUserName();
			}

			for (LocalDate date : masterRequest.getRequest().getHolidayDate()) {

				if(date.isBefore(LocalDate.now()))
				{
					errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.PAST_SLOT_DATE.getCode(), ErrorConstants.PAST_SLOT_DATE.getMessage()+date.toString());
					logger.error(ErrorConstants.PAST_SLOT_DATE.getMessage());
					continue;
				}
				if(Boolean.valueOf(masterRequest.getRequest().getIsHoliday()))
				{
					slotList=masterRepo.findDocSlotDtlsByDate(userId, DateUtils.getDateFromDateString(date.toString(),SLOT_DATE_FORMAT));
					if(slotList.size()>0) {
						errorList=SlotUtility.getExceptionList(errorList, ErrorConstants.APPOINTMENT_AVAILABLE.getCode(), ErrorConstants.APPOINTMENT_AVAILABLE.getMessage()+date.toString());
						holidayNotMark=holidayNotMark+date.toString()+".";
						notMark=true;
						logger.error(ErrorConstants.APPOINTMENT_AVAILABLE.getMessage());
						continue;
					}
					else {
						slotList=masterRepo.getSlotDetails(userId, DateUtils.getDateFromDateString(date.toString(),SLOT_DATE_FORMAT));
						/*
						 * if(slotList.size()==0) { errorList=SlotUtility.getExceptionList(errorList,
						 * ErrorConstants.NO_SLOT_DETAILS_FOUND.getCode(),
						 * ErrorConstants.NO_SLOT_DETAILS_FOUND.getMessage()+date.toString());
						 * logger.error(ErrorConstants.NO_SLOT_DETAILS_FOUND.getMessage()); continue; }
						 * else {
						 */
							HolidayEntity holidy=holidayRepository.findByDrUserIdFkAndHolidayDate(userId, date);
							if(holidy==null) {
								holidy=new HolidayEntity();
								holidy.setCreatedBy(userDetails.getUserName());
								holidy.setCreatedTmstmp(LocalDateTime.now());
									
							}
							else if(holidy!=null && holidy.getIsactive()==true) {

								errorList=SlotUtility.getExceptionList(errorList,ErrorConstants.HOLIDAY_DETAILS_FOUND.getCode(),ErrorConstants.HOLIDAY_DETAILS_FOUND.getMessage()+date.toString());
								logger.error(ErrorConstants.HOLIDAY_DETAILS_FOUND.getMessage());
								continue;
							}

							else {
								holidy.setModifiedBy(userDetails.getUserName());
								holidy.setModifiedTmstmp(LocalDateTime.now());
								
							}
							holidy.setDrUserIdFk(userId);
							holidy.setHolidayDate(date);
							holidy.setIsactive(true);
							holidy.setHolidayReason(masterRequest.getRequest().getHolidayReason());
							holidayRepository.save(holidy);
							holidayMark=holidayMark+date.toString()+".";
							mark=true;
							for (DocSlotDtlsEntity docSlotDtlsEntity : slotList) {
								docSlotDtlsEntity.setModifiedBy(userDetails.getUserName());
								docSlotDtlsEntity.setModifiedTmstmp(LocalDateTime.now());
								docSlotDtlsEntity.setIsactive(false);
								masterRepo.save(docSlotDtlsEntity);
								AudDocSlotDtlsEntity audDocSlotDtlsEntity=new AudDocSlotDtlsEntity(docSlotDtlsEntity, userDetails.getUserName());
								logger.info("Saving audit to DB");
								autidRepo.save(audDocSlotDtlsEntity);
								logger.info("Audit saveto DB");
							}

						/*}*/
					}


				}else
				{
					slotList=masterRepo.getInActiveSlotDetails(userId, DateUtils.getDateFromDateString(date.toString(),SLOT_DATE_FORMAT));
					/*
					 * if(slotList.size()==0) { errorList=SlotUtility.getExceptionList(errorList,
					 * ErrorConstants.NO_SLOT_DETAILS_FOUND.getCode(),
					 * ErrorConstants.NO_SLOT_DETAILS_FOUND.getMessage()+date.toString());
					 * logger.error(ErrorConstants.NO_SLOT_DETAILS_FOUND.getMessage()); continue; }
					 * else {
					 */
						
						HolidayEntity holidy=holidayRepository.findByDrUserIdFkAndHolidayDate(userId, date);
						if(holidy==null ||(holidy!=null && holidy.getIsactive()==false)) {

							errorList=SlotUtility.getExceptionList(errorList,ErrorConstants.NO_HOLIDAY_DETAILS_FOUND.getCode(),ErrorConstants.NO_HOLIDAY_DETAILS_FOUND.getMessage()+date.toString());
							logger.error(ErrorConstants.NO_HOLIDAY_DETAILS_FOUND.getMessage());
							continue;
						}
						holidy.setModifiedBy(userDetails.getUserName());
						holidy.setModifiedTmstmp(LocalDateTime.now());
						holidy.setIsactive(false);
						holidy.setHolidayReason(masterRequest.getRequest().getHolidayReason());
						holidayRepository.save(holidy);
						for (DocSlotDtlsEntity docSlotDtlsEntity : slotList) {
							docSlotDtlsEntity.setModifiedBy(userDetails.getUserName());
							docSlotDtlsEntity.setModifiedTmstmp(LocalDateTime.now());
							docSlotDtlsEntity.setIsactive(true);
							masterRepo.save(docSlotDtlsEntity);
							AudDocSlotDtlsEntity audDocSlotDtlsEntity=new AudDocSlotDtlsEntity(docSlotDtlsEntity, userDetails.getUserName());
							logger.info("Saving audit to DB");
							autidRepo.save(audDocSlotDtlsEntity);
							logger.info("Audit saveto DB");
						}
					/*}*/

				}
			}
		}
		catch(Exception e) {
			logger.error(ErrorConstants.SOMETHING_WENT_WRONG.getMessage(),e);
			throw e;
		}
		if(errorList!=null) {
			responseDTO.setStatus(false);
			
			if(mark && notMark)
				holidayMark=holidayMark+holidayNotMark+" Because appointments are present on that that. ";
			else if(notMark && !mark)
				holidayMark=holidayNotMark+" Because appointments are present on that that. ";
			responseDTO.setErrors(SlotUtility.getExceptionList(null,ErrorConstants.NO_HOLIDAY_DETAILS_FOUND.getCode(),holidayMark));
			if(masterRequest.getRequest().getHolidayDate().length==errorList.size()) {
				responseDTO.setResponse(ErrorConstants.FAIL_TO_UPDATE.getMessage());
				logger.error(ErrorConstants.FAIL_TO_UPDATE.getMessage());
			}
			else {
				responseDTO.setResponse("info: "+errorList.size()+" Records are failure out of "+masterRequest.getRequest().getHolidayDate().length+". Please refer error details !");
				logger.error("info: "+errorList.size()+" Records are failure out of "+masterRequest.getRequest().getHolidayDate().length+". Please refer error details !");	
			}
		}else {
			responseDTO.setStatus(true);
			responseDTO.setResponse(ErrorConstants.HOLIDAY_UPDATE.getMessage());
			logger.info(ErrorConstants.HOLIDAY_UPDATE.getMessage());
		}
		return responseDTO;


	}
}
