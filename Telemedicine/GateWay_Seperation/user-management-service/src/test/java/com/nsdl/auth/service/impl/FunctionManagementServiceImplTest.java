/*package com.nsdl.auth.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.test.util.ReflectionTestUtils;

import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.FunctionDeleteRequestDto;
import com.nsdl.auth.dto.FunctionDetailsDto;
import com.nsdl.auth.dto.FunctionRequestDTO;
import com.nsdl.auth.dto.FunctionResposeDTO;
import com.nsdl.auth.dto.FunctionUpdateRequestDto;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.entity.AuditFunctionMasterEntity;
import com.nsdl.auth.entity.FunctionMasterEntity;
import com.nsdl.auth.repository.AuditFunctionServiceRepository;
import com.nsdl.auth.repository.FunctionServiceRepository;
import com.nsdl.auth.utility.IdGenerator;
import com.nsdl.telemedicine.gateway.config.UserDTO;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IdGenerator.class)
public class FunctionManagementServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Mock
	public FunctionServiceRepository functionRepository;

	@InjectMocks
	public FunctionManagementServiceImpl functionManagementService;

	@Mock
	private AuditFunctionServiceRepository auditFunctionRepository;
	
	UserDTO userDetails = new UserDTO();

	FunctionMasterEntity functionMasterEntity;
	AuditFunctionMasterEntity auditFunctionMasterEntity;

	@Before
	public void setUp() throws ParseException {
		userDetails.setUserName("SYS123");
		ReflectionTestUtils.setField(functionManagementService, "userDTO", userDetails);

		functionMasterEntity = new FunctionMasterEntity();
		functionMasterEntity.setCreatedBy("Jeevan");
		functionMasterEntity.setCreatedDateTime(LocalDateTime.now());
		functionMasterEntity.setFunctionName("doctor registra");
		functionMasterEntity.setID(12345);
		functionMasterEntity.setIsActive(true);
		functionMasterEntity.setIsDeleted(false);
		functionMasterEntity.setUpdatedBy("Jeevan");
		functionMasterEntity.setUpdatedDateTime(functionMasterEntity.getCreatedDateTime());

		auditFunctionMasterEntity = new AuditFunctionMasterEntity();
		BeanUtils.copyProperties(functionMasterEntity, auditFunctionMasterEntity);
		auditFunctionMasterEntity.setUserId("SYS123");
	}

	 @Test
	public void saveFunctionPositiveTest() {

		FunctionResposeDTO functionResposeDTOExpected = new FunctionResposeDTO();
		functionResposeDTOExpected.setRoleId(12345);
		functionResposeDTOExpected.setStatus(AuthConstant.ACTIVE);

		FunctionRequestDTO functionRequestDTO = new FunctionRequestDTO();
		functionRequestDTO.setFunctionName("view payment");
		Mockito.when(functionRepository.existsByFunctionName(Mockito.anyString())).thenReturn(false);
		Mockito.when(functionRepository.save(Mockito.any(FunctionMasterEntity.class))).thenReturn(functionMasterEntity);
		Mockito.when(auditFunctionRepository.save(Mockito.any(AuditFunctionMasterEntity.class)))
				.thenReturn(auditFunctionMasterEntity);
		PowerMockito.mockStatic(IdGenerator.class);
		Mockito.when(IdGenerator.createFunctionId()).thenReturn(12345);
		FunctionResposeDTO actualFunctionResposeDTO = functionManagementService.saveFunction(functionRequestDTO);
		assertThat(actualFunctionResposeDTO).isEqualTo(functionResposeDTOExpected);
	}

	 @Test
	public void updatePositiveTest() {

		MainResponseDTO<CommonResponseDTO> mainResponseExpected = new MainResponseDTO<CommonResponseDTO>();
		CommonResponseDTO functionUpdateResponseDtoExpected = new CommonResponseDTO();
		functionUpdateResponseDtoExpected.setMessage(AuthConstant.FUNCTION_UPDATE_SUCCESS);
		mainResponseExpected.setResponse(functionUpdateResponseDtoExpected);

		Optional<FunctionMasterEntity> functionMasterEntityExpected = Optional.of(functionMasterEntity);
		FunctionUpdateRequestDto functionUpdateRequest = new FunctionUpdateRequestDto();
		functionUpdateRequest.setFunctionName("doctor registra");
		functionUpdateRequest.setNewFunctionName("doctor reg");
		Mockito.when(functionRepository.findById(Mockito.anyString())).thenReturn(functionMasterEntityExpected);
		Mockito.when(functionRepository.existsByFunctionName(Mockito.anyString())).thenReturn(false);
		Mockito.when(auditFunctionRepository.save(Mockito.any(AuditFunctionMasterEntity.class)))
				.thenReturn(auditFunctionMasterEntity);
		Mockito.when(functionRepository.updateFunction(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(LocalDateTime.class), Mockito.anyString())).thenReturn(2);

		MainResponseDTO<CommonResponseDTO> mainResponseActual = functionManagementService.update(functionUpdateRequest);
		assertThat(mainResponseActual.getResponse()).isEqualTo(mainResponseExpected.getResponse());
	}

	 @Test
	public void getFunctionsPositiveTest() {

		List<FunctionMasterEntity> functions = new ArrayList<>();
		functions.add(functionMasterEntity);
		List<FunctionDetailsDto> functionDetailsExpected = new ArrayList<FunctionDetailsDto>();

		FunctionDetailsDto functionDetailsDto = new FunctionDetailsDto();
		functionDetailsDto.setId(12345);
		functionDetailsDto.setFunctionName("doctor registra");
		functionDetailsExpected.add(functionDetailsDto);

		Mockito.when(functionRepository.findByIsActiveAndIsDeleted(true, false)).thenReturn(functions);
		List<FunctionDetailsDto> actualList = functionManagementService.getFunctions();
		assertThat(actualList).isEqualTo(functionDetailsExpected);
	}

	@Test
	public void deleteFunctionPositiveTest() {

		MainRequestDTO<FunctionDeleteRequestDto> mainfunctionDeleteRequestDto = new MainRequestDTO<>();
		FunctionDeleteRequestDto functionDeleteRequestDto = new FunctionDeleteRequestDto();
		functionDeleteRequestDto.setFunctionName("doctor registra");
		mainfunctionDeleteRequestDto.setRequest(functionDeleteRequestDto);

		Optional<FunctionMasterEntity> optionalEntity = Optional.of(functionMasterEntity);

		Mockito.when(functionRepository.findById(Mockito.anyString())).thenReturn(optionalEntity);
		Mockito.when(auditFunctionRepository.save(Mockito.any(AuditFunctionMasterEntity.class)))
				.thenReturn(auditFunctionMasterEntity);
		Mockito.when(functionRepository.deleteFunction(Mockito.anyString(), Mockito.any(LocalDateTime.class)))
				.thenReturn(2);
		
		 * Mockito.when(functionRepository.save(functionMasterEntity)).thenReturn(value)
		 
		MainResponseDTO<CommonResponseDTO> mainResponseExpected = new MainResponseDTO<>();
		CommonResponseDTO functionDeleteResponseDto = new CommonResponseDTO();
		functionDeleteResponseDto.setMessage(AuthConstant.FUNCTION_DELETE_SUCCESS);
		mainResponseExpected.setResponse(functionDeleteResponseDto);

		MainResponseDTO<CommonResponseDTO> mainResponseActual = functionManagementService
				.deleteFunction(mainfunctionDeleteRequestDto);

		assertThat(mainResponseActual.getResponse()).isEqualTo(mainResponseExpected.getResponse());
	}

}
*/