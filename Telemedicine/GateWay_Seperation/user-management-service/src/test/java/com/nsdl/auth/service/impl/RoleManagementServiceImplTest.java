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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RoleDeleteRequestDto;
import com.nsdl.auth.dto.RoleDetailsDto;
import com.nsdl.auth.dto.RoleRequestDTO;
import com.nsdl.auth.dto.RoleUpdateRequestDto;
import com.nsdl.auth.entity.AuditRoleMasterEntity;
import com.nsdl.auth.entity.RoleMasterEntity;
import com.nsdl.auth.repository.AuditRoleServiceRepository;
import com.nsdl.auth.repository.RoleServiceRepository;
import com.nsdl.auth.utility.DateUtils;
import com.nsdl.auth.utility.IdGenerator;
import com.nsdl.telemedicine.gateway.config.UserDTO;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IdGenerator.class)
public class RoleManagementServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	public RoleManagementServiceImpl roleManagementServiceImpl;

	@Mock
	public AuditRoleServiceRepository auditRoleServiceRepository;

	@Mock
	public RoleServiceRepository roleRepository;

	UserDTO userDetails = new UserDTO();

	RoleMasterEntity roleMasterEntity;
	AuditRoleMasterEntity auditRoleMasterEntity;

	@Before
	public void setUp() throws ParseException {
		userDetails.setUserName("SYS123");
		ReflectionTestUtils.setField(roleManagementServiceImpl, "userDTO", userDetails);
		roleMasterEntity = new RoleMasterEntity();
		roleMasterEntity.setID(101);
		roleMasterEntity.setIsActive(true);
		roleMasterEntity.setIsDeleted(false);
		roleMasterEntity.setRoleName("checker");
		roleMasterEntity.setUpdatedDateTime(LocalDateTime.now());
		roleMasterEntity.setUpdatedBy("USER123");
		auditRoleMasterEntity = new AuditRoleMasterEntity();
		BeanUtils.copyProperties(roleMasterEntity, auditRoleMasterEntity);
		auditRoleMasterEntity.setUserId("SYS123");
	}

	@Test
	public void saveRolePositiveTest() {
		CommonResponseDTO roleResponseExpected = new CommonResponseDTO();
		roleResponseExpected.setMessage(AuthConstant.ROLE_CREATE_SUCCESS);
		RoleRequestDTO roleRequestDTO = new RoleRequestDTO();
		roleRequestDTO.setRoleName("checker");
		PowerMockito.mockStatic(IdGenerator.class);
		Mockito.when(IdGenerator.createFunctionId()).thenReturn(101);
		Mockito.when(auditRoleServiceRepository.save(Mockito.any(AuditRoleMasterEntity.class)))
				.thenReturn(auditRoleMasterEntity);
		Mockito.when(roleRepository.save(Mockito.any(RoleMasterEntity.class))).thenReturn(roleMasterEntity);
		assertThat(roleManagementServiceImpl.saveRole(roleRequestDTO)).isEqualTo(roleResponseExpected);
	}

	@Test
	public void updatePositiveTest() {

		RoleUpdateRequestDto updateRequest = new RoleUpdateRequestDto();
		updateRequest.setRoleName("checker");
		updateRequest.setNewRoleName("user");

		Optional<RoleMasterEntity> optional = Optional.of(roleMasterEntity);
		Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(optional);
		Mockito.when(auditRoleServiceRepository.save(Mockito.any(AuditRoleMasterEntity.class)))
				.thenReturn(auditRoleMasterEntity);
		Mockito.when(roleRepository.updateRole(Mockito.anyString(), Mockito.anyString(),
				Mockito.any(LocalDateTime.class), Mockito.anyString())).thenReturn(2);

		MainResponseDTO<CommonResponseDTO> responseExpected = new MainResponseDTO<>();
		CommonResponseDTO roleUpdateResponseDto = new CommonResponseDTO();
		roleUpdateResponseDto.setMessage(AuthConstant.ROLE_UPDATE_SUCCESS);
		responseExpected.setResponse(roleUpdateResponseDto);
		assertThat(roleManagementServiceImpl.update(updateRequest).getResponse())
				.isEqualTo(responseExpected.getResponse());
	}

	@Test
	public void getRolesPositiveTest() {

		List<RoleMasterEntity> listOfRoleEntities = new ArrayList<>();
		listOfRoleEntities.add(roleMasterEntity);

		Mockito.when(roleRepository.findByIsActiveAndIsDeleted(true,false)).thenReturn(listOfRoleEntities);
		List<RoleDetailsDto> roleDetails = new ArrayList<RoleDetailsDto>();
		RoleDetailsDto roleDetailsDto = new RoleDetailsDto();
		roleDetailsDto.setId(roleMasterEntity.getID());
		roleDetailsDto.setRoleName(roleMasterEntity.getRoleName());
		roleDetails.add(roleDetailsDto);
		assertThat(roleManagementServiceImpl.getRoles()).isEqualTo(roleDetails);
	}

	@Test
	public void deleteRolePositiveTest() {

		MainRequestDTO<RoleDeleteRequestDto> roleDeleteRequest = new MainRequestDTO<>();
		RoleDeleteRequestDto roleDeleteRequestDto = new RoleDeleteRequestDto();
		roleDeleteRequestDto.setRoleName("checker");
		roleDeleteRequest.setRequest(roleDeleteRequestDto);

		Optional<RoleMasterEntity> optional = Optional.of(roleMasterEntity);
		Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(optional);
		Mockito.when(auditRoleServiceRepository.save(Mockito.any(AuditRoleMasterEntity.class)))
		.thenReturn(auditRoleMasterEntity);
		Mockito.when(roleRepository.deleteRole(Mockito.anyString(), Mockito.any(LocalDateTime.class))).thenReturn(2);

		MainResponseDTO<CommonResponseDTO> responseExpected = new MainResponseDTO<>();
		CommonResponseDTO roleDeleteResponseDto = new CommonResponseDTO();
		roleDeleteResponseDto.setMessage(AuthConstant.ROLE_DELETE_SUCCESS);
		responseExpected.setResponse(roleDeleteResponseDto);
		assertThat(roleManagementServiceImpl.deleteRole(roleDeleteRequest).getResponse())
				.isEqualTo(responseExpected.getResponse());
	}

}
*/