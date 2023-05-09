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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.auth.dto.ActiveDeactiveResponseDTO;
import com.nsdl.auth.dto.FunctionActiveDeactiveDTO;
import com.nsdl.auth.dto.Functions;
import com.nsdl.auth.dto.GetMenuResponseDTO;
import com.nsdl.auth.dto.MainMenu;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RoleFunctionFetchRequestDTO;
import com.nsdl.auth.dto.RoleFunctionRequestDTO;
import com.nsdl.auth.dto.RoleFunctionResponseDTO;
import com.nsdl.auth.dto.SubFunctions;
import com.nsdl.auth.service.RoleFunctionManagementService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleFunctionManagerControllerTest {
	
	public MockMvc mockMvc;

	@Autowired
	public WebApplicationContext context;
	
	@Autowired
	public ObjectMapper objectMapper;
	
	@MockBean
	public RoleFunctionManagementService roleFunctionManagementService;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void addFunctionPositiveTest() throws Exception {
		
		MainResponseDTO<RoleFunctionResponseDTO> expectedResponse = new MainResponseDTO<>();
		RoleFunctionResponseDTO roleFunctionResponseDTO = new RoleFunctionResponseDTO();
		roleFunctionResponseDTO.setStatus("ACTIVE");
		roleFunctionResponseDTO.setRoleFunctionId(6608);
		expectedResponse.setResponse(roleFunctionResponseDTO);
		
		MainRequestDTO<RoleFunctionRequestDTO> request = new MainRequestDTO<>();
		request.setId("user");
		request.setVersion("1.0");
		RoleFunctionRequestDTO roleFunctionRequestDTO = new RoleFunctionRequestDTO();
		roleFunctionRequestDTO.setFunctionName("view payment");
		roleFunctionRequestDTO.setFunctionUri("/viewPayment");
		roleFunctionRequestDTO.setRoleName("ADMIN");
		request.setRequest(roleFunctionRequestDTO);
		
		Mockito.when(roleFunctionManagementService.saveFunction(roleFunctionRequestDTO)).thenReturn(roleFunctionResponseDTO);
		String requestStr = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc.perform(post("/roleFunction/create").content(requestStr).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String resultContent = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<RoleFunctionResponseDTO> actualResponse = objectMapper.readValue(resultContent, new TypeReference<MainResponseDTO<RoleFunctionResponseDTO>>() {
		});
		
		assertThat(actualResponse.getResponse()).isEqualTo(expectedResponse.getResponse());
	}
	
	@Test
	public void getFunctionListByRoleNamePositiveTest() throws Exception {
		
		MainResponseDTO<GetMenuResponseDTO> responseExpected = new MainResponseDTO<>();
		responseExpected.setId("user");
		responseExpected.setStatus(true);
		responseExpected.setVersion("1.0");
		
		GetMenuResponseDTO responseEnt = new GetMenuResponseDTO();
		List<MainMenu> mainMenuList = new ArrayList<MainMenu>();
		MainMenu mainMenu = new MainMenu();
		List<Functions> funcList = new ArrayList<Functions>();
		Functions functions = new Functions();
		List<SubFunctions> subMenuList = new ArrayList<SubFunctions>();
		SubFunctions subFunctions = new SubFunctions();
		subMenuList.add(subFunctions);
		functions.setFunctionName("Verify Doctor Details");
		functions.setRoute("checker");
		functions.setSubMenu(subMenuList);
		funcList.add(functions);
		mainMenu.setMainMenuName("Verify Doctor");
		mainMenu.setFunctions(funcList);
		mainMenuList.add(mainMenu);
		responseEnt.setRoleName("checker");
		responseEnt.setMainMenu(mainMenuList);
		responseExpected.setResponse(responseEnt);
		
		MainRequestDTO<RoleFunctionFetchRequestDTO> mainRequest = new MainRequestDTO<>();
		RoleFunctionFetchRequestDTO roleFunctionFetchRequestDTO = new RoleFunctionFetchRequestDTO();
		roleFunctionFetchRequestDTO.setRoleName("checker");
		mainRequest.setRequest(roleFunctionFetchRequestDTO);
		
		Mockito.when(roleFunctionManagementService.getFunctionListByRoleName(roleFunctionFetchRequestDTO)).thenReturn(responseEnt);
		
		String jsonStr = objectMapper.writeValueAsString(mainRequest);
		MvcResult mvcResult = mockMvc
				.perform(post("/roleFunction/getFunctions").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<GetMenuResponseDTO> mainActualResponse  = objectMapper.readValue(responseStr, new TypeReference<MainResponseDTO<GetMenuResponseDTO>>() {
		});
		
		assertThat(mainActualResponse.getResponse()).isEqualTo(responseExpected.getResponse());
	}
	
	@Test
	public void activateDeactivateRoleFunctionMappingPositiveTest() throws Exception {
		
		MainResponseDTO<ActiveDeactiveResponseDTO> mainResponseExpected = new MainResponseDTO<>();
		ActiveDeactiveResponseDTO activeDeactiveResponseDTO = new ActiveDeactiveResponseDTO();
		activeDeactiveResponseDTO.setMessage("Activated Successfully");
		activeDeactiveResponseDTO.setFunctionName("SAVE DOCTOR");
		mainResponseExpected.setResponse(activeDeactiveResponseDTO);
		
		MainRequestDTO<FunctionActiveDeactiveDTO> request = new MainRequestDTO<>();
		FunctionActiveDeactiveDTO functionActiveDeactiveDTO = new FunctionActiveDeactiveDTO();
		functionActiveDeactiveDTO.setRole("ADMIN");
		functionActiveDeactiveDTO.setOperationType("ACTIVATE");
		functionActiveDeactiveDTO.setFunctionName("SAVE DOCTOR");
		request.setRequest(functionActiveDeactiveDTO);
		
		Mockito.when(roleFunctionManagementService.activeDeactiveFunctionRoleService(request)).thenReturn(mainResponseExpected);
		String jsonStr = objectMapper.writeValueAsString(request);
		MvcResult mvcResult = mockMvc
				.perform(post("/roleFunction/activeDeactiveRoleFunctionMapping").content(jsonStr).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		String responseStr = mvcResult.getResponse().getContentAsString();
		MainResponseDTO<ActiveDeactiveResponseDTO> mainActualResponse  = objectMapper.readValue(responseStr, new TypeReference<MainResponseDTO<ActiveDeactiveResponseDTO>>() {
		});
		
		assertThat(mainActualResponse.getResponse()).isEqualTo(mainResponseExpected.getResponse());
		
	}
	
	@Test
	public void getrolefunctionmappingPositiveTest() throws Exception {
		String expected = "Allfuntionrolemappingdetails";
		Mockito.when(roleFunctionManagementService.getAllRoleFunctionMapping()).thenReturn(expected);
		
		MvcResult mvcResult = mockMvc
				.perform(get("/roleFunction/getAllRoleFunctionsMapping").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String responseStr = mvcResult.getResponse().getContentAsString();
		
		assertThat(responseStr).isEqualTo(expected);
		
	}
}
