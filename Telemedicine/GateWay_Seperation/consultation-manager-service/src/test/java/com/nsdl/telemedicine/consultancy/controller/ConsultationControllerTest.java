package com.nsdl.telemedicine.consultancy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

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
import com.nsdl.telemedicine.consultancy.dto.AppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationDtlDTO;
import com.nsdl.telemedicine.consultancy.dto.DrAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.MainRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.MainResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.PatientReportDTO;
import com.nsdl.telemedicine.consultancy.dto.PrescriptionDetailsDTO;
import com.nsdl.telemedicine.consultancy.dto.PtAppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtls;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtlsDTO;
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
	
	ConsultationDtlDTO consultationDtlCommonDTO = new ConsultationDtlDTO();
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		consultationDtlCommonDTO.setCtApptId("2021011905140457");
		consultationDtlCommonDTO.setCtDoctorId("MONIKADOC1");
		consultationDtlCommonDTO.setCtPatientId("MONIKAPT");
		consultationDtlCommonDTO.setCtScribeId(null);
		consultationDtlCommonDTO.setCtChiefComplaints("Please insert chief complaints");
		consultationDtlCommonDTO.setCtDiagnosis("Fever,cold,cough,allergy,covid19");
		consultationDtlCommonDTO.setCtMedication("Paracitamol:1 dose:5mg,PanD:2 dosage:2mg,vicks500,citragen");
		consultationDtlCommonDTO.setCtAdvice("Please insert doctor advice");
		consultationDtlCommonDTO.setHandwrittenPrescription(null);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getAppointmentListByPatientIDTest() throws Exception {
		PtAppointmentDTO ptAppointmentDTO = new PtAppointmentDTO();
		ptAppointmentDTO.setPtRegID("PATIENTTEST1");
		List<AppointmentDTO> apptDtls = new ArrayList<>();
		AppointmentDTO appointmentDTO = new AppointmentDTO();
		appointmentDTO.setAppointmentDate("2020-03-21");
		appointmentDTO.setAppointmentID("12635127");
		appointmentDTO.setAppointmentTime("11.30");
		appointmentDTO.setDoctorName("AMOL");
		appointmentDTO.setPatientName("PATIENTTEST1");
		appointmentDTO.setStatus("Y");
		apptDtls.add(appointmentDTO);
		ptAppointmentDTO.setApptDtls(apptDtls);

		Mockito.when(appointmentService.getAppointmentListByPatientID()).thenReturn(ptAppointmentDTO);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/getAppointmentListByPatientID").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
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
	public void getConsultationListByDoctorIDTest() throws Exception {
		MainRequestDTO<AppointmentDTO> mainRequest = new MainRequestDTO<AppointmentDTO>();
		AppointmentDTO request = new AppointmentDTO();
		request.setAppointmentDate("25-02-2021");
		mainRequest.setRequest(request);
		
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
//		drAppointmentDTO.setApptDtls(apptDtlsMap);
		
		String jsonString = objectMapper.writeValueAsString(mainRequest);

		Mockito.when(appointmentService.getAppointmentListByDrID(Mockito.anyString())).thenReturn(drAppointmentDTO);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/getConsultationListByDoctorID")
				.content(jsonString).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
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
	public void getListOfScribeByDoctorTest() throws Exception {
		MainRequestDTO<String> mainRequest = new MainRequestDTO<String>();
		mainRequest.setAPI("get list of scribe by doctor");
		mainRequest.setVersion("v1.0");
		mainRequest.setRequest(null);

		ScribeDtlsDTO scribeDtlsDTO = new ScribeDtlsDTO();
		scribeDtlsDTO.setDrRegId("722");

		List<ScribeDtls> scribeDtlsList = new ArrayList<>();
		ScribeDtls scribeDtls = ScribeDtls.builder().scrbEmailID("kulkarni01@gmail.com").scrbFullName("Manish Kulkarni")
				.scrbMobileNo("9878987898").build();
		scribeDtlsList.add(scribeDtls);
		scribeDtlsDTO.setScribeDtls(scribeDtlsList);

		String jsonString = objectMapper.writeValueAsString(mainRequest);
		Mockito.when(consultationService.getListOfScribeByDoctor()).thenReturn(scribeDtlsDTO);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/getListOfScribeByDoctor").content(jsonString)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ScribeDtlsDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		ScribeDtlsDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				ScribeDtlsDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(scribeDtlsDTO);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void saveConsultationDtlsTest() throws Exception {
		MainRequestDTO<ConsultationDtlDTO> request = new MainRequestDTO<ConsultationDtlDTO>();
		request.setRequest(consultationDtlCommonDTO);

		MainResponseDTO mainResponseDto = new MainResponseDTO();
		mainResponseDto.setResponse("consultation Details Inserted Successfully..");

		Mockito.when(consultationService.saveConsultationDtls(request)).thenReturn(mainResponseDto);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/saveConsultationDtls").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(mainResponseDto);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getPrescriptionDetailsTest() throws Exception {
		MainRequestDTO<ConsultationDtlDTO> request = new MainRequestDTO<ConsultationDtlDTO>();
		ConsultationDtlDTO consultationDtlDTO = new ConsultationDtlDTO();
		consultationDtlDTO.setCtApptId("2021011905140457");
		consultationDtlDTO.setCtDoctorId("MONIKADOC1");
		consultationDtlDTO.setCtPatientId("MONIKAPT");
		request.setRequest(consultationDtlDTO);
		
		MainResponseDTO<PrescriptionDetailsDTO> responseWrapper = new MainResponseDTO<PrescriptionDetailsDTO>();
		PrescriptionDetailsDTO prescriptionDetailsDTO = new PrescriptionDetailsDTO();
		prescriptionDetailsDTO.setInfo("prescription details api");
		prescriptionDetailsDTO.setPdfData(DatatypeConverter.parseBase64Binary("abcdefghijklmnopqrs"));
		prescriptionDetailsDTO.setPdfpath("D:\\var\\telemedicine\\documents\\prescription\\girish.pdf");
		responseWrapper.setResponse(prescriptionDetailsDTO);
		
		Mockito.when(consultationService.getPrescriptionDetails(Mockito.any(ConsultationDtlDTO.class))).thenReturn(prescriptionDetailsDTO);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/getPrescriptionDetails").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<PrescriptionDetailsDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		PrescriptionDetailsDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				PrescriptionDetailsDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(prescriptionDetailsDTO);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getConsultationDetails() throws Exception {
		MainRequestDTO<ConsultationDtlDTO> mainRequest = new MainRequestDTO<ConsultationDtlDTO>();
		ConsultationDtlDTO request = new ConsultationDtlDTO();
		request.setCtApptId("2021011905140457");
		request.setCtDoctorId("MONIKADOC1");
		request.setCtPatientId("MONIKAPT");
		mainRequest.setRequest(request);
		
		Mockito.when(consultationService.getConsultationDetails(Mockito.any(ConsultationDtlDTO.class))).thenReturn(consultationDtlCommonDTO);
		String jsonRequest = objectMapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/getConsultationDetails").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ConsultationDtlDTO> responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		ConsultationDtlDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
				ConsultationDtlDTO.class);
		assertThat(actualResponse).isEqualToComparingFieldByField(consultationDtlCommonDTO);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void updateConsultationStatusTest() throws Exception {
		MainRequestDTO<ConsultationDtlDTO> mainRequest = new MainRequestDTO<ConsultationDtlDTO>();
		ConsultationDtlDTO request = new ConsultationDtlDTO();
		request.setCtApptId("2021011905140457");
		request.setCtDoctorId("MONIKADOC1");
		request.setCtPatientId("MONIKAPT");
		request.setCtAppointmentStatus("completed");
		request.setCtPrescriptionPath("D:\\var\\telemedicine\\documents\\prescription\\girish.pdf");
		mainRequest.setRequest(request);

		MainResponseDTO mainResponseDto = new MainResponseDTO();
		mainResponseDto.setResponse("consultation Details updated Successfully..");

		Mockito.when(consultationService.updateConsultationStatus(Mockito.any(MainRequestDTO.class))).thenReturn(mainResponseDto);
		String jsonRequest = objectMapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/updateConsultationStatus").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO responseWrapperActual = objectMapper.readValue(resultContent,
				MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(mainResponseDto);
	}
	
	@SuppressWarnings({"rawtypes"})
	@Test
	public void getCountOfConsultation() throws Exception {
		Long count = 5L;
		MainRequestDTO<String> mainRequest = new MainRequestDTO<String>();
		MainResponseDTO<Long> mainResponseDto = new MainResponseDTO<Long>();
		mainResponseDto.setResponse(count);

		Mockito.when(consultationService.getCountOfConsultation()).thenReturn(mainResponseDto.getResponse());
		String jsonRequest = objectMapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/getCountOfConsultation").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<Long> responseWrapperActual = objectMapper.readValue(resultContent,MainResponseDTO.class);
		Long actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),new TypeReference<Long>() {});
		assertThat(actualResponse).isEqualTo(mainResponseDto.getResponse());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void uploadPrescriptionTemplateTest() throws Exception {
		MainRequestDTO<ConsultationDtlDTO> mainRequest = new MainRequestDTO<ConsultationDtlDTO>();
		ConsultationDtlDTO request = new ConsultationDtlDTO();
		request.setCtDoctorId("MONIKADOC1");
		request.setTemplateHeader("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
		request.setTemplateFooter("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
		mainRequest.setRequest(request);

		MainResponseDTO mainResponseDto = new MainResponseDTO();
		mainResponseDto.setResponse("Prescription Template uploaded successfully..");

		Mockito.when(consultationService.uploadPrescriptionTemplate(Mockito.any(MainRequestDTO.class))).thenReturn(mainResponseDto);
		String jsonRequest = objectMapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/uploadPrescriptionTemplate").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO responseWrapperActual = objectMapper.readValue(resultContent,MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(mainResponseDto);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void downloadDocument() throws Exception {
		MainRequestDTO<AppointmentDTO> mainRequest = new MainRequestDTO<AppointmentDTO>();
		AppointmentDTO request = new AppointmentDTO();
		request.setAppointmentID("2021011905140457");
		mainRequest.setRequest(request);

		MainResponseDTO<List<PatientReportDTO>> responseWrapper = new MainResponseDTO<List<PatientReportDTO>>();
		List<PatientReportDTO> patientReportList = new ArrayList<PatientReportDTO>();
		for(int i=1;i<=2;i++) {
			PatientReportDTO patientReportDTO = new PatientReportDTO();
			patientReportDTO.setReportname("Report"+i);
			patientReportDTO.setReportpath("D:\\var\\telemedicine\\documents\\prescription\\Report"+i+".pdf");
			patientReportDTO.setReport("data:application/pdf;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
			patientReportDTO.setReporttype("type"+i);
			patientReportDTO.setMimetype("application/pdf");
			patientReportList.add(patientReportDTO);
		}
		responseWrapper.setResponse(patientReportList);
		
		Mockito.when(consultationService.getPatientReportDetails(Mockito.any(MainRequestDTO.class))).thenReturn(responseWrapper);
		String jsonRequest = objectMapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc.perform(post("/appointment/downloadPatientReport").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO responseWrapperActual = objectMapper.readValue(resultContent,MainResponseDTO.class);
		List<PatientReportDTO> actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),new TypeReference<List<PatientReportDTO>>() {});
		assertThat(actualResponse).isEqualTo(responseWrapper.getResponse());
	}
}
