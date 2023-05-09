
package com.nsdl.telemedicine.patient.service.impl.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nsdl.telemedicine.patient.dto.PatientRegistrationDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.dto.UserDTO;
import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.exception.DateParsingException;
import com.nsdl.telemedicine.patient.repository.PatientPersonalDetailsRepository;
import com.nsdl.telemedicine.patient.service.AuditPatientService;
import com.nsdl.telemedicine.patient.service.impl.PatientRegistrationServiceImpl;
import com.nsdl.telemedicine.patient.utility.CommonValidationUtil;

@RunWith(SpringRunner.class)
public class PatientRegistrationServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	PatientRegistrationServiceImpl patientRegistrationServiceImpl;

	@Mock
	PatientPersonalDetailsRepository patientRegistrationRepository;

	@Mock

	@Qualifier("patientCommonValidation")
	CommonValidationUtil validate;

	@Mock
	RestTemplate restTemplate;

	@Mock
	UserDTO userDto;

	@Mock
	AuditPatientService auditPatientService;

	@Value("${CREATE_USER_URL}")
	private String createUserURL;

	@Value("${OTP_GENERATE_URL}")
	private String generateOtpURL;

	PatientRegistrationDto registrationDto;

	@Before
	public void setup() {
		registrationDto = new PatientRegistrationDto();
		registrationDto.setPtFullName("Mamta Andhe");
		registrationDto.setPtMobNo(Long.parseLong("7065434670"));
		registrationDto.setPtEmail("mamta@gmail.com");
		registrationDto.setPtUserID("MAMTA");
		registrationDto.setPtPassword("Pass123!@#");
		registrationDto.setPtProfilePhoto("D:\\var\\telemedicine\\documents\\profilePhoto\\9108238908_mamta.jpeg");
	}

	@Test
	public void savePatientDetailsTest() throws JsonProcessingException {
		RequestWrapper<PatientRegistrationDto> request = new RequestWrapper<PatientRegistrationDto>();
		createRequest(request, "registration");
		request.setRequest(registrationDto);
		// System.err.println(objectMapper.writeValueAsString(request));
		patientRegistrationServiceImpl.savePatientDetails(request);
	}

	@Test
	public void getPatientDetailsFromIPANTest() throws IOException {
		RequestWrapper<String> request = new RequestWrapper<String>();
		createRequest(request, "verify");
		request.setRequest("ADMIN1234");

		PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
		entity.setPtFullName("Mamta Andhe");
		entity.setPtMobNo(Long.parseLong("7065434670"));
		entity.setPtEmail("mamta@gmail.com");
		entity.setPtUserID("MAMTA");
		entity.setPtPassword("Pass123!@#");
		entity.setProfilePhotoPath("\\var\\telemedicine\\documents\\profilePhoto\\9108238908_mamta.jpeg");
		Mockito.when(patientRegistrationRepository.findByPtUserID(userDto.getUserName())).thenReturn(entity);
		ResponseWrapper<PatientRegistrationDto> response = patientRegistrationServiceImpl.getPatientDetailsFromIPAN();
		assertThat(response.getResponse()).isEqualTo(registrationDto);
	}

	@Test(expected = DateParsingException.class)
	public void getPatientDetailsFromIPANNegativeTest() throws IOException {
		RequestWrapper<String> request = new RequestWrapper<String>();
		createRequest(request, "verify");
		request.setRequest("mamta");

		PatientPersonalDetailEntity entity = new PatientPersonalDetailEntity();
		Mockito.when(patientRegistrationRepository.findByPtUserID(userDto.getUserName())).thenReturn(entity);
		patientRegistrationServiceImpl.getPatientDetailsFromIPAN();
	}

	@SuppressWarnings("rawtypes")
	private RequestWrapper<?> createRequest(RequestWrapper request, String id) {
		request.setId(id);
		request.setVersion("1.0");
		request.setRequestTime("2020-11-05T18:38:47.282Z");
		return request;
	}

}
