
package com.nsdl.auth.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsdl.auth.constant.AuthConstant;
import com.nsdl.auth.constant.UserRoleByUserType;
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
import com.nsdl.auth.entity.AuditRoleFunctionEntity;
import com.nsdl.auth.entity.LoginUserEntity;
import com.nsdl.auth.entity.RoleFunctionEntity;
import com.nsdl.auth.entity.RoleFunctionMappingEntity;
import com.nsdl.auth.entity.RoleMasterEntity;
import com.nsdl.auth.exception.AuthErrorConstant;
import com.nsdl.auth.exception.DateParsingException;
import com.nsdl.auth.exception.ServiceError;
import com.nsdl.auth.logger.LoggingClientInfo;
import com.nsdl.auth.repository.AuditRoleFunctionRepository;
import com.nsdl.auth.repository.LoginUserRepo;
import com.nsdl.auth.repository.RoleFunctionMappingRepo;
import com.nsdl.auth.repository.RoleFunctionRepository;
import com.nsdl.auth.repository.RoleServiceRepository;
import com.nsdl.auth.service.RoleFunctionManagementService;
import com.nsdl.auth.utility.DateUtils;
import com.nsdl.auth.utility.EmptyCheckUtility;

@Service
@Transactional
@LoggingClientInfo
public class RoleFunctionManagementServiceImpl implements RoleFunctionManagementService {

	@Autowired
	RoleFunctionRepository roleFunctionRepository;

	@Autowired
	RoleServiceRepository roleServiceRepository;

	@Autowired
	AuditRoleFunctionRepository auditRoleFunctionRepository;

	@Autowired
	RoleFunctionMappingRepo roleFunctionMappingRepo;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	LoginUserRepo userRepo;

	/*@Autowired
	private UserDTO userDetails;*/

	private static final Logger logger = LoggerFactory.getLogger(RoleFunctionManagementServiceImpl.class);

	@Override
	public RoleFunctionResponseDTO saveFunction(RoleFunctionRequestDTO roleFunctionRequestDTO) {

		logger.info("Request received for saving roleFunction entity data");
		RoleFunctionEntity roleFunctionMasterEntity = new RoleFunctionEntity();
		AuditRoleFunctionEntity auditRoleFunctionMasterEntity = new AuditRoleFunctionEntity();

		RoleFunctionResponseDTO roleFunctionResponse = null;

		roleFunctionMasterEntity.setFunctionName(roleFunctionRequestDTO.getFunctionName());
		roleFunctionMasterEntity.setRoleName(roleFunctionRequestDTO.getRoleName());
		roleFunctionMasterEntity.setFunctionUri(roleFunctionRequestDTO.getFunctionUri());
		roleFunctionMasterEntity.setCreatedDateTime(LocalDateTime.now());
		roleFunctionMasterEntity.setIsActive(true);
		roleFunctionMasterEntity.setIsDeleted(false);
		roleFunctionMasterEntity.setCreatedBy("");
		BeanUtils.copyProperties(roleFunctionMasterEntity, auditRoleFunctionMasterEntity);
		auditRoleFunctionMasterEntity.setUserId("");
		logger.info("save role function details into audit table");
		auditRoleFunctionRepository.save(auditRoleFunctionMasterEntity);
		roleFunctionMasterEntity = roleFunctionRepository.save(roleFunctionMasterEntity);

		logger.info("save role function details into main table");
		if (null != roleFunctionMasterEntity) {
			roleFunctionResponse = new RoleFunctionResponseDTO();
			roleFunctionResponse.setRoleFunctionId(roleFunctionMasterEntity.getID());
			roleFunctionResponse.setStatus(AuthConstant.ACTIVE);
		}
		return roleFunctionResponse;
	}

	@Override
	public GetMenuResponseDTO getFunctionListByRoleName(RoleFunctionFetchRequestDTO roleFunctionFetchRequestDTO) {
		logger.info("Request received for getting all list of functions by role name:"
				+ roleFunctionFetchRequestDTO.getRoleName());

		List<RoleFunctionMappingEntity> listOfRoleFunctionMapping = roleFunctionMappingRepo
				.findByRoleNameAndIsActive(roleFunctionFetchRequestDTO.getRoleName().toUpperCase(), true);

		if (listOfRoleFunctionMapping.isEmpty() || listOfRoleFunctionMapping == null) {
			logger.error("RoleFunction Data Not found");
			throw new DateParsingException(new ServiceError("UM-0000", AuthConstant.ROLE_FUNCTION_DATA_NOT_FOUND));
		}

		Map<MainMenu, List<Functions>> map = new HashMap<MainMenu, List<Functions>>();

		for (RoleFunctionMappingEntity rfm : listOfRoleFunctionMapping) {
			MainMenu m = new MainMenu();
			m.setMainMenuName(rfm.getMenuName());
			if (map.containsKey(m)) {
				List<Functions> funcList = map.get(m);

				if (!EmptyCheckUtility.isNullEmpty(rfm.getSubMenuName())) {
					if (funcList == null) {
						funcList = new ArrayList<Functions>();
					}
					Functions temp = new Functions();
					temp.setFunctionName(rfm.getFunctionName());
					Functions fun;
					if (funcList.contains(temp)) {
						fun = funcList.remove(funcList.indexOf(temp));
					} else {
						fun = new Functions();
						fun.setFunctionName(rfm.getFunctionName());
					}
					if (fun.getSubMenu() == null) {
						fun.setSubMenu(new ArrayList<SubFunctions>());
					}
					SubFunctions s = new SubFunctions();
					s.setSubFunctionName(rfm.getSubMenuName());
					s.setSubFunctionRoute(rfm.getRoute());
					fun.getSubMenu().add(s);
					funcList.add(fun);
				} else if (!EmptyCheckUtility.isNullEmpty(rfm.getFunctionName())) {
					if (funcList == null) {
						funcList = new ArrayList<Functions>();
					}
					Functions fun = new Functions();
					fun.setFunctionName(rfm.getFunctionName());
					fun.setRoute(rfm.getRoute());
					funcList.add(fun);
				}
				map.replace(m, funcList);
			} else {
				List<Functions> func = new ArrayList<Functions>();
				List<SubFunctions> subFunc = new ArrayList<SubFunctions>();
				if (!EmptyCheckUtility.isNullEmpty(rfm.getSubMenuName())) {
					SubFunctions s = new SubFunctions();
					s.setSubFunctionName(rfm.getSubMenuName());
					s.setSubFunctionRoute(rfm.getRoute());
					subFunc.add(s);
				}
				if (!EmptyCheckUtility.isNullEmpty(rfm.getFunctionName())) {
					Functions f = new Functions();
					if (subFunc.size() == 0) {
						f.setRoute(rfm.getRoute());
					} else {
						f.setSubMenu(subFunc);
					}
					f.setFunctionName(rfm.getFunctionName());
					func.add(f);
				}
				MainMenu main = new MainMenu();
				main.setMainMenuName(rfm.getMenuName());
				if (func.size() == 0) {
					main.setRoute(rfm.getRoute());
					map.put(main, null);
				} else {
					map.put(main, func);
				}
			}
		}
		GetMenuResponseDTO responseEnt = new GetMenuResponseDTO();
		List<MainMenu> mainMenuList = new ArrayList<MainMenu>();
		for (Map.Entry<MainMenu, List<Functions>> entry : map.entrySet()) {
			List<Functions> f = entry.getValue();
			MainMenu main = entry.getKey();
			if (f != null) {
				main.setFunctions(f);
			}
			mainMenuList.add(main);
		}
		responseEnt.setMainMenu(mainMenuList);
		responseEnt.setRoleName(roleFunctionFetchRequestDTO.getRoleName());
		logger.info("Returning dymanic menu list for given role");
		return responseEnt;
	}

	@Override
	public String getAllRoleFunctionMapping() {
		List<RoleMasterEntity> roleDtls = roleServiceRepository.findAll();

		// List<RoleFunctionEntity> roleFunctionEnityDtls =
		// roleFunctionRepository.findAll();
		List<RoleFunctionEntity> roleFunctionEnityDtls = roleFunctionRepository.findByIsActive(true);

		String jsonString = null;
		// Map<String, Map<String, String>> roleToFunctionMap = new HashMap<String,
		// Map<String, String>>();
		if (!roleDtls.isEmpty() && !roleFunctionEnityDtls.isEmpty()) {
			logger.info("Fetched all Roles");
			logger.info("Fetched all RoleFunction Mapping");
			Map<String, List<FunctionListDtls>> roleToFunctionMap = new HashMap<String, List<FunctionListDtls>>();
			roleDtls.forEach(role -> {
				String roleName = role.getRoleName();

//				Map<String, String> functionDetails = roleFunctionEnityDtls.stream().filter(f -> f.getRoleName().equalsIgnoreCase(roleName))
//						.collect(Collectors.toMap(RoleFunctionEntity::getFunctionName, RoleFunctionEntity::getFunctionUri));
//	           roleToFunctionMap.put(roleName, functionDetails);
				// List<FunctionListDtls> functionUrlList = new ArrayList<FunctionListDtls>();
				List<FunctionListDtls> functionUrlList = roleFunctionEnityDtls.stream()
						.filter(f -> f.getRoleName().equalsIgnoreCase(roleName))
						.map(filterdata -> FunctionListDtls.builder().functionName(filterdata.getFunctionName())
								.functionUrl(filterdata.getFunctionUri()).build())
						.collect(Collectors.toList());
				roleToFunctionMap.put(roleName, functionUrlList);
			});
			try {

				jsonString = objectMapper.writeValueAsString(roleToFunctionMap);
				logger.info("Converted RoleFunction Data into Json String");
			} catch (JsonProcessingException e) {
				logger.error("Error While converting RoleFunction Data into Json String");
				throw new DateParsingException(new ServiceError("UM-0000", e.getMessage()));
			}
		} else {
			logger.error("RoleFunction Data Not found");
			throw new DateParsingException(new ServiceError("UM-0000", AuthConstant.ROLE_FUNCTION_DATA_NOT_FOUND));
		}
		logger.info("Returning RoleFunction Json String Data");
		return jsonString;
	}

	//sayali gateway changes
	@Override
	public MainResponseDTO<ActiveDeactiveResponseDTO> activeDeactiveFunctionRoleService(
			MainRequestDTO<FunctionActiveDeactiveDTO> request) {
		MainResponseDTO<ActiveDeactiveResponseDTO> response = new MainResponseDTO<ActiveDeactiveResponseDTO>();
		ActiveDeactiveResponseDTO activeDeactiveResponse = new ActiveDeactiveResponseDTO();
		String userId = "";
		logger.info("Fetched User Name from Token");
		LoginUserEntity userEntity = userRepo.findByUserId(userId);
		FunctionActiveDeactiveDTO activeDeactiveRequest = request.getRequest();
		String operationType = activeDeactiveRequest.getOperationType().trim().toUpperCase();
//		if (userEntity == null) {
//			throw new DateParsingException(
//					new ServiceError(AuthErrorConstant.INVALID_USER_ERR_CODE, AuthConstant.INVALID_USER_ERROR));
//		} else if (!userEntity.getIsActive()) {
//			throw new DateParsingException(
//					new ServiceError(AuthErrorConstant.INACTIVE_USER_ERR_CODE, AuthConstant.INACTIVE_USER_ERROR));
//		}
		boolean status = true;
		if (!userEntity.getUserType().equals(UserRoleByUserType.ADMIN.getUserType())) {
			logger.error("Usertype is not Admin :Operation not allowed");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.OPERATION_NOT_ALLOWED_ERR_CODE,
					AuthConstant.OPERATION_NOT_ALLOWED));
		} else {
			List<RoleFunctionEntity> roleFunctionEntity = roleFunctionRepository.findByRoleNameAndFunctionName(
					activeDeactiveRequest.getRole(), activeDeactiveRequest.getFunctionName());
			logger.info("Fetched RoleFunction Entity");
			if (roleFunctionEntity.isEmpty() || roleFunctionEntity == null) {
				logger.error("RoleFunction data is not found for the requested input");
				throw new DateParsingException(new ServiceError(AuthErrorConstant.INVALID_FUNCTION_ROLE_ERR_CODE,
						AuthConstant.INVALID_FUNCTION_ROLE));
			}
			switch (operationType) {
			case AuthConstant.ACTIVE:
				logger.info("Operation Type is :Active");
				if (roleFunctionEntity.get(0).getIsActive()) {
					logger.error("Requested Role Function is Already Active");
					throw new DateParsingException(
							new ServiceError(AuthErrorConstant.ROLE_FUNCTION_ALREADY_ACTIVE_ERR_CODE,
									AuthConstant.ROLE_FUNCTION_ALREADY_ACTIVE));
				}
				status = true;
				break;
			case AuthConstant.DEACTIVE:
				logger.info("Operation Type is :Dective");
				if (!roleFunctionEntity.get(0).getIsActive()) {
					logger.error("Requested Role Function is Already Deactive");
					throw new DateParsingException(
							new ServiceError(AuthErrorConstant.ROLE_FUNCTION_ALREADY_DEACTIVE_ERR_CODE,
									AuthConstant.ROLE_FUNCTION_ALREADY_DEACTIVE));
				}
				status = false;
				break;
			default:
				logger.error("Invalid Operation Type : Not supported");
				throw new DateParsingException(
						new ServiceError(AuthErrorConstant.INVALID_OPERATION_ERR_CODE, AuthConstant.INVALID_OPERATION));
			}
			AuditRoleFunctionEntity auditRoleFunctionMasterEntity = new AuditRoleFunctionEntity();
			BeanUtils.copyProperties(roleFunctionEntity.get(0), auditRoleFunctionMasterEntity);
			auditRoleFunctionMasterEntity.setUserId(userId);
			auditRoleFunctionRepository.save(auditRoleFunctionMasterEntity);

			int result = roleFunctionRepository.updateFunctionRoleStatus(status,
					activeDeactiveRequest.getFunctionName(), activeDeactiveRequest.getRole(), userId,
					DateUtils.getCurrentLocalDateTime());
			activeDeactiveResponse.setFunctionName(activeDeactiveRequest.getFunctionName());
			if (result > 0) {
				logger.info("FunctionRole mapping Active/Deactive: Success");
				activeDeactiveResponse.setMessage(AuthConstant.ACTIVE_DEACTIVE_SUCCESS);
				response.setStatus(true);
			} else {
				logger.info("FunctionRole mapping Active/Deactive: Failed");
				activeDeactiveResponse.setMessage(AuthConstant.ACTIVE_DEACTIVE_FAILED);
			}
		}
		response.setResponse(activeDeactiveResponse);
		response.setResponsetime(DateUtils.getCurrentLocalDateTime());
		logger.info("Returning response of FunctionRole mapping Active/Deactive");
		return response;
	}
}
