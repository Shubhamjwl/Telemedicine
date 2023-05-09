package com.nsdl.auth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.ActiveDeactiveResponseDTO;
import com.nsdl.auth.dto.FunctionActiveDeactiveDTO;
import com.nsdl.auth.dto.FunctionListDtls;
import com.nsdl.auth.dto.Functions;
import com.nsdl.auth.dto.GetMenuResponseDTO;
import com.nsdl.auth.dto.MainMenu;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RoleFunctionFetchRequestDTO;
import com.nsdl.auth.dto.RoleFunctionRequestDTO;
import com.nsdl.auth.dto.RoleFunctionResponseDTO;
import com.nsdl.auth.dto.SubFunctions;
import com.nsdl.auth.dto.UserDTO;
import com.nsdl.auth.entity.AuditRoleFunctionEntity;
import com.nsdl.auth.entity.LoginUserEntity;
import com.nsdl.auth.entity.RoleFunctionEntity;
import com.nsdl.auth.entity.RoleFunctionMappingEntity;
import com.nsdl.auth.entity.RoleMasterEntity;
import com.nsdl.auth.repository.AuditRoleFunctionRepository;
import com.nsdl.auth.repository.LoginUserRepo;
import com.nsdl.auth.repository.RoleFunctionMappingRepo;
import com.nsdl.auth.repository.RoleFunctionRepository;
import com.nsdl.auth.repository.RoleServiceRepository;

@RunWith(SpringRunner.class)
public class RoleFunctionManagementServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@MockBean
	public RoleFunctionRepository roleFunctionRepository;

	@MockBean
	public RoleServiceRepository roleServiceRepository;

	@InjectMocks
	public RoleFunctionManagementServiceImpl roleFunctionManagementService;

	@MockBean
	AuditRoleFunctionRepository auditRoleFunctionRepository;

	@MockBean
	RoleFunctionMappingRepo roleFunctionMappingRepo;

	@MockBean
	public LoginUserRepo userRepo;

	@MockBean
	public ObjectMapper mapper;

	RoleFunctionEntity roleFunctionMasterEntity;
	AuditRoleFunctionEntity auditRoleFunctionMasterEntity;

	UserDTO userDetails = new UserDTO();

	@Before
	public void setUp() throws ParseException {
		userDetails.setUserName("SYS123");
		ReflectionTestUtils.setField(roleFunctionManagementService, "userDetails", userDetails);

		roleFunctionMasterEntity = new RoleFunctionEntity();
		roleFunctionMasterEntity.setFunctionName("test function");
		roleFunctionMasterEntity.setRoleName("checker");
		roleFunctionMasterEntity.setFunctionUri("/test");
		roleFunctionMasterEntity.setCreatedDateTime(LocalDateTime.now());
		roleFunctionMasterEntity.setIsActive(true);
		roleFunctionMasterEntity.setIsDeleted(false);

		auditRoleFunctionMasterEntity = new AuditRoleFunctionEntity();
		BeanUtils.copyProperties(roleFunctionMasterEntity, auditRoleFunctionMasterEntity);
		auditRoleFunctionMasterEntity.setUserId("USER123");

	}

	 @Test
	public void saveFunctionPositiveTest() {

		RoleFunctionRequestDTO roleFunctionRequestDTO = new RoleFunctionRequestDTO();
		roleFunctionRequestDTO.setFunctionName("test");
		roleFunctionRequestDTO.setFunctionUri("/test");
		roleFunctionRequestDTO.setRoleName("checker");

		Mockito.when(auditRoleFunctionRepository.save(Mockito.any(AuditRoleFunctionEntity.class)))
				.thenReturn(auditRoleFunctionMasterEntity);
		Mockito.when(roleFunctionRepository.save(roleFunctionMasterEntity)).thenReturn(roleFunctionMasterEntity);

		RoleFunctionResponseDTO roleFunctionResponseExpected = new RoleFunctionResponseDTO();
		roleFunctionResponseExpected.setRoleFunctionId(roleFunctionMasterEntity.getID());
		roleFunctionResponseExpected.setStatus(AuthConstant.ACTIVE);

		RoleFunctionResponseDTO roleFunctionResponseActual = roleFunctionManagementService
				.saveFunction(roleFunctionRequestDTO);

		assertThat(roleFunctionResponseActual).isEqualTo(roleFunctionResponseExpected);

	}

	@Test
	public void getFunctionListByRoleNamePositiveTest() {

		RoleFunctionFetchRequestDTO roleFunctionFetchRequestDTO = new RoleFunctionFetchRequestDTO();
		roleFunctionFetchRequestDTO.setRoleName("checker");

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

		List<RoleFunctionMappingEntity> roleFunctionMappingEntityList = new ArrayList<RoleFunctionMappingEntity>();
		RoleFunctionMappingEntity roleFunctionMappingEntity = new RoleFunctionMappingEntity();
		roleFunctionMappingEntity.setRoleName("checker");
		roleFunctionMappingEntity.setFunctionName("Verify Doctor Details");
		roleFunctionMappingEntity.setIsActive(true);
		roleFunctionMappingEntity.setMenuName("Verify Doctor");
		roleFunctionMappingEntity.setRoute("checker");
		roleFunctionMappingEntity.setCreatedtime(LocalDateTime.now());
		roleFunctionMappingEntity.setUpdatedTime(LocalDateTime.now());
		roleFunctionMappingEntityList.add(roleFunctionMappingEntity);

		Mockito.when(roleFunctionMappingRepo.findByRoleNameAndIsActive(Mockito.anyString(), Mockito.anyBoolean()))
				.thenReturn(roleFunctionMappingEntityList);
		
		GetMenuResponseDTO listOfActual = roleFunctionManagementService
				.getFunctionListByRoleName(roleFunctionFetchRequestDTO);

		assertThat(listOfActual).isEqualTo(responseEnt);
	}

	 @Test
	public void getAllRoleFunctionMappingPositiveTest() throws JsonProcessingException {

		List<RoleMasterEntity> listOfRoleMasterEntities = new ArrayList<>();
		RoleMasterEntity roleMasterEntity = new RoleMasterEntity();
		roleMasterEntity.setRoleName("checker");
		listOfRoleMasterEntities.add(roleMasterEntity);
		List<RoleFunctionEntity> listOfRoleFunEntities = new ArrayList<>();
		listOfRoleFunEntities.add(roleFunctionMasterEntity);

		Mockito.when(roleServiceRepository.findAll()).thenReturn(listOfRoleMasterEntities);
		Mockito.when(roleFunctionRepository.findByIsActive(Mockito.anyBoolean())).thenReturn(listOfRoleFunEntities);

		Map<String, List<FunctionListDtls>> roleToFunctionMap = new HashMap<>();
		List<FunctionListDtls> list = new ArrayList<>();
		FunctionListDtls functionList = FunctionListDtls.builder().functionName("test function").functionUrl("/test")
				.build();
		list.add(functionList);

		roleToFunctionMap.put(roleMasterEntity.getRoleName(), list);
		String jsonStr = "{\"SYSTEMUSER\":[{\"functionName\":\"login\",\"functionUrl\":\"/usermanagement/v1/user/login/usersignin\"}]}";
		// String jsonStr = mapper.writeValueAsString(roleToFunctionMap);
		Mockito.when(mapper.writeValueAsString(roleToFunctionMap)).thenReturn(jsonStr);
		// Mockito.when(roleFunctionManagementService.getAllRoleFunctionMapping()).thenReturn(jsonStr);

		String expected = roleFunctionManagementService.getAllRoleFunctionMapping();

		assertThat(expected).isEqualTo(jsonStr);
	}

	@Test
	public void activeDeactiveFunctionRoleServicePositiveTest() {
		//String userId = "MOSIP";
		LoginUserEntity adminEntity = new LoginUserEntity();
		adminEntity.setUserType("ADMIN");
		
		Mockito.when(userRepo.findByUserId(Mockito.anyString())).thenReturn(adminEntity);

		MainRequestDTO<FunctionActiveDeactiveDTO> request = new MainRequestDTO<>();
		FunctionActiveDeactiveDTO functionDTO = new FunctionActiveDeactiveDTO();
		functionDTO.setFunctionName("scribe registration");
		functionDTO.setOperationType("DEACTIVE");
		functionDTO.setRole("scribe");
		request.setRequest(functionDTO);

		FunctionActiveDeactiveDTO activeDeactiveRequest = request.getRequest();

		List<RoleFunctionEntity> listOfFuncRoleEntities = new ArrayList<>();
		RoleFunctionEntity roleFunctionEntity = new RoleFunctionEntity();
		roleFunctionEntity.setIsActive(true);
		roleFunctionEntity.setFunctionName("scribe registration");
		roleFunctionEntity.setRoleName("scribe");
		listOfFuncRoleEntities.add(roleFunctionEntity);

		Mockito.when(roleFunctionRepository.findByRoleNameAndFunctionName(activeDeactiveRequest.getRole(),
				activeDeactiveRequest.getFunctionName())).thenReturn(listOfFuncRoleEntities);

		Mockito.when(auditRoleFunctionRepository.save(Mockito.any(AuditRoleFunctionEntity.class)))
		.thenReturn(auditRoleFunctionMasterEntity);
		
		Mockito.when(roleFunctionRepository.updateFunctionRoleStatus(Mockito.anyBoolean(), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(), Mockito.any(LocalDateTime.class))).thenReturn(1);

		MainResponseDTO<ActiveDeactiveResponseDTO> response = new MainResponseDTO<>();
		ActiveDeactiveResponseDTO activeDeactiveResponse = new ActiveDeactiveResponseDTO();
		activeDeactiveResponse.setFunctionName("scribe registration");
		activeDeactiveResponse.setMessage(AuthConstant.ACTIVE_DEACTIVE_SUCCESS);
		response.setResponse(activeDeactiveResponse);
		//Mockito.when(roleFunctionManagementService.activeDeactiveFunctionRoleService(request)).thenReturn(response);
		assertThat(roleFunctionManagementService.activeDeactiveFunctionRoleService(request).getResponse())
				.isEqualTo(response.getResponse());

	}

}
