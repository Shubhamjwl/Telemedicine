/*package com.nsdl.telemedicine.consultancy.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.nsdl.telemedicine.consultancy.dto.ChiefComplaintsDtls;
import com.nsdl.telemedicine.consultancy.dto.ConsultPresDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationDTO;
import com.nsdl.telemedicine.consultancy.dto.ConsultationResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.MainRequestDTO;
import com.nsdl.telemedicine.consultancy.dto.MainResponseDTO;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtls;
import com.nsdl.telemedicine.consultancy.dto.ScribeDtlsDTO;
import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultAdvDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultCcDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultDiagDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ConsultMedicationDtls;
import com.nsdl.telemedicine.consultancy.entity.ConsultPriscpDtl;
import com.nsdl.telemedicine.consultancy.entity.DocMstrDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.DocRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.InvestigationDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.ScribeRegEntity;
import com.nsdl.telemedicine.consultancy.entity.audit.AuditConsultCcDtls;
import com.nsdl.telemedicine.consultancy.entity.audit.AuditConsultDiagDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.audit.AuditConsultPriscpDtl;
import com.nsdl.telemedicine.consultancy.exception.ConsultationServiceException;
import com.nsdl.telemedicine.consultancy.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultCcDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultDiagDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationMedicationRepository;
import com.nsdl.telemedicine.consultancy.repository.ConsultationPrescriptionRepo;
import com.nsdl.telemedicine.consultancy.repository.DocMstrDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.DocRegDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.InvestigationDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.audit.AuditConsultCcDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.audit.AuditConsultDiagDtlsRepository;
import com.nsdl.telemedicine.consultancy.repository.audit.AuditConsultPriscpDtlRepository;
import com.nsdl.telemedicine.consultancy.service.impl.ConsultationServiceImpl;
import com.nsdl.telemedicine.consultancy.utility.PdfGenerator;
import com.nsdl.telemedicine.gateway.config.UserDTO;

@RunWith(SpringRunner.class)
public class ConsultationServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	@Spy
	private ConsultationServiceImpl consultationServiceImpl;

	@Mock
	private DocMstrDtlsRepository docMstrDtlsRepository;

	@Mock
	private AppointmentDtlsRepository appointmentDtlsRepository;

	@Mock
	private DocRegDtlsRepository docRegDtlsRepository;

	@Mock
	private ConsultCcDtlsRepository consultCcDtlsRepository;

	@Mock
	private AuditConsultCcDtlsRepository consultCcDtlsAuditRepository;

	@Mock
	private ConsultationMedicationRepository consultationMedicationRepository;

	@Mock
	private UserDTO userDetails;

	@Mock
	private ConsultDiagDtlsRepository consultDiagnosisRepo;

	@Mock
	private InvestigationDtlsRepository investigationDtlsRepository;

	@Mock
	private PdfGenerator pdfGenerator;

	@Mock
	private ConsultationPrescriptionRepo consultationPrescriptionRepo;

	@Mock
	private AppointmentDtlsRepository appointmentRepo;

	@Mock
	private AuditConsultPriscpDtlRepository consultPriscpDtlAuditRepository;

	@Mock
	AuditConsultDiagDtlsRepository auditDiagnosisRepo;

	DateTimeFormatter sdfo = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	@Before
	public void setUp() throws ParseException {
		ReflectionTestUtils.setField(consultationServiceImpl, "chiefComplainResponseMsg",
				"chief complaint details saved successfully");
		ReflectionTestUtils.setField(consultationServiceImpl, "savePrescriptionResponseMsg",
				"Prescription saved successfully and send for verification");
		ReflectionTestUtils.setField(consultationServiceImpl, "diagnosisResponseMsg",
				"Diagnosis details saved successfully");
	}

	@Test
	public void getListOfScribeByDoctorTest() throws Exception {

		String drRegID = "722";

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

		assertThat(scribeDtlsDTO)
				.isEqualToComparingFieldByField(consultationServiceImpl.getListOfScribeByDoctor());
	}

	@Test(expected = ConsultationServiceException.class)
	public void getListOfScribeByDoctorNegativeTest() throws Exception {

		String drRegID = null;
		consultationServiceImpl.getListOfScribeByDoctor();
	}

	@Test
	public void saveConsultationChiefComplaintTest() throws Exception {

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

		ConsultationResponseDTO response = new ConsultationResponseDTO("chief complaint details saved successfully");

		AppointmentDtlsEntity appointmentDtlsEntity = new AppointmentDtlsEntity();
		appointmentDtlsEntity.setAdApptId("101");
		DocMstrDtlsEntity docMstrDtls = new DocMstrDtlsEntity();
		docMstrDtls.setDmdUserId("AMOL");
		appointmentDtlsEntity.setDocMstrDtlsEntity(docMstrDtls);

		DocRegDtlsEntity docRegDtls = new DocRegDtlsEntity();
		docRegDtls.setDrdUserId("AMOL");
		PatientRegDtlsEntity patientRegDtlsEntity = new PatientRegDtlsEntity();
		patientRegDtlsEntity.setPrdUserId("pt2");
		appointmentDtlsEntity.setPatientRegDtlsEntity(patientRegDtlsEntity);

		List<ConsultCcDtlsEntity> entityList = new ArrayList<>();
		ConsultCcDtlsEntity entity = new ConsultCcDtlsEntity();
		entity.setAppointmentDtlsEntity(appointmentDtlsEntity);
		entity.setDocRegDtlsEntity(docRegDtls);
		entity.setPatientRegDtlsEntity(patientRegDtlsEntity);
		entityList.add(entity);
		appointmentDtlsEntity.setConsultCcDetailsEntity(entityList);

		List<AuditConsultCcDtls> auditEntityList = new ArrayList<AuditConsultCcDtls>();

		Mockito.when(appointmentDtlsRepository.findByAdApptId(Mockito.anyString())).thenReturn(appointmentDtlsEntity);
		Mockito.when(docRegDtlsRepository.findByDrdUserId(Mockito.anyString())).thenReturn(docRegDtls);
		Mockito.when(
				consultCcDtlsRepository.findAllByCcdSymptomAndCccdApptIdFk(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(entity);
		Mockito.when(userDetails.getUserName()).thenReturn("nitin");
		Mockito.when(consultCcDtlsAuditRepository.saveAll(Mockito.any())).thenReturn(auditEntityList);
		Mockito.when(consultCcDtlsRepository.saveAll(Mockito.any())).thenReturn(entityList);

		assertThat(response).isEqualToComparingFieldByField(
				consultationServiceImpl.saveConsultationChiefComplaint(consultationDTO));
	}

	@Test(expected = ConsultationServiceException.class)
	public void saveConsultationChiefComplaintNegativeTest() throws Exception {

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

		AppointmentDtlsEntity appointmentDtlsEntity = null;

		Mockito.when(appointmentDtlsRepository.findByAdApptId(Mockito.anyString())).thenReturn(appointmentDtlsEntity);
		consultationServiceImpl.saveConsultationChiefComplaint(consultationDTO);
	}

	@Test
	public void getConsultationChiefComplaintTest() throws Exception {

		String apptID = "101";

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
		Mockito.when(consultCcDtlsRepository.findByCccdApptIdFk(Mockito.anyString())).thenReturn(data);

		assertThat(consultationDTO)
				.isEqualToComparingFieldByField(consultationServiceImpl.getConsultationChiefComplaint(apptID));
	}

	@Test(expected = ConsultationServiceException.class)
	public void getConsultationChiefComplaintNegativeTest() throws Exception {

		String apptID = "";
		consultationServiceImpl.getConsultationChiefComplaint(apptID);
	}

	@Test
	public void savePrescriptionDetailsTest() throws Exception {

		ConsultPresDTO consultPresDTO = new ConsultPresDTO();
		consultPresDTO.setAppointID("101");
		consultPresDTO.setFilePath("prescription");

		ConsultationResponseDTO response = new ConsultationResponseDTO(
				"Prescription saved successfully and send for verification");

		AppointmentDtlsEntity appointmentEntity = new AppointmentDtlsEntity();
		List<ConsultMedicationDtls> consultMedicationList = new ArrayList<>();
		List<ConsultCcDtlsEntity> consultCcList = new ArrayList<>();
		List<ConsultAdvDtlsEntity> consultAdvList = new ArrayList<>();
		List<ConsultDiagDtlsEntity> consultDiagList = new ArrayList<>();
		List<InvestigationDtlsEntity> consultInvsList = new ArrayList<>();
		ConsultPriscpDtl consultPriscpDtl = new ConsultPriscpDtl();
		AuditConsultPriscpDtl consultPriscpDtlAudit = new AuditConsultPriscpDtl();

		appointmentEntity.setAdApptId("101");
		appointmentEntity.setConsultCcDetailsEntity(consultCcList);
		appointmentEntity.setConsultAdvDtlsEntities(consultAdvList);
		appointmentEntity.setAdCreatedTmstmp(LocalDateTime.parse("2020-11-17 12:30", sdfo));

		DocRegDtlsEntity docRegDtls = new DocRegDtlsEntity();
		docRegDtls.setDrdUserId("722");
		docRegDtls.setDrdDrName("AMOL");

		DocMstrDtlsEntity docMstrDtlsEntity = new DocMstrDtlsEntity();
		docMstrDtlsEntity.setDmdDrName("AMOL");
		docMstrDtlsEntity.setDmdUserId("722");
		appointmentEntity.setDocMstrDtlsEntity(docMstrDtlsEntity);
		PatientRegDtlsEntity patientRegDtlsEntity = new PatientRegDtlsEntity();
		patientRegDtlsEntity.setPrdUserId("pt2");
		patientRegDtlsEntity.setPrdName("pt2");
		patientRegDtlsEntity.setPrdEmail("amol@nsdl.com");
		patientRegDtlsEntity.setPrdMobileNo("9833474747");
		appointmentEntity.setPatientRegDtlsEntity(patientRegDtlsEntity);
		consultPriscpDtl.setAppointmentDtlsEntity(appointmentEntity);

		Mockito.when(appointmentDtlsRepository.findByAdApptId(Mockito.anyString())).thenReturn(appointmentEntity);
		Mockito.when(consultationMedicationRepository.findAllByCmdApptIdFk(Mockito.anyString()))
				.thenReturn(consultMedicationList);
		Mockito.when(consultDiagnosisRepo.findByAppointId(Mockito.anyString())).thenReturn(consultDiagList);
		Mockito.when(investigationDtlsRepository.findByCcidApptIdFk(Mockito.anyString())).thenReturn(consultInvsList);
		Mockito.when(pdfGenerator.generatePDF(Mockito.any(), Mockito.any())).thenReturn("\\home");
		Mockito.when(consultationPrescriptionRepo.findByAdApptId(Mockito.anyString())).thenReturn(consultPriscpDtl);
		Mockito.when(userDetails.getUserName()).thenReturn("nitin");
		Mockito.when(consultPriscpDtlAuditRepository.save(Mockito.any())).thenReturn(consultPriscpDtlAudit);
		Mockito.when(consultationPrescriptionRepo.saveAndFlush(Mockito.any())).thenReturn(consultPriscpDtl);

		assertThat(response)
				.isEqualToComparingFieldByField(consultationServiceImpl.savePrescriptionDetails(consultPresDTO));
	}

	@Test(expected = ConsultationServiceException.class)
	public void savePrescriptionDetailsNegativeTest() throws Exception {

		ConsultPresDTO consultPresDTO = new ConsultPresDTO();
		consultPresDTO.setAppointID("");
		consultPresDTO.setFilePath("prescription");

		consultationServiceImpl.savePrescriptionDetails(consultPresDTO);
	}

	@Test
	public void saveConsultationDiagnosisServiceTest() throws Exception {

		MainRequestDTO<ConsultationDTO<String>> request = new MainRequestDTO<>();
		ConsultationDTO<String> consultationDTO = new ConsultationDTO<String>();
		consultationDTO.setAppointID("101");
		consultationDTO.setTabID("prescription");
		List<String> data = Arrays.asList("migrane", "fever");
		consultationDTO.setData(data);

		request.setRequest(consultationDTO);

		ConsultationResponseDTO response = new ConsultationResponseDTO("Diagnosis details saved successfully");

		AppointmentDtlsEntity appointmentDtlsEntity = new AppointmentDtlsEntity();
		appointmentDtlsEntity.setAdApptId("101");

		DocMstrDtlsEntity docMstrDtlsEntity = new DocMstrDtlsEntity();
		docMstrDtlsEntity.setDmdDrName("AMOL");
		docMstrDtlsEntity.setDmdUserId("722");
		appointmentDtlsEntity.setDocMstrDtlsEntity(docMstrDtlsEntity);

		DocRegDtlsEntity docRegDtls = new DocRegDtlsEntity();
		docRegDtls.setDrdUserId("AMOL");
		PatientRegDtlsEntity patientRegDtlsEntity = new PatientRegDtlsEntity();
		patientRegDtlsEntity.setPrdUserId("pt2");
		appointmentDtlsEntity.setPatientRegDtlsEntity(patientRegDtlsEntity);

		List<ConsultDiagDtlsEntity> diagnosisEntityList = new ArrayList<ConsultDiagDtlsEntity>();
		List<AuditConsultDiagDtlsEntity> auditdiagnosisEntityList = new ArrayList<AuditConsultDiagDtlsEntity>();

		Mockito.when(appointmentRepo.findByAdApptId(Mockito.anyString())).thenReturn(appointmentDtlsEntity);
		Mockito.when(docRegDtlsRepository.findByDrdUserId(Mockito.anyString())).thenReturn(docRegDtls);
		Mockito.when(userDetails.getUserName()).thenReturn("nitin");
		Mockito.when(auditDiagnosisRepo.saveAll(Mockito.any())).thenReturn(auditdiagnosisEntityList);
		Mockito.when(consultDiagnosisRepo.saveAll(Mockito.any())).thenReturn(diagnosisEntityList);

		assertThat(response).isEqualToComparingFieldByField(
				consultationServiceImpl.saveConsultationDiagnosisService(consultationDTO));
	}

	@Test(expected = ConsultationServiceException.class)
	public void saveConsultationDiagnosisServiceNegativeTest() throws Exception {

		MainRequestDTO<ConsultationDTO<String>> request = new MainRequestDTO<>();
		ConsultationDTO<String> consultationDTO = new ConsultationDTO<String>();
		consultationDTO.setAppointID("101");
		consultationDTO.setTabID("prescription");
		List<String> data = Arrays.asList("migrane", "fever");
		consultationDTO.setData(data);

		request.setRequest(consultationDTO);

		AppointmentDtlsEntity appointmentDtlsEntity = new AppointmentDtlsEntity();
		appointmentDtlsEntity.setAdApptId("101");

		DocMstrDtlsEntity docMstrDtlsEntity = new DocMstrDtlsEntity();
		docMstrDtlsEntity.setDmdDrName("AMOL");
		docMstrDtlsEntity.setDmdUserId("722");
		appointmentDtlsEntity.setDocMstrDtlsEntity(docMstrDtlsEntity);

		Mockito.when(appointmentRepo.findByAdApptId(Mockito.anyString())).thenReturn(appointmentDtlsEntity);
		Mockito.when(docRegDtlsRepository.findByDrdUserId(Mockito.anyString())).thenReturn(null);

		consultationServiceImpl.saveConsultationDiagnosisService(consultationDTO);
	}

	@Test
	public void getConsultationDiagnosisServiceTest() throws Exception {

		String appointID = "736424";

		ConsultationDTO<String> consultationDTO = new ConsultationDTO<String>();
		consultationDTO.setAppointID("736424");
		consultationDTO.setTabID("DIAGNOSIS");
		List<String> data = Arrays.asList("migrane", "fever");
		consultationDTO.setData(data);

		List<ConsultDiagDtlsEntity> diagnosisListEntities = new ArrayList<>();
		ConsultDiagDtlsEntity consultDiagDtlsEntity = new ConsultDiagDtlsEntity();
		consultDiagDtlsEntity.setDiagnosis("migrane");

		ConsultDiagDtlsEntity consultDiagDtlsEntity1 = new ConsultDiagDtlsEntity();
		consultDiagDtlsEntity1.setDiagnosis("fever");

		diagnosisListEntities.add(consultDiagDtlsEntity);
		diagnosisListEntities.add(consultDiagDtlsEntity1);

		Mockito.when(consultDiagnosisRepo.findByAppointId(Mockito.anyString())).thenReturn(diagnosisListEntities);
		assertThat(consultationDTO)
				.isEqualToComparingFieldByField(consultationServiceImpl.getConsultationDiagnosisService(appointID));
	}

	@Test(expected = ConsultationServiceException.class)
	public void getConsultationDiagnosisServiceNegativeTest() throws Exception {

		String appointID = "736424";

		List<ConsultDiagDtlsEntity> diagnosisListEntities = new ArrayList<>();

		Mockito.when(consultDiagnosisRepo.findByAppointId(Mockito.anyString())).thenReturn(diagnosisListEntities);
		consultationServiceImpl.getConsultationDiagnosisService(appointID);
	}

}
*/