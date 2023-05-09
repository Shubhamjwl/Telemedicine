package com.nsdl.ndhm.service;

import com.nsdl.ndhm.dto.AuthConfirmNotifyRequestDTO;
import com.nsdl.ndhm.dto.AuthInitReqDTO;
import com.nsdl.ndhm.dto.OnConfirmRequestDTO;
import com.nsdl.ndhm.dto.OnFetchModesRequestDTO;
import com.nsdl.ndhm.entity.AuthInitEntity;
import com.nsdl.ndhm.entity.FetchModesEntity;

public interface GatewayCallbackService {

	FetchModesEntity updateFetchModes(OnFetchModesRequestDTO onFetchModesDTO);

	AuthInitEntity updateInit(AuthInitReqDTO authInitReqDTO);
	
	void updateConfirm(OnConfirmRequestDTO onConfirmRequestDTO);

	void confirmNotify(AuthConfirmNotifyRequestDTO authConfirmNotifyRequestDTO);
}
