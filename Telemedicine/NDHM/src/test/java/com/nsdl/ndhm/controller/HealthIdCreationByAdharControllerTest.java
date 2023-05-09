package com.nsdl.ndhm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.ndhm.dto.*;
import com.nsdl.ndhm.service.CommonApiService;
import com.nsdl.ndhm.service.HealthIdCreationByAdharService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.nsdl.ndhm.utility.CommonUtil.getMainResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HealthIdCreationByAdharControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(HealthIdCreationByAdharControllerTest.class);

    public MockMvc mockMvc;

    @Autowired
    public WebApplicationContext context;

    @MockBean
    HealthIdCreationByAdharService healthIdCreationByAdharService;

    @MockBean
    CommonApiService commonApiService;

    @Autowired
    public ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Generate OTP for Aadhaar making POST request to endpoint - /generateOTPForAadhaar")
    public void generateOTPForAadharPositiveTest() throws Exception {
        MainResponseDTO<GenerateOtpResp> mainResponse = getMainResponse();
        GenerateOtpResp generateOtpResp = GenerateOtpResp.builder()
                .txnId("c2b57029-e678-45e0-84fa-0b89930e1604").error(null).errorCode(null).build();
        mainResponse.setResponse(generateOtpResp);

        GenerateOtpAadhaarDTO generateAadhaarOtpDTO = GenerateOtpAadhaarDTO.builder().aadhaar("999911117777").build();

        Mockito.when(healthIdCreationByAdharService
                .generateOTPForAadhar(Mockito.anyMap(), Mockito.any(GenerateOtpAadhaarDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/generateOTPForAadhaar").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateAadhaarOtpDTO)))
                        .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<GenerateOtpResp> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        GenerateOtpResp actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<GenerateOtpResp>() {
                });
        assertThat(actualResponse).isEqualTo(generateOtpResp);

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Resend OTP for Aadhaar making POST request to endpoint - /resendOTPForAadhaar")
    public void resendOTPForAadharPositiveTest() throws Exception {
/*        MainResponseDTO<AadharOtpResendRespDTO> mainResponse = getMainResponse();
        AadharOtpResendRespDTO aadharOtpResendRespDTO  = AadharOtpResendRespDTO.builder()
                .txnId("050bedb1-f8ca-4d69-89e1-bbba57422af8").build();
        mainResponse.setResponse(aadharOtpResendRespDTO);

        ResendOtpDTO resendOtpDTO = ResendOtpDTO.builder()
                .authMethod("AADHAAR_OTP").txnId("c2b57029-e678-45e0-84fa-0b89930e1604").build();

        Mockito.when(healthIdCreationByAdharService
                .resendOTPForAadhar(Mockito.anyMap(), Mockito.any(ResendOtpDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/resendOTPForAadhaar").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resendOtpDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<AadharOtpResendRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);

        System.out.println("responseWrapperActual: "+responseWrapperActual);
        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(), AadharOtpResendRespDTO.class));

        AadharOtpResendRespDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<AadharOtpResendRespDTO>() {
                });
        assertThat(responseWrapperActual).isEqualTo(mainResponse);

        logger.info("response: {}",responseWrapperActual);*/
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Verify OTP for Aadhaar making POST request to endpoint - /verifyOTPForAadhaar")
    public void verifyOTPForAadhaarPositiveTest() throws Exception {
        MainResponseDTO<ConfirmOtpRespAadhaar> mainResponse = getMainResponse();
        ConfirmOtpRespAadhaar confirmOtpRespAadhaar = ConfirmOtpRespAadhaar.builder()
                .txnId("050bedb1-f8ca-4d69-89e1-bbba57422af8").error(null).errorCode(null).build();
        mainResponse.setResponse(confirmOtpRespAadhaar);

        ConfirmOtpAadhaarDTO confirmOtpAadhaarDTO  = ConfirmOtpAadhaarDTO.builder()
                .otp("222459").txnId("c2b57029-e678-45e0-84fa-0b89930e1604").restrictions("string").build();

        Mockito.when(healthIdCreationByAdharService
                .confirmOTP(Mockito.anyMap(), Mockito.any(ConfirmOtpAadhaarDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/verifyOTPForAadhaar").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(confirmOtpAadhaarDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<ConfirmOtpRespAadhaar> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        ConfirmOtpRespAadhaar actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<ConfirmOtpRespAadhaar>() {
                });
        assertThat(actualResponse).isEqualTo(confirmOtpRespAadhaar);

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Create & Save HealthID by Aadhaar OTP by making POST request to endpoint - /helathIDcreationForAadhaar")
    public void saveHealthIdDetailsPositiveTest() throws Exception {
        MainResponseDTO<HealthIDResp> mainResponse = getMainResponse();
        HealthIDResp healthIDResp = getHealthIDResponse();
        mainResponse.setResponse(healthIDResp);

        Mockito.when(healthIdCreationByAdharService
                .healthIdCreationByAdharService(Mockito.anyMap(), Mockito.any(HealthIDAadhaarDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/helathIDcreationForAadhaar").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getHealthIDAadhaarDTO())))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<HealthIDResp> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        HealthIDResp actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<HealthIDResp>() {
                });
        assertThat(actualResponse).isEqualTo(healthIDResp);

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Generate mobile OTP making POST request to endpoint - /generateMobileOTP")
    public void generateMobileOTPPositiveTest() throws Exception {
        MainResponseDTO<GenerateOtpResp> mainResponse = getMainResponse();
        GenerateOtpResp generateOtpResp = GenerateOtpResp.builder()
                .txnId("c2b57029-e678-45e0-84fa-0b89930e1604").error(null).errorCode(null).build();
        mainResponse.setResponse(generateOtpResp);

        GenerateMobileOTP generateMobileOTP = GenerateMobileOTP.builder()
                .mobile("9999111777").txnId("4aecd454-c028-4b1b-8291-a7e56622d3cc").build();

        Mockito.when(healthIdCreationByAdharService
                .generateMobileOTP(Mockito.anyMap(), Mockito.any(GenerateMobileOTP.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/generateMobileOTP").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateMobileOTP)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<GenerateOtpResp> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        GenerateOtpResp actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<GenerateOtpResp>() {
                });
        assertThat(actualResponse).isEqualTo(generateOtpResp);

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Verify mobile OTP making POST request to endpoint - /verifyMobileOTP")
    public void verifyMobileOTPPositiveTest() throws Exception {
        MainResponseDTO<ConfirmOtpRespAadhaar> mainResponse = getMainResponse();
        ConfirmOtpRespAadhaar confirmOtpRespAadhaar = ConfirmOtpRespAadhaar.builder()
                .txnId("050bedb1-f8ca-4d69-89e1-bbba57422af8").error(null).errorCode(null).build();
        mainResponse.setResponse(confirmOtpRespAadhaar);

        ConfirmOtpDTO confirmOtpDTO  = ConfirmOtpDTO.builder()
                .otp("680639").txnId("4aecd454-c028-4b1b-8291-a7e56622d3cc").build();

        Mockito.when(healthIdCreationByAdharService
                .verifyMobileOTP(Mockito.anyMap(), Mockito.any(ConfirmOtpDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/verifyMobileOTP").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(confirmOtpDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<ConfirmOtpRespAadhaar> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        ConfirmOtpRespAadhaar actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<ConfirmOtpRespAadhaar>() {
                });
        assertThat(actualResponse).isEqualTo(confirmOtpRespAadhaar);

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Create HealthID Pre-Verified by making POST request to endpoint - /helathIDcreationForPreVerified")
    public void helathIDcreationForPreVerifiedPositiveTest() throws Exception {
        MainResponseDTO<CreateHealthIdPreverifiedRespDTO> mainResponse = getMainResponse();
        CreateHealthIdPreverifiedRespDTO preVerifiedResp = getCreateHealthIdPreverifiedRespDTO();
        mainResponse.setResponse(preVerifiedResp);

        Mockito.when(healthIdCreationByAdharService
                .helathIDcreationForPreVerified(Mockito.anyMap(), Mockito.any(CreateHealthIdPreverifiedRequestDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/helathIDcreationForPreVerified").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCreateHealthIdPreverifiedRequestDTO())))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<CreateHealthIdPreverifiedRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        CreateHealthIdPreverifiedRespDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<CreateHealthIdPreverifiedRespDTO>() {
                });
        assertThat(actualResponse).isEqualTo(preVerifiedResp);

        logger.info("response: {}",actualResponse);
    }

    private static HealthIDAadhaarDTO getHealthIDAadhaarDTO() {
        return HealthIDAadhaarDTO.builder()
                .email("johndoe@example.com")
                .firstName("John")
                .middleName("W")
                .lastName("Doe")
                .mobile("9999111777")
                .otp("839179")
                .password("John$4de")
                .profilePhoto("string")
                .restrictions("string")
                .txnId("e4433ab9-7af4-4c9c-af88-739d66ff3336")
                .username("johnnew")
                .build();
    }

    private static HealthIDResp getHealthIDResponse() {
        return HealthIDResp.builder()
                .token("string")
                .healthIdNumber("11-1234-5678-7890")
                .name("John W Doe")
                .gender("M")
                .yearOfBirth("1991")
                .monthOfBirth("1")
                .dayOfBirth("1")
                .firstName("John").healthId("johnnew@sbx")
                .lastName("Doe")
                .middleName("W")
                .stateCode("27")
                .districtCode("490")
                .stateName("Maharashtra")
                .districtName("Pune")
                .email("johndoe@example.com")
                .kycPhoto("string")
                .profilePhoto("string")
                .mobile("9999111777")
                .authMethods(Arrays.asList("AADHAAR_OTP","PASSWORD","AADHAAR_BIO","DEMOGRAPHICS"))
                .tags(TagsDTO.builder().build())
                .New(true)
                .build();
    }

    private static CreateHealthIdPreverifiedRequestDTO getCreateHealthIdPreverifiedRequestDTO() {
        return CreateHealthIdPreverifiedRequestDTO.builder()
                .email("johndoe@example.com")
                .firstName("John")
                .healthId("johnnew")
                .lastName("Doe")
                .middleName("W")
                .password("John$4de")
                .profilePhoto("string")
                .txnId("e4433ab9-7af4-4c9c-af88-739d66ff3336")
                .build();
    }

    private static CreateHealthIdPreverifiedRespDTO getCreateHealthIdPreverifiedRespDTO() {
        return CreateHealthIdPreverifiedRespDTO.builder()
                .token("string")
                .healthIdNumber("11-1234-5678-7890")
                .name("John W Doe")
                .gender("M")
                .yearOfBirth("1991")
                .monthOfBirth("1")
                .dayOfBirth("1")
                .firstName("John")
                .healthId("johnnew@sbx")
                .lastName("Doe")
                .middleName("W")
                .stateCode("27")
                .districtCode("490")
                .stateName("Maharashtra")
                .districtName("Pune")
                .email("johndoe@example.com")
                .kycPhoto("string")
                .profilePhoto("string")
                .mobile("9999111777")
                .authMethods(Arrays.asList("AADHAAR_OTP","PASSWORD","AADHAAR_BIO","DEMOGRAPHICS"))
                .tags(TagsDTO.builder().build())
                .New(true)
                .build();
    }
}