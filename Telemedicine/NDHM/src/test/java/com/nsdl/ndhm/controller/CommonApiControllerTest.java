package com.nsdl.ndhm.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.ndhm.dto.DistrictDTO;
import com.nsdl.ndhm.dto.GenerateSessionRespDTO;
import com.nsdl.ndhm.dto.MainResponseDTO;
import com.nsdl.ndhm.dto.StateDtlsDTO;
import com.nsdl.ndhm.service.CommonApiService;
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

import java.util.ArrayList;
import java.util.List;

import static com.nsdl.ndhm.utility.CommonUtil.getMainResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonApiControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(CommonApiControllerTest.class);

    public MockMvc mockMvc;

    @Autowired
    public WebApplicationContext context;

    @Autowired
    public ObjectMapper objectMapper;

    @MockBean
    private CommonApiService commonApiService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should Generate Tokens making POST request to endpoint - /generateToken")
    public void generateTokenPositiveTest() throws Exception {
        MainResponseDTO<GenerateSessionRespDTO> response = getMainResponse();
        GenerateSessionRespDTO generateOtpResp = GenerateSessionRespDTO.builder()
                .accessToken("string")
                .expiresIn("600")
                .refreshExpiresIn("1800")
                .refreshToken("string")
                .tokenType("bearer")
                .errorCode(null)
                .error(null)
                .build();
        response.setResponse(generateOtpResp);
        Mockito.when(commonApiService.generateToken()).thenReturn(response);

        MvcResult mvcResult = mockMvc
                .perform(post("/generateToken").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String resultContent = mvcResult.getResponse().getContentAsString();

        MainResponseDTO<GenerateSessionRespDTO> responseWrapperActual = objectMapper.readValue(resultContent, MainResponseDTO.class);
        responseWrapperActual.setResponse(objectMapper.convertValue(responseWrapperActual.getResponse(), GenerateSessionRespDTO.class));
        assertThat(responseWrapperActual).isEqualTo(response);

        logger.info("response: {}",response);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should get States List making GET request to endpoint - /states")
    public void getStatesPositiveTest() throws Exception {
        MainResponseDTO<List<StateDtlsDTO>> mainUserExpectedResponse = getMainResponse();
        List<StateDtlsDTO> listOfRoleDetailsExpected = new ArrayList<>();
        StateDtlsDTO stateDtlsDTO = StateDtlsDTO.builder().build();
        stateDtlsDTO.setCode("35");
        stateDtlsDTO.setName("ANDAMAN AND NICOBAR ISLANDS");

        List<DistrictDTO> districts = new ArrayList<>();
        DistrictDTO districtDTO1 = DistrictDTO.builder().build();
        districtDTO1.setCode("603");
        districtDTO1.setName("NICOBARS");

        DistrictDTO districtDTO2 = DistrictDTO.builder().build();
        districtDTO2.setCode("632");
        districtDTO2.setName("NORTH AND MIDDLE ANDAMAN");

        districts.add(districtDTO1);
        districts.add(districtDTO2);

        stateDtlsDTO.setDistricts(districts);
        listOfRoleDetailsExpected.add(stateDtlsDTO);
        mainUserExpectedResponse.setResponse(listOfRoleDetailsExpected);

        Mockito.when(commonApiService.getStates(Mockito.anyMap())).thenReturn(mainUserExpectedResponse);
        MvcResult mvcResult = mockMvc.perform(get("/states").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();

        MainResponseDTO<List<StateDtlsDTO>> responseWrapperActual = objectMapper.readValue(responseStr,
                MainResponseDTO.class);

        List<StateDtlsDTO> actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<List<StateDtlsDTO>>() {
                });
        assertThat(actualResponse).isEqualTo(mainUserExpectedResponse.getResponse());

        logger.info("response: {}",actualResponse);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Should get District List making GET request to endpoint - /districts")
    public void getDistrictsTest() throws Exception {
/*        MainResponseDTO<List<DistrictDTO>> mainUserExpectedResponse = getMainResponse();
        List<DistrictDTO> listOfRoleDetailsExpected = new ArrayList<>();
        DistrictDTO districtDTO1 = DistrictDTO.builder().build();
        districtDTO1.setCode("603");
        districtDTO1.setName("NICOBARS");

        DistrictDTO districtDTO2 = DistrictDTO.builder().build();
        districtDTO2.setCode("632");
        districtDTO2.setName("NORTH AND MIDDLE ANDAMAN");

        listOfRoleDetailsExpected.add(districtDTO1);
        listOfRoleDetailsExpected.add(districtDTO2);

        mainUserExpectedResponse.setResponse(listOfRoleDetailsExpected);

        Mockito.when(commonApiService.getDistricts(Mockito.anyMap(), Mockito.anyString())).thenReturn(mainUserExpectedResponse);
        MvcResult mvcResult = mockMvc.perform(get("/districts").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String responseStr = mvcResult.getResponse().getContentAsString();
        MainResponseDTO<List<DistrictDTO>> responseWrapperActual = objectMapper.readValue(responseStr,
                MainResponseDTO.class);
        List<DistrictDTO> actualResponse = objectMapper.convertValue(responseWrapperActual.getResponse(),
                new TypeReference<List<DistrictDTO>>() {
                });
        assertThat(actualResponse).isEqualTo(mainUserExpectedResponse.getResponse());

        logger.info("response: {}",actualResponse);*/
    }
}