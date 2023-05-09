package com.nsdl.auth.service;

import java.util.List;

import javax.validation.Valid;

import com.nsdl.auth.dto.AdminConfigRequest;
import com.nsdl.auth.dto.AdminConfigResponse;
import com.nsdl.auth.dto.AdminSendNotificationRequest;
import com.nsdl.auth.dto.AdminSendNotificationResponse;
import com.nsdl.auth.dto.CategoryStatusRequest;
import com.nsdl.auth.dto.CategoryStatusResponse;
import com.nsdl.auth.dto.DoctorDtoResponse;
import com.nsdl.auth.dto.MainRequestDTO;
import com.nsdl.auth.dto.MainResponseDTO;

public interface AdminConfigrationService {

	List<AdminConfigResponse> adminConfig(MainRequestDTO<AdminConfigRequest> adminConfigRequest);

	MainResponseDTO<CategoryStatusResponse> updateCategoryStatus(
			@Valid MainRequestDTO<List<CategoryStatusRequest>> categoryStatusRequest);

	List<AdminSendNotificationResponse> adminSendAlert(
			@Valid MainRequestDTO<AdminSendNotificationRequest> adminSendAlert);

	List<DoctorDtoResponse> getAllDoctorList();

}
