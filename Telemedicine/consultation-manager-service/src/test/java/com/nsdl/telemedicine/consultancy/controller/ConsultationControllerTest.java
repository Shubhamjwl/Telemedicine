/*package com.nsdl.telemedicine.consultancy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.nsdl.telemedicine.consultancy.constant.ConsultationConstants;
import com.nsdl.telemedicine.consultancy.dto.AddressDTO;
import com.nsdl.telemedicine.consultancy.dto.AppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.ChiefComplaintsDtls;
import com.nsdl.telemedicine.consultancy.dto.ConsultPresDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.DrAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.MainRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.MainResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.PtAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.PtLifeStyleDtls;
import com.nsdl.telemedicine.consultancy.dto.PtMedicalDtls;
import com.nsdl.telemedicine.consultancy.dto.PatientDtls;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtls;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtlsDTO;
import com.nsdl.telemedicine.consultancy.exception.ServiceError;
import com.nsdl.telemedicine.consultancy.service.AppointmentService;
import com.nsdl.telemedicine.consultancy.service.ConsultationService;
import com.nsdl.telemedicine.consultancy.service.PatientService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsultationControllerTest {

	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;

	@MockBean
	private ConsultationService consultationService;

	@MockBean
	private AppointmentService appointmentService;

	@MockBean
	private PatientService patientService;

	@Autowired
	public ObjectMapper objectMapper;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAppointmentListByPatientIDTest() throws Exception {

		String ptRegId = "pt2";

		MainResponseDTO<PtAppointmentDTO> response = new MainResponseDTO<>();
		PtAppointmentDTO ptAppointmentDTO = new PtAppointmentDTO();
		ptAppointmentDTO.setPtRegID("pt2");
		List<AppointmentDTO> apptDtls = new ArrayList<>();
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setAppointmentDate("2020-03-21");
		appointmentDTO.setAppointmentID("12635127");
		appointmentDTO.setAppointmentTime("11.30");
		appointmentDTO.setDoctorName("AMOL");
		appointmentDTO.setPatientName("pt2");
		appointmentDTO.setStatus("Y");
		apptDtls.add(appointmentDTO);
		ptAppointmentDTO.setApptDtls(apptDtls);
		response.setResponse(ptAppointmentDTO);

		Mockito.when(appointmentService.getAppointmentListByPatientID()).thenReturn(ptAppointmentDTO);
		MvcResult mvcResult = mockMvc.perform(get("/appointment/getAppointmentListByPatientID")
				.param("ptRegID", ptRegId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<PtAppointmentDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		PtAppointmentDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				PtAppointmentDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(ptAppointmentDTO);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAppointmentListByPatientIDNegativeTest() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(get("/appointment/getAppointmentListByPatientID").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<PtAppointmentDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<ServiceError> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getConsultationListByDoctorIDTest() throws Exception {

		String drRegID = "722";

		MainResponseDTO<DrAppointmentDTO> response = new MainResponseDTO<>();
		DrAppointmentDTO drAppointmentDTO = new DrAppointmentDTO();
		drAppointmentDTO.setDrRegID("722");

		List<AppointmentDTO> apptDtls = new ArrayList<>();
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setAppointmentDate("2020-03-21");
		appointmentDTO.setAppointmentID("12635127");
		appointmentDTO.setAppointmentTime("11.30");
		appointmentDTO.setDoctorName("AMOL");
		appointmentDTO.setPatientName("pt2");
		appointmentDTO.setStatus("Y");
		apptDtls.add(appointmentDTO);
		Map<String, List<AppointmentDTO>> apptDtlsMap = new LinkedHashMap<String, List<AppointmentDTO>>();
		apptDtlsMap.put(ConsultationConstants.TODAY.getValue(), apptDtls);
		drAppointmentDTO.setApptDtls(apptDtlsMap);
		response.setResponse(drAppointmentDTO);

		Mockito.when(appointmentService.getAppointmentListByDrID(appointmentDTO.getAppointmentDate())).thenReturn(drAppointmentDTO);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/getConsultationListByDoctorID")
				.param("drRegID", drRegID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<DrAppointmentDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		DrAppointmentDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				DrAppointmentDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(drAppointmentDTO);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getConsultationListByDoctorIDNegativeTest() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(post("/appointment/getConsultationListByDoctorID").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<DrAppointmentDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<ServiceError> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getPatientDetailsTest() throws Exception {

		String drRegID = "722";
		String apptID = "1231728";
		String ptRegID = "pt2";

		MainResponseDTO<PatientDtls> responseWrapper = new MainResponseDTO<>();
		PatientDtls ptPersonalDtls = new PatientDtls();
		ptPersonalDtls.setBloodgrp("A+");
		ptPersonalDtls.setApptId(apptID);
		ptPersonalDtls.setHeight("5.6");
		ptPersonalDtls.setWeight("68");
		AddressDTO address = AddressDTO.builder().address1("om shanti").address2("indra lok").address3("bhayander")
				.build();
		ptPersonalDtls.setAddress(address);
		PtLifeStyleDtls ptLifeStyleDtls = new PtLifeStyleDtls();
		ptLifeStyleDtls.setDrinking("no");
		ptPersonalDtls.setPtLifeStyleDtls(ptLifeStyleDtls);
		PtMedicalDtls ptMedicalDtls = new PtMedicalDtls();
		ptMedicalDtls.setAllergies(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));

		ptPersonalDtls.setPtMedicalDtls(ptMedicalDtls);
		responseWrapper.setResponse(ptPersonalDtls);

		Mockito.when(patientService.getPatientDetails(apptID, drRegID, ptRegID)).thenReturn(ptPersonalDtls);
		MvcResult mvcResult = mockMvc
				.perform(get("/appointment/getPatientDetails").param("apptID", apptID).param("drRegID", drRegID)
						.param("ptRegID", ptRegID).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<PatientDtls> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		PatientDtls actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				PatientDtls.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(ptPersonalDtls);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getPatientDetailsNegativeTest() throws Exception {

		String drRegID = "722";
		MvcResult mvcResult = mockMvc.perform(
				get("/appointment/getPatientDetails").param("drRegID", drRegID).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<PatientDtls> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<ServiceError> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getListOfScribeByDoctorTest() throws Exception {

		String drRegID = "722";

		MainResponseDTO<ScribeDtlsDTO> responseWrapper = new MainResponseDTO<>();
		ScribeDtlsDTO scribeDtlsDTO = new ScribeDtlsDTO();
		scribeDtlsDTO.setDrRegId("722");

		List<ScribeDtls> scribeDtlsList = new ArrayList<>();
		ScribeDtls scribeDtls = ScribeDtls.builder().scrbEmailID("kulkarni01@gmail.com").scrbFullName("Manish Kulkarni")
				.scrbMobileNo("9878987898").build();
		scribeDtlsList.add(scribeDtls);
		scribeDtlsDTO.setScribeDtls(scribeDtlsList);

		responseWrapper.setResponse(scribeDtlsDTO);

		Mockito.when(consultationService.getListOfScribeByDoctor()).thenReturn(scribeDtlsDTO);
		MvcResult mvcResult = mockMvc.perform(get("/appointment/getListOfScribeByDoctor").param("drRegID", drRegID)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ScribeDtlsDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		ScribeDtlsDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				ScribeDtlsDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(scribeDtlsDTO);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getListOfScribeByDoctorNegativeTest() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(get("/appointment/getListOfScribeByDoctor").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ScribeDtlsDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<ServiceError> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void saveConsultationChiefComplaintTest() throws Exception {

		MainRequestDTO<ConsultationDTO<ChiefComplaintsDtls>> request = new MainRequestDTO<>();
		ConsultationDTO<ChiefComplaintsDtls> consultationDTO = new ConsultationDTO<>();
		consultationDTO.setAppointID("101");
		consultationDTO.setTabID("chiefComp");
		List<ChiefComplaintsDtls> data = new ArrayList<>();
		ChiefComplaintsDtls complaintsDtls = new ChiefComplaintsDtls();
		complaintsDtls.setDuration("3 days");
		complaintsDtls.setNote("");
		complaintsDtls.setSeverity("medium");
		complaintsDtls.setSymptoms("Dry Skin");
		data.add(complaintsDtls);
		consultationDTO.setData(data);
		request.setRequest(consultationDTO);

		ConsultationResponseDTO response = new ConsultationResponseDTO("chief complaint details saved successfully");

		Mockito.when(consultationService.saveConsultationChiefComplaint(consultationDTO)).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/saveConsultationChiefComplaint").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationResponseDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		ConsultationResponseDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				ConsultationResponseDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void saveConsultationChiefComplaintNegativeTest() throws Exception {

		MainRequestDTO<ConsultationDTO<ChiefComplaintsDtls>> request = new MainRequestDTO<>();
		ConsultationDTO<ChiefComplaintsDtls> consultationDTO = new ConsultationDTO<>();
		consultationDTO.setAppointID("");
		consultationDTO.setTabID("");
		List<ChiefComplaintsDtls> data = new ArrayList<>();
		ChiefComplaintsDtls complaintsDtls = new ChiefComplaintsDtls();
		complaintsDtls.setDuration("3 days");
		complaintsDtls.setNote("");
		complaintsDtls.setSeverity("medium");
		complaintsDtls.setSymptoms("Dry Skin");
		data.add(complaintsDtls);
		consultationDTO.setData(data);
		request.setRequest(consultationDTO);

		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/saveConsultationChiefComplaint").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationResponseDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<ServiceError> errors = responseWrapperActual.getErrors();
		assertEquals(2, errors.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getConsultationChiefComplaintTest() throws Exception {

		String apptID = "736424";

		MainResponseDTO<ConsultationDTO<ChiefComplaintsDtls>> response = new MainResponseDTO<>();
		ConsultationDTO<ChiefComplaintsDtls> consultationDTO = new ConsultationDTO<>();
		consultationDTO.setAppointID("101");
		consultationDTO.setTabID("chiefComp");
		List<ChiefComplaintsDtls> data = new ArrayList<>();
		ChiefComplaintsDtls complaintsDtls = new ChiefComplaintsDtls();
		complaintsDtls.setDuration("3 days");
		complaintsDtls.setNote("");
		complaintsDtls.setSeverity("medium");
		complaintsDtls.setSymptoms("Dry Skin");
		data.add(complaintsDtls);
		consultationDTO.setData(data);

		response.setResponse(consultationDTO);

		Mockito.when(consultationService.getConsultationChiefComplaint(apptID)).thenReturn(consultationDTO);
		MvcResult mvcResult = mockMvc.perform(get("/appointment/getConsultationChiefComplaint").param("apptID", apptID)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationDTO<ChiefComplaintsDtls>> responseWrapperActual = objectMapper
				.readValue(resultContent, MainResponseDTO.class);
		ConsultationDTO<ChiefComplaintsDtls> consultationDto = objectMapper
				.convertValue(responseWrapperActual.getResponse(), ConsultationDTO.class);
		List<ChiefComplaintsDtls> actualResponse = objectMapper.convertValue(consultationDto.getData(),
				new TypeReference<List<ChiefComplaintsDtls>>() {
				});
		assertEquals(consultationDto.getTabID(), consultationDTO.getTabID());
		assertThat(actualResponse).containsExactlyInAnyOrderElementsOf(consultationDTO.getData());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getConsultationChiefComplaintNegativeTest() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(get("/appointment/getConsultationChiefComplaint").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationDTO<ChiefComplaintsDtls>> responseWrapperActual = objectMapper
				.readValue(resultContent, MainResponseDTO.class);
		List<ServiceError> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void savePrescriptionDetailsTest() throws Exception {

		MainRequestDTO<ConsultPresDTO> request = new MainRequestDTO<>();
		ConsultPresDTO consultPresDTO = new ConsultPresDTO();
		consultPresDTO.setAppointID("23244");
		consultPresDTO.setFilePath("prescription");
		request.setRequest(consultPresDTO);

		ConsultationResponseDTO response = new ConsultationResponseDTO(
				"Prescription saved successfully and send for verification");

		Mockito.when(consultationService.savePrescriptionDetails(consultPresDTO)).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/savePrescriptionDetails").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationResponseDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		ConsultationResponseDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				ConsultationResponseDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void savePrescriptionDetailsNegativeTest() throws Exception {

		MainRequestDTO<ConsultPresDTO> request = new MainRequestDTO<>();
		ConsultPresDTO consultPresDTO = new ConsultPresDTO();
		consultPresDTO.setAppointID(null);
		consultPresDTO.setFilePath("prescriptionPath");
		request.setRequest(consultPresDTO);

		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/savePrescriptionDetails").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationResponseDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<ServiceError> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void saveConsultationDiagnosisTest() throws Exception {

		MainRequestDTO<ConsultationDTO<String>> request = new MainRequestDTO<>();
		ConsultationDTO<String> consultationDTO = new ConsultationDTO<String>();
		consultationDTO.setAppointID("101");
		consultationDTO.setTabID("prescription");
		List<String> data = Arrays.asList("migrane", "fever");
		consultationDTO.setData(data);

		request.setRequest(consultationDTO);

		ConsultationResponseDTO response = new ConsultationResponseDTO("Diagnosis details saved successfully");

		Mockito.when(consultationService.saveConsultationDiagnosisService(consultationDTO)).thenReturn(response);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/saveConsultationDiagnosis").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationResponseDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		ConsultationResponseDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				ConsultationResponseDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(response);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void saveConsultationDiagnosisNegativeTest() throws Exception {

		MainRequestDTO<ConsultationDTO<String>> request = new MainRequestDTO<>();
		ConsultationDTO<String> consultationDTO = new ConsultationDTO<String>();
		consultationDTO.setAppointID("");
		consultationDTO.setTabID("prescription");
		List<String> data = Arrays.asList("migrane", "fever");
		consultationDTO.setData(data);

		request.setRequest(consultationDTO);

		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/saveConsultationDiagnosis").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationResponseDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<ServiceError> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getConsultationDiagnosisDtlsTest() throws Exception {

		String appointID = "736424";

		MainResponseDTO<ConsultationDTO<String>> response = new MainResponseDTO<>();
		ConsultationDTO<String> consultationDTO = new ConsultationDTO<String>();
		consultationDTO.setAppointID("736424");
		consultationDTO.setTabID("prescription");
		List<String> data = Arrays.asList("migrane", "fever");
		consultationDTO.setData(data);

		response.setResponse(consultationDTO);

		Mockito.when(consultationService.getConsultationDiagnosisService(appointID)).thenReturn(consultationDTO);
		MvcResult mvcResult = mockMvc.perform(get("/appointment/getConsultationDiagnosisDtls")
				.param("appointID", appointID).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationDTO<String>> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		ConsultationDTO<String> actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				ConsultationDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(response.getResponse());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getConsultationDiagnosisDtlsNegativeTest() throws Exception {

		MvcResult mvcResult = mockMvc
				.perform(get("/appointment/getConsultationDiagnosisDtls").contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationDTO<String>> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		List<ServiceError> errors = responseWrapperActual.getErrors();
		assertEquals(1, errors.size());
	}

}
*/