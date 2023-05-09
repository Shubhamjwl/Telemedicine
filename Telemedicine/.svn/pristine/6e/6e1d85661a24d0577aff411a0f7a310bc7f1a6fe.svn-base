/*package com.nsdl.auth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import com.nsdl.auth.controller.LoginController;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.DoctorDocDtlsDTO;
import com.nsdl.auth.dto.DoctorRegDtlsDTO;
import com.nsdl.auth.dto.GetDoctorDetailsDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.VerifyDoctorRequestDTO;
import com.nsdl.auth.dto.VerifyNotificationDTO;
import com.nsdl.auth.entity.DoctorDocsDtlEntity;
import com.nsdl.auth.entity.DoctorRegDtlsEntity;
import com.nsdl.auth.repository.DoctorDocumentRepo;
import com.nsdl.auth.repository.DoctorMstrRepo;
import com.nsdl.auth.repository.LoginUserRepo;
import com.nsdl.auth.repository.RegistrationRepo;
import com.nsdl.telemedicine.gateway.config.UserDTO;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DoctorVerificationServiceImpl.class })
public class DoctorVerificationServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@MockBean
	public LoginController login;

	@Mock
	public RegistrationRepo registrationRepo;

	@MockBean
	public DoctorMstrRepo doctorMstrRepo;

	@InjectMocks
	private DoctorVerificationServiceImpl doctorVerificationServiceImpl;

	@Mock
	public DoctorDocumentRepo doctorDocumentRepo;

	@MockBean
	public LoginUserRepo loginRepo;

	List<DoctorRegDtlsEntity> doctorRegDtlsEntityList = new ArrayList<DoctorRegDtlsEntity>();

	UserDTO userDetails = new UserDTO();

	@Before
	public void setUp() throws ParseException {

		userDetails.setUserName("SYS123");
		ReflectionTestUtils.setField(doctorVerificationServiceImpl, "userDTO", userDetails);

		DoctorRegDtlsEntity doctorRegDtlsEntity = new DoctorRegDtlsEntity();
		doctorRegDtlsEntity.setDrdIdPk(662);
		doctorRegDtlsEntity.setDrdAddress1("PUNE");
		doctorRegDtlsEntity.setDrdCity("NASHIK");
		doctorRegDtlsEntity.setDrdConsulFee(500);
		doctorRegDtlsEntity.setDrdDrName("NSDLUSERDOC");
		doctorRegDtlsEntity.setDrdEmail("TEST012@NSDL.CO.IN");
		doctorRegDtlsEntity.setDrdGender("FEMALE");
		doctorRegDtlsEntity.setDrdIsRegByIpan("N");
		doctorRegDtlsEntity.setDrdIsverified("A");
		doctorRegDtlsEntity.setDrdMciNumber("MCI100");
		doctorRegDtlsEntity.setDrdMobileNo("9876543210");
		doctorRegDtlsEntity.setDrdPassword("Pass12!@");
		doctorRegDtlsEntity.setDrdSmcNumber("SMC100");
		doctorRegDtlsEntity.setDrdSpecialiazation("GENERAL PHYSICIAN");
		doctorRegDtlsEntity.setDrdState("MAHARASHTRA");
		doctorRegDtlsEntity.setDrdUserId("DOCT123");

		List<DoctorDocsDtlEntity> drDocsDtls = new ArrayList<DoctorDocsDtlEntity>();
		DoctorDocsDtlEntity document = new DoctorDocsDtlEntity();
		document.setDdtDocIdPk(620);
		document.setDdtDocsName("NSDLUSERDOC");
		document.setDdtDocsPath("/var/telemedicine/documents/regDocs/DOCTORS1/1167006.jpg");
		document.setDdtDocsType("AadharCard");
		document.setDdtDocsRemark("document");
		drDocsDtls.add(document);

		doctorRegDtlsEntity.setDrDocsDtls(drDocsDtls);
		doctorRegDtlsEntityList.add(doctorRegDtlsEntity);
	}

	 @Test
	public void getDoctorListToVerify() {
		String userName = userDetails.getUserName();

		List<DoctorRegDtlsDTO> listOfExpectedFucntionDoctorDTOs = new ArrayList<>();
		DoctorRegDtlsDTO expectedDoctorDtls = DoctorRegDtlsDTO.builder().drFullName("NSDLUSERDOC")
				.drEmail("TEST012@NSDL.CO.IN").drMCINo("MCI100").drSMCNo("SMC100").drSpecilization("GENERAL PHYSICIAN")
				.drMobNo("9876543210").currentStatus("APPROVED").drUserID("DOCT123").build();
		listOfExpectedFucntionDoctorDTOs.add(expectedDoctorDtls);

		Mockito.when(registrationRepo.findDoctorListToVerify(userName)).thenReturn(doctorRegDtlsEntityList);
		List<DoctorRegDtlsDTO> responseList = doctorVerificationServiceImpl.getDoctorListToVerify();
		assertThat(listOfExpectedFucntionDoctorDTOs.size()).isEqualTo(responseList.size());
		assertThat(listOfExpectedFucntionDoctorDTOs).isEqualTo(responseList);
	}

	 @Test
	public void verifyDoctorByDocNameSendNotificationTest() throws Exception {
		CommonResponseDTO verifyResponse = new CommonResponseDTO();
		verifyResponse.setMessage("Doctor verified successfully");
		String doctorId = "DOCT123";
		VerifyDoctorRequestDTO verifyDoctorRequestDTO = new VerifyDoctorRequestDTO();
		verifyDoctorRequestDTO.setRegDocUserName("DOCT123");
		verifyDoctorRequestDTO.setVerificationStatusFlag("Y");

		DoctorRegDtlsEntity doctorRegDtlsEntity = new DoctorRegDtlsEntity();
		doctorRegDtlsEntity.setDrdEmail("test@gmail.com");
		doctorRegDtlsEntity.setDrdMobileNo("9876543210");
		doctorRegDtlsEntity.setDrdUserId(doctorId);
		doctorRegDtlsEntity.setDrdIsverified("A");

		Mockito.when(registrationRepo.findDoctorDetailsByUserID(Mockito.anyString())).thenReturn(doctorRegDtlsEntity);
		Mockito.when(registrationRepo.levelTwoApproval(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(LocalDateTime.class), Mockito.anyString())).thenReturn(2);

		DoctorVerificationServiceImpl doctorVerificationServiceSpy = PowerMockito.spy(doctorVerificationServiceImpl);

		PowerMockito.doReturn(true).when(doctorVerificationServiceSpy, "sendNotification",
				ArgumentMatchers.any(VerifyNotificationDTO.class));
		PowerMockito.doReturn(true).when(doctorVerificationServiceSpy, "saveDoctorDetailsToMaster",
				ArgumentMatchers.any(DoctorRegDtlsEntity.class));
		PowerMockito.doReturn(true).when(doctorVerificationServiceSpy, "saveDoctorDetailsToUserDetails",
				ArgumentMatchers.any(DoctorRegDtlsEntity.class), ArgumentMatchers.anyString());

		MainResponseDTO<CommonResponseDTO> mainResponse = doctorVerificationServiceSpy
				.verifyDoctorByDocName(verifyDoctorRequestDTO);
		assertThat(verifyResponse).isEqualTo(mainResponse.getResponse());
		
	}

	@Test
	public void getDoctorDetailsByDoctorIdTest() throws Exception {
		String doctorId = "DOCT123";
		GetDoctorDetailsDTO expectedDoctorDetailsResponse = new GetDoctorDetailsDTO();
		DoctorDocDtlsDTO expectedDoctorDocDtlsDTO = new DoctorDocDtlsDTO();
		BeanUtils.copyProperties(doctorRegDtlsEntityList.get(0), expectedDoctorDetailsResponse);
		List<DoctorDocsDtlEntity> drDocsDtlsEntityList = new ArrayList<DoctorDocsDtlEntity>();
		List<DoctorDocDtlsDTO> expectedDoctorDtlsDTOList = new ArrayList<DoctorDocDtlsDTO>();
		DoctorDocsDtlEntity documentEntity = new DoctorDocsDtlEntity();
		documentEntity.setDdtDocIdPk(620);
		documentEntity.setDdtDocsName("NSDLUSERDOC");
		documentEntity.setDdtDocsPath("/var/telemedicine/documents/regDocs/DOCTORS1/1167006.jpg");
		documentEntity.setDdtDocsType("AadharCard");
		documentEntity.setDdtDocsRemark("document");
		drDocsDtlsEntityList.add(documentEntity);
		BeanUtils.copyProperties(documentEntity, expectedDoctorDocDtlsDTO);
		expectedDoctorDtlsDTOList.add(expectedDoctorDocDtlsDTO);
		expectedDoctorDetailsResponse.setDrDocsDtls(expectedDoctorDtlsDTOList);
		Mockito.when(registrationRepo.findDoctorDetailsByUserID(Mockito.anyString()))
				.thenReturn(doctorRegDtlsEntityList.get(0));
		Mockito.when(doctorDocumentRepo.findDoctorDocuments(doctorId)).thenReturn(drDocsDtlsEntityList);

		DoctorVerificationServiceImpl doctorVerificationServiceSpy = PowerMockito.spy(doctorVerificationServiceImpl);
		PowerMockito.doReturn(null).when(doctorVerificationServiceSpy, "getDoctorProfilePhoto",
				ArgumentMatchers.any(String.class));
		MainResponseDTO<GetDoctorDetailsDTO> responseList = doctorVerificationServiceSpy
				.getDoctorDetailsByDoctorId(doctorId);
		assertThat(expectedDoctorDetailsResponse).isEqualTo(responseList.getResponse());
	}

}
*/