package com.nsdl.ndhm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.ndhm.dto.*;
import com.nsdl.ndhm.service.HealthIdCreationByMobileService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static com.nsdl.ndhm.utility.CommonUtil.getMainRequest;
import static com.nsdl.ndhm.utility.CommonUtil.getMainResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HealthIdCreationByMobileNoControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(HealthIdCreationByMobileNoControllerTest.class);

    public MockMvc mockMvc;

    @Autowired
    public WebApplicationContext context;

    @MockBean
    HealthIdCreationByMobileService healthIdCreationByMobileService;

    @Autowired
    public ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should generate Mobile OTP by making POST request to endpoint - /generateOTPForMobile")
    public void generateOTPPositiveTest() throws Exception {
        MainResponseDTO<GenerateOtpResp> mainResponse = getMainResponse();
        GenerateOtpResp generateOtpResp = GenerateOtpResp.builder()
                .txnId("c2b57029-e678-45e0-84fa-0b89930e1604")
                .error(null).errorCode(null).build();
        mainResponse.setResponse(generateOtpResp);

        GenerateOtpDTO generateOtpDTO = GenerateOtpDTO.builder().mobile("9999111777").build();

        Mockito.when(healthIdCreationByMobileService
                .generateOTP(Mockito.anyMap(), Mockito.any(GenerateOtpDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/generateOTPForMobile").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateOtpDTO)))
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
    @DisplayName("Should Resend OTP by making POST request to endpoint - /resendOTPForMobile")
    public void resendOTPForMobilePositiveTest() throws Exception {
        MainResponseDTO<ResendOtpResp> mainResponse = getMainResponse();
        ResendOtpResp resendOtpResp = ResendOtpResp.builder().resendStatus(true).build();
        mainResponse.setResponse(resendOtpResp);

        ResendOtpDTO resendOtpDTO = ResendOtpDTO.builder().txnId("3687d7d7-e779-418d-8e26-66d8971044f1").build();

        Mockito.when(healthIdCreationByMobileService.resendOTP(Mockito.anyMap(), Mockito.any(ResendOtpDTO.class)))
                                                                 .thenReturn(mainResponse);
        MvcResult mvcResult = mockMvc
                .perform(post("/resendOTPForMobile").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resendOtpDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<ResendOtpResp> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        ResendOtpResp actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<ResendOtpResp>() {
                });
        assertThat(actualResponse).isEqualTo(resendOtpResp);

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Verify Mobile OTP by making POST request to endpoint - /verifyOTPForMobile")
    public void confirmOTPPositiveTest() throws Exception {
        MainResponseDTO<ConfirmOtpResp> mainResponse = getMainResponse();
        ConfirmOtpResp confirmOtpResp = ConfirmOtpResp.builder().token("eyJhbGciOiJSeyJiI5MTU2NTI5ODkCJpYXQ").build();
        mainResponse.setResponse(confirmOtpResp);

        ConfirmOtpDTO confirmOtpDTO = ConfirmOtpDTO.builder().otp("015303")
                                                             .txnId("3687d7d7-e779-418d-8e26-66d8971044f1").build();
        Mockito.when(healthIdCreationByMobileService.confirmOTP(Mockito.anyMap(), Mockito.any(ConfirmOtpDTO.class)))
                .thenReturn(mainResponse);
        MvcResult mvcResult = mockMvc
                .perform(post("/verifyOTPForMobile").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(confirmOtpDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<ConfirmOtpResp> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        ConfirmOtpResp actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<ConfirmOtpResp>() {
                });
        assertThat(actualResponse).isEqualTo(confirmOtpResp);

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Create HealthID by Mobile OTP by making POST request to endpoint - /helathIDcreationForMobile")
    public void saveHealthIdDetailsPositiveTest() throws Exception {
        MainResponseDTO<HealthIDResp> mainResponse = getMainResponse();
        mainResponse.setResponse(getHealthIDResponse());

        MainRequestDTO<HealthIDDTO> mainRequest = getMainRequest();
        mainRequest.setRequest(getHealthIDDTORequest());

        Mockito.when(healthIdCreationByMobileService.saveHealthIdByMobile(Mockito.anyMap(), Mockito.any(MainRequestDTO.class)))
                .thenReturn(mainResponse);
        MvcResult mvcResult = mockMvc
                .perform(post("/helathIDcreationForMobile").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mainRequest)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<HealthIDResp> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(),
                HealthIDResp.class));
        assertThat(responseWrapperActual).isEqualTo(mainResponse);

        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should generate X-Token by making POST request to endpoint - /userAuthWithPassword")
    public void userAuthWithPasswordPositiveTest() throws Exception {
        MainResponseDTO<UserAuthRespDTO> mainResponse = getMainResponse();
        UserAuthRespDTO userAuthRespDTO  = UserAuthRespDTO.builder().token("eyJhbGciOiJSeyJiI5MTU2NTI5ODkCJpYXQ").build();
        mainResponse.setResponse(userAuthRespDTO);

        UserAuthRequestDTO userAuthRequestDTO = UserAuthRequestDTO.builder().healthId("johnnew@sbx")
                                                                            .password("John$4de").build();
        Mockito.when(healthIdCreationByMobileService.userAuthWithPassword(Mockito.anyMap(),
                                                       Mockito.any(UserAuthRequestDTO.class))).thenReturn(mainResponse);
        MvcResult mvcResult =
                 mockMvc.perform(post("/userAuthWithPassword").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userAuthRequestDTO)))
                        .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<UserAuthRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        UserAuthRespDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<UserAuthRespDTO>() {
                });
        assertThat(actualResponse).isEqualTo(userAuthRespDTO);

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should get HealthCard in PDF by making POST request to endpoint - /getHealthCardInPdf")
    public void getHealthCardInPdfPositiveTest() throws Exception {
        ResponseEntity<byte[]> mainResponse = ResponseEntity.status(HttpStatus.OK)
                                                            .body("Byte array initialization".getBytes());

        Mockito.when(healthIdCreationByMobileService.getPdfCard(Mockito.anyMap())).thenReturn(mainResponse);
        MvcResult mvcResult =
                mockMvc.perform(get("/getHealthCardInPdf").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();

        String responseStr = mvcResult.getResponse().getContentAsString();
        assertThat(responseStr).isEqualTo(new String(mainResponse.getBody()));
        logger.info("response: {}",responseStr);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should get HealthCard in PNG by making POST request to endpoint - /getHealthCardInPng")
    public void getHealthCardInPngPositiveTest() throws Exception {
        ResponseEntity<byte[]> mainResponse = ResponseEntity.status(HttpStatus.OK).body("Byte array initialization".getBytes());

        Mockito.when(healthIdCreationByMobileService.getPngCard(Mockito.anyMap())).thenReturn(mainResponse);
        MvcResult mvcResult =
                mockMvc.perform(get("/getHealthCardInPng").contentType(MediaType.APPLICATION_JSON))
                       .andExpect(status().isOk()).andReturn();

        String responseStr = mvcResult.getResponse().getContentAsString();
        assertThat(responseStr).isEqualTo(new String(mainResponse.getBody()));
        logger.info("response: {}",responseStr);
    }

    private static HealthIDDTO getHealthIDDTORequest() {
        return HealthIDDTO.builder()
                .address("MH")
                .dayOfBirth("1")
                .districtCode("490")
                .email("johndoe@example.com")
                .firstName("John")
                .gender("M")
                .healthId("johnnew@sbx")
                .lastName("Doe")
                .middleName("W")
                .monthOfBirth("1")
                .name("John W Doe")
                .password("John$4de")
                .pincode(411045)
                .profilePhoto("string")
                .restrictions("string")
                .stateCode("27")
                .subdistrictCode("string")
                .token("string")
                .townCode("string")
                .txnId("bf573fd4-8367-4354-b4b4-2c115f4b376f")
                .villageCode("string")
                .wardCode("string")
                .yearOfBirth("1990")
                .mobileNo("9999111777")
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
                .authMethods(Arrays.asList("AADHAAR_BIO", "DEMOGRAPHICS", "AADHAAR_OTP",
                                                  "PASSWORD", "MOBILE_OTP"))
                .tags(TagsDTO.builder().build())
                .New(true)
                .build();
    }
}