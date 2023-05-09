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
import com.nsdl.auth.dto.FunctionDeleteRequestDto;
import com.nsdl.auth.dto.FunctionDetailsDto;
import com.nsdl.auth.dto.FunctionRequestDTO;
import com.nsdl.auth.dto.FunctionResposeDTO;
import com.nsdl.auth.dto.FunctionUpdateRequestDto;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;
import com.nsdl.auth.dto.UserDTO;
import com.nsdl.auth.entity.AuditFunctionMasterEntity;
import com.nsdl.auth.entity.FunctionMasterEntity;
import com.nsdl.auth.exception.AuthErrorConstant;
import com.nsdl.auth.exception.DateParsingException;
import com.nsdl.auth.exception.ServiceError;
import com.nsdl.auth.repository.AuditFunctionServiceRepository;
import com.nsdl.auth.repository.FunctionServiceRepository;
import com.nsdl.auth.service.FunctionManagementService;
import com.nsdl.auth.utility.DateUtils;
import com.nsdl.auth.utility.IdGenerator;

@Service
@Transactional
public class FunctionManagementServiceImpl implements FunctionManagementService {

	@Autowired
	private FunctionServiceRepository functionRepository;

	@Autowired
	private AuditFunctionServiceRepository auditFunctionRepository;

	@Autowired
	private UserDTO userDTO;

	private static final Logger logger = LoggerFactory.getLogger(FunctionManagementServiceImpl.class);

	@Override
	public FunctionResposeDTO saveFunction(FunctionRequestDTO functionRequestDTO) {
		logger.info("Request received for update function service");
		FunctionMasterEntity functionMasterEntity = new FunctionMasterEntity();
		AuditFunctionMasterEntity auditFunctionMasterEntity = new AuditFunctionMasterEntity();
		FunctionResposeDTO functionResponse = null;
		String functionName = functionRequestDTO.getFunctionName().toUpperCase();
		if (functionRepository.existsByFunctionName(functionName)) {
			logger.info("function : " + functionName + " Already exists");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.FUNCTION_EXISTS_ERR_CODE,
					String.format(AuthConstant.FUNCTION_EXISTS, functionName)));
		}
		int id = IdGenerator.createFunctionId();
		logger.info("Function id generated is:" + id);
		functionMasterEntity.setID(id);
		functionMasterEntity.setFunctionName(functionName);
		functionMasterEntity.setCreatedBy(userDTO.getUserName());
		functionMasterEntity.setCreatedDateTime(DateUtils.getCurrentLocalDateTime());
		functionMasterEntity.setIsActive(true);
		functionMasterEntity.setIsDeleted(false);
		BeanUtils.copyProperties(functionMasterEntity, auditFunctionMasterEntity);
		auditFunctionMasterEntity.setUserId(userDTO.getUserName());
		logger.info("Auditing function details");
		auditFunctionRepository.save(auditFunctionMasterEntity);
		logger.info("audited function details");
		if (functionRepository.save(functionMasterEntity) != null) {
			functionResponse = new FunctionResposeDTO();
			functionResponse.setRoleId(functionMasterEntity.getID());
			functionResponse.setStatus(AuthConstant.ACTIVE);
		}
		return functionResponse;
	}

	@Override
	public MainResponseDTO<CommonResponseDTO> update(FunctionUpdateRequestDto functionUpdateRequest) {
		logger.info("Received request for update function service");
		LocalDateTime now = LocalDateTime.now();
		MainResponseDTO<CommonResponseDTO> response = new MainResponseDTO<>();
		CommonResponseDTO functionUpdateResponseDto = new CommonResponseDTO();
		FunctionMasterEntity functionMasterEntity = null;
		AuditFunctionMasterEntity auditFunctionMasterEntity = new AuditFunctionMasterEntity();
		String functionName = functionUpdateRequest.getFunctionName().toUpperCase();
		String newfunctionName = functionUpdateRequest.getNewFunctionName().toUpperCase();
		Optional<FunctionMasterEntity> OptionalFunctionMasterEntity = functionRepository.findById(functionName);
		if (OptionalFunctionMasterEntity.isPresent()) {
			functionMasterEntity = OptionalFunctionMasterEntity.get();
			logger.info(
					"function:" + functionMasterEntity.toString() + "is present with function name:" + functionName);
		} else {
			logger.info("Existing Function Name : " + functionName + " not found in system");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.FUNCTION_ID_NOTFOUND_ERR_CODE,
					String.format(AuthConstant.FUNCTION_ID_NOT_FOUND, functionName)));
		}
		if (functionRepository.existsByFunctionName(newfunctionName)) {
			logger.info("function : " + newfunctionName + " Already exists");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.FUNCTION_EXISTS_ERR_CODE,
					String.format(AuthConstant.FUNCTION_EXISTS, newfunctionName)));
		}
		if (functionMasterEntity.getIsActive() && (!functionMasterEntity.getIsDeleted())) {

			logger.info("function: with function name:" + functionName + "is not deleted");
			auditFunctionMasterEntity.setFunctionName(functionUpdateRequest.getFunctionName());
			auditFunctionMasterEntity.setIsActive(true);
			auditFunctionMasterEntity.setIsDeleted(false);
			auditFunctionMasterEntity.setUpdatedBy(userDTO.getUserName());
			auditFunctionMasterEntity.setUpdatedDateTime(now);
			auditFunctionMasterEntity.setTimeStamp(now);

			BeanUtils.copyProperties(functionMasterEntity, auditFunctionMasterEntity);
			auditFunctionMasterEntity.setUserId(userDTO.getUserName());
			logger.info("Auditing existing data to audit table");
			auditFunctionRepository.save(auditFunctionMasterEntity);
			logger.info("save function entity data into audit table");
			int status = functionRepository.updateFunction(functionName, newfunctionName,
					DateUtils.getCurrentLocalDateTime(), userDTO.getUserName());
			logger.info("update role entity data into main table");
			if (status > 0) {
				functionUpdateResponseDto.setMessage(AuthConstant.FUNCTION_UPDATE_SUCCESS);
			} else {
				functionUpdateResponseDto.setMessage(String.format(AuthConstant.FUNCTION_UPDATE_FAILED, functionName));
			}
		} else {
			logger.info("Existing Role " + functionName + " is not Active or is deleted");
			throw new DateParsingException(new ServiceError(AuthErrorConstant.FUNCTION_NOTACTIVE_ERR_CODE,
					String.format(AuthConstant.FUNCTION_NOT_ACTIVE_OR_DELETED, functionName)));
		}
		response.setResponse(functionUpdateResponseDto);
		return response;
	}

	/**
	 * <p>
	 * This method brings the all function's present in the database.
	 * </p>
	 * </br>
	 * 1. Checks the db for function details.</br>
	 * 2. if function details not found throws the exception </br>
	 * 3. else returns the function's </br>
	 * 
	 * @return FunctionDetailsDto list of role's </br>
	 */
	@Override
	public List<FunctionDetailsDto> getFunctions() {
		logger.info("Received request for get functions service");
		List<FunctionDetailsDto> functionDetails = new ArrayList<FunctionDetailsDto>();
		List<FunctionMasterEntity> functions = functionRepository.findByIsActiveAndIsDeleted(true, false);
		if (!functions.isEmpty()) {
			for (FunctionMasterEntity function : functions) {
				FunctionDetailsDto functionDetailsDto = new FunctionDetailsDto();
				functionDetailsDto.setId(function.getID());
				functionDetailsDto.setFunctionName(function.getFunctionName());
				functionDetails.add(functionDetailsDto);
			}
		} else {
			logger.error("No Function Available in function master table");
			throw new DateParsingException(
					new ServiceError(AuthErrorConstant.RECORD_NOT_FOUND_ERR_CODE, AuthConstant.RECORD_NOT_FOUND));
		}
		return functionDetails;
	}

	@Override
	public MainResponseDTO<CommonResponseDTO> deleteFunction(
			MainRequestDTO<FunctionDeleteRequestDto> functionDeleteRequestDto) {
		logger.info("Request received for deleting function by admin");
		FunctionDeleteRequestDto request = functionDeleteRequestDto.getRequest();
		MainResponseDTO<CommonResponseDTO> response = new MainResponseDTO<>();
		CommonResponseDTO functionDeleteResponseDto = new CommonResponseDTO();
		AuditFunctionMasterEntity auditFunctionMasterEntity = new AuditFunctionMasterEntity();
		FunctionMasterEntity functionMasterEntity = null;
		String functionName = request.getFunctionName().toUpperCase();
			Optional<FunctionMasterEntity> optionalFunctionMasterEntity = functionRepository
					.findById(functionName);
			if (optionalFunctionMasterEntity.isPresent()) {
				functionMasterEntity = optionalFunctionMasterEntity.get();
				logger.info("function: is present with function name:"
						+ functionName);
			}else {
				logger.error("function : " + functionName + " not found in system");
				throw new DateParsingException(new ServiceError(AuthErrorConstant.FUNCTION_ID_NOTFOUND_ERR_CODE,
						String.format(AuthConstant.FUNCTION_ID_NOT_FOUND, functionName)));
			}
			
			if ((!functionMasterEntity.getIsActive()) && functionMasterEntity.getIsDeleted()) {
				logger.error("Role : " + functionName + " is already deleted");
				throw new DateParsingException(new ServiceError(AuthErrorConstant.FUNCTION_ALREADY_DELETED_ERR_CODE,
						String.format(AuthConstant.FUNCTION_ALREADY_DELETED, functionName)));
			}
		if (functionMasterEntity != null) {
			BeanUtils.copyProperties(functionMasterEntity, auditFunctionMasterEntity);
			auditFunctionMasterEntity.setUserId(userDTO.getUserName());
			auditFunctionRepository.save(auditFunctionMasterEntity);
			logger.info("Audited function master data before soft delete");

			int deleteStatus = functionRepository.deleteFunction(functionName, DateUtils.getCurrentLocalDateTime());
			if (deleteStatus > 0) {
				logger.info("Function soft deletion : SUCCESS");
				functionDeleteResponseDto.setMessage(AuthConstant.FUNCTION_DELETE_SUCCESS);
			} else {
				logger.info("Function soft deletion : FAILED");
				functionDeleteResponseDto.setMessage(AuthConstant.FUNCTION_DELETE_FAILED);
			}
		}
		response.setResponse(functionDeleteResponseDto);

		return response;
	}

}
