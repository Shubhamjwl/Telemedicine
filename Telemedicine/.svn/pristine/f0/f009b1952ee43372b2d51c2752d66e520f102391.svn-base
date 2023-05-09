package com.nsdl.telemedicine.patient.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.patient.constant.AuthConstant;
import com.nsdl.telemedicine.patient.constant.AuthErrorConstant;
import com.nsdl.telemedicine.patient.dto.DocumentDtlsResponse;
import com.nsdl.telemedicine.patient.dto.ExceptionJSONInfoDTO;
import com.nsdl.telemedicine.patient.dto.PatientResponseDto;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.dto.UploadDocumentRequest;
import com.nsdl.telemedicine.patient.dto.UploadDocumentResponse;
import com.nsdl.telemedicine.patient.dto.UserDTO;
import com.nsdl.telemedicine.patient.entity.PatientPersonalDetailEntity;
import com.nsdl.telemedicine.patient.entity.PatientReportUploadEntity;
import com.nsdl.telemedicine.patient.exception.DateParsingException;
import com.nsdl.telemedicine.patient.loggers.PatientLoggingClientInfo;
import com.nsdl.telemedicine.patient.repository.PatientPersonalDetailsRepository;
import com.nsdl.telemedicine.patient.repository.PatientReportUploadRepo;
import com.nsdl.telemedicine.patient.service.DocumentManagementService;
import com.nsdl.telemedicine.patient.utility.AuthUtil;

@Service
@PatientLoggingClientInfo
public class DocumentManagementServiceImpl implements DocumentManagementService{

	@Value("${uploadedPath}")
	private String uploadedPath;
	
	@Value("${ProfilePhotoSize}")
	private String documentUploadSize;
	
	@Autowired
	private UserDTO userDto;
	
	@Autowired
	PatientPersonalDetailsRepository patientPersonalDetailsRepository;
	
	@Autowired
	PatientReportUploadRepo patientReportUploadRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(DocumentManagementServiceImpl.class);

	@Override
	public ResponseWrapper<UploadDocumentResponse> savePatientReports(@Valid RequestWrapper<UploadDocumentRequest> request) {
	
		logger.info("Inside Save Patient Reports");
		ResponseWrapper<UploadDocumentResponse> response = null;
		response = (ResponseWrapper<UploadDocumentResponse>) AuthUtil.getMainResponseDto(request);	
		String temppath = null;
		String userId = userDto.getUserName().trim().toUpperCase();
		PatientReportUploadEntity patientReportUploadEntity = null;
		UploadDocumentResponse responseMsg = new UploadDocumentResponse();
	//	UploadDocumentResponse response = new UploadDocumentResponse();
		
			if(request.getRequest().getDocType()!= null) {
				
				File byteStorePath = null;
				byteStorePath = new File(uploadedPath);
				
				PatientPersonalDetailEntity patientDtls = patientPersonalDetailsRepository.findByPtUserID(userId);
				
				if(request.getRequest().getDocData() != null) {

					
						if (request.getRequest().getDocData().length() < Long
								.parseLong(documentUploadSize)) {
							 
							String[] strings = request.getRequest().getDocData().split(",");
							byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
							try {
							createDirectory(AuthConstant.FOLDER_NAME_REPORT);
							createDirectory(AuthConstant.FOLDER_NAME_REPORT + File.separator
									+ userId);
							} catch (Exception e) {
								e.printStackTrace();
								response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.INVALID_REQUEST,
										AuthConstant.SOMETHING_WENT_WRONG));
							}
							logger.info("get path of file and save file to particular path");
							temppath = byteStorePath + File.separator
									+ AuthConstant.FOLDER_NAME_REPORT + File.separator
									+ userId;
							String docName=request.getRequest().getDocName();
							if(docName.contains(" ")){
								docName = docName.trim().replaceAll("\\s+", "_");
							}
							String path = temppath + File.separator + docName;
							File file = new File(path);
							if(file.exists()) {
								throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.FILE_WRITE,
										AuthConstant.FILE_EXIST));
							}else {
								if (path != null) {
								    patientReportUploadEntity = new PatientReportUploadEntity();
									patientReportUploadEntity.setPtUserId(userId);
									patientReportUploadEntity.setReportType(request.getRequest().getDocType());
									patientReportUploadEntity.setReportName(request.getRequest().getDocName());
									patientReportUploadEntity.setReportPath(path);
									patientReportUploadEntity.setUploadDate(LocalDateTime.now());
									patientReportUploadEntity.setUploadedBy(patientDtls.getPtFullName());
									patientReportUploadRepo.save(patientReportUploadEntity);	
								}
							}
							
							try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
								logger.info("write data to file");
								if (patientReportUploadEntity != null) {
									outputStream.write(data);
								}
							} catch (IOException e) {
								e.printStackTrace();
								response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.FILE_WRITE,
										AuthConstant.FILE_WRITE));
							}
						} else {
							throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.SIZE_LIMIT,
									AuthConstant.SIZE_LIMIT_DOC));
						}
					
				}else {
					throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_REQUEST,
							AuthConstant.SOMETHING_WENT_WRONG));
				}
			}
	
		logger.info("Returning from saveConsultationPatientReports() of service class");
		responseMsg.setMsg(AuthConstant.UPLOADED_SUCCESSFULLY);
		response.setResponse(responseMsg);
		response.setStatus(true);;
		return response;

	}
	
	
	public void createDirectory(String folder_name) throws IOException {
		logger.info("Inside Create Directory method");
		String directoryFilePath = getFilePathBasedOnOS() + "/" + folder_name;
		File file = new File(directoryFilePath);
		if (!file.exists()) {
			try {
				file.mkdir();
			} catch (Exception e) {
			}
		} else {
		}
	}
	
	public String getFilePathBasedOnOS() throws IOException {
		logger.info("Fetch File Path Based On OS");
		logger.info("Returning from getFilePathBasedOnOS() of service class");
		return uploadedPath;
	}


	@Override
	public ResponseWrapper<List<DocumentDtlsResponse>> fetchAllDocumentDtls() {
		
		ResponseWrapper<List<DocumentDtlsResponse>> response=new ResponseWrapper<List<DocumentDtlsResponse>>();
		List<DocumentDtlsResponse> responseList = new ArrayList<DocumentDtlsResponse>();
		 String ptUserId = userDto.getUserName().trim().toUpperCase();
		if(ptUserId != null) {
			List<PatientReportUploadEntity> patientReportUploadEntity = patientReportUploadRepo.getAllDocument(ptUserId);
			if(patientReportUploadEntity != null && !patientReportUploadEntity.isEmpty() && patientReportUploadEntity.size() > 0) {
			
			for(PatientReportUploadEntity patientReportDtls: patientReportUploadEntity) {
				DocumentDtlsResponse docDtls = new DocumentDtlsResponse();
				docDtls.setDocId(patientReportDtls.getId());
				docDtls.setDocumentName(patientReportDtls.getReportName());
				docDtls.setDocumentType(patientReportDtls.getReportType());
				docDtls.setDocumentPath(patientReportDtls.getReportPath());
				docDtls.setUploadDate(patientReportDtls.getUploadDate());
				docDtls.setUploadedBy(patientReportDtls.getUploadedBy());
				responseList.add(docDtls);
			}
			}else {
				response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.NO_DETAILS_FOUND,
						AuthConstant.NO_DETAILS_FOUND));
			}
		}else {
			response.setErrors(AuthUtil.getExceptionList(null, AuthErrorConstant.INVALID_REQUEST,
					AuthConstant.SOMETHING_WENT_WRONG));
		}
	    response.setResponse(responseList);
		return response;
	}
	
	@Override
	public int deletePatientDocument(Integer id) {
		String ptUserId = userDto.getUserName().trim().toUpperCase();
		int status = 1;
		if(ptUserId != null) {
		try {
			PatientReportUploadEntity reportEntity = patientReportUploadRepo.findDocumentPath(id,ptUserId);
			File file = null;
			if (reportEntity != null) {
				String path = reportEntity.getReportPath();
				if(path != null) {
					file = new File(path);
					if (file.delete()) {
						patientReportUploadRepo.delete(reportEntity);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();;
			status = 0;
		}
		}else {
			throw new DateParsingException(new ExceptionJSONInfoDTO(AuthErrorConstant.INVALID_USERID,
					AuthConstant.SOMETHING_WENT_WRONG));
		}
		return status;
	}
	
}
