/**
 * 
 */
package com.nsdl.telemedicine.review.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.nsdl.telemedicine.review.constant.PatientReviewConstants;
import com.nsdl.telemedicine.review.dto.MainRequestDTO;
import com.nsdl.telemedicine.review.dto.MainResponseDTO;
import com.nsdl.telemedicine.review.dto.PatientRevDtlsDTO;
import com.nsdl.telemedicine.review.service.PatientReviewService;

/**
 * @author Pegasus_girishk
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientReviewControllerTest {

	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;
	
	@Autowired
	public ObjectMapper objectMapper;
	
	@MockBean
	public PatientReviewService patientReviewService;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void savePatientReviewDtlsTest() throws Exception {
		MainRequestDTO<PatientRevDtlsDTO> request = new MainRequestDTO<PatientRevDtlsDTO>();
		PatientRevDtlsDTO reviewDetails = new PatientRevDtlsDTO();
		request.setRequest(reviewDetails);

		MainResponseDTO mainResponseDto = new MainResponseDTO();
		mainResponseDto.setResponse(PatientReviewConstants.PATIENT_REVIEW_INSERTED.getValue());

		Mockito.when(patientReviewService.savePatientReviewDtls(request)).thenReturn(mainResponseDto);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/review/v1/savePatientReviewDtls").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO responseWrapperActual = objectMapper.readValue(resultContent,MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(mainResponseDto);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getNumberOfLikesToDoctorTest() throws Exception {
		Long likes = 15000L;
		MainRequestDTO<PatientRevDtlsDTO> mainRequest = new MainRequestDTO<PatientRevDtlsDTO>();
		PatientRevDtlsDTO patientRevDtlsDTO= new PatientRevDtlsDTO();
		patientRevDtlsDTO.setPrdDrUserIdFk("GIRISHDOC");
		mainRequest.setRequest(patientRevDtlsDTO);
		MainResponseDTO<Long> mainResponseDto = new MainResponseDTO<Long>();
		mainResponseDto.setResponse(likes);

		Mockito.when(patientReviewService.getNumberOfLikesToDoctor(Mockito.anyString())).thenReturn(mainResponseDto.getResponse());
		String jsonRequest = objectMapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc.perform(post("/review/v1/getNumberOfLikesToDoctor").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<Long> responseWrapperActual = objectMapper.readValue(resultContent,MainResponseDTO.class);
		Long actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),new TypeReference<Long>() {});
		assertThat(actualResponse).isEqualTo(mainResponseDto.getResponse());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getNumberOfCommentsToDoctorTest() throws Exception {
		Long comments = 15000L;
		MainRequestDTO<PatientRevDtlsDTO> mainRequest = new MainRequestDTO<PatientRevDtlsDTO>();
		PatientRevDtlsDTO patientRevDtlsDTO= new PatientRevDtlsDTO();
		patientRevDtlsDTO.setPrdDrUserIdFk("GIRISHDOC");
		mainRequest.setRequest(patientRevDtlsDTO);
		MainResponseDTO<Long> mainResponseDto = new MainResponseDTO<Long>();
		mainResponseDto.setResponse(comments);

		Mockito.when(patientReviewService.getNumberOfCommentsToDoctor(Mockito.anyString())).thenReturn(mainResponseDto.getResponse());
		String jsonRequest = objectMapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc.perform(post("/review/v1/getNumberOfCommentsToDoctor").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<Long> responseWrapperActual = objectMapper.readValue(resultContent,MainResponseDTO.class);
		Long actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),new TypeReference<Long>() {});
		assertThat(actualResponse).isEqualTo(mainResponseDto.getResponse());
	}
}
