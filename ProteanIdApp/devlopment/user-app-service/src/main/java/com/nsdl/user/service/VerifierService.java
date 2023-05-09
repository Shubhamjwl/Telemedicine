package com.nsdl.user.service;

import com.nsdl.user.dto.SyncRequest;
import com.nsdl.user.dto.SyncResponse;

public interface VerifierService {

	SyncResponse sync(SyncRequest request);

}
