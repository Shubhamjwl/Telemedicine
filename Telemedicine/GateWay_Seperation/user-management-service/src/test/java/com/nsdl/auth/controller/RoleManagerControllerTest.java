package com.nsdl.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.DoctorRegDtlsDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RoleDeleteRequestDto;
import com.nsdl.auth.dto.RoleDeleteResponseDto;
import com.nsdl.auth.dto.RoleDetailsDto;
import com.nsdl.auth.dto.RoleRequestDTO;
import com.nsdl.auth.dto.RoleUpdateRequestDto;
import com.nsdl.auth.service.RoleManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleManagerControllerTest {

	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;

	@Autowired
	public ObjectMapper mapper;

	@MockBean
	private RoleManagementService roleManagementService;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	// @Test
	public void addRolePositiveTest() throws Exception {
		MainResponseDTO<CommonResponseDTO> mainExpectedResponse = new MainResponseDTO<>();
		CommonResponseDTO roleResposeDTO = new CommonResponseDTO();
		roleResposeDTO.setMessage(AuthConstant.ROLE_CREATE_SUCCESS);
		mainExpectedResponse.setResponse(roleResposeDTO);

		MainRequestDTO<RoleRequestDTO> request = new MainRequestDTO<>();
		RoleRequestDTO roleRequestDTO = new RoleRequestDTO();
		roleRequestDTO.setRoleName("TESTROLE");
		request.setRequest(roleRequestDTO);

		String jsonStr = mapper.writeValueAsString(request);
		Mockito.when(roleManagementService.saveRole(roleRequestDTO)).thenReturn(roleResposeDTO);

		MvcResult mvcResult = mockMvc
				.perform(post("/role/create").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> actualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});
		assertThat(actualResponse.getResponse()).isEqualTo(mainExpectedResponse.getResponse());
	}

	//@Test
	public void modifyRolePositiveTest() throws Exception {

		MainResponseDTO<CommonResponseDTO> mainResponseExcepted = new MainResponseDTO<>();
		CommonResponseDTO roleUpdateResponseDto = new CommonResponseDTO();
		roleUpdateResponseDto.setMessage(AuthConstant.ROLE_UPDATE_SUCCESS);
		mainResponseExcepted.setResponse(roleUpdateResponseDto);

		MainRequestDTO<RoleUpdateRequestDto> roleUpdateRequestDto = new MainRequestDTO<>();
		RoleUpdateRequestDto updateRequest = new RoleUpdateRequestDto();
		updateRequest.setRoleName("MAKER");
		updateRequest.setNewRoleName("SYSTEMUSER");
		roleUpdateRequestDto.setRequest(updateRequest);

		Mockito.when(roleManagementService.update(Mockito.any(RoleUpdateRequestDto.class)))
				.thenReturn(mainResponseExcepted);
		String jsonStr = mapper.writeValueAsString(roleUpdateRequestDto);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/role/update").content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc
				.perform(builder)
				.andExpect(status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> actualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});

		assertThat(actualResponse.getResponse()).isEqualTo(mainResponseExcepted.getResponse());
	}

	// @Test
	public void getRolesPositiveTest() throws Exception {
		MainResponseDTO<List<RoleDetailsDto>> mainUserExpectedResponse = new MainResponseDTO<>();
		List<RoleDetailsDto> listOfRoleDetailsExpected = new ArrayList<>();
		RoleDetailsDto roleDetailsDto = new RoleDetailsDto();
		roleDetailsDto.setId(1500);
		roleDetailsDto.setRoleName("DOCTOR");
		listOfRoleDetailsExpected.add(roleDetailsDto);
		mainUserExpectedResponse.setResponse(listOfRoleDetailsExpected);
		Mockito.when(roleManagementService.getRoles()).thenReturn(listOfRoleDetailsExpected);
		MvcResult mvcResult = mockMvc.perform(get("/role/getRoles").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<List<DoctorRegDtlsDTO>> responseWrapperActual = mapper.readValue(responseStr,
				MainResponseDTO.class);
		List<RoleDetailsDto> actualResponse = mapper.convertValue(responseWrapperActual.getResponse(),
				new TypeReference<List<RoleDetailsDto>>() {
				});
		assertThat(actualResponse).isEqualTo(mainUserExpectedResponse.getResponse());
	}

	 @Test
	public void deleteRolePositiveTest() throws Exception {

		MainResponseDTO<CommonResponseDTO> mainResponseExpected = new MainResponseDTO<>();
		CommonResponseDTO roleDeleteResponseDto = new CommonResponseDTO();
		roleDeleteResponseDto.setMessage(AuthConstant.ROLE_DELETE_SUCCESS);
		mainResponseExpected.setResponse(roleDeleteResponseDto);
		MainRequestDTO<RoleDeleteRequestDto> mainroleDeleteRequestDto = new MainRequestDTO<>();
		RoleDeleteRequestDto roleDeleteRequestDto = new RoleDeleteRequestDto();
		roleDeleteRequestDto.setRoleName("testchecker");
		mainroleDeleteRequestDto.setRequest(roleDeleteRequestDto);
		Mockito.when(roleManagementService.deleteRole(mainroleDeleteRequestDto)).thenReturn(mainResponseExpected);
		String jsonStr = mapper.writeValueAsString(mainroleDeleteRequestDto);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/role/delete").content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc
				.perform(builder)
				.andExpect(status().isOk()).andReturn();
		
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<CommonResponseDTO> actualResponse = mapper.readValue(responseStr,
				new TypeReference<MainResponseDTO<CommonResponseDTO>>() {
				});
		assertThat(actualResponse.getResponse()).isEqualTo(mainResponseExpected.getResponse());

	}
}
