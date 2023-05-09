package com.nsdl.telemedicine.patient.service.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.time.LocalDateTime;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.telemedicine.patient.constant.AuthConstant;
import com.nsdl.telemedicine.patient.dto.AddressDto;
import com.nsdl.telemedicine.patient.dto.AllPatientDetailDto;
import com.nsdl.telemedicine.patient.dto.LifeStyleDetailDto;
import com.nsdl.telemedicine.patient.dto.MedicalDetailDto;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.PersonalDetailDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.dto.UserDTO;
import com.nsdl.telemedicine.patient.entity.PatientLifestyleDetailsEntity;
import com.nsdl.telemedicine.patient.entity.PatientMedicalDetailsEntity;
import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.exception.DateParsingException;
import com.nsdl.telemedicine.patient.repository.PatientLifestyleDetailsRepository;
import com.nsdl.telemedicine.patient.repository.PatientMedicalDetailsRepository;
import com.nsdl.telemedicine.patient.repository.PatientPersonalDetailsRepository;
import com.nsdl.telemedicine.patient.service.AuditPatientService;
import com.nsdl.telemedicine.patient.service.impl.PatientModificationServiceImpl;
import com.nsdl.telemedicine.patient.utility.CommonValidationUtil;

@RunWith(SpringRunner.class)
public class PatientModificationServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	PatientModificationServiceImpl patientModificationServiceImpl;

	@Mock
	PatientPersonalDetailsRepository patientRegistrationRepository;

	@Mock
	PatientMedicalDetailsRepository patientMedicalDetailsRepository;

	@Mock
	PatientLifestyleDetailsRepository patientLifestyleDetailsRepository;

	@Mock
	AuditPatientService auditPatientService;

	@InjectMocks
	UserDTO userDto;

	@Mock

	@Qualifier("patientCommonValidation")
	CommonValidationUtil validate;
	ObjectMapper objectMapper = new ObjectMapper();

	AllPatientDetailDto allPatientDetailDto;
	PersonalDetailDto personalDetailDto;
	LifeStyleDetailDto lifeStyleDetailDto;
	MedicalDetailDto medicalDetailDto;

	PatientPersonalDetailEntity patientPersonalDetailEntity;
	PatientMedicalDetailsEntity patientMedicalDetailsEntity;
	PatientLifestyleDetailsEntity patientLifestyleDetailsEntity;
	List<PatientLifestyleDetailsEntity> lifeStyleDetailEntities;
	List<PatientMedicalDetailsEntity> medicalDetailEntities;

	@Before
	public void setup() {
		ReflectionTestUtils.setField(patientModificationServiceImpl, "byteDataPatientPath", "D:\\");
		personalDetailDto = new PersonalDetailDto();
		personalDetailDto.setPtFullName("AJIT");
		personalDetailDto.setPtEmail("developererakansha@gmail.com");
		personalDetailDto.setPtMobNo(Long.parseLong("7987193665"));
		personalDetailDto.setHeight(6.5);
		personalDetailDto.setWeight(80.7);
		personalDetailDto.setBloodgrp("O pos");
		AddressDto add = new AddressDto();
		add.setAddress1("address1");
		add.setAddress2("address2");
		add.setAddress3("address3");
		personalDetailDto.setAddress(add);

		medicalDetailDto = new MedicalDetailDto();
		medicalDetailDto.setAllergies("fish");

		lifeStyleDetailDto = new LifeStyleDetailDto();
		lifeStyleDetailDto.setAlcohol("weekly");

		allPatientDetailDto = new AllPatientDetailDto();
		allPatientDetailDto.setPtPersonalDtls(personalDetailDto);
		allPatientDetailDto.setPtLifeStyleDtls(lifeStyleDetailDto);
		allPatientDetailDto.setPtMedicalDtls(medicalDetailDto);

		patientPersonalDetailEntity = new PatientPersonalDetailEntity();
		patientPersonalDetailEntity.setHeight(6.5);
		patientPersonalDetailEntity.setWeight(80.7);
		patientPersonalDetailEntity.setBloodGroup("O pos");
		patientPersonalDetailEntity.setAddress1("address1");
		patientPersonalDetailEntity.setAddress2("address2");
		patientPersonalDetailEntity.setAddress3("address3");

		medicalDetailEntities = new ArrayList<PatientMedicalDetailsEntity>();
		patientMedicalDetailsEntity = new PatientMedicalDetailsEntity();
		patientMedicalDetailsEntity.setMedicalType("allergies");
		patientMedicalDetailsEntity.setMedicalTypeValue("fish");
		medicalDetailEntities.add(patientMedicalDetailsEntity);

		lifeStyleDetailEntities = new ArrayList<PatientLifestyleDetailsEntity>();
		patientLifestyleDetailsEntity = new PatientLifestyleDetailsEntity();
		patientLifestyleDetailsEntity.setLifeStyleType("alcohol");
		patientLifestyleDetailsEntity.setLifeStyleTypeValue("weekly");
		lifeStyleDetailEntities.add(patientLifestyleDetailsEntity);

	}

	@Test
	public void getPatientAllDetailsTest() throws Exception {
		RequestWrapper<String> request = new RequestWrapper<String>();
		request.setRequest("AKKU7987193667");

		Mockito.when(patientRegistrationRepository.findByPtUserID(request.getRequest()))
				.thenReturn(patientPersonalDetailEntity);
		Mockito.when(patientMedicalDetailsRepository.findByPtUserID(request.getRequest()))
				.thenReturn(medicalDetailEntities);
		Mockito.when(patientLifestyleDetailsRepository.findByPtUserID(request.getRequest()))
				.thenReturn(lifeStyleDetailEntities);

		ResponseWrapper<AllPatientDetailDto> response = patientModificationServiceImpl.getPatientAllDetails();
		assertEquals(response.getResponse(), allPatientDetailDto);
	}

	@Test
	public void getPatientAllDetailsNgativeTest() throws Exception {
		RequestWrapper<String> request = new RequestWrapper<String>();
		request.setRequest(userDto.getUserName());
		ResponseWrapper<AllPatientDetailDto> response = patientModificationServiceImpl.getPatientAllDetails();
		assertEquals(response.getErrors().size(), 1);
	}

	@Test
	public void updatePatientPersonalDetailsTest() throws Exception {
		RequestWrapper<PersonalDetailDto> updateRequest = new RequestWrapper<PersonalDetailDto>();
		personalDetailDto.setPtProfilePhoto(
				"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");
		personalDetailDto.setDob(new Date(1434535354));
		updateRequest.setRequest(personalDetailDto);
		ResponseWrapper<PatientResponseDto> updateResponse = createResponse();
		patientPersonalDetailEntity.setPtMobNo(Long.parseLong("7065434670"));
		patientPersonalDetailEntity.setPtUserID("MAMTA");

		Mockito.when(patientRegistrationRepository.findByPtUserID("MAMTA")).thenReturn(patientPersonalDetailEntity);
		File byteStorePath = new File("D:\\NSDL\\Telemedicine proj docs");
		String temppath = byteStorePath + File.separator + AuthConstant.patientProfileDirectory;
		String profilePhotoPath = temppath + File.separator + patientPersonalDetailEntity.getPtMobNo() + "_"
				+ patientPersonalDetailEntity.getPtUserID() + ".jpeg";
		Mockito.when(patientRegistrationRepository.updateByPtUserID(personalDetailDto.getPtFullName(),
				personalDetailDto.getPtEmail(), personalDetailDto.getPtMobNo(), personalDetailDto.getHeight(),
				personalDetailDto.getWeight(), personalDetailDto.getBloodgrp(), new Date(),
				personalDetailDto.getAddress().getAddress1(), personalDetailDto.getAddress().getAddress2(),
				personalDetailDto.getAddress().getAddress3(), LocalDateTime.now(), profilePhotoPath,
				personalDetailDto.getPtGender(), personalDetailDto.getPtCity(), personalDetailDto.getPtState(),
				personalDetailDto.getPtCountry(), "MAMTA")).thenReturn(1);
		ResponseWrapper<PatientResponseDto> actualRes = patientModificationServiceImpl.updatePatientPersonalDetails(updateRequest); // System.err.println(actualRes.getResponse());
		assertEquals(actualRes.getResponse(), updateResponse.getResponse());
		assertNotNull(actualRes);
	}

	@Test
	public void updatePatientPersonalDetailsNegativeTest() throws Exception {
		RequestWrapper<PersonalDetailDto> updateRequest = new RequestWrapper<PersonalDetailDto>();
		personalDetailDto.setPtProfilePhoto(
				"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");
		updateRequest.setRequest(personalDetailDto);

		Mockito.when(patientRegistrationRepository.findByPtUserID("AKKU7987193667"))
				.thenReturn(patientPersonalDetailEntity);
		ResponseWrapper<PatientResponseDto> actualRes = patientModificationServiceImpl.updatePatientPersonalDetails(updateRequest);
		assertEquals(1, actualRes.getErrors().size());
	}

	@Test
	public void updatePatientMedicalDetailsTest() throws Exception {
		RequestWrapper<MedicalDetailDto> updateRequest = new RequestWrapper<MedicalDetailDto>();
		updateRequest.setRequest(medicalDetailDto);
		ResponseWrapper<PatientResponseDto> updateResponse = createResponse();

		Mockito.when(patientRegistrationRepository.existsByPtUserID("MAMTA")).thenReturn(true);
		Mockito.when(patientMedicalDetailsRepository.findByPtUserID("MAMTA")).thenReturn(medicalDetailEntities);
		ResponseWrapper<PatientResponseDto> actualRes = patientModificationServiceImpl
				.updatePatientMedicalDetails(updateRequest);
		assertEquals(actualRes.getResponse(), updateResponse.getResponse());
	}

	@Test
	public void updatePatientMedicalDetailsNegativeTest() throws Exception {
		RequestWrapper<MedicalDetailDto> updateRequest = new RequestWrapper<MedicalDetailDto>();
		updateRequest.setRequest(medicalDetailDto);

		Mockito.when(patientRegistrationRepository.existsByPtUserID("MAMTA")).thenReturn(true);
		Mockito.when(patientMedicalDetailsRepository.findByPtUserID("MAMTA")).thenReturn(null);

		ResponseWrapper<PatientResponseDto> actualRes = patientModificationServiceImpl
				.updatePatientMedicalDetails(updateRequest);
		assertEquals(1, actualRes.getErrors().size());
	}

	@Test
	public void updatePatientLifeStyleDetailsTest() throws Exception {
		RequestWrapper<LifeStyleDetailDto> updateRequest = new RequestWrapper<LifeStyleDetailDto>();
		updateRequest.setRequest(lifeStyleDetailDto);
		ResponseWrapper<PatientResponseDto> updateResponse = createResponse();

		Mockito.when(patientRegistrationRepository.existsByPtUserID("MAMTA")).thenReturn(true);
		Mockito.when(patientLifestyleDetailsRepository.findByPtUserID("MAMTA")).thenReturn(lifeStyleDetailEntities);
		ResponseWrapper<PatientResponseDto> actualRes = patientModificationServiceImpl
				.updatePatientLifeStyleDetails(updateRequest);
		assertEquals(actualRes.getResponse(), updateResponse.getResponse());
	}

	@Test
	public void updatePatientLifeStyleDetailsNegativeTest() throws Exception {
		RequestWrapper<LifeStyleDetailDto> updateRequest = new RequestWrapper<LifeStyleDetailDto>();
		updateRequest.setRequest(lifeStyleDetailDto);
		ResponseWrapper<PatientResponseDto> updateResponse = createResponse();

		Mockito.when(patientRegistrationRepository.existsByPtUserID("MAMTA")).thenReturn(true);
		Mockito.when(patientLifestyleDetailsRepository.findByPtUserID("MAMTA")).thenReturn(null);
		ResponseWrapper<PatientResponseDto> actualRes = patientModificationServiceImpl
				.updatePatientLifeStyleDetails(updateRequest);
		assertEquals(actualRes.getResponse(), updateResponse.getResponse());
	}

	@Test
	public void updateAllPatientDetailsTest() {
		RequestWrapper<AllPatientDetailDto> updateRequest = new RequestWrapper<AllPatientDetailDto>();
		updateRequest.setRequest(allPatientDetailDto);
		ResponseWrapper<PatientResponseDto> updateResponse = createResponse();

		Mockito.when(patientRegistrationRepository.existsByPtUserID("MAMTA")).thenReturn(true);

		ResponseWrapper<PatientResponseDto> actualRes = patientModificationServiceImpl
				.updateAllPatientDetails(updateRequest);
		assertEquals(actualRes.getResponse(), updateResponse.getResponse());

	}

	@Test(expected = DateParsingException.class)
	public void updateAllPatientDetailsExceptionTest() {
		RequestWrapper<AllPatientDetailDto> updateRequest = new RequestWrapper<AllPatientDetailDto>();
		updateRequest.setRequest(allPatientDetailDto);

		Mockito.when(patientRegistrationRepository.existsByPtUserID(userDto.getUserName())).thenReturn(false);
		patientModificationServiceImpl.updateAllPatientDetails(updateRequest);

	}

	private ResponseWrapper<PatientResponseDto> createResponse() {
		ResponseWrapper<PatientResponseDto> response = new ResponseWrapper<PatientResponseDto>();
		PatientResponseDto responseDto = new PatientResponseDto();
		response.setId("modification");
		response.setVersion("1.0");
		response.setStatus(true);
		response.setResponse(responseDto);
		return response;
	}
}
