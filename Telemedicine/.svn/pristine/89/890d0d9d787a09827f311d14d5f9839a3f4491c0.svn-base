package com.nsdl.telemedicine.doctor.service.Impl.test;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.telemedicine.doctor.dto.DoctorMstrDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.DoctorRegDtlsDTO;
import com.nsdl.telemedicine.doctor.dto.MainRequestDTO;
import com.nsdl.telemedicine.doctor.dto.MainResponseDTO;
import com.nsdl.telemedicine.doctor.dto.ScribeRegDtlsDTO;
import com.nsdl.telemedicine.doctor.entity.AuditScribeRegEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorDocsDtlEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorMstrDtlsEntity;
import com.nsdl.telemedicine.doctor.entity.DoctorRegDtlsEntity;
import com.nsdl.telemedicine.doctor.entity.ScribeRegEntity;
import com.nsdl.telemedicine.doctor.exception.DoctorRegistrationException;
import com.nsdl.telemedicine.doctor.repository.DoctorDocumentRepo;
import com.nsdl.telemedicine.doctor.repository.DoctorMstrRepo;
import com.nsdl.telemedicine.doctor.repository.DocumentRepo;
import com.nsdl.telemedicine.doctor.repository.RegistrationRepo;
import com.nsdl.telemedicine.doctor.repository.ScribeRegRepo;
import com.nsdl.telemedicine.doctor.repository.ScribeRegRepoAudited;
import com.nsdl.telemedicine.doctor.service.Impl.RegistrationServiceImpl;
import com.nsdl.telemedicine.doctor.utility.CommonValidationUtil;

@RunWith(SpringRunner.class)
public class RegistrationServiceImplTest {
	
	@Value("${OTP_GENERATE_URL}")
	private String generateOtpURL;
	
	@Value("${CREATE_USER_URL}")
	private String createUserURL;
	
	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	@InjectMocks
	RegistrationServiceImpl registrationServiceImpl;
	
	@Mock
	private CommonValidationUtil validate;
	
	@Mock
	RegistrationRepo registrationRepo;
	
	@Mock
	DocumentRepo documentrepo;
	
	@Mock
	DoctorMstrRepo doctorMstrRepo;
	
	@Mock
	DoctorDocumentRepo doctorDocumentRepo;
	
	@Mock
	ScribeRegRepo scribeRegRepo;
	
	@Mock
	ScribeRegRepoAudited scribeRegRepoAudited;

	ObjectMapper objectMapper = new ObjectMapper();

	DoctorRegDtlsDTO doctorRegDtlsDTO;
	MockMultipartFile firstFile, secondFile;
	MultipartFile[] files;
	List<MultipartFile> filesm;
	
	@Before
	public void setup() {
		firstFile = new MockMultipartFile("file1", "filename.txt", "application/multipart", "some txt file".getBytes());
	    secondFile = new MockMultipartFile("file2", "doctorprofile.pdf", "text/plain", "some other type".getBytes());
		files = new MultipartFile[2];
		files[0] = firstFile;
		files[1] = secondFile;
		filesm = new ArrayList<MultipartFile>();
		filesm.add(firstFile);filesm.add(secondFile);
		
		doctorRegDtlsDTO = new DoctorRegDtlsDTO();
		doctorRegDtlsDTO.setDrFullName("saili");
		doctorRegDtlsDTO.setDrMobNo("7032208638");
		doctorRegDtlsDTO.setDrEmail("SAIOSS1@LNSDL.CO.IN");
		doctorRegDtlsDTO.setDrUserID("sayali1");
		doctorRegDtlsDTO.setDrPassword("pas12t&*");
		doctorRegDtlsDTO.setDrMCINo("MCI1234");
		doctorRegDtlsDTO.setDrSMCNo("SMC1234");
		doctorRegDtlsDTO.setDrConsultFee(200);
		doctorRegDtlsDTO.setDrSpecilization("General Physician");
	}
	
	/*//@Test
	public void saveDoctorRegistrationDetailsTest() throws JsonProcessingException {
		MainRequestDTO<DoctorRegDtlsDTO> registerRequest = new MainRequestDTO<DoctorRegDtlsDTO>();
		createRequest(registerRequest, "registration", "multipart/form-data");
		registerRequest.setRequest(doctorRegDtlsDTO);
		
		//System.out.println(".asdf..."+objectMapper.writeValueAsString(registrationServiceImpl.saveDoctorRegistrationDetails(files, registerRequest)));
		assertNotNull(registrationServiceImpl.saveDoctorRegistrationDetails(files, registerRequest));
	}*/
	
	@Test
	public void deleteuploadedDocumentsTest() {
		assertEquals(1,registrationServiceImpl.deleteuploadedDocuments("rahul"));
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Test
	public void doctorProfileTest() throws IOException {
		MainRequestDTO<String> profileRequest = new MainRequestDTO<>();
		createRequest(profileRequest, "view doctor profile", null);
		profileRequest.setId("1");
		profileRequest.setRequest("722");
		
		DoctorMstrDtlsEntity entity = new DoctorMstrDtlsEntity();
		entity.setDmdDrName("test");
		entity.setDmdIdPk(1);
		entity.setDmdConsulFee(200);
		entity.setDmdEmail("bbb@gmail.com");
		entity.setDmdGender("male");
		entity.setDmdIsRegByIpan("Y");
		entity.setDmdIsverified(true);
		entity.setDmdMciNumber("345345435345345");
		entity.setDmdPassword("123");
		entity.setDmdSmcNumber("4334534534534");
		entity.setDmdSpecialiazation("Physics");
		entity.setDmdUserId("722");
		
		DoctorMstrDtlsDTO doctorMstrDtlsDTO = new DoctorMstrDtlsDTO();
		doctorMstrDtlsDTO.setDmdDrName("test");
		doctorMstrDtlsDTO.setDmdIdPk(1);
		doctorMstrDtlsDTO.setDmdConsulFee(200);
		doctorMstrDtlsDTO.setDmdEmail("bbb@gmail.com");
		doctorMstrDtlsDTO.setDmdGender("male");
		doctorMstrDtlsDTO.setDmdIsRegByIpan("Y");
		doctorMstrDtlsDTO.setDmdIsverified(true);
		doctorMstrDtlsDTO.setDmdMciNumber("345345435345345");
		doctorMstrDtlsDTO.setDmdPassword("123");
		doctorMstrDtlsDTO.setDmdSmcNumber("4334534534534");
		doctorMstrDtlsDTO.setDmdSpecialiazation("Physics");
		doctorMstrDtlsDTO.setDmdUserId("722");
		
		Mockito.when(doctorMstrRepo.findByDmdUserId(profileRequest.getRequest())).thenReturn(entity);
		
		String actualRes = objectMapper.writeValueAsString(registrationServiceImpl.doctorProfile(profileRequest));
		MainResponseDTO res  =  objectMapper.readValue(actualRes, MainResponseDTO.class);
		assertEquals(actualRes,"{\"id\":null,\"version\":null,\"responsetime\":\""+res.getResponsetime()+"\",\"response\":{\"dmdIdPk\":1,\"dmdConsulFee\":200,\"dmdDrName\":\"test\",\"dmdEmail\":\"bbb@gmail.com\",\"dmdGender\":\"male\",\"dmdIsRegByIpan\":\"Y\",\"dmdIsverified\":true,\"dmdMciNumber\":\"345345435345345\",\"dmdMobileNo\":null,\"dmdModifiedBy\":null,\"dmdModifiedTmstmp\":null,\"dmdOptiVersion\":null,\"dmdPassword\":\"123\",\"dmdSmcNumber\":\"4334534534534\",\"dmdSpecialiazation\":\"Physics\",\"dmdUserId\":\"722\",\"drDocsDtls\":[],\"drScribeDtls\":null},\"status\":true,\"errors\":null}");
	}
	
	@Test(expected = DoctorRegistrationException.class)
	public void doctorProfileNegativeTest() throws IOException {
		MainRequestDTO<String> profileRequest = new MainRequestDTO<>();
		createRequest(profileRequest, "view doctor profile", null);
		profileRequest.setId("1");
		profileRequest.setRequest(null);
		
		registrationServiceImpl.doctorProfile(profileRequest);
	}
	
	@Test
	public void getDoctorDocumentsDetailsTest() throws IOException {
		MainRequestDTO<String> drDocDtlsRequest = new MainRequestDTO<String>();
		drDocDtlsRequest.setRequest("722");
		
		List<DoctorDocsDtlEntity> doctorDocsDtlEntiities = new ArrayList<DoctorDocsDtlEntity>();
		DoctorDocsDtlEntity doctorDocsDtlEntity1 = new DoctorDocsDtlEntity();
			doctorDocsDtlEntity1.setDdtDocIdPk(6);
			doctorDocsDtlEntity1.setDdtCreatedBy("vis1");
			doctorDocsDtlEntity1.setDdtCreatedTmstmp(null);
			doctorDocsDtlEntity1.setDdtDocsName("sample.pdf");
			doctorDocsDtlEntity1.setDdtDocsPath("D:\\\\temp\\\\sample.pdf");
			doctorDocsDtlEntity1.setDdtDocsRemark("document");
			doctorDocsDtlEntity1.setDdtDocsType("BLANK");
			doctorDocsDtlEntity1.setDdtModifiedBy("vis1");
			doctorDocsDtlEntity1.setDdtModifiedTmstmp(null);
			doctorDocsDtlEntity1.setDdtOptiVersion(null);
			doctorDocsDtlEntiities.add(doctorDocsDtlEntity1);
		
		Mockito.when(doctorDocumentRepo.findDoctorDocuments(drDocDtlsRequest.getRequest())).thenReturn(doctorDocsDtlEntiities);
		String actualRes = objectMapper.writeValueAsString(registrationServiceImpl.getDoctorDocumentsDetails(drDocDtlsRequest));
		MainResponseDTO<?> res  =  objectMapper.readValue(actualRes, MainResponseDTO.class);
		assertEquals("{\"id\":null,\"version\":null,\"responsetime\":\""+res.getResponsetime()+"\",\"response\":[{\"ddtDocIdPk\":6,\"ddtCreatedBy\":\"vis1\",\"ddtCreatedTmstmp\":null,\"ddtDocsName\":\"sample.pdf\",\"ddtDocsPath\":\"D:\\\\\\\\temp\\\\\\\\sample.pdf\",\"ddtDocsRemark\":\"document\",\"ddtDocsType\":\"BLANK\",\"ddtModifiedBy\":\"vis1\",\"ddtModifiedTmstmp\":null,\"ddtOptiVersion\":null,\"doctorRegDtlsEntity\":null}],\"status\":true,\"errors\":null}",actualRes);	
	}

	@Test(expected = DoctorRegistrationException.class)
	public void getDoctorDocumentsDetailsNegativeTest() throws IOException {
		MainRequestDTO<String> drDocDtlsRequest = new MainRequestDTO<String>();
		drDocDtlsRequest.setRequest(null);
		registrationServiceImpl.getDoctorDocumentsDetails(drDocDtlsRequest);
	}
	
	@Test
	public void updateDoctorProfileTest() throws IOException {
		MainRequestDTO<DoctorMstrDtlsDTO> profileUpdateRequest = new MainRequestDTO<DoctorMstrDtlsDTO>();
		createRequest(profileUpdateRequest, "update doctor profile", "application/json");
		profileUpdateRequest.setId("1");
		DoctorMstrDtlsDTO doctorMstrDtlsDTO = new  DoctorMstrDtlsDTO();
		doctorMstrDtlsDTO.setDmdUserId("722");
		doctorMstrDtlsDTO.setDmdDrName("test1");
		doctorMstrDtlsDTO.setDmdMobileNo((long) 1134567892);
		doctorMstrDtlsDTO.setDmdEmail("bbb@gmail.com");
		doctorMstrDtlsDTO.setDmdSmcNumber("4334534534534");
		doctorMstrDtlsDTO.setDmdMciNumber("345345435345345");
		doctorMstrDtlsDTO.setDmdConsulFee(50);
		doctorMstrDtlsDTO.setDmdSpecialiazation("Physics");
		profileUpdateRequest.setRequest(doctorMstrDtlsDTO);

		DoctorMstrDtlsEntity doctorMstrDtlsEntity = new DoctorMstrDtlsEntity();
		doctorMstrDtlsEntity.setDmdUserId("722");
		Mockito.when(doctorMstrRepo.findByDmdUserId(profileUpdateRequest.getRequest().getDmdUserId())).thenReturn(doctorMstrDtlsEntity);
		String actualRes = objectMapper.writeValueAsString(registrationServiceImpl.updateDoctorProfile(profileUpdateRequest));
		MainResponseDTO<?> res  =  objectMapper.readValue(actualRes, MainResponseDTO.class);
		assertEquals("{\"id\":null,\"version\":null,\"responsetime\":\""+res.getResponsetime()+"\",\"response\":\"Profile Updated Successfully..\",\"status\":true,\"errors\":null}", actualRes);
	}
	
	@Test(expected = DoctorRegistrationException.class)
	public void updateDoctorProfileNegativeTest() throws IOException {
		MainRequestDTO<DoctorMstrDtlsDTO> profileUpdateRequest = new MainRequestDTO<DoctorMstrDtlsDTO>();
		createRequest(profileUpdateRequest, "update doctor profile", "application/json");
		profileUpdateRequest.setId("1");
		DoctorMstrDtlsDTO doctorMstrDtlsDTO = new  DoctorMstrDtlsDTO();
		doctorMstrDtlsDTO.setDmdUserId(null);
		profileUpdateRequest.setRequest(doctorMstrDtlsDTO);

		DoctorMstrDtlsEntity doctorMstrDtlsEntity = new DoctorMstrDtlsEntity();
		doctorMstrDtlsEntity.setDmdUserId("722");
		
		Mockito.when(doctorMstrRepo.findByDmdUserId(profileUpdateRequest.getRequest().getDmdUserId())).thenReturn(doctorMstrDtlsEntity);
		registrationServiceImpl.updateDoctorProfile(profileUpdateRequest);
	}
	
//	@Test
//	public void viewScribeListTest() throws IOException {
//		MainRequestDTO<String> scribeListRequest = new MainRequestDTO<String>();
//		createRequest(scribeListRequest, "view scribe list", "application/json");
//		scribeListRequest.setId("1");
//		scribeListRequest.setRequest("722");
//		
//		List<ScribeRegEntity> scribeRegEntitiesList = new ArrayList<ScribeRegEntity>();
//		ScribeRegEntity scribeRegEntity = new ScribeRegEntity();
//		scribeRegEntity.setScrbId(74);
//		scribeRegEntity.setScrbFullName("Jeevan");
//		scribeRegEntity.setScrbMobNo((long)1082656038);
//		scribeRegEntity.setScrbEmail("_evan@gmail.com");
//		scribeRegEntity.setScrbUserID("Jeevan2");
//		scribeRegEntity.setScrbdrUserIDfk("722");
//		scribeRegEntity.setScrbPassword("Pass123!@#");
//		scribeRegEntity.setScrbAdd1("Gandhi nagar");
//		scribeRegEntity.setScrbisActive("y");
//		scribeRegEntity.setProfilePhotoPath("D:\\\\Required_Files\\\\Telemedicine-Files\\\\Scribe-Photos\\\\base64_data\\\\snap8418800428195640036.txt");
//		scribeRegEntitiesList.add(scribeRegEntity);
//		
//		Mockito.when(scribeRegRepo.findScribeDetails(scribeListRequest.getRequest())).thenReturn(scribeRegEntitiesList);
//		String actualRes = objectMapper.writeValueAsString(registrationServiceImpl.viewScribeList(scribeListRequest));
//		MainResponseDTO<?> res  =  objectMapper.readValue(actualRes, MainResponseDTO.class);
//		assertEquals("{\"id\":null,\"version\":null,\"responsetime\":\""+res.getResponsetime()+"\",\"response\":[{\"scrbId\":74,\"scrbFullName\":\"Jeevan\",\"scrbMobNo\":1082656038,\"scrbEmail\":\"_evan@gmail.com\",\"scrbUserID\":\"Jeevan2\",\"scrbdrUserIDfk\":\"722\",\"scrbPassword\":\"Pass123!@#\",\"scrbAdd1\":\"Gandhi nagar\",\"scrbAdd2\":null,\"scrbAdd3\":null,\"scrbAdd4\":null,\"scrbisActive\":\"y\",\"profilePhotoPath\":\"D:\\\\\\\\Required_Files\\\\\\\\Telemedicine-Files\\\\\\\\Scribe-Photos\\\\\\\\base64_data\\\\\\\\snap8418800428195640036.txt\",\"scrbCreadtedBy\":null,\"scrbCreatedTime\":null,\"scrbModifiedBy\":null,\"scrbModifiedTime\":null,\"scrbOptiVersion\":null}],\"status\":true,\"errors\":null}", actualRes);
//	}
	
	@Test
	public void activeDeactiveScribeTest() throws IOException {
		MainRequestDTO<ScribeRegDtlsDTO> scribeActivationRequest = new MainRequestDTO<ScribeRegDtlsDTO>();
		createRequest(scribeActivationRequest, "active deactive scribe", "application/json");
		scribeActivationRequest.setId("1");
		ScribeRegDtlsDTO scribeRegDtlsDTO = new ScribeRegDtlsDTO();
		scribeRegDtlsDTO.setScrbUserID("Jeevan1");
		scribeRegDtlsDTO.setScrbdrUserIDfk("722");
		scribeRegDtlsDTO.setScrbisActive("Y");
		scribeActivationRequest.setRequest(scribeRegDtlsDTO);
		
		scribeRegRepo.activeDeactiveScribe(scribeActivationRequest.getRequest().getScrbUserID(), scribeActivationRequest.getRequest().getScrbdrUserIDfk(), scribeActivationRequest.getRequest().getScrbisActive().toUpperCase());
		ScribeRegEntity scribeRegEntity = new ScribeRegEntity();
		Mockito.when(scribeRegRepo.findScribe(scribeActivationRequest.getRequest().getScrbUserID().toLowerCase(),scribeActivationRequest.getRequest().getScrbdrUserIDfk().toLowerCase())).thenReturn(scribeRegEntity);

		AuditScribeRegEntity auditScribeRegEntity = new AuditScribeRegEntity();
		scribeRegRepoAudited.save(auditScribeRegEntity);
	
		String actualRes = objectMapper.writeValueAsString(registrationServiceImpl.activeDeactiveScribe(scribeActivationRequest));
		MainResponseDTO<?> res  =  objectMapper.readValue(actualRes, MainResponseDTO.class);
		assertEquals("{\"id\":null,\"version\":null,\"responsetime\":\""+res.getResponsetime()+"\",\"response\":\"Scribe activated\",\"status\":true,\"errors\":null}", actualRes);
	}
	
	@Test(expected = DoctorRegistrationException.class)
	public void activeDeactiveScribeNegativeTest() throws IOException {
		MainRequestDTO<ScribeRegDtlsDTO> scribeActivationRequest = new MainRequestDTO<ScribeRegDtlsDTO>();
		createRequest(scribeActivationRequest, "active deactive scribe", "application/json");
		scribeActivationRequest.setId("1");
		ScribeRegDtlsDTO scribeRegDtlsDTO = new ScribeRegDtlsDTO();
		scribeRegDtlsDTO.setScrbUserID("");
		scribeRegDtlsDTO.setScrbdrUserIDfk("722");
		scribeRegDtlsDTO.setScrbisActive("Y");
		scribeActivationRequest.setRequest(scribeRegDtlsDTO);
		
		registrationServiceImpl.activeDeactiveScribe(scribeActivationRequest);
	}
	
	
	/*@Test
	public void saveuploadedDocumentsTest() {
		DoctorRegDtlsEntity doctorrRegDtlsEntity= new DoctorRegDtlsEntity();
		registrationServiceImpl.saveuploadedDocuments(files, doctorrRegDtlsEntity, "");
	}*/
	
	@Test
	public void getDoctorDocDetailsByIDTest() {
		Integer docId = 722;
		DoctorDocsDtlEntity doctorDocsDtlEntity = new DoctorDocsDtlEntity();
		doctorDocsDtlEntity.setDdtDocIdPk(6);
		doctorDocsDtlEntity.setDdtCreatedBy("vis1");
		doctorDocsDtlEntity.setDdtCreatedTmstmp(null);
		doctorDocsDtlEntity.setDdtDocsName("sample.pdf");
		doctorDocsDtlEntity.setDdtDocsPath("D:\\\\temp\\\\sample.pdf");
		doctorDocsDtlEntity.setDdtDocsRemark("document");
		doctorDocsDtlEntity.setDdtDocsType("BLANK");
		doctorDocsDtlEntity.setDdtModifiedBy("vis1");
		doctorDocsDtlEntity.setDdtModifiedTmstmp(null);
		doctorDocsDtlEntity.setDdtOptiVersion(null);
		
		Mockito.when(doctorDocumentRepo.findByDdtDocIdPk(docId)).thenReturn(doctorDocsDtlEntity);
		assertThat(registrationServiceImpl.getDoctorDocDetailsByID(docId)).isEqualTo(doctorDocsDtlEntity);
	}
		
	private void createRequest(MainRequestDTO<?> mainRequestDTO, String api, String mimeType) {
		mainRequestDTO.setAPI(api);
		mainRequestDTO.setRequesttime(new Date());
		mainRequestDTO.setVersion("1.0");
		if(mimeType!=null)
		mainRequestDTO.setMimeType(mimeType);
	}
}

