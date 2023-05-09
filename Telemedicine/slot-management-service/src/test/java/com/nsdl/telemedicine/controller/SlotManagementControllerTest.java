package com.nsdl.telemedicine.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.telemedicine.slot.dto.AvailableSlotReqDto;
import com.nsdl.telemedicine.slot.dto.AvailableSlotResDto;
import com.nsdl.telemedicine.slot.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.slot.dto.HolidayReqtDto;
import com.nsdl.telemedicine.slot.dto.MainRequestDTO;
import com.nsdl.telemedicine.slot.dto.MainResponseDTO;
import com.nsdl.telemedicine.slot.dto.SaveSlotRequestDTO;
import com.nsdl.telemedicine.slot.dto.ScribeDTO;
import com.nsdl.telemedicine.slot.dto.SlotReqtDto;
import com.nsdl.telemedicine.slot.dto.ViewSlotRequestDTO;
import com.nsdl.telemedicine.slot.dto.WorkingTime;
import com.nsdl.telemedicine.slot.service.SlotManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SlotManagementControllerTest {

	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;

	@MockBean
	private SlotManagementService slotManagementService;

	@Autowired
	public ObjectMapper objectMapper;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@SuppressWarnings("unchecked")
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
		saveSlotRequestDTO.setSlotDate("2020-11-03T00:00");
		saveSlotRequestDTO.setSlotTime("12:30-13:30");
		saveSlotRequestDTOs.add(saveSlotRequestDTO);
		response.setResponse(saveSlotRequestDTOs);

		when(slotManagementService.viewSlot(Mockito.any())).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/viewSlotDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<List<SaveSlotRequestDTO>> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<SaveSlotRequestDTO> actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				new TypeReference<List<SaveSlotRequestDTO>>() {
				});
		assertThat(actualResponse.get(0)).isEqualToComparingFieldByField(saveSlotRequestDTO);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void viewSlotDetailsNegativeTest() throws Exception {

		MainRequestDTO<ViewSlotRequestDTO> request = new MainRequestDTO<>();
		ViewSlotRequestDTO viewSlotRequestDTO = new ViewSlotRequestDTO();
		viewSlotRequestDTO.setDrRegID(null);
		viewSlotRequestDTO.setSlotDate(null);
		request.setRequest(viewSlotRequestDTO);

		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/viewSlotDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<List<SaveSlotRequestDTO>> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);

		List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		assertEquals(2, errors.size());
		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void modifySlotDetailsTest() throws Exception {

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
		response.setResponse("Slot updated successfully.");

		when(slotManagementService.modifySlot(Mockito.any())).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/modifySlotDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void modifySlotDetailsNegativeTest() throws Exception {

		MainRequestDTO<List<SaveSlotRequestDTO>> request = new MainRequestDTO<>();

		List<SaveSlotRequestDTO> saveSlotRequestDTOs = new ArrayList<>();
		SaveSlotRequestDTO saveSlotRequestDTO = new SaveSlotRequestDTO();
		saveSlotRequestDTO.setDrRegID("AMOL");
		saveSlotRequestDTO.setConsultAmount(null);
		saveSlotRequestDTO.setIsActive(null);
		saveSlotRequestDTO.setSlotDate("2020-11-03");
		saveSlotRequestDTO.setSlotTime("12:30-13:30");
		saveSlotRequestDTOs.add(saveSlotRequestDTO);
		request.setRequest(saveSlotRequestDTOs);

		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/modifySlotDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void saveSlotDetailsTest() throws Exception {

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

		when(slotManagementService.saveSlot(Mockito.any())).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/saveSlotDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void saveSlotDetailsNegativeTest() throws Exception {

		MainRequestDTO<SlotReqtDto> request = new MainRequestDTO<>();
		SlotReqtDto saveSlotRequestDTO = new SlotReqtDto();
		List<WorkingTime> workingTimeList =new ArrayList<WorkingTime>();
		WorkingTime workingTime=new WorkingTime();
		workingTime.setEndTime("");
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

		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/saveSlotDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getDocRegIdNegativeTest() throws Exception {
		MainRequestDTO<ScribeDTO> request=new MainRequestDTO<ScribeDTO>();
		ScribeDTO scribeDTO=new ScribeDTO();
		scribeDTO.setScribeId(null);
		request.setRequest(scribeDTO);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getDocRegId").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
		assertEquals(400, mvcResult.getResponse().getStatus());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getDocRegIdTest() throws Exception {
		MainRequestDTO<ScribeDTO> request=new MainRequestDTO<ScribeDTO>();
		ScribeDTO scribeDTO=new ScribeDTO();
		scribeDTO.setScribeId("ABC");
		request.setRequest(scribeDTO);
		MainResponseDTO<String> response = new MainResponseDTO<>();
		response.setResponse("ABC");
		when(slotManagementService.getDocRegId(Mockito.any())).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getDocRegId").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(response);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void getSlotCreatedDaysTest() throws Exception {
		MainResponseDTO<List<String>> response=new MainResponseDTO<List<String>>();
		MainRequestDTO<String> request =new MainRequestDTO<String>();
		request.setRequest("");
		List<String> days=new ArrayList<>();
		days.add("Sun");
		response.setResponse(days);
		when(slotManagementService.getSlotCreatedDays(Mockito.any())).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getSlotCreatedDays").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(response);
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void getSlotCreatedDaysNegativeTest() throws Exception {
		MainRequestDTO<?> request =new MainRequestDTO<>();
		request.setRequest(null);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getSlotCreatedDays").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
		assertEquals(400, mvcResult.getResponse().getStatus());
	}
	@SuppressWarnings("unchecked")
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
		when(slotManagementService.getAvailableSlotByMonth(Mockito.any())).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getAvailableSlotByMonth").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingOnlyGivenFields(response);
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void getAvailableSlotByMonthNegativeTest() throws Exception {
		MainRequestDTO<AvailableSlotReqDto> request=new MainRequestDTO<AvailableSlotReqDto>();
		AvailableSlotReqDto availableSlotReqDto=new AvailableSlotReqDto();
		request.setRequest(availableSlotReqDto);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getAvailableSlotByMonth").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
		assertEquals(400, mvcResult.getResponse().getStatus());
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
		when(slotManagementService.holidayManagement(Mockito.any())).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/holidayManagement").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(response);
		
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
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/holidayManagement").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
		List<ExceptionJSONInfoDTO> errors = responseWrapperActual.getErrors();
		assertEquals(3, errors.size());
		assertEquals(400, mvcResult.getResponse().getStatus());
		
	}
}
