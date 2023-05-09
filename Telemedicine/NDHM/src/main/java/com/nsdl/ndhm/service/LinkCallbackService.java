package com.nsdl.ndhm.service;

import com.nsdl.ndhm.dto.DisLinkingInitReqDTO;
import com.nsdl.ndhm.dto.LinkConfirmDTO;
import com.nsdl.ndhm.dto.OnAddContextResponseDTO;
import org.springframework.http.ResponseEntity;

public interface LinkCallbackService {

	void updateOnAddContext(OnAddContextResponseDTO addContextResponseDTO);

	ResponseEntity<String> discoverInitDemo(DisLinkingInitReqDTO disLinkingInitReqDTO);
	
	ResponseEntity<String> discoverInit(DisLinkingInitReqDTO disLinkingInitReqDTO) throws Exception;

	ResponseEntity<String> linkOnConfirm(LinkConfirmDTO linkConfirmDTO);
	
	ResponseEntity<String> linkOnConfirmActual(LinkConfirmDTO linkConfirmDTO);
}
