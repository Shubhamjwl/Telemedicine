package com.nsdl.auth.service;

import com.nsdl.auth.entity.LoginUserEntity;

public interface AuditService {
	
	boolean auditloginService(LoginUserEntity loginUserEntity, String userId);
}
