package com.nsdl.ndhm.transfer.service;

import org.springframework.http.ResponseEntity;

import com.nsdl.ndhm.transfer.dto.SharePatientReqDTO;
import com.nsdl.ndhm.transfer.dto.SmsNotifyResponseDTO;

public interface SharePatientService {

	ResponseEntity<String> sharePatient(SharePatientReqDTO sharePatientReqDTO);

	void onSmsNotify(SmsNotifyResponseDTO smsNotifyResponseDTO);

}
