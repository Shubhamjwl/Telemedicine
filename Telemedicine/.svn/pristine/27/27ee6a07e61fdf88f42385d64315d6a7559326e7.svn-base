package com.nsdl.ndhm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.ndhm.dto.*;
import com.nsdl.ndhm.service.ForgotHealthIdAndNumService;
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

import static com.nsdl.ndhm.utility.CommonUtil.getMainResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForgotHealthIdAndNumControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(ForgotHealthIdAndNumControllerTest.class);

    public MockMvc mockMvc;

    @Autowired
    public WebApplicationContext context;

    @MockBean
    ForgotHealthIdAndNumService forgotHealthIdAndNumService;

    @Autowired
    public ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should generate Aadhaar OTP for forgot HealthId by making POST request to endpoint - /forgotHealthIdAadhaarOTP")
    public void genForgotHealthIdAadhaarOTPPositiveTest() throws Exception {
        MainResponseDTO<GenerateOtpResp> response = getMainResponse();
        response.setResponse(GenerateOtpResp.builder().txnId("9326f3e2-77c5-4f96-9f65-854bdf1801db").build());

        GenerateOtpDTO generateAadhaarOtpDTO  = GenerateOtpDTO.builder().build();
        generateAadhaarOtpDTO.setAadhaar("999911117777");

        Mockito.when(forgotHealthIdAndNumService.generateAadhaarOTP(Mockito.anyMap(),Mockito.any(GenerateOtpDTO.class)))
                .thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/forgotHealthIdAadhaarOTP").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateAadhaarOtpDTO)))
                .andExpect(status().isOk()).andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<GenerateOtpResp> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);

        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(),
                GenerateOtpResp.class));
        assertThat(responseWrapperActual).isEqualTo(response);
        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should give HealthId & Number by making POST request to endpoint - /getForgotHealthIdAadhaar")
    public void genForgotHealthIdAadhaarPositiveTest() throws Exception {
        MainResponseDTO<GenerateHealthIdAndNumRespDTO> response = getMainResponse();
        response.setResponse(getHealthIdAndNumResponse());

        ConfirmOtpDTO confirmOtpDTO  =
                ConfirmOtpDTO.builder().otp("899406").txnId("9326f3e2-77c5-4f96-9f65-854bdf1801db").build();

        Mockito.when(forgotHealthIdAndNumService.getHealthIdByAadhaar(Mockito.anyMap(),Mockito.any(ConfirmOtpDTO.class)))
                .thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/getForgotHealthIdAadhaar").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(confirmOtpDTO)))
                .andExpect(status().isOk()).andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<GenerateHealthIdAndNumRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);

        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(),
                GenerateHealthIdAndNumRespDTO.class));
        assertThat(responseWrapperActual).isEqualTo(response);
        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should generate mobile OTP for forgot HealthId by making POST request to endpoint - /forgotHealthIdMobileOTP")
    public void genForgotHealthIdMobileOTPPositiveTest() throws Exception {
        MainResponseDTO<GenerateOtpResp> response = getMainResponse();
        response.setResponse(GenerateOtpResp.builder().txnId("9326f3e2-77c5-4f96-9f65-854bdf1801db").build());

        GenerateOtpDTO generateAadhaarOtpDTO  = GenerateOtpDTO.builder().build();
        generateAadhaarOtpDTO.setMobile("9999111777");

        Mockito.when(forgotHealthIdAndNumService.generateMobileOTP(Mockito.anyMap(),Mockito.any(GenerateOtpDTO.class)))
                .thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/forgotHealthIdMobileOTP").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateAadhaarOtpDTO)))
                .andExpect(status().isOk()).andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<GenerateOtpResp> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);

        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(),
                GenerateOtpResp.class));
        assertThat(responseWrapperActual).isEqualTo(response);
        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should give HealthId & Number by making POST request to endpoint - /getForgotHealthIdMobile")
    public void genForgotHealthIdMobilePositiveTest() throws Exception {
        MainResponseDTO<GenerateHealthIdAndNumRespDTO> response = getMainResponse();
        response.setResponse(getHealthIdAndNumResponse());

        ForgotHealthByMobileReqDTO forgotHealthByMobileReq = ForgotHealthByMobileReqDTO.builder()
                .dayOfBirth("1")
                .firstName("John")
                .gender("M")
                .lastName("Doe")
                .middleName("J")
                .monthOfBirth("2")
                .name("John J Doe")
                .otp("353917")
                .txnId("6d5e3dff-4c39-4783-b5b2-a9a489867b2c")
                .yearOfBirth("1990")
                .build();

        Mockito.when(forgotHealthIdAndNumService.getHealthIdByMobile(Mockito.anyMap(),Mockito.any(ForgotHealthByMobileReqDTO.class)))
                .thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/getForgotHealthIdMobile").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(forgotHealthByMobileReq)))
                .andExpect(status().isOk()).andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<GenerateHealthIdAndNumRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);

        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(),
                GenerateHealthIdAndNumRespDTO.class));
        assertThat(responseWrapperActual).isEqualTo(response);
        logger.info("response: {}",responseWrapperActual);
    }

    private static GenerateHealthIdAndNumRespDTO getHealthIdAndNumResponse() {
        return GenerateHealthIdAndNumRespDTO.builder().healthId("johnnew@sbx").healthIdNumber("51-8041-2263-0463").build();
    }
}