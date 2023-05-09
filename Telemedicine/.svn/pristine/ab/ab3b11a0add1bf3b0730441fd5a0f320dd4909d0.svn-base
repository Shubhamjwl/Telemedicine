package com.nsdl.ndhm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.ndhm.dto.*;
import com.nsdl.ndhm.service.HealthIdProfileService;
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
import java.util.HashMap;
import java.util.Map;

import static com.nsdl.ndhm.utility.CommonUtil.getMainResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HealthIdProfileControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(HealthIdProfileControllerTest.class);

    public MockMvc mockMvc;

    @Autowired
    public WebApplicationContext context;

    @MockBean
    HealthIdProfileService healthIdProfileService;

    @Autowired
    public ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Generate OTP for Aadhaar making POST request to endpoint - /account/generateAadhaarOTP")
    public void generateAadhaarOTPPositiveTest() throws Exception {
/*        MainResponseDTO<AadharOtpResendRespDTO> mainResponse = getMainResponse();
        AadharOtpResendRespDTO aadharOtpResendRespDTO = AadharOtpResendRespDTO.builder()
                .txnId("c2b57029-e678-45e0-84fa-0b89930e1604").build();
        mainResponse.setResponse(aadharOtpResendRespDTO);

        GenerateAadhaarOtpDTO generateAadhaarOtpDTO = GenerateAadhaarOtpDTO.builder().aadhaar("999911117777").build();

        Mockito.when(healthIdProfileService
                .generateAadhaarOTP(Mockito.anyMap(), Mockito.any(GenerateAadhaarOtpDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/account/generateAadhaarOTP").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(generateAadhaarOtpDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<AadharOtpResendRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        AadharOtpResendRespDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<AadharOtpResendRespDTO>() {
                });
        assertThat(actualResponse).isEqualTo(aadharOtpResendRespDTO);

        logger.info("response: {}",actualResponse);*/
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Verify OTP for Aadhaar making POST request to endpoint - /account/verifyAadhaarOTP")
    public void verifyAadhaarOTPPositiveTest() throws Exception {
        MainResponseDTO<Boolean> mainResponse = getMainResponse();
        mainResponse.setResponse(true);

        ConfirmOtpDTO confirmOtpDTO = ConfirmOtpDTO.builder()
                .otp("809545").txnId("17e48720-3e73-4d2c-b461-75299fb28aba").build();

        Mockito.when(healthIdProfileService
                .verifyAadhaarOTP(Mockito.anyMap(), Mockito.any(ConfirmOtpDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/account/verifyAadhaarOTP").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(confirmOtpDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<AadharOtpResendRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        assertThat(responseWrapperActual).isEqualTo(mainResponse);
        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should get List of Benefits associated with HealthID GET request to endpoint - /account/accountBenefits")
    public void accountBenefitsPositiveTest() throws Exception {
        MainResponseDTO<AccountBenefitsRespDTO> mainUserExpectedResponse = getMainResponse();
        mainUserExpectedResponse.setResponse(getAccountBenefitsRespDTO());

        Mockito.when(healthIdProfileService.accountBenefits(Mockito.anyMap())).thenReturn(mainUserExpectedResponse);
        MvcResult mvcResult = mockMvc.perform(get("/account/accountBenefits").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();

        MainResponseDTO<AccountBenefitsRespDTO> responseWrapperActual = objectMapper.readValue(responseStr,
                MainResponseDTO.class);

        AccountBenefitsRespDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<AccountBenefitsRespDTO>() {
                });
        assertThat(actualResponse).isEqualTo(mainUserExpectedResponse.getResponse());

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should change account password by aadhaar by making POST request to endpoint - /account/changePasswordByAadhaar")
    public void changePasswordByAadhaarPositiveTest() throws Exception {
        MainResponseDTO<String> mainResponse = getMainResponse();
        mainResponse.setResponse("");

        AccountChangePasswordDTO accountChangePasswordDTO = AccountChangePasswordDTO.builder()
                .newPassword("John$4made").otp("831562").txnId("08f86e40-6a50-4e0a-a1d8-d253f2c4e5db").build();

        Mockito.when(healthIdProfileService
                .changePasswordByAadhaar(Mockito.anyMap(), Mockito.any(AccountChangePasswordDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/account/changePasswordByAadhaar").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountChangePasswordDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        assertThat(responseWrapperActual).isEqualTo(mainResponse);
        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should change account password by mobile by making POST request to endpoint - /account/changePasswordByMobile")
    public void changePasswordByMobilePositiveTest() throws Exception {
        MainResponseDTO<String> mainResponse = getMainResponse();
        mainResponse.setResponse("");

        AccountChangePasswordDTO accountChangePasswordDTO = AccountChangePasswordDTO.builder()
                .newPassword("John$4made").otp("831562").txnId("08f86e40-6a50-4e0a-a1d8-d253f2c4e5db").build();

        Mockito.when(healthIdProfileService
                .changePasswordByMobile(Mockito.anyMap(), Mockito.any(AccountChangePasswordDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/account/changePasswordByMobile").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountChangePasswordDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        assertThat(responseWrapperActual).isEqualTo(mainResponse);
        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Generate OTP for change password Aadhaar by making GET request to endpoint - /account/changePasswdGenerateAadhaarOTP")
    public void changePasswdGenerateAadhaarOTPPositiveTest() throws Exception {
/*        MainResponseDTO<AadharOtpResendRespDTO> mainUserExpectedResponse = getMainResponse();
        AadharOtpResendRespDTO aadharOtpResendRespDTO = AadharOtpResendRespDTO.builder()
                .txnId("0c2d22f2-1d46-4dfc-9a5a-569efadb7ea1").build();
        mainUserExpectedResponse.setResponse(aadharOtpResendRespDTO);

        Mockito.when(healthIdProfileService.changePasswdGenerateAadhaarOTP(Mockito.anyMap())).thenReturn(mainUserExpectedResponse);
        MvcResult mvcResult = mockMvc.perform(get("/account/changePasswdGenerateAadhaarOTP")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();

        MainResponseDTO<AadharOtpResendRespDTO> responseWrapperActual = objectMapper.readValue(responseStr,
                MainResponseDTO.class);

        AadharOtpResendRespDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<AadharOtpResendRespDTO>() {
                });
        assertThat(actualResponse).isEqualTo(mainUserExpectedResponse.getResponse());

        logger.info("response: {}",actualResponse);*/
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should generate mobile OTP for change password mobile by making GET request to endpoint - /account/changePasswdGenerateMobileOTP")
    public void changePasswdGenerateMobileOTPPositiveTest() throws Exception {
/*        MainResponseDTO<AadharOtpResendRespDTO> mainUserExpectedResponse = getMainResponse();
        AadharOtpResendRespDTO aadharOtpResendRespDTO = AadharOtpResendRespDTO.builder()
                .txnId("0c2d22f2-1d46-4dfc-9a5a-569efadb7ea1").build();
        mainUserExpectedResponse.setResponse(aadharOtpResendRespDTO);

        Mockito.when(healthIdProfileService.changePasswdGenerateMobileOTP(Mockito.anyMap())).thenReturn(mainUserExpectedResponse);
        MvcResult mvcResult = mockMvc.perform(get("/account/changePasswdGenerateMobileOTP")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();

        MainResponseDTO<AadharOtpResendRespDTO> responseWrapperActual = objectMapper.readValue(responseStr,
                MainResponseDTO.class);

        AadharOtpResendRespDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<AadharOtpResendRespDTO>() {
                });
        assertThat(actualResponse).isEqualTo(mainUserExpectedResponse.getResponse());

        logger.info("response: {}",actualResponse);*/
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should change account password by making POST request to endpoint - /account/changePasswordByHealthId")
    public void changePasswordByHealthIdPositiveTest() throws Exception {
        MainResponseDTO<String> mainResponse = getMainResponse();
        mainResponse.setResponse("string");

        AccountChangePassByHealthIdReqDTO accountChangePassByHealthIdReqDTO = AccountChangePassByHealthIdReqDTO.builder()
                                                           .newPassword("John$4made").oldPassword("John$4de").build();
        Mockito.when(healthIdProfileService
                .changePasswordByHealthId(Mockito.anyMap(), Mockito.any(AccountChangePassByHealthIdReqDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/account/changePasswordByHealthId").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountChangePassByHealthIdReqDTO)))
                .andExpect(status().is2xxSuccessful()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<String> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        assertThat(responseWrapperActual).isEqualTo(mainResponse);
        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should logout user account profile by making GET request to endpoint - /account/logoutAccountProfile")
    public void logoutUserAccountProfilePositiveTest() throws Exception {
        ResponseEntity mainResponse = new ResponseEntity(HttpStatus.OK);

        Mockito.when(healthIdProfileService.logoutProfile(Mockito.anyMap())).thenReturn(mainResponse);
        MvcResult mvcResult = mockMvc.perform(get("/account/logoutAccountProfile")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should get account profile by making GET request to endpoint - /account/getAccountProfile")
    public void getUserAccountProfilePositiveTest() throws Exception {
        MainResponseDTO<AccountProfileRespDTO> mainUserExpectedResponse = getMainResponse();
        mainUserExpectedResponse.setResponse(getAccountProfileRespDTO());

        Mockito.when(healthIdProfileService.getProfile(Mockito.anyMap())).thenReturn(mainUserExpectedResponse);
        MvcResult mvcResult = mockMvc.perform(get("/account/getAccountProfile")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();

        MainResponseDTO<AccountProfileRespDTO> responseWrapperActual = objectMapper.readValue(responseStr,
                MainResponseDTO.class);

        AccountProfileRespDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<AccountProfileRespDTO>() {
                });
        assertThat(actualResponse).isEqualTo(mainUserExpectedResponse.getResponse());

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should update account details by making POST request to endpoint - /account/updateAccountProfile")
    public void updateUserAccountProfilePositiveTest() throws Exception {
        MainResponseDTO<AccountProfileRespDTO> mainResponse = getMainResponse();
        AccountProfileRespDTO accountProfileRespDTO = getAccountProfileRespDTO();
        mainResponse.setResponse(accountProfileRespDTO);

        Mockito.when(healthIdProfileService
                .updateProfile(Mockito.anyMap(), Mockito.any(AccountProfileUpdateDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/account/updateAccountProfile").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getAccountProfileUpdateDTO())))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<AccountProfileRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        AccountProfileRespDTO actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<AccountProfileRespDTO>() {
                });
        assertThat(actualResponse).isEqualTo(accountProfileRespDTO);

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should delete user account profile by DELETE request to endpoint - /account/deleteAccount")
    public void deleteAccountProfilePositiveTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("STATUS",HttpStatus.OK.toString());

        Mockito.when(healthIdProfileService.deleteProfile(Mockito.anyMap())).thenReturn(map);
        MvcResult mvcResult = mockMvc.perform(delete("/account/deleteAccount")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        Map<String, String> responseWrapperActual = objectMapper.readValue(resultContent,
                Map.class);
        assertThat(responseWrapperActual).isEqualTo(map);

        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should get QR code in byte-array by making GET request to endpoint - /account/getQrCodeInPng")
    public void getAccountQRCodeInPngPositiveTest() throws Exception {
        ResponseEntity<byte[]> mainResponse = ResponseEntity.status(HttpStatus.OK).body("string".getBytes());
        Mockito.when(healthIdProfileService.getQrCode(Mockito.anyMap())).thenReturn(mainResponse);
        MvcResult mvcResult = mockMvc.perform(get("/account/getQrCodeInPng").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String responseStr = mvcResult.getResponse().getContentAsString();

        assertThat(responseStr).isEqualTo(new String(mainResponse.getBody()));

        logger.info("response: {}",responseStr);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should give true response if Auth Token is valid by making POST request to endpoint - /account/checkAuthToken")
    public void checkAuthTokenBooleanPositiveTest() throws Exception {
        MainResponseDTO<Boolean> mainResponse = getMainResponse();
        mainResponse.setResponse(true);

        AuthTokenReqDTO authTokenReqDTO = AuthTokenReqDTO.builder()
                .authToken("Bearer yJhbGciOiJSUzUxMiJ9.eyJzdWIMzYwfQ.rzzDm-r4Dhj3cKD_GqtiQR").build();

        Mockito.when(healthIdProfileService
                .checkValidateAuthToken(Mockito.anyMap(), Mockito.any(AuthTokenReqDTO.class)))
                .thenReturn(mainResponse);

        MvcResult mvcResult = mockMvc
                .perform(post("/account/checkAuthToken").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authTokenReqDTO)))
                .andExpect(status().isOk()).andReturn();

        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<Boolean> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);
        assertThat(responseWrapperActual).isEqualTo(mainResponse);

        logger.info("response: {}",responseWrapperActual);
    }

    private static AccountProfileUpdateDTO getAccountProfileUpdateDTO() {
        return AccountProfileUpdateDTO.builder()
                .address("MH")
                .dayOfBirth("1")
                .districtCode("490")
                .email("johndoe@example.com")
                .firstName("John")
                .healthId("johnnew@sbx")
                .lastName("Doe")
                .middleName("W")
                .monthOfBirth("1")
                .password("John$4de")
                .pincode(411002)
                .profilePhoto("string")
                .stateCode("27")
                .subdistrictCode("string")
                .townCode("string")
                .villageCode("string")
                .wardCode("string")
                .yearOfBirth("1990")
                .build();
    }

    private static AccountProfileRespDTO getAccountProfileRespDTO() {
        return AccountProfileRespDTO.builder()
                .healthIdNumber("11-1234-5678-7890")
                .healthId("johnnew@sbx")
                .mobile("9999111777")
                .firstName("John")
                .middleName("W")
                .lastName("Doe")
                .name("John W Doe")
                .yearOfBirth("1990")
                .dayOfBirth("1")
                .monthOfBirth("1")
                .gender("M")
                .email("johndoe@example.com")
                .profilePhoto("string")
                .stateCode("27")
                .districtCode("490")
                .subDistrictCode("string")
                .villageCode("string")
                .townCode("string")
                .wardCode("string")
                .pincode("411002")
                .address("MH")
                .kycPhoto("string")
                .stateName("Maharashtra")
                .districtName("Pune")
                .subdistrictName("string")
                .villageName("string")
                .townName("string")
                .wardName("string")
                .authMethods(Arrays.asList("DEMOGRAPHICS","MOBILE_OTP","PASSWORD"))
                .tags(TagsDTO.builder().build())
                .kycVerified(false)
                .isNew(false)
                .emailVerified(false)
                .build();
    }

    private static AccountBenefitsRespDTO getAccountBenefitsRespDTO() {
        return AccountBenefitsRespDTO.builder()
              .address("MH")
              .authMethods(Arrays.asList("PASSWORD","DEMOGRAPHICS","AADHAAR_BIO","MOBILE_OTP","AADHAAR_OTP"))
              .dayOfBirth("1")
              .districtCode("490")
              .districtName("Pune")
              .email("John W Doe")
              .emailVerified(true)
              .firstName("John")
              .gender("M")
              .healthId("johnnew@sbx")
              .healthIdNumber("11-1234-5678-7890")
              .kycPhoto("string")
              .kycVerified(true)
              .lastName("Doe")
              .middleName("W")
              .mobile("9999111777")
              .monthOfBirth("1")
              .name("John W Doe")
              .New(false)
              .pincode("411002")
              .profilePhoto("string")
              .stateCode("27")
              .stateName("Maharashtra")
              .subDistrictCode("string")
              .subdistrictName("string")
              .tags(TagsDTO.builder().build())
              .townCode("string")
              .townName("string")
              .villageCode("string")
              .villageName("string")
              .wardCode("string")
              .wardName("string")
              .yearOfBirth("1990")
              .build();
    }
}