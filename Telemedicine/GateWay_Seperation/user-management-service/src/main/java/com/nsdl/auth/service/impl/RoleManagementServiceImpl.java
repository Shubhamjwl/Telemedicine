package com.nsdl.auth.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.dto.CommonResponseDTO;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.RoleDeleteRequestDto;
import com.nsdl.auth.dto.RoleDetailsDto;
import com.nsdl.auth.dto.RoleRequestDTO;
import com.nsdl.auth.dto.RoleUpdateRequestDto;
import com.nsdl.auth.dto.UserDTO;
import com.nsdl.auth.entity.AuditRoleMasterEntity;
import com.nsdl.auth.entity.RoleMasterEntity;
import com.nsdl.auth.exception.AuthErrorConstant;
import com.nsdl.auth.exception.DateParsingException;
import com.nsdl.auth.exception.ServiceError;
import com.nsdl.auth.repository.AuditRoleServiceRepository;
import com.nsdl.auth.repository.RoleServiceRepository;
import com.nsdl.auth.service.RoleManagementService;
import com.nsdl.auth.utility.DateUtils;
import com.nsdl.auth.utility.IdGenerator;

/**
 * This service class contains methods for fetching, Adding, modifying and
 * deleting roles.
 * 
 * @since 1.0.0
 * @author Sudip Banerjee
 */
@Service
@Transactional
public class RoleManagementServiceImpl implements RoleManagementService {

	@Autowired
	RoleServiceRepository roleRepository;

	@Autowired
	AuditRoleServiceRepository auditRoleServiceRepository;

	@Autowired
	private UserDTO userDTO;

	private static final Logger logger = LoggerFactory.getLogger(RoleManagementServiceImpl.class);

	@Override
	public CommonResponseDTO saveRole(RoleRequestDTO roleRequestDTO) {

		logger.info("Request received for save role in service class");

		String role = roleRequestDTO.getRoleName().toUpperCase();
		if (roleRepository.existsByRoleName(role)) {
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.ROLE_EXISTS_ERR_CODE, AuthConstant.ROLE_EXISTS));
		}
		LocalDateTime now = LocalDateTime.now();
		AuditRoleMasterEntity auditRoleMasterEntity = new AuditRoleMasterEntity();
		RoleMasterEntity roleMasterEntity = new RoleMasterEntity();
		CommonResponseDTO roleResponse = null;
		int id = IdGenerator.createRoleId();
		logger.info("Id for role id pk is:" + id);
		roleMasterEntity.setID(id);
		roleMasterEntity.setRoleName(role);
		roleMasterEntity.setCreatedBy(userDTO.getUserName());
		roleMasterEntity.setCreatedDateTime(now);
		roleMasterEntity.setUpdatedBy(userDTO.getUserName());
		roleMasterEntity.setUpdatedDateTime(now);
		roleMasterEntity.setIsActive(true);
		roleMasterEntity.setIsDeleted(false);
		BeanUtils.copyProperties(roleMasterEntity, auditRoleMasterEntity);
		auditRoleServiceRepository.save(auditRoleMasterEntity);
		logger.info("saved role entity data into audit table");

		RoleMasterEntity savedMasterEntity = roleRepository.save(roleMasterEntity);
		logger.info("saved role entity data into main table");
		if (null != savedMasterEntity) {
			roleResponse = new CommonResponseDTO();
			//roleResponse.setRoleId(roleMasterEntity.getID());
			roleResponse.setMessage(AuthConstant.ROLE_CREATE_SUCCESS);
		}
		return roleResponse;
	}

	@Override
	public MainResponseDTO<CommonResponseDTO> update(RoleUpdateRequestDto updateRequest) {
		logger.info("Request received for update role request in service class");
		MainResponseDTO<CommonResponseDTO> response = new MainResponseDTO<>();
		CommonResponseDTO roleUpdateResponseDto = new CommonResponseDTO();
		RoleMasterEntity roleMasterEntity = null;
		AuditRoleMasterEntity auditRoleMasterEntity = new AuditRoleMasterEntity();
		String roleName = updateRequest.getRoleName().toUpperCase();
		String newRole = updateRequest.getNewRoleName().toUpperCase();
		Optional<RoleMasterEntity> OptionalRoleMasterEntity = roleRepository.findById(roleName);
		if (OptionalRoleMasterEntity.isPresent()) {
			roleMasterEntity = OptionalRoleMasterEntity.get();
			logger.info("role:" + roleMasterEntity.toString() + "is present with role name:" + roleName);
		} else {
			logger.info("Existing Role Name : " + roleName + " not found in system");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.ROLE_NOT_EXISTS_ERR_CODE,
					String.format(AuthConstant.ROLE_NOT_EXISTS, roleName)));
		}
		if (roleRepository.existsByRoleName(newRole)) {
			logger.info("Role : " + newRole + " Already exists");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.ROLE_EXISTS_ERR_CODE,
					String.format(AuthConstant.ROLE_EXISTS, newRole)));
		}
		if (roleMasterEntity.getIsActive() && (!roleMasterEntity.getIsDeleted())) {
			logger.info("role: with role name:" + roleName + " is not deleted");
			auditRoleMasterEntity.setUserId(userDTO.getUserName());
			BeanUtils.copyProperties(roleMasterEntity, auditRoleMasterEntity);
			auditRoleServiceRepository.save(auditRoleMasterEntity);
			logger.info("Audited Role Master Data before update");

			int status = roleRepository.updateRole(roleName, newRole, DateUtils.getCurrentLocalDateTime(),
					userDTO.getUserName());
			logger.info("update role entity data into main table");
			if (status > 0) {
				roleUpdateResponseDto.setMessage(AuthConstant.ROLE_UPDATE_SUCCESS);
			} else {
				roleUpdateResponseDto.setMessage(String.format(AuthConstant.ROLE_UPDATE_FAILED, roleName));
			}
		} else {
			logger.info("Existing Role " + roleName + " is not Active or is deleted");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.ROLE_NOT_ACTIVE_OR_DELETED_ERR_CODE,
					String.format(AuthConstant.ROLE_NOT_ACTIVE_OR_DELETED, roleName)));
		}
		response.setResponse(roleUpdateResponseDto);
		return response;
	}

	/**
	 * <p>
	 * This method brings the all role's present in the database.
	 * </p>
	 * </br>
	 * 1. Checks the db for role details.</br>
	 * 2. if role details not found throws the exception </br>
	 * 3. else returns the role's </br>
	 * 
	 * @return RoleDetailsDto list of role's </br>
	 */
	@Override
	public List<RoleDetailsDto> getRoles() {
		logger.info("Request received for getting all roles from table");
		List<RoleDetailsDto> roleDetails = new ArrayList<RoleDetailsDto>();
		List<RoleMasterEntity> roles = roleRepository.findByIsActiveAndIsDeleted(true, false);
		if (!roles.isEmpty()) {
			for (RoleMasterEntity role : roles) {
				RoleDetailsDto roleDetailsDto = new RoleDetailsDto();
				roleDetailsDto.setId(role.getID());
				roleDetailsDto.setRoleName(role.getRoleName());
				roleDetails.add(roleDetailsDto);
			}
		}else {
			logger.error("No Roles Available in Role master table");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.RECORD_NOT_FOUND_ERR_CODE, AuthConstant.RECORD_NOT_FOUND));
		}
		return roleDetails;
	}

	@Override
	public MainResponseDTO<CommonResponseDTO> deleteRole(MainRequestDTO<RoleDeleteRequestDto> roleDeleteRequestDto) {

		logger.info("Request received for deleting role by admin");
		RoleDeleteRequestDto request = roleDeleteRequestDto.getRequest();
		MainResponseDTO<CommonResponseDTO> response = new MainResponseDTO<>();
		CommonResponseDTO roleDeleteResponseDto = new CommonResponseDTO();
		AuditRoleMasterEntity auditRoleMasterEntity = new AuditRoleMasterEntity();
		RoleMasterEntity roleMasterEntity = null;
		String roleName = request.getRoleName().toUpperCase();

		Optional<RoleMasterEntity> optionalRoleMasterEntity = roleRepository.findById(roleName);
		if (optionalRoleMasterEntity.isPresent()) {
			roleMasterEntity = optionalRoleMasterEntity.get();
			logger.info("role:" + roleMasterEntity.toString() + "is present with role name:" + roleName);
		} else {
			logger.error("Role : " + roleName + " not found in system");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.ROLE_NOT_EXISTS_ERR_CODE,
					String.format(AuthConstant.ROLE_NOT_EXISTS, roleName)));
		}

		if ((!roleMasterEntity.getIsActive()) && roleMasterEntity.getIsDeleted()) {
			logger.error("Role : " + roleName + " is already deleted");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.ROLE_ALREADY_DELETED_ERR_CODE,
					String.format(AuthConstant.ROLE_ALREADY_DELETED, roleName)));
		}
		if (roleMasterEntity != null) {
			auditRoleMasterEntity.setUserId(userDTO.getUserName());
			BeanUtils.copyProperties(roleMasterEntity, auditRoleMasterEntity);
			auditRoleServiceRepository.save(auditRoleMasterEntity);
			logger.info("Audited role master data before soft delete");
			int deleteStatus = roleRepository.deleteRole(roleName, DateUtils.getCurrentLocalDateTime());
			if (deleteStatus > 0) {
				logger.info("Role soft deletion : SUCCESS");
				roleDeleteResponseDto.setMessage(AuthConstant.ROLE_DELETE_SUCCESS);
			} else {
				logger.info("Role soft deletion : FAILED");
				roleDeleteResponseDto.setMessage(AuthConstant.ROLE_DELETE_FAILED);
			}
		}
		response.setResponse(roleDeleteResponseDto);

		return response;
	}

}
