package com.nsdl.user.service;

import com.nsdl.user.dto.BioRequest;
import com.nsdl.user.dto.BioResponse;
import com.nsdl.user.dto.ConsentChangeRequest;
import com.nsdl.user.dto.ConsentChangeResponse;
import com.nsdl.user.dto.SubmitOtpRequest;
import com.nsdl.user.dto.SubmitOtpResponse;
import com.nsdl.user.dto.SubmitUidRequest;
import com.nsdl.user.dto.SubmitUidResponse;

public interface UserService {

	SubmitUidResponse submitUid(SubmitUidRequest request);

	SubmitOtpResponse submitOtp(SubmitOtpRequest request);

	ConsentChangeResponse consentQrCodeVerification(ConsentChangeRequest request);

	BioResponse getBioDetails(BioRequest bioRequest);

}
