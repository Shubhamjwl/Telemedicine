/**
 * 
 */
package com.nsdl.telemedicine.scribe.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.telemedicine.scribe.constants.ScribeRegConstants;
import com.nsdl.telemedicine.scribe.dto.MainRequestDTO;
import com.nsdl.telemedicine.scribe.dto.MainResponseDTO;
import com.nsdl.telemedicine.scribe.dto.ScribeRegDTO;
import com.nsdl.telemedicine.scribe.service.ScribeRegistrationService;

/**
 * @author Pegasus_girishk
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ScribeRegistrationControllerTest {

	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;
	
	@Autowired
	public ObjectMapper objectMapper;
	
	@MockBean
	private ScribeRegistrationService scribeRegistrationService;
	
	ScribeRegDTO scribeRegDTO = new ScribeRegDTO();
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		scribeRegDTO.setScrbFullName("SCRIBEUSER");
		scribeRegDTO.setScrbMobNo("9876565656");
		scribeRegDTO.setScrbUserID("SCRIBEUSER");
		scribeRegDTO.setScrbEmail("scribe12@gmail.com");
		scribeRegDTO.setScrbdrUserIDfk("GIRISHDOC");
		scribeRegDTO.setScrbAdd1("Pune");
		scribeRegDTO.setScrbAdd2("Pune");
		scribeRegDTO.setScrbAdd3("Pune");
		scribeRegDTO.setScrbAdd4("Pune");
		scribeRegDTO.setScribeProfilePhoto("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/4RCiRXhpZgAATU0AKgAAAAgABAE7AAIAAAAQAAAISodpAAQAAAABAAAI");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void saveScribeDetailsTest() throws Exception {
		MainRequestDTO<ScribeRegDTO> request = new MainRequestDTO<ScribeRegDTO>();
		request.setRequest(scribeRegDTO);

		MainResponseDTO mainResponseDto = new MainResponseDTO();
		mainResponseDto.setStatus(true);
		mainResponseDto.setResponse(ScribeRegConstants.REGISTRATION_SUCCESS.toString());

		Mockito.when(scribeRegistrationService.saveScribeDetails(request)).thenReturn(mainResponseDto);
		String jsonRequest = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/scribeRegistration").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO responseWrapperActual = objectMapper.readValue(resultContent,MainResponseDTO.class);
		assertThat(responseWrapperActual).isEqualToComparingFieldByField(mainResponseDto);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void updateScribeProfile() throws Exception 
	{ 
		MainRequestDTO<ScribeRegDTO> profileUpdateRequest = new MainRequestDTO<ScribeRegDTO>();
		profileUpdateRequest.setRequest(scribeRegDTO);
		
		MainResponseDTO mainResponseDto = new MainResponseDTO();
		mainResponseDto.setStatus(true);
		mainResponseDto.setResponse("Profile Updated Successfully..");
		
		Mockito.when(scribeRegistrationService.updateScribeProfile(profileUpdateRequest)).thenReturn(mainResponseDto);
		String jsonRequest = objectMapper.writeValueAsString(profileUpdateRequest);
		MvcResult mvcResult = mockMvc.perform(post("/updateScribeProfile").content(jsonRequest).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO responseWrapperActual = objectMapper.readValue(resultContent,MainResponseDTO.class);
		assertThat(responseWrapperActual.getResponse()).isEqualToComparingFieldByField(mainResponseDto.getResponse());
	}
}
