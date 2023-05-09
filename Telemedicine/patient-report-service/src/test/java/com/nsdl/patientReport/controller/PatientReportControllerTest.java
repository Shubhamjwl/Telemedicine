/*package com.nsdl.patientReport.controller;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Date;
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
import com.nsdl.patientReport.constant.ErrorConstant;
import com.nsdl.patientReport.dto.AppointmentDTO;
import com.nsdl.patientReport.dto.AppointmentRequestDTO;
import com.nsdl.patientReport.dto.MainRequestDTO;
import com.nsdl.patientReport.dto.MainResponseDTO;
import com.nsdl.patientReport.entity.ConsultPriscpDtl;
import com.nsdl.patientReport.exception.ApiError;
import com.nsdl.patientReport.exception.PatientReportException;
import com.nsdl.patientReport.service.PatientReportService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientReportControllerTest {

	public MockMvc mockMvc;
	
	@Autowired
	public WebApplicationContext context;
	
	@MockBean
	PatientReportService patientReportService;

	@Autowired
	public ObjectMapper objectMapper; 


	MainResponseDTO<List<AppointmentDTO>> mainResponseDto = new MainResponseDTO<>();
	
	ConsultPriscpDtl prescription = new ConsultPriscpDtl();

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getPatientAppointmentDetailsTest() throws Exception {

		MainRequestDTO<AppointmentRequestDTO> request = new MainRequestDTO<>();
		AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
		appointmentRequestDTO.setDoc_user_id("AMOL");
		appointmentRequestDTO.setPatient_user_id("JEEVAN");
		request.setRequest(appointmentRequestDTO);

		Mockito.when(patientReportService.getPatientAppointmentDetails(request)).thenReturn(mainResponseDto);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getPatientAppointmentDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<List<AppointmentDTO>> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<AppointmentDTO> actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				new TypeReference<List<AppointmentDTO>>() {
		});
		assertThat(actualResponse).isEqualTo((mainResponseDto.getResponse()));
	}
	
	@Test
	public void getPatientAppointmentDetailsNegetiveTest() throws Exception {

		MainRequestDTO<AppointmentRequestDTO> request = new MainRequestDTO<>();
		AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
		appointmentRequestDTO.setDoc_user_id("AMOL");
		appointmentRequestDTO.setPatient_user_id("");
		request.setRequest(appointmentRequestDTO);
		List<String> errors = new ArrayList<>();
		errors.add(ErrorConstant.PATIENT_ID_MISSING);
		mainResponseDto.setErrors(errors);
		
		Mockito.when(patientReportService.getPatientAppointmentDetails(request)).thenReturn(mainResponseDto);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getPatientAppointmentDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<?> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<String> actualRes = responseWrapperActual.getErrors();
		assertEquals(1, actualRes.size());
	}


	@SuppressWarnings("unchecked")
	@Test
	public void getDoctorAppointmentDetailsTest() throws Exception {

		MainRequestDTO<AppointmentRequestDTO> request = new MainRequestDTO<>();
		AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
		appointmentRequestDTO.setDoc_user_id("AMOL");
		appointmentRequestDTO.setPatient_user_id("JEEVAN");
		request.setRequest(appointmentRequestDTO);

		Mockito.when(patientReportService.getDoctorAppointmentDetails(request)).thenReturn(mainResponseDto);

		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getDoctorAppointmentDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<List<AppointmentDTO>> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<AppointmentDTO> actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				new TypeReference<List<AppointmentDTO>>() { });
		assertThat(actualResponse).isEqualTo((mainResponseDto.getResponse()));
	}


	@Test
	public void getDoctorAppointmentDetailsNegetiveTest() throws Exception {

		MainRequestDTO<AppointmentRequestDTO> request = new MainRequestDTO<>();
		AppointmentRequestDTO appointmentRequestDTO = new AppointmentRequestDTO();
		appointmentRequestDTO.setDoc_user_id("");
		appointmentRequestDTO.setPatient_user_id("JEEVAN");
		request.setRequest(appointmentRequestDTO);
		List<String> errors = new ArrayList<>();
		errors.add(ErrorConstant.DOCTOR_ID_MISSING);
		mainResponseDto.setErrors(errors);
		
		Mockito.when(patientReportService.getDoctorAppointmentDetails(request)).thenReturn(mainResponseDto);

		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/getDoctorAppointmentDetails").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<?> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<String> acterrors = responseWrapperActual.getErrors();
		assertEquals(1, acterrors.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void downloadDocumentTest() throws Exception {

		MainRequestDTO<ConsultPriscpDtl> request = new MainRequestDTO<ConsultPriscpDtl>();
		ConsultPriscpDtl consultPriscpDtl = new ConsultPriscpDtl();
		consultPriscpDtl.setCpdApptIdFk("c2f4e41d-c760-41b2-80f1-d2cf4bf62aeb");
		consultPriscpDtl.setCpdPriscpPath("D:\\\\temptodelte\\\\sample.txt");
		consultPriscpDtl.setCpdDrTmpltName("document");
		consultPriscpDtl.setCpdDrUserIdFk("AjitK0123");
		consultPriscpDtl.setCpdOptiVersion("5");
		consultPriscpDtl.setCpdPtUserIdFk("SANDESH");
		consultPriscpDtl.setCpdCreatedBy("SANDESH");
		
		request.setId("Prescription");
		request.setRequest(consultPriscpDtl);
		request.setRequesttime(new Date());
		request.setVersion("1.0");

		Mockito.when(patientReportService.downloadPrescription(request)).thenReturn(prescription);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/downloadPrescription").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<List<AppointmentDTO>> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		ConsultPriscpDtl actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				new TypeReference<ConsultPriscpDtl>() {
		});
		assertThat(actualResponse).isEqualToComparingFieldByField(prescription);
	}

	@Test
	public void downloadDocumentNegativeTest() throws Exception{
		MainRequestDTO<ConsultPriscpDtl> request = new MainRequestDTO<ConsultPriscpDtl>();
		ConsultPriscpDtl consultPriscpDtl = new ConsultPriscpDtl();
		consultPriscpDtl.setCpdApptIdFk("c2f4e41d-c760-41b2-80f1-d2cf4bf62aeb");
		consultPriscpDtl.setCpdPriscpPath("D:\\\\temptodelte\\\\sample.txt");
		consultPriscpDtl.setCpdDrTmpltName("document");
		consultPriscpDtl.setCpdDrUserIdFk("AjitK0123");
		consultPriscpDtl.setCpdOptiVersion("5");
		consultPriscpDtl.setCpdPtUserIdFk("SANDESH");
		consultPriscpDtl.setCpdCreatedBy("SANDESH");
		
		request.setId("Prescription");
		request.setRequest(consultPriscpDtl);
		request.setRequesttime(new Date());
		request.setVersion("1.0");
		
		mockMvc.perform(post("/downloadPrescription").contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk());
	}
	
}
*/