package com.nsdl.telemedicine.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.nsdl.telemedicine.slot.dto.AvailableSlotReqDto;
import com.nsdl.telemedicine.slot.dto.AvailableSlotResDto;
import com.nsdl.telemedicine.slot.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.slot.dto.HolidayReqtDto;
import com.nsdl.telemedicine.slot.dto.MainRequestDTO;
import com.nsdl.telemedicine.slot.dto.MainResponseDTO;
import com.nsdl.telemedicine.slot.dto.SaveSlotRequestDTO;
import com.nsdl.telemedicine.slot.dto.ScribeDTO;
import com.nsdl.telemedicine.slot.dto.SlotReqtDto;
import com.nsdl.telemedicine.slot.dto.UserDTO;
import com.nsdl.telemedicine.slot.dto.ViewSlotRequestDTO;
import com.nsdl.telemedicine.slot.dto.WorkingTime;
import com.nsdl.telemedicine.slot.entity.AudDocSlotDtlsEntity;
import com.nsdl.telemedicine.slot.entity.AudDrSlotMstr;
import com.nsdl.telemedicine.slot.entity.DocSlotDtlsEntity;
import com.nsdl.telemedicine.slot.entity.DrSlotMstr;
import com.nsdl.telemedicine.slot.entity.HolidayEntity;
import com.nsdl.telemedicine.slot.repository.AudDrSlotMstrRepository;
import com.nsdl.telemedicine.slot.repository.AudRepo;
import com.nsdl.telemedicine.slot.repository.DrSlotMstrRepository;
import com.nsdl.telemedicine.slot.repository.HolidayRepository;
import com.nsdl.telemedicine.slot.repository.SlotRepo;
import com.nsdl.telemedicine.slot.service.impl.SlotManagementServiceImpl;
import com.nsdl.telemedicine.slot.utility.CommonValidationUtil;
import com.nsdl.telemedicine.slot.utility.DateUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateUtils.class)
public class SlotManagementServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	private SlotManagementServiceImpl slotManagementServiceImpl;

	@Mock
	CommonValidationUtil validater;

	@Mock
	SlotRepo masterRepo;

	@Mock
	AudRepo autidRepo;
	
	@Mock
	private UserDTO userDetails;
	
	@Mock
	HolidayRepository holidayRepository;
	
	@Mock
	DrSlotMstrRepository slotMstrRepository;
	
	@Mock
	AudDrSlotMstrRepository audSlotMstrRepository;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Before
	public void setUp() throws ParseException {

	}

	@Test
	public void saveSlotTest() throws Exception {

		MainRequestDTO<SlotReqtDto> request = new MainRequestDTO<>();
		SlotReqtDto saveSlotRequestDTO = new SlotReqtDto();
		List<WorkingTime> workingTimeList =new ArrayList<WorkingTime>();
		WorkingTime workingTime=new WorkingTime();
		workingTime.setEndTime("08:00");
		workingTime.setStartTime("07:00");
		workingTimeList.add(workingTime);
		String [] slotWorkingDays={"Sat"};
		saveSlotRequestDTO.setAutoRep(true);
		saveSlotRequestDTO.setConsultAmount(1000);
		saveSlotRequestDTO.setRepetForMonths(1);
		saveSlotRequestDTO.setSlotDuration(60);
		saveSlotRequestDTO.setSlotWorkingDays(slotWorkingDays);
		saveSlotRequestDTO.setWorkingTime(workingTimeList);
		request.setRequest(saveSlotRequestDTO);

		MainResponseDTO<String> response = new MainResponseDTO<>();
		response.setResponse("slot has been added successfully ");
		AudDocSlotDtlsEntity audDocSlotDtlsEntity = new AudDocSlotDtlsEntity();
		DocSlotDtlsEntity slotDtlsEntity = new DocSlotDtlsEntity();
		List<String> docRegIdList=new ArrayList<String>();
		DrSlotMstr drSlotMstr=new DrSlotMstr();
		AudDrSlotMstr audDrSlotMstr=new AudDrSlotMstr();
		//String[] name={"ABC"};
		docRegIdList.add("ABC");
		HolidayEntity holiday =new HolidayEntity();
		Mockito.when(userDetails.getRole()).thenReturn("SCRIBE");
		Mockito.when(userDetails.getUserName()).thenReturn("ABC");
		Mockito.when(masterRepo.getDocRegId(Mockito.any())).thenReturn(docRegIdList);
		Mockito.when(holidayRepository.findByDrUserIdFkAndHolidayDate(Mockito.any(), Mockito.any())).thenReturn(holiday);
		Mockito.when(autidRepo.save(Mockito.any())).thenReturn(audDocSlotDtlsEntity);
		Mockito.when(masterRepo.save(Mockito.any())).thenReturn(slotDtlsEntity);
		Mockito.when(slotMstrRepository.findByDrUserIdFk(Mockito.any())).thenReturn(drSlotMstr);
		Mockito.when(slotMstrRepository.save(Mockito.any())).thenReturn(drSlotMstr);
		Mockito.when(audSlotMstrRepository.save(Mockito.any())).thenReturn(audDrSlotMstr);
		assertThat(response.getResponse()).isEqualTo(slotManagementServiceImpl.saveSlot(request).getResponse());
	}

	@Test
	public void saveSlotNegativeTest() throws Exception {


		MainRequestDTO<SlotReqtDto> request = new MainRequestDTO<>();
		SlotReqtDto saveSlotRequestDTO = new SlotReqtDto();
		List<WorkingTime> workingTimeList =new ArrayList<WorkingTime>();
		WorkingTime workingTime=new WorkingTime();
		workingTime.setEndTime("08:00");
		workingTime.setStartTime("07:00");
		workingTimeList.add(workingTime);
		String [] slotWorkingDays={"Sat"};
		saveSlotRequestDTO.setAutoRep(true);
		saveSlotRequestDTO.setConsultAmount(1000);
		saveSlotRequestDTO.setRepetForMonths(1);
		saveSlotRequestDTO.setSlotDuration(0);
		saveSlotRequestDTO.setSlotWorkingDays(slotWorkingDays);
		saveSlotRequestDTO.setWorkingTime(workingTimeList);
		request.setRequest(saveSlotRequestDTO);

		MainResponseDTO<String> response = new MainResponseDTO<>();
		List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
		ExceptionJSONInfoDTO exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode("ERR-SLT-004");
		exceptionJSONInfoDTO.setMessage("Invalid slot duration.");
		errorList.add(exceptionJSONInfoDTO);
		response.setErrors(errorList);
		assertThat(response.getErrors())
				.containsExactlyInAnyOrderElementsOf(slotManagementServiceImpl.saveSlot(request).getErrors());
	}

	@Test
	public void modifySlotTest() throws Exception {

		MainRequestDTO<List<SaveSlotRequestDTO>> request = new MainRequestDTO<>();

		List<SaveSlotRequestDTO> saveSlotRequestDTOs = new ArrayList<>();
		SaveSlotRequestDTO saveSlotRequestDTO = new SaveSlotRequestDTO();
		saveSlotRequestDTO.setDrRegID("AMOL");
		saveSlotRequestDTO.setConsultAmount("1000");
		saveSlotRequestDTO.setIsActive("true");
		saveSlotRequestDTO.setSlotDate("2020-11-03");
		saveSlotRequestDTO.setSlotTime("12:30-13:30");
		saveSlotRequestDTOs.add(saveSlotRequestDTO);
		request.setRequest(saveSlotRequestDTOs);

		MainResponseDTO<String> response = new MainResponseDTO<>();
		response.setResponse("Slot updated successfully.");

		List<DocSlotDtlsEntity> slotList = new ArrayList<>();
		AudDocSlotDtlsEntity audDocSlotDtlsEntity = new AudDocSlotDtlsEntity();
		DocSlotDtlsEntity slotDtlsEntity = new DocSlotDtlsEntity();
		slotList.add(slotDtlsEntity);

		Mockito.when(validater.validateSlotTime(Mockito.anyString())).thenReturn(true);
		Mockito.when(validater.validateNumber(Mockito.anyString())).thenReturn(true);
		Mockito.when(masterRepo.getSlotDetailsByDocAndDate(Mockito.anyString(), Mockito.any(), Mockito.anyString()))
				.thenReturn(slotList);

		Mockito.when(autidRepo.save(Mockito.any())).thenReturn(audDocSlotDtlsEntity);
		Mockito.when(masterRepo.save(Mockito.any())).thenReturn(slotDtlsEntity);
		Mockito.when(userDetails.getRole()).thenReturn("SCRIBE");

		assertThat(response.getResponse()).isEqualTo(slotManagementServiceImpl.modifySlot(request).getResponse());
	}

	@Test
	public void modifySlotNegativeTest() throws Exception {

		MainRequestDTO<List<SaveSlotRequestDTO>> request = new MainRequestDTO<>();

		List<SaveSlotRequestDTO> saveSlotRequestDTOs = new ArrayList<>();
		SaveSlotRequestDTO saveSlotRequestDTO = new SaveSlotRequestDTO();
		saveSlotRequestDTO.setDrRegID("AMOL");
		saveSlotRequestDTO.setConsultAmount("1000");
		saveSlotRequestDTO.setIsActive(null);
		saveSlotRequestDTO.setSlotDate("2020-11-03");
		saveSlotRequestDTO.setSlotTime("12:30-13:30");
		saveSlotRequestDTOs.add(saveSlotRequestDTO);
		request.setRequest(saveSlotRequestDTOs);

		MainResponseDTO<String> response = new MainResponseDTO<>();
		List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
		ExceptionJSONInfoDTO exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode("ERR-SLT-011");
		exceptionJSONInfoDTO.setMessage("Invalid value for is active.null");
		ExceptionJSONInfoDTO exceptionJSONInfoDTO1 = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO1.setErrorCode("ERR-SLT-007");
		exceptionJSONInfoDTO1.setMessage("Invalid consult amount.1000");
		errorList.add(exceptionJSONInfoDTO);
		errorList.add(exceptionJSONInfoDTO1);
		response.setErrors(errorList);

		Mockito.when(validater.validateSlotTime(Mockito.anyString())).thenReturn(true);
		Mockito.when(validater.validateNumber(Mockito.anyString())).thenReturn(false);

		assertThat(response.getErrors())
				.containsExactlyInAnyOrderElementsOf(slotManagementServiceImpl.modifySlot(request).getErrors());
	}

	@Test
	public void viewSlotDetailsTest() throws Exception {

		MainRequestDTO<ViewSlotRequestDTO> request = new MainRequestDTO<>();
		ViewSlotRequestDTO viewSlotRequestDTO = new ViewSlotRequestDTO();
		viewSlotRequestDTO.setDrRegID("AMOL");
		viewSlotRequestDTO.setSlotDate("2020-11-03");
		request.setRequest(viewSlotRequestDTO);

		MainResponseDTO<List<SaveSlotRequestDTO>> response = new MainResponseDTO<>();
		List<SaveSlotRequestDTO> saveSlotRequestDTOs = new ArrayList<>();
		SaveSlotRequestDTO saveSlotRequestDTO = new SaveSlotRequestDTO();
		saveSlotRequestDTO.setDrRegID("AMOL");
		saveSlotRequestDTO.setConsultAmount("1000");
		saveSlotRequestDTO.setIsActive("null");
		saveSlotRequestDTO.setSlotDate("Tue Nov 03 00:00:00 IST 2020");
		saveSlotRequestDTO.setSlotTime("12:30-13:30");
		saveSlotRequestDTOs.add(saveSlotRequestDTO);
		response.setResponse(saveSlotRequestDTOs);

		List<DocSlotDtlsEntity> slotList = new ArrayList<>();
		DocSlotDtlsEntity slot = new DocSlotDtlsEntity();
		slot.setDrUserIdFk("AMOL");
		slot.setSlotDate(DateUtils.getDateFromDateString("2020-11-03", "yyyy-MM-dd"));
		slot.setSlot("12:30-13:30");
		slot.setConsulFee(1000);
		slotList.add(slot);

		Mockito.when(masterRepo.getSlotDetails(Mockito.anyString(), Mockito.any())).thenReturn(slotList);
		Mockito.when(userDetails.getRole()).thenReturn("SCRIBE");

		assertThat(response.getResponse().get(0))
				.isEqualToComparingFieldByField(slotManagementServiceImpl.viewSlot(request).getResponse().get(0));
	}

	@Test
	public void viewSlotDetailsNegativeTest() throws Exception {

		MainRequestDTO<ViewSlotRequestDTO> request = new MainRequestDTO<>();
		ViewSlotRequestDTO viewSlotRequestDTO = new ViewSlotRequestDTO();
		viewSlotRequestDTO.setDrRegID("AMOL");
		viewSlotRequestDTO.setSlotDate(null);
		request.setRequest(viewSlotRequestDTO);

		MainResponseDTO<String> response = new MainResponseDTO<>();
		List<ExceptionJSONInfoDTO> errorList = new ArrayList<>();
		ExceptionJSONInfoDTO exceptionJSONInfoDTO = new ExceptionJSONInfoDTO();
		exceptionJSONInfoDTO.setErrorCode("ERR-SLT-005");
		exceptionJSONInfoDTO.setMessage("Invalid slot date.");
		errorList.add(exceptionJSONInfoDTO);
		response.setErrors(errorList);
		
		PowerMockito.mockStatic(DateUtils.class);
		PowerMockito.when(DateUtils.getDateFromDateString(Mockito.any(), Mockito.any())).thenReturn(null);

		assertThat(response.getErrors())
				.containsExactlyInAnyOrderElementsOf(slotManagementServiceImpl.viewSlot(request).getErrors());
	}

	@SuppressWarnings("unused")
	public void getDocRegIdNegativeTest() throws Exception {
		MainRequestDTO<ScribeDTO> request=new MainRequestDTO<ScribeDTO>();
		ScribeDTO scribeDTO=new ScribeDTO();
		scribeDTO.setScribeId(null);
		request.setRequest(scribeDTO);
		List<String> list=null;
		Mockito.when(userDetails.getUserName()).thenReturn("ABC");
		Mockito.when(masterRepo.getDocRegId(Mockito.anyString())).thenReturn(list);
		List<String> docRegIdList=masterRepo.getDocRegId(userDetails.getUserName());
	}
	
	@Test
	public void getDocRegIdTest() throws Exception {
		MainRequestDTO<ScribeDTO> request=new MainRequestDTO<ScribeDTO>();
		ScribeDTO scribeDTO=new ScribeDTO();
		scribeDTO.setScribeId("ABC");
		request.setRequest(scribeDTO);
		MainResponseDTO<String> response = new MainResponseDTO<>();
		response.setResponse("ABC");
		List<String> list=new ArrayList<>();
		//Object[] obj= {"ABC"};
		list.add("ABC");
		Mockito.when(userDetails.getUserName()).thenReturn("ABC");
		Mockito.when(masterRepo.getDocRegId(Mockito.anyString())).thenReturn(list);
		MainResponseDTO<String> actualResponse=slotManagementServiceImpl.getDocRegId(request);
		//assertThat(actualResponse).isEqualToComparingFieldByField(response);
	}
	@Test
	public void getSlotCreatedDaysTest() throws Exception {
		MainResponseDTO<List<String>> response=new MainResponseDTO<List<String>>();
		MainRequestDTO<String> request =new MainRequestDTO<String>();
		request.setRequest("");
		List<String> days=new ArrayList<>();
		days.add("Sun");
		response.setResponse(days);
		//when(slotManagementServiceImpl.getSlotCreatedDays(Mockito.any())).thenReturn(response);
		/*
		 * String jsonRequest = objectMapper.writeValueAsString(request); MvcResult
		 * mvcResult = mockMvc
		 * .perform(post("/getSlotCreatedDays").content(jsonRequest).contentType(
		 * MediaType.APPLICATION_JSON)) .andExpect(status().isOk()).andReturn(); String
		 * resultContent = mvcResult.getResponse().getContentAsString();
		 */
		//MainResponseDTO<String> responseWrapperActual = null;
		//assertThat(responseWrapperActual).isEqualToComparingFieldByField(response);
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void getSlotCreatedDaysNegativeTest() throws Exception {
		MainRequestDTO<?> request =new MainRequestDTO<>();
		request.setRequest(null);
		/*
		 * String jsonRequest = objectMapper.writeValueAsString(request); MvcResult
		 * mvcResult = mockMvc
		 * .perform(post("/getSlotCreatedDays").content(jsonRequest).contentType(
		 * MediaType.APPLICATION_JSON)) .andReturn(); String resultContent =
		 * mvcResult.getResponse().getContentAsString();
		 */
		//MainResponseDTO<String> responseWrapperActual = null;//objectMapper.readValue(resultContent, MainResponseDTO.class);
		//List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		//assertEquals(1, errors.size());
		//assertEquals(400, mvcResult.getResponse().getStatus());
	}
	@Test
	public void getAvailableSlotByMonthTest() throws Exception {
		
		MainResponseDTO<List<AvailableSlotResDto>> response=new MainResponseDTO<List<AvailableSlotResDto>>();
		MainRequestDTO<AvailableSlotReqDto> request=new MainRequestDTO<AvailableSlotReqDto>();
		AvailableSlotReqDto availableSlotReqDto=new AvailableSlotReqDto();
		availableSlotReqDto.setMonth("2");
		request.setRequest(availableSlotReqDto);
		List<AvailableSlotResDto> list=new ArrayList<AvailableSlotResDto>();
		AvailableSlotResDto availableSlotResDto=new AvailableSlotResDto();
		availableSlotResDto.setHoliday(false);
		availableSlotResDto.setNoOfBookedSlot(2);
		availableSlotResDto.setSlotDate(LocalDate.now());
		list.add(availableSlotResDto);
		response.setResponse(list);
		//when(slotManagementServiceImpl.getAvailableSlotByMonth(Mockito.any())).thenReturn(response);
		/*
		 * String jsonRequest = objectMapper.writeValueAsString(request); MvcResult
		 * mvcResult = mockMvc
		 * .perform(post("/getAvailableSlotByMonth").content(jsonRequest).contentType(
		 * MediaType.APPLICATION_JSON)) .andExpect(status().isOk()).andReturn(); String
		 * resultContent = mvcResult.getResponse().getContentAsString();
		 */
		//MainResponseDTO<String> responseWrapperActual =null;// objectMapper.readValue(resultContent, MainResponseDTO.class);
		//assertThat(responseWrapperActual).isEqualToComparingOnlyGivenFields(response);
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void getAvailableSlotByMonthNegativeTest() throws Exception {
		MainRequestDTO<AvailableSlotReqDto> request=new MainRequestDTO<AvailableSlotReqDto>();
		AvailableSlotReqDto availableSlotReqDto=new AvailableSlotReqDto();
		request.setRequest(availableSlotReqDto);
		/*
		 * String jsonRequest = objectMapper.writeValueAsString(request); MvcResult
		 * mvcResult = mockMvc
		 * .perform(post("/getAvailableSlotByMonth").content(jsonRequest).contentType(
		 * MediaType.APPLICATION_JSON)) .andReturn(); String resultContent =
		 * mvcResult.getResponse().getContentAsString();
		 */
		///MainResponseDTO<String> responseWrapperActual =null;// objectMapper.readValue(resultContent, MainResponseDTO.class);
		//List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		//assertEquals(1, errors.size());
		//assertEquals(400, mvcResult.getResponse().getStatus());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void holidayManagementTest() throws Exception {
		MainResponseDTO<String> response=new MainResponseDTO<String>();
		response.setResponse("Holiday updated successfully.");
		MainRequestDTO<HolidayReqtDto> request=new MainRequestDTO<HolidayReqtDto>();
		HolidayReqtDto  holidayReqtDto=new HolidayReqtDto();
		LocalDate[] date= {LocalDate.now()};
		holidayReqtDto.setHolidayDate(date);
		holidayReqtDto.setHolidayReason("Holiday");
		holidayReqtDto.setIsHoliday("true");
		request.setRequest(holidayReqtDto);
		//when(slotManagementServiceImpl.holidayManagement(Mockito.any())).thenReturn(response);
		/*
		 * String jsonRequest = objectMapper.writeValueAsString(request); MvcResult
		 * mvcResult = mockMvc
		 * .perform(post("/holidayManagement").content(jsonRequest).contentType(
		 * MediaType.APPLICATION_JSON)) .andExpect(status().isOk()).andReturn(); String
		 * resultContent = mvcResult.getResponse().getContentAsString();
		 */
		//MainResponseDTO<String> responseWrapperActual =null;// objectMapper.readValue(resultContent, MainResponseDTO.class);
		//assertThat(responseWrapperActual).isEqualToComparingFieldByField(response);
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void holidayManagementNegativeTest() throws Exception {
		MainRequestDTO<HolidayReqtDto> request=new MainRequestDTO<HolidayReqtDto>();
		HolidayReqtDto  holidayReqtDto=new HolidayReqtDto();
		LocalDate[] date= {};
		holidayReqtDto.setHolidayDate(date);
		holidayReqtDto.setHolidayReason("Holiday");
		holidayReqtDto.setIsHoliday("");
		request.setRequest(holidayReqtDto);
		/*
		 * String jsonRequest = objectMapper.writeValueAsString(request); MvcResult
		 * mvcResult = mockMvc
		 * .perform(post("/holidayManagement").content(jsonRequest).contentType(
		 * MediaType.APPLICATION_JSON)) .andReturn(); String resultContent =
		 * mvcResult.getResponse().getContentAsString();
		 */
		//MainResponseDTO<String> responseWrapperActual =null;// objectMapper.readValue(resultContent, MainResponseDTO.class);
		//List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		//assertEquals(3, errors.size());
		//assertEquals(400, mvcResult.getResponse().getStatus());
		
	}

}
