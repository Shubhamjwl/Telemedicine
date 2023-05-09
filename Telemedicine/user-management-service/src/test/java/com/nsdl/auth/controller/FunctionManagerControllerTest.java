package com.nsdl.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.FunctionDeleteRequestDto;
import com.nsdl.auth.dto.FunctionDetailsDto;
import com.nsdl.auth.dto.FunctionRequestDTO;
import com.nsdl.auth.dto.FunctionResposeDTO;
import com.nsdl.auth.dto.FunctionUpdateRequestDto;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.service.FunctionManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionManagerControllerTest {

	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;

	@Autowired
	public ObjectMapper mapper;

	@MockBean
	public FunctionManagementService functionManagementService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void addFunctionPositiveTest() throws Exception {
		MainResponseDTO<FunctionResposeDTO> responseExpected = new MainResponseDTO<>();
		FunctionResposeDTO functionResponseExcpected = new FunctionResposeDTO();
		functionResponseExcpected.setRoleId(6608);
		functionResponseExcpected.setStatus(AuthConstant.ACTIVE);
		responseExpected.setResponse(functionResponseExcpected);

		MainRequestDTO<FunctionRequestDTO> mainRequest = new MainRequestDTO<>();
		FunctionRequestDTO functionRequestDTO = new FunctionRequestDTO();
		functionRequestDTO.setFunctionName("Save Scribe Details");
		mainRequest.setRequest(functionRequestDTO);
		Mockito.when(functionManagementService.saveFunction(Mockito.any(FunctionRequestDTO.class)))
				.thenReturn(functionResponseExcpected);

		String jsonStr = mapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc
				.perform(post("/function/create").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<FunctionResposeDTO> mainActualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<FunctionResposeDTO>>() {
				});

		assertThat(mainActualResponse.getResponse()).isEqualToComparingFieldByField(responseExpected.getResponse());

	}

	@Test
	public void modifyFunctionPositiveTest() throws Exception {

		MainResponseDTO<CommonResponseDTO> responseExpected = new MainResponseDTO<>();
		CommonResponseDTO functionUpdateExpectedResponseDto = new CommonResponseDTO();
		functionUpdateExpectedResponseDto.setMessage(AuthConstant.FUNCTION_UPDATE_SUCCESS);
		responseExpected.setResponse(functionUpdateExpectedResponseDto);

		MainRequestDTO<FunctionUpdateRequestDto> mainRequest = new MainRequestDTO<>();
		FunctionUpdateRequestDto functionUpdateRequestDto = new FunctionUpdateRequestDto();
		functionUpdateRequestDto.setFunctionName("doctor registration");
		functionUpdateRequestDto.setNewFunctionName("doctor onboarding");
		mainRequest.setRequest(functionUpdateRequestDto);
		Mockito.when(functionManagementService.update(functionUpdateRequestDto)).thenReturn(responseExpected);

		String jsonStr = mapper.writeValueAsString(mainRequest);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/function/update").content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> mainActualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});

		assertThat(mainActualResponse.getResponse()).isEqualToComparingFieldByField(responseExpected.getResponse());

	}

	@Test
	public void getAllFunctionsPositiveTest() throws Exception {
		MainResponseDTO<List<FunctionDetailsDto>> mainResponse = new MainResponseDTO<>();
		List<FunctionDetailsDto> listOfExpectedFucntionDTOs = new ArrayList<>();
		FunctionDetailsDto functionDetailsDto = new FunctionDetailsDto();
		functionDetailsDto.setId(101);
		functionDetailsDto.setFunctionName("patient registration");
		listOfExpectedFucntionDTOs.add(functionDetailsDto);
		mainResponse.setResponse(listOfExpectedFucntionDTOs);
		Mockito.when(functionManagementService.getFunctions()).thenReturn(listOfExpectedFucntionDTOs);

		MvcResult mvcResult = mockMvc.perform(get("/function/getFunctions").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<List<FunctionDetailsDto>> responseWrapperActual = mapper.readValue(responseStr,
				MainResponseDTO.class);
		List<FunctionDetailsDto> actualResponse = mapper.convertValue(responseWrapperActual.getResponse(),
				new TypeReference<List<FunctionDetailsDto>>() {
				});

		assertThat(actualResponse).isEqualTo(listOfExpectedFucntionDTOs);
	}

	@Test
	public void deleteFunctionPositiveTest() throws Exception {

		MainResponseDTO<CommonResponseDTO> responseExpected = new MainResponseDTO<>();
		CommonResponseDTO functionDeleteExcpectedResponseDto = new CommonResponseDTO();
		functionDeleteExcpectedResponseDto.setMessage(AuthConstant.FUNCTION_DELETE_SUCCESS);
		responseExpected.setResponse(functionDeleteExcpectedResponseDto);

		MainRequestDTO<FunctionDeleteRequestDto> mainRequest = new MainRequestDTO<>();
		FunctionDeleteRequestDto functionDeleteRequestDto = new FunctionDeleteRequestDto();
		functionDeleteRequestDto.setFunctionName("scribe registration");
		mainRequest.setRequest(functionDeleteRequestDto);
		Mockito.when(functionManagementService.deleteFunction(mainRequest)).thenReturn(responseExpected);

		String actualRequestStr = mapper.writeValueAsString(mainRequest);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/function/delete")
				.content(actualRequestStr).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> actualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});

		assertThat(actualResponse.getResponse()).isEqualToComparingFieldByField(responseExpected.getResponse());
	}

}
