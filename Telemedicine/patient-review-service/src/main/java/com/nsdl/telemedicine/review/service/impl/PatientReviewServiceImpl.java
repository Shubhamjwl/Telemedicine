package com.nsdl.telemedicine.review.service.impl;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.review.constant.ErrorConstants;
import com.nsdl.telemedicine.review.constant.PatientReviewConstants;
import com.nsdl.telemedicine.review.dto.MainRequestDTO;
import com.nsdl.telemedicine.review.dto.MainResponseDTO;
import com.nsdl.telemedicine.review.dto.PatientRevDtlsDTO;
import com.nsdl.telemedicine.review.dto.UserDTO;
import com.nsdl.telemedicine.review.entity.PatientRevDtlsEntity;
import com.nsdl.telemedicine.review.entity.PatientRevDtlsEntityAudited;
import com.nsdl.telemedicine.review.exception.PatientReviewServiceException;
import com.nsdl.telemedicine.review.exception.ServiceError;
import com.nsdl.telemedicine.review.loggers.PatientReviewLoggingClientInfo;
import com.nsdl.telemedicine.review.repository.PatientReviewAuditedRepository;
import com.nsdl.telemedicine.review.repository.PatientReviewRepository;
import com.nsdl.telemedicine.review.service.PatientReviewService;

@Service
@Transactional
@PatientReviewLoggingClientInfo
public class PatientReviewServiceImpl implements PatientReviewService {
	
	private static final Logger logger = LoggerFactory.getLogger(PatientReviewServiceImpl.class);
	
	@Autowired
	PatientReviewRepository patientReviewRepository;
	
	@Autowired
	PatientReviewAuditedRepository patientReviewAuditedRepository;
	
	@Autowired
	private UserDTO userDto;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MainResponseDTO<?> savePatientReviewDtls(MainRequestDTO<PatientRevDtlsDTO> reviewRequest) {
		MainResponseDTO mainResponseDto = new MainResponseDTO();
		PatientRevDtlsEntity reviewRequestEntity = new PatientRevDtlsEntity();
		PatientRevDtlsEntity existedReviewRequestEntity = new PatientRevDtlsEntity();
		PatientRevDtlsEntityAudited reviewRequestEntityAudited = new PatientRevDtlsEntityAudited();
		PatientRevDtlsEntity persistedPatientRevDtlsEntity = null;
		if (null != reviewRequest.getRequest()) {
			try {
				existedReviewRequestEntity = patientReviewRepository.findPatientReviews(reviewRequest.getRequest().getPrdDrUserIdFk().toLowerCase(), userDto.getUserName().toLowerCase());
				if(null != existedReviewRequestEntity) {
					BeanUtils.copyProperties(existedReviewRequestEntity, reviewRequestEntity);
					reviewRequestEntity.setPrdReview(reviewRequest.getRequest().getPrdReview());
					reviewRequestEntity.setPrdRating(reviewRequest.getRequest().getPrdRating());
				}else {
					BeanUtils.copyProperties(reviewRequest.getRequest(), reviewRequestEntity);
					reviewRequestEntity.setPrdPtUserIdFk(userDto.getUserName().toUpperCase());
				}
				persistedPatientRevDtlsEntity = patientReviewRepository.save(reviewRequestEntity);
			}catch(Exception e) {
				logger.error(" Exception while saving patient review details -->");
				e.printStackTrace();
				throw e;
			}
			//inserted audit information.
			try {
				BeanUtils.copyProperties(persistedPatientRevDtlsEntity, reviewRequestEntityAudited);
				reviewRequestEntityAudited.setUserId(persistedPatientRevDtlsEntity.getPrdPtUserIdFk());
				patientReviewAuditedRepository.save(reviewRequestEntityAudited);
			}catch(Exception e) {
				logger.error(" Exception while saving audited information of patient review details -->");
				e.printStackTrace();
				throw e;
			}
		}else {
			logger.error("Review request should not be null.");
			throw new PatientReviewServiceException(new ServiceError(ErrorConstants.INVALID_USER_INPUT.getCode(), ErrorConstants.INVALID_USER_INPUT.getMessage()));
		}
		
		mainResponseDto.setId(null);
		mainResponseDto.setVersion(reviewRequest.getVersion());
		mainResponseDto.setStatus(true);
		mainResponseDto.setResponse(PatientReviewConstants.PATIENT_REVIEW_INSERTED.getValue());
		mainResponseDto.setResponseTime(LocalDateTime.now());
		logger.info(" returning response");
		return mainResponseDto;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MainResponseDTO<?> viewPatientReviewForDoctor(MainRequestDTO<PatientRevDtlsDTO> viewPtReviewRequest) {
		MainResponseDTO mainResponseDTO = new MainResponseDTO();
		PatientRevDtlsDTO patientReviewDtlsDTO = new PatientRevDtlsDTO();
		PatientRevDtlsEntity patientRevDtlsEntity = null;
		if(null != viewPtReviewRequest.getRequest() && null != viewPtReviewRequest.getRequest().getPrdDrUserIdFk() && !viewPtReviewRequest.getRequest().getPrdDrUserIdFk().isEmpty()
			&& null != viewPtReviewRequest.getRequest().getPrdPtUserIdFk() && !viewPtReviewRequest.getRequest().getPrdDrUserIdFk().isEmpty()) {
			patientRevDtlsEntity = patientReviewRepository.findPatientReviews(viewPtReviewRequest.getRequest().getPrdDrUserIdFk().toLowerCase(), viewPtReviewRequest.getRequest().getPrdPtUserIdFk().toLowerCase());
			if(null == patientRevDtlsEntity) {
				logger.error(" No reviews found for doctor.");
				throw new PatientReviewServiceException(new ServiceError(ErrorConstants.NO_REVIEWS_FOUND.getCode(), ErrorConstants.NO_REVIEWS_FOUND.getMessage()));
			}
			BeanUtils.copyProperties(patientRevDtlsEntity, patientReviewDtlsDTO);
		}else {
			logger.error("Review request or its fields should not be null or blank.");
			throw new PatientReviewServiceException(new ServiceError(ErrorConstants.INVALID_USER_INPUT.getCode(), ErrorConstants.INVALID_USER_INPUT.getMessage()));
		}
		
		mainResponseDTO.setId(null);
		mainResponseDTO.setVersion(viewPtReviewRequest.getVersion());
		mainResponseDTO.setResponse(patientReviewDtlsDTO);
		mainResponseDTO.setStatus(true);
		mainResponseDTO.setResponseTime(LocalDateTime.now());
		logger.info(" returning response");
		return mainResponseDTO;
	}

	@Override
	public Long getNumberOfLikesToDoctor(String doctorUserId) {
		Long count = 0L;
		try {
			count =  patientReviewRepository.getNumberOfLikesToDoctor(doctorUserId.toUpperCase());
		}catch(Exception e) {
			logger.error("Exception while getting number of likes to doctor.");
	        e.printStackTrace();
		}
		return count;
	}

	@Override
	public Long getNumberOfCommentsToDoctor(String doctorUserId) {
		Long count = 0L;
		try {
			count = patientReviewRepository.getNumberOfCommentsToDoctor(doctorUserId.toUpperCase());
		}catch(Exception e) {
			logger.error("Exception while getting number of comments to doctor.");
	        e.printStackTrace();
		}
		return count;
	}
}
