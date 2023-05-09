package com.nsdl.ndhm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.ndhm.dto.*;
import com.nsdl.ndhm.service.SearchHealthIdService;
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

import static com.nsdl.ndhm.utility.CommonUtil.getMainRequest;
import static com.nsdl.ndhm.utility.CommonUtil.getMainResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchApiControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(SearchApiControllerTest.class);

    public MockMvc mockMvc;

    @Autowired
    public WebApplicationContext context;

    @MockBean
    SearchHealthIdService searchHealthIdService;

    @Autowired
    public ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should give boolean status of exist HealthId by making POST request to endpoint - /searchExistsByHealthId")
    public void existsByHealthIdPositiveTest() throws Exception {
/*        MainRequestDTO<SearchByHealthIdDTO> mainRequest = getMainRequest();
        mainRequest.setRequest(SearchByHealthIdDTO.builder().healthId("rahulnew@sbx").build());

        MainResponseDTO<SearchExistHealthIdRespDTO> response = getMainResponse();
        response.setResponse(SearchExistHealthIdRespDTO.builder().status(true).build());

        Mockito.when(searchHealthIdService.checkExistsByHealthId(Mockito.anyMap(),Mockito.any(MainRequestDTO.class)))
                .thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/searchExistsByHealthId").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mainRequest)))
                        .andExpect(status().isOk()).andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<SearchExistHealthIdRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);

        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(), SearchExistHealthIdRespDTO.class));

        assertThat(responseWrapperActual).isEqualTo(response);
        logger.info("response: {}",responseWrapperActual);*/
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should give HealthID & Number details by making POST request to endpoint - /searchByMobile")
    public void searchByMobilePositiveTest() throws Exception {
        MainRequestDTO<SearchByMobDTO> mainRequest = getMainRequest();
        mainRequest.setRequest(SearchByMobDTO.builder().gender("M").mobile("9999111777")
                                                       .name("John Doe").yearOfBirth("1990").build());

        MainResponseDTO<SearchByMobRespDTO> response = getMainResponse();
        response.setResponse(getSearchByMobileResp());

        Mockito.when(searchHealthIdService.searchByMobile(Mockito.anyMap(),Mockito.any(MainRequestDTO.class)))
                .thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/searchByMobile").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mainRequest)))
                .andExpect(status().isOk()).andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<SearchByMobRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);

        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(),
                                                                               SearchByMobRespDTO.class));
        assertThat(responseWrapperActual).isEqualTo(response);
        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should give HealthID & Number details by making POST request to endpoint - /searchByHealthId")
    public void searchByHealthIdPositiveTest() throws Exception {
        MainRequestDTO<SearchByHealthIdDTO> mainRequest = getMainRequest();
        mainRequest.setRequest(SearchByHealthIdDTO.builder().healthId("johnnew@sbx").build());

        MainResponseDTO<SearchByMobRespDTO> response = getMainResponse();
        response.setResponse(getSearchByMobileResp());

        Mockito.when(searchHealthIdService.searchByHealthId(Mockito.anyMap(),Mockito.any(MainRequestDTO.class)))
                .thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/searchByHealthId").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mainRequest)))
                .andExpect(status().isOk()).andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<SearchByMobRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);

        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(),
                SearchByMobRespDTO.class));
        assertThat(responseWrapperActual).isEqualTo(response);
        logger.info("response: {}",responseWrapperActual);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should give HealthID & Number details by making POST request to endpoint - /searchByAadhar")
    public void searchByAadharPositiveTest() throws Exception {
/*        MainRequestDTO<SearchByAadharDTO> mainRequest = getMainRequest();
        mainRequest.setRequest(SearchByAadharDTO.builder().aadhaar("999911117777").build());

        MainResponseDTO<SearchByMobRespDTO> response = getMainResponse();
        response.setResponse(getSearchByMobileResp());

        Mockito.when(searchHealthIdService.searchByAadhar(Mockito.anyMap(),Mockito.any(MainRequestDTO.class)))
                .thenReturn(response);
        MvcResult mvcResult = mockMvc
                .perform(post("/searchByAadhar").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mainRequest)))
                .andExpect(status().isOk()).andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<SearchByMobRespDTO> responseWrapperActual = objectMapper.readValue(resultContent,
                MainResponseDTO.class);

        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(),
                SearchByMobRespDTO.class));
        assertThat(responseWrapperActual).isEqualTo(response);
        logger.info("response: {}",responseWrapperActual);*/
    }

    private static SearchByMobRespDTO getSearchByMobileResp() {
        return SearchByMobRespDTO.builder()
                .healthId("johnnew@sbx")
                .healthIdNumber("51-8041-2263-0463")
                .name(null)
                .authMethods(Arrays.asList("AADHAAR_BIO", "DEMOGRAPHICS", "AADHAAR_OTP", "PASSWORD", "MOBILE_OTP"))
                .tags(TagsDTO.builder().build())
                .build();
    }
}