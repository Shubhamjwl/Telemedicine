package com.nsdl.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.DoctorDocDtlsDTO;
import com.nsdl.auth.dto.DoctorRegDtlsDTO;
import com.nsdl.auth.dto.GetDoctorDetailsDTO;
import com.nsdl.auth.dto.GetDoctorDetailsRequestDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.VerifyDoctorRequestDTO;
import com.nsdl.auth.service.DoctorVerificationService;

@RunWith(SpringRunner.class)
@SpringBootTest

public class DoctorVerificationControllerTest {
	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;

	@Autowired
	public ObjectMapper mapper;

	@MockBean
	private DoctorVerificationService doctorVerificationService;

	public String authToken = null;
	public String authorization = null;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

		authorization = "BearereyJhbGciOiJIUzUxMiJ9"
				+ ".eyJzdWIiOiJTQVlBTEkyNSIsInJvbGUiOiJET0NUT1IiLCJpYXQiOjE2MDQ0OTI4MzEsImV4cCI6MTYwNDQ5NjQzMX0"
				+ ".l_Xk5nCuoeuAsuvFro9Kk7X0d4Noa9xdR7cAn1W6VDWfTnnd2il9r-xlHsbZ2Q-RXj56MWtZs52ggjL4-vuXoQ;"
				+ " Max-Age=3600000; Expires=Wed, 16-Dec-2020 04:27:11 GMT; Path=/; Secure; HttpOnly";

		authToken = "Authorization=BearereyJhbGciOiJIUzUxMiJ9"
				+ ".eyJzdWIiOiJTQVlBTEkyNSIsInJvbGUiOiJET0NUT1IiLCJpYXQiOjE2MDQ0OTI4MzEsImV4cCI6MTYwNDQ5NjQzMX0"
				+ ".l_Xk5nCuoeuAsuvFro9Kk7X0d4Noa9xdR7cAn1W6VDWfTnnd2il9r-xlHsbZ2Q-RXj56MWtZs52ggjL4-vuXoQ;"
				+ " Max-Age=3600000; Expires=Wed, 16-Dec-2020 04:27:11 GMT; Path=/; Secure; HttpOnly";
	}

	@Test
	public void getDoctorListToVerify() throws Exception {
		MainResponseDTO<List<DoctorRegDtlsDTO>> mainUserExpectedResponse = new MainResponseDTO<>();
		mainUserExpectedResponse.setId("user");
		mainUserExpectedResponse.setStatus(true);
		mainUserExpectedResponse.setVersion("v1");

		MainRequestDTO<String> mainRequest = new MainRequestDTO<>();
		mainRequest.setRequest("");
		String jsonRequest = mapper.writeValueAsString(mainRequest);
		List<DoctorRegDtlsDTO> listOfExpectedFucntionDoctorDTOs = new ArrayList<>();
		DoctorRegDtlsDTO expectedDoctorDtls = DoctorRegDtlsDTO.builder().drFullName("DOCTORTELEMED")
				.drEmail("TEST012@NSDL.CO.IN").drMCINo("MCI1277").drSMCNo("SMC1234")
				.drSpecilization("GENERAL PHYSICIAN").drUserID("RIKSW909").build();
		listOfExpectedFucntionDoctorDTOs.add(expectedDoctorDtls);
		mainUserExpectedResponse.setResponse(listOfExpectedFucntionDoctorDTOs);
		Mockito.when(doctorVerificationService.getDoctorListToVerify()).thenReturn(listOfExpectedFucntionDoctorDTOs);
		MvcResult mvcResult = mockMvc
				.perform(post("/verifyDoctor/getDoctorListToVerify").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<List<DoctorRegDtlsDTO>> responseWrapperActual = mapper.readValue(resultContent,
				MainResponseDTO.class);
		List<DoctorRegDtlsDTO> actualResponse = mapper.convertValue(responseWrapperActual.getResponse(),
				new TypeReference<List<DoctorRegDtlsDTO>>() {
				});
		assertThat(actualResponse).isEqualTo(mainUserExpectedResponse.getResponse());
	}

	@Test
	public void verifyDoctorByDocNameTest() throws Exception {
		MainResponseDTO<CommonResponseDTO> mainResponseExpected = new MainResponseDTO<>();
		mainResponseExpected.setId("user");
		mainResponseExpected.setStatus(true);
		CommonResponseDTO userResponse = new CommonResponseDTO();
		// userResponse.setUserId(null);
		userResponse.setMessage("Doctor verified successfully");
		mainResponseExpected.setResponse(userResponse);
		MainRequestDTO<VerifyDoctorRequestDTO> mainRequest = new MainRequestDTO<>();
		VerifyDoctorRequestDTO request = new VerifyDoctorRequestDTO();
		request.setRegDocUserName("nsdl567868");
		request.setVerificationStatusFlag("Y");
		request.setReason("");
		mainRequest.setRequest(request);
		Mockito.when(doctorVerificationService.verifyDoctorByDocName(Mockito.any(VerifyDoctorRequestDTO.class)))
				.thenReturn(mainResponseExpected);
		String jsonRequest = mapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc.perform(post("/verifyDoctor/verifyDoctorByDocName").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();

		MainResponseDTO<CommonResponseDTO> responseWrapperActual = mapper.readValue(resultContent,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});
		assertThat(responseWrapperActual.getResponse()).isEqualToComparingFieldByField(userResponse);

	}

	@Test
	public void verifyDoctorByDocNameNegativeTest() throws Exception {
		MainRequestDTO<VerifyDoctorRequestDTO> mainRequest = new MainRequestDTO<VerifyDoctorRequestDTO>();
		VerifyDoctorRequestDTO request = new VerifyDoctorRequestDTO();
		mainRequest.setRequest(request);
		String jsonRequest = mapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc.perform(post("/verifyDoctor/verifyDoctorByDocName").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<?> responseWrapperActual = mapper.readValue(resultContent, MainResponseDTO.class);
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(400);
		assertNotNull(responseWrapperActual.getErrors());
		assertThat(responseWrapperActual.getErrors().size()).isEqualTo(2);

	}

	@Test
	public void getDoctorDetailsByDoctorIdTest() throws Exception {
		MainRequestDTO<GetDoctorDetailsRequestDTO> mainRequest = new MainRequestDTO<>();
		GetDoctorDetailsRequestDTO request = new GetDoctorDetailsRequestDTO();
		request.setDoctorId("ANANDDOC1");
		mainRequest.setRequest(request);

		MainResponseDTO<GetDoctorDetailsDTO> mainResponseExpected = new MainResponseDTO<GetDoctorDetailsDTO>();
		GetDoctorDetailsDTO response = new GetDoctorDetailsDTO();
		response.setDrdAddress1("PUNE");
		response.setDrdCity("NASHIK");
		response.setDrdConsulFee(200);
		response.setDrdDrName("ANAND");
		response.setDrdEmail("AJITKUMARS@NSDL.CO.IN");
		response.setDrdGender("MALE");
		response.setDrdIsRegByIpan("N");
		response.setDrdMciNumber("MCI123");
		response.setDrdMobileNo("9876543210");
		List<DoctorDocDtlsDTO> doctorDocDtlsDTOList = new ArrayList<DoctorDocDtlsDTO>();
		DoctorDocDtlsDTO doctorDocDtlsDTO = new DoctorDocDtlsDTO();
		doctorDocDtlsDTO.setDdtDocIdPk(580);
		doctorDocDtlsDTO.setDdtDocsName("UI_tele.xlsx");
		doctorDocDtlsDTO.setDdtDocsPath("/var/telemedicine/documents/regDocs/ANANDDOC1/UI_tele.xlsx");
		doctorDocDtlsDTO.setDdtDocsRemark("document");
		doctorDocDtlsDTO.setDdtDocsType("AadharCard");
		doctorDocDtlsDTOList.add(doctorDocDtlsDTO);
		response.setDrDocsDtls(doctorDocDtlsDTOList);
		response.setDrdSmcNumber("SMC123");
		response.setDrdSpecialiazation("SKIN & HAIR");
		response.setDrdState("MAHARASHTRA");
		response.setDrdUserId("ANANDDOC1");
		mainResponseExpected.setResponse(response);
		String jsonRequest = mapper.writeValueAsString(mainRequest);
		Mockito.when(doctorVerificationService.getDoctorDetailsByDoctorId(Mockito.anyString()))
				.thenReturn(mainResponseExpected);
		MvcResult mvcResult = mockMvc.perform(
				post("/verifyDoctor/getDoctorDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();

		MainResponseDTO<GetDoctorDetailsDTO> responseWrapperActual = mapper.readValue(resultContent,
				new TypeReference<MainResponseDTO<GetDoctorDetailsDTO>>() {
				});
		assertThat(responseWrapperActual.getResponse()).isEqualToComparingFieldByField(response);

	}

	@Test
	public void getDoctorDetailsByDoctorIdNegativeTest() throws Exception {
		MainRequestDTO<GetDoctorDetailsRequestDTO> mainRequest = new MainRequestDTO<>();
		String jsonStr = mapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc
				.perform(
						post("/verifyDoctor/getDoctorDetails").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<GetDoctorDetailsDTO> responseWrapperActual = mapper.readValue(resultContent,
				MainResponseDTO.class);
		assertThat(mvcResult.getResponse().getStatus()).isEqualTo(500);
		assertNotNull(responseWrapperActual.getErrors());
		assertThat(responseWrapperActual.getErrors().size()).isEqualTo(1);
	}
}
