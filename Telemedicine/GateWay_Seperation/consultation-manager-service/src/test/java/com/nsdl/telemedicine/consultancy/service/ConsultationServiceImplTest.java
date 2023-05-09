package com.nsdl.telemedicine.consultancy.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import com.nsdl.telemedicine.consultancy.dto.AppointmentDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationDtlDTO;
import com.nsdl.telemedicine.consultancy.dto.MainRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.MainResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.PatientReportDTO;
import com.nsdl.telemedicine.consultancy.dto.PrescriptionDetailsDTO;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtls;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtlsDTO;
import com.nsdl.telemedicine.consultancy.dto.UserDTO;
import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultationDtl;
import com.nsdl.telemedicine.consultancy.entity.DocMstrDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientReportUploadDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PrescriptionTemplateDetails;
import com.nsdl.telemedicine.consultancy.entity.ScribeRegEntity;
import com.nsdl.telemedicine.consultancy.entity.audit.AuditConsultationDtl;
import com.nsdl.telemedicine.consultancy.exception.ConsultationServiceException;
import com.nsdl.telemedicine.consultancy.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationDtlsAuditedRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.DocMstrDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.DocRegDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.PatientReportUploadDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.PrescriptionTemplateRepo;
import com.nsdl.telemedicine.consultancy.repository.audit.AppointmentDtlsAuditRepository;
import com.nsdl.telemedicine.consultancy.service.impl.ConsultationServiceImpl;
import com.nsdl.telemedicine.consultancy.utility.PdfGenerator;

/**
 * @author Pegasus_girishk
 *
 */
//@RunWith(SpringRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ConsultationServiceImpl.class })
public class ConsultationServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	private ConsultationServiceImpl consultationServiceImpl;

	@Mock
	private DocMstrDtlsRepository docMstrDtlsRepository;

	@Mock
	private AppointmentDtlsRepository appointmentDtlsRepository;
	
	@Mock
	private AppointmentDtlsRepository appointmentRepo;

	@Mock
	private DocRegDtlsRepository docRegDtlsRepository;

	@Mock
	private PdfGenerator pdfGenerator;
	
	@Mock
	private ConsultationDtlsRepository consultationDtlsRepository;
	
	@Mock
	private ConsultationDtlsAuditedRepository consultationDtlsAuditedRepository;
	
	@Mock
	private PrescriptionTemplateRepo prescriptionTemplateRepo;
	
	@MockBean
	private AppointmentDtlsAuditRepository appointmentDtlsAuditRepository;
	
	@Mock
	private PatientReportUploadDtlsRepository patientReportUploadDtlsRepository;
	
	UserDTO userDetails = new UserDTO();

	DateTimeFormatter sdfo = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Before
	public void setUp() throws ParseException {
		userDetails.setUserName("722");
		ReflectionTestUtils.setField(consultationServiceImpl, "userDetails",userDetails);
	}

	@Test
	public void getListOfScribeByDoctorTest() throws Exception {

		ScribeDtlsDTO scribeDtlsDTO = new ScribeDtlsDTO();
		scribeDtlsDTO.setDrRegId("722");

		List<ScribeDtls> scribeDtlsList = new ArrayList<>();
		ScribeDtls scribeDtls = ScribeDtls.builder().scrbEmailID("kulkarni01@gmail.com").scrbFullName("Manish Kulkarni")
				.scrbMobileNo("9878987898").build();
		scribeDtlsList.add(scribeDtls);
		scribeDtlsDTO.setScribeDtls(scribeDtlsList);

		DocMstrDtlsEntity docMstrDtls = new DocMstrDtlsEntity();
		List<ScribeRegEntity> scribeEntityList = new ArrayList<>();
		ScribeRegEntity scribeRegEntity = new ScribeRegEntity();
		scribeRegEntity.setSrdEmail("kulkarni01@gmail.com");
		scribeRegEntity.setSrdMobileNo(9878987898L);
		scribeRegEntity.setSrdScrbName("Manish Kulkarni");
		scribeEntityList.add(scribeRegEntity);
		docMstrDtls.setScribeRegEntity(scribeEntityList);

		Mockito.when(docMstrDtlsRepository.findByDmdUserId(Mockito.anyString())).thenReturn(docMstrDtls);
		assertThat(scribeDtlsDTO).isEqualToComparingFieldByField(consultationServiceImpl.getListOfScribeByDoctor());
	}

	@Test(expected = ConsultationServiceException.class)
	public void getListOfScribeByDoctorNegativeTest() throws Exception {
		ScribeDtlsDTO scribeDtlsDTO = new ScribeDtlsDTO();
		scribeDtlsDTO.setDrRegId("722");

		List<ScribeDtls> scribeDtlsList = new ArrayList<>();
		ScribeDtls scribeDtls = ScribeDtls.builder().scrbEmailID("kulkarni01@gmail.com").scrbFullName("Manish Kulkarni")
				.scrbMobileNo("9878987898").build();
		scribeDtlsList.add(scribeDtls);
		scribeDtlsDTO.setScribeDtls(scribeDtlsList);

		DocMstrDtlsEntity docMstrDtls = null; //condition for negative testing.

		Mockito.when(docMstrDtlsRepository.findByDmdUserId(Mockito.anyString())).thenReturn(docMstrDtls);

		assertThat(scribeDtlsDTO).isEqualToComparingFieldByField(consultationServiceImpl.getListOfScribeByDoctor());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void saveConsultationDtlsTest() {
		MainRequestDTO<ConsultationDtlDTO> consultationRequest = new MainRequestDTO<ConsultationDtlDTO>();
		ConsultationDtlDTO consultationDtlDTO = new ConsultationDtlDTO();
		consultationDtlDTO.setCtApptId("2021011905140457");
		consultationDtlDTO.setCtDoctorId("MONIKADOC1");
		consultationDtlDTO.setCtPatientId("MONIKAPT");
		consultationDtlDTO.setCtAdvice("drink plenty of water");
		consultationDtlDTO.setCtChiefComplaints("complaints");
		consultationDtlDTO.setCtDiagnosis("Fever");
		consultationDtlDTO.setCtMedication("Paracetamol");
		consultationRequest.setRequest(consultationDtlDTO);
		
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		mainResponseDTO.setResponse("consultation Details Inserted Successfully..");
		mainResponseDTO.setStatus(true);
		
		ConsultationDtl persistedConsultationDtlsEntity = new ConsultationDtl();
		AuditConsultationDtl consultationDtlsAudited = new AuditConsultationDtl();
		
		Mockito.when(consultationDtlsRepository.checkConsultationDtlsExist(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(null);
		Mockito.when(consultationDtlsRepository.save(Mockito.any(ConsultationDtl.class))).thenReturn(persistedConsultationDtlsEntity);
		Mockito.when(consultationDtlsAuditedRepository.save(Mockito.any(AuditConsultationDtl.class))).thenReturn(consultationDtlsAudited);
		assertThat(mainResponseDTO).isEqualToComparingFieldByField(consultationServiceImpl.saveConsultationDtls(consultationRequest));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test(expected = ConsultationServiceException.class)
	public void saveConsultationDtlsNegativeTest() {
		MainRequestDTO<ConsultationDtlDTO> consultationRequest = new MainRequestDTO<ConsultationDtlDTO>();
		consultationRequest.setRequest(null);
		
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		mainResponseDTO.setResponse("consultation Details Inserted Successfully..");
		mainResponseDTO.setStatus(true);
		
		ConsultationDtl persistedConsultationDtlsEntity = new ConsultationDtl();
		AuditConsultationDtl consultationDtlsAudited = new AuditConsultationDtl();
		
		Mockito.when(consultationDtlsRepository.checkConsultationDtlsExist(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenReturn(null);
		Mockito.when(consultationDtlsRepository.save(Mockito.any(ConsultationDtl.class))).thenReturn(persistedConsultationDtlsEntity);
		Mockito.when(consultationDtlsAuditedRepository.save(Mockito.any(AuditConsultationDtl.class))).thenReturn(consultationDtlsAudited);
		assertThat(mainResponseDTO).isEqualToComparingFieldByField(consultationServiceImpl.saveConsultationDtls(consultationRequest));
	}
	
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
		prescriptionDetailsDTO.setPdfData(null);
		prescriptionDetailsDTO.setPdfpath("D:\\var\\telemedicine\\documents\\prescription\\2021011905140457.pdf");
		responseWrapper.setResponse(prescriptionDetailsDTO);
		
		ConsultationDtl consultationDtl = new ConsultationDtl();
		consultationDtl.setCtAdvice("drink plenty of water");
		consultationDtl.setCtChiefComplaints("complaints");
		consultationDtl.setCtDiagnosis("Fever");
		consultationDtl.setCtMedication("Paracetamol");
		
		AppointmentDtlsEntity appointmentEntity = new AppointmentDtlsEntity();
		appointmentEntity.setAdApptId("2021011905140457");
		appointmentEntity.setAdCreatedTmstmp(LocalDateTime.now());
		PatientRegDtlsEntity patientRegDtls = new PatientRegDtlsEntity();
		patientRegDtls.setPrdName("MONIKAPT");
		patientRegDtls.setPrdDOB(LocalDate.now());
		patientRegDtls.setPrdMobileNo("7898765434");
		patientRegDtls.setPrdGender("F");
		patientRegDtls.setPrdAddress1("PUNE");
		patientRegDtls.setPrdEmail("MONIKAPT@GMAIL.COM");
		appointmentEntity.setPatientRegDtlsEntity(patientRegDtls);
		
		DocMstrDtlsEntity docMstrDtlsEntity = new DocMstrDtlsEntity();
		docMstrDtlsEntity.setDmdDrName("AMOL");
		docMstrDtlsEntity.setDmdSpecialiazation("MD");
		docMstrDtlsEntity.setDmdSmcNumber("SMC12345");
		docMstrDtlsEntity.setDmdMciNumber("MCI12345");
		docMstrDtlsEntity.setDmdMobileNo(9876545654L);
		docMstrDtlsEntity.setDmdEmail("MONIKA@GMAIL.COM");
		appointmentEntity.setDocMstrDtlsEntity(docMstrDtlsEntity);
		
		Mockito.when(appointmentDtlsRepository.findByAdApptId(request.getRequest().getCtApptId())).thenReturn(appointmentEntity);
		Mockito.when(consultationDtlsRepository.checkConsultationDtlsExist(Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase())).thenReturn(consultationDtl);
		Mockito.when(prescriptionTemplateRepo.checkIfPrescriptionTempAvailable(Mockito.anyString().toUpperCase())).thenReturn(null);
		Mockito.when(pdfGenerator.generateDefaultPrescriptionPDF(Mockito.anyMap())).thenReturn("D:\\var\\telemedicine\\documents\\prescription\\2021011905140457.pdf");
//		assertThat(prescriptionDetailsDTO).isEqualTo(consultationServiceImpl.getPrescriptionDetails(request.getRequest()));
		assertThat(prescriptionDetailsDTO).isExactlyInstanceOf(consultationServiceImpl.getPrescriptionDetails(request.getRequest()).getClass());
	}
	
	@Test(expected = ConsultationServiceException.class)
	public void getPrescriptionDetailsNegativeTest() throws Exception {
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
		prescriptionDetailsDTO.setPdfpath("D:\\var\\telemedicine\\documents\\prescription\\2021011905140457.pdf");
		responseWrapper.setResponse(prescriptionDetailsDTO);
		
		ConsultationDtl consultationDtl = null; //condition for negative testing.
		
		AppointmentDtlsEntity appointmentEntity = new AppointmentDtlsEntity();
		appointmentEntity.setAdApptId("2021011905140457");
		appointmentEntity.setAdCreatedTmstmp(LocalDateTime.now());
		PatientRegDtlsEntity patientRegDtls = new PatientRegDtlsEntity();
		patientRegDtls.setPrdName("MONIKAPT");
		patientRegDtls.setPrdDOB(LocalDate.now());
		patientRegDtls.setPrdMobileNo("7898765434");
		patientRegDtls.setPrdGender("F");
		patientRegDtls.setPrdAddress1("PUNE");
		patientRegDtls.setPrdEmail("MONIKAPT@GMAIL.COM");
		appointmentEntity.setPatientRegDtlsEntity(patientRegDtls);
		
		DocMstrDtlsEntity docMstrDtlsEntity = new DocMstrDtlsEntity();
		docMstrDtlsEntity.setDmdDrName("AMOL");
		docMstrDtlsEntity.setDmdSpecialiazation("MD");
		docMstrDtlsEntity.setDmdSmcNumber("SMC12345");
		docMstrDtlsEntity.setDmdMciNumber("MCI12345");
		docMstrDtlsEntity.setDmdMobileNo(9876545654L);
		docMstrDtlsEntity.setDmdEmail("MONIKA@GMAIL.COM");
		appointmentEntity.setDocMstrDtlsEntity(docMstrDtlsEntity);
		
		Mockito.when(appointmentDtlsRepository.findByAdApptId(request.getRequest().getCtApptId())).thenReturn(appointmentEntity);
		Mockito.when(consultationDtlsRepository.checkConsultationDtlsExist(Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase())).thenReturn(consultationDtl);
		Mockito.when(prescriptionTemplateRepo.checkIfPrescriptionTempAvailable(Mockito.anyString().toUpperCase())).thenReturn(null);
		Mockito.when(pdfGenerator.generateDefaultPrescriptionPDF(Mockito.anyMap())).thenReturn("D:\\var\\telemedicine\\documents\\prescription\\2021011905140457.pdf");
		assertThat(prescriptionDetailsDTO).isEqualToComparingFieldByField(consultationServiceImpl.getPrescriptionDetails(request.getRequest()));
	}
	
	@Test
	public void getConsultationDetailsTest() throws Exception {
		MainRequestDTO<ConsultationDtlDTO> mainRequest = new MainRequestDTO<ConsultationDtlDTO>();
		ConsultationDtlDTO request = new ConsultationDtlDTO();
		request.setCtApptId("2021011905140457");
		request.setCtDoctorId("MONIKADOC1");
		request.setCtPatientId("MONIKAPT");
		mainRequest.setRequest(request);
		
		ConsultationDtl consultationDtls = new ConsultationDtl();
		ConsultationDtlDTO consultationDtlsDTO = new ConsultationDtlDTO();
		
		Mockito.when(consultationDtlsRepository.checkConsultationDtlsExist(Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase())).thenReturn(consultationDtls);
		assertThat(consultationDtlsDTO).isEqualToComparingFieldByField(consultationServiceImpl.getConsultationDetails(mainRequest.getRequest()));
	}
	
	@Test(expected = ConsultationServiceException.class)
	public void getConsultationDetailsNegativeTest() throws Exception {
		MainRequestDTO<ConsultationDtlDTO> mainRequest = new MainRequestDTO<ConsultationDtlDTO>();
		ConsultationDtlDTO request = new ConsultationDtlDTO();
		request.setCtApptId("2021011905140457");
		request.setCtDoctorId("MONIKADOC1");
		request.setCtPatientId("MONIKAPT");
		mainRequest.setRequest(request);
		
		ConsultationDtl consultationDtls = null; //condition for negative testing.
		ConsultationDtlDTO consultationDtlsDTO = new ConsultationDtlDTO();
		
		Mockito.when(consultationDtlsRepository.checkConsultationDtlsExist(Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase())).thenReturn(consultationDtls);
		assertThat(consultationDtlsDTO).isEqualToComparingFieldByField(consultationServiceImpl.getConsultationDetails(mainRequest.getRequest()));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		mainResponseDto.setStatus(true);
		mainResponseDto.setResponse("Consultation Details updated Successfully..");
		
		ConsultationDtl consultationDtls = new ConsultationDtl();
		AppointmentDtlsEntity appointmentEntity = new AppointmentDtlsEntity();
		appointmentEntity.setAdIdPk(1L);
		
		Mockito.when(consultationDtlsRepository.checkConsultationDtlsExist(Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase())).thenReturn(consultationDtls);
		Mockito.when(appointmentDtlsRepository.findByAdApptId(mainRequest.getRequest().getCtApptId())).thenReturn(appointmentEntity);
		
		ConsultationServiceImpl consultationServiceSpy = PowerMockito.spy(consultationServiceImpl);
		PowerMockito.doNothing().when(consultationServiceSpy, "auditAppointmentData",ArgumentMatchers.any(AppointmentDtlsEntity.class));

		Mockito.when(appointmentRepo.save(appointmentEntity)).thenReturn(appointmentEntity);
		Mockito.when(consultationDtlsRepository.save(consultationDtls)).thenReturn(consultationDtls);
		assertThat(mainResponseDto.getResponse()).isEqualToComparingFieldByField(consultationServiceSpy.updateConsultationStatus(mainRequest).getResponse());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test(expected = ConsultationServiceException.class)
	public void updateConsultationStatusNegativeTest() throws Exception {
		MainRequestDTO<ConsultationDtlDTO> mainRequest = new MainRequestDTO<ConsultationDtlDTO>();
		ConsultationDtlDTO request = new ConsultationDtlDTO();
		request.setCtApptId("2021011905140457");
		request.setCtDoctorId("MONIKADOC1");
		request.setCtPatientId("MONIKAPT");
		request.setCtAppointmentStatus("completed");
		request.setCtPrescriptionPath("D:\\var\\telemedicine\\documents\\prescription\\girish.pdf");
		mainRequest.setRequest(request);

		MainResponseDTO mainResponseDto = new MainResponseDTO();
		mainResponseDto.setStatus(true);
		mainResponseDto.setResponse("Consultation Details updated Successfully..");
		
		ConsultationDtl consultationDtls = new ConsultationDtl();
		AppointmentDtlsEntity appointmentEntity = null; //condition for negative testing.
		Mockito.when(consultationDtlsRepository.checkConsultationDtlsExist(Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase(),Mockito.anyString().toLowerCase())).thenReturn(consultationDtls);
		Mockito.when(appointmentDtlsRepository.findByAdApptId(mainRequest.getRequest().getCtApptId())).thenReturn(appointmentEntity);
		
		ConsultationServiceImpl consultationServiceSpy = PowerMockito.spy(consultationServiceImpl);
		PowerMockito.doNothing().when(consultationServiceSpy, "auditAppointmentData",ArgumentMatchers.any(AppointmentDtlsEntity.class));

		Mockito.when(appointmentRepo.save(appointmentEntity)).thenReturn(appointmentEntity);
		Mockito.when(consultationDtlsRepository.save(consultationDtls)).thenReturn(consultationDtls);
		assertThat(mainResponseDto.getResponse()).isEqualTo(consultationServiceSpy.updateConsultationStatus(mainRequest).getResponse());
	}
	
	@Test
	public void getCountOfConsultation() throws Exception {
		Long count = 5L;
		Mockito.when(consultationDtlsRepository.getCountOfConsultation()).thenReturn(count);
		assertThat(count).isEqualToComparingFieldByField(consultationServiceImpl.getCountOfConsultation());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		mainResponseDto.setStatus(true);
		
		PrescriptionTemplateDetails prescriptionTemplateDetails = new PrescriptionTemplateDetails();
		
		ConsultationServiceImpl consultationServiceSpy = PowerMockito.spy(consultationServiceImpl);
		PowerMockito.doNothing().when(consultationServiceSpy, "uploadPrescriptionImages",ArgumentMatchers.anyString(),ArgumentMatchers.anyString(),ArgumentMatchers.anyString());
		Mockito.when(prescriptionTemplateRepo.save(prescriptionTemplateDetails)).thenReturn(prescriptionTemplateDetails);
		assertThat(mainResponseDto.getResponse()).isEqualToComparingFieldByField(consultationServiceSpy.uploadPrescriptionTemplate(mainRequest).getResponse());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void downloadDocumentTest() throws Exception {
		MainRequestDTO<AppointmentDTO> mainRequest = new MainRequestDTO<AppointmentDTO>();
		AppointmentDTO request = new AppointmentDTO();
		request.setAppointmentID("2021011905140457");
		mainRequest.setRequest(request);

		MainResponseDTO<List<PatientReportDTO>> responseWrapper = new MainResponseDTO<List<PatientReportDTO>>();
		List<PatientReportDTO> patientReportList = new ArrayList<PatientReportDTO>();
		PatientReportDTO patientReportDTO = new PatientReportDTO();
		patientReportDTO.setReportname("Report");
		patientReportDTO.setReportpath("D:\\var\\telemedicine\\documents\\prescription\\sample_Report.pdf");
		patientReportDTO.setReport("data:application/pdf;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
		patientReportDTO.setReporttype("type");
		patientReportDTO.setMimetype("application/pdf");
		patientReportList.add(patientReportDTO);
		responseWrapper.setResponse(patientReportList);
		
		List<PatientReportUploadDtlsEntity> patientReportUploadDtlsEntityList = new ArrayList<PatientReportUploadDtlsEntity>();
		PatientReportUploadDtlsEntity patientReportUploadDtlsEntity = new PatientReportUploadDtlsEntity();
		patientReportUploadDtlsEntity.setReportName("Report");
		patientReportUploadDtlsEntity.setPath("D:\\var\\telemedicine\\documents\\prescription\\sample_Report.pdf");
		patientReportUploadDtlsEntity.setReportType("type");
		patientReportUploadDtlsEntityList.add(patientReportUploadDtlsEntity);
		
		Mockito.when(patientReportUploadDtlsRepository.findByAurdApptIdFk(mainRequest.getRequest().getAppointmentID())).thenReturn(patientReportUploadDtlsEntityList);
		assertThat(responseWrapper.getResponse().size()).isEqualTo(consultationServiceImpl.getPatientReportDetails(mainRequest).getResponse().size());
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = ConsultationServiceException.class)
	public void downloadDocumentNegativeTest() throws Exception {
		MainRequestDTO<AppointmentDTO> mainRequest = new MainRequestDTO<AppointmentDTO>();
		AppointmentDTO request = new AppointmentDTO();
		request.setAppointmentID("2021011905140457");
		mainRequest.setRequest(request);

		MainResponseDTO<List<PatientReportDTO>> responseWrapper = new MainResponseDTO<List<PatientReportDTO>>();
		List<PatientReportDTO> patientReportList = new ArrayList<PatientReportDTO>();
		PatientReportDTO patientReportDTO = new PatientReportDTO();
		patientReportDTO.setReportname("Report");
		patientReportDTO.setReportpath("D:\\var\\telemedicine\\documents\\prescription\\Report.pdf");
		patientReportDTO.setReport("data:application/pdf;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
		patientReportDTO.setReporttype("type");
		patientReportDTO.setMimetype("application/pdf");
		patientReportList.add(patientReportDTO);
		responseWrapper.setResponse(patientReportList);
		
		List<PatientReportUploadDtlsEntity> patientReportUploadDtlsEntityList = null;
		
		Mockito.when(patientReportUploadDtlsRepository.findByAurdApptIdFk(mainRequest.getRequest().getAppointmentID())).thenReturn(patientReportUploadDtlsEntityList);
		assertThat(responseWrapper.getResponse()).isEqualTo(consultationServiceImpl.getPatientReportDetails(mainRequest).getResponse());
	}
}
