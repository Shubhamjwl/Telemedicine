package com.nsdl.telemedicine.patient.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.patient.dto.DocumentDtlsResponse;
import com.nsdl.telemedicine.patient.dto.RequestWrapper;
import com.nsdl.telemedicine.patient.dto.ResponseWrapper;
import com.nsdl.telemedicine.patient.dto.UploadDocumentRequest;
import com.nsdl.telemedicine.patient.dto.UploadDocumentResponse;

@Service
public interface DocumentManagementService {

	ResponseWrapper<UploadDocumentResponse> savePatientReports(@Valid RequestWrapper<UploadDocumentRequest> request);

	ResponseWrapper<List<DocumentDtlsResponse>> fetchAllDocumentDtls();

	int deletePatientDocument(Integer request);

	
}
