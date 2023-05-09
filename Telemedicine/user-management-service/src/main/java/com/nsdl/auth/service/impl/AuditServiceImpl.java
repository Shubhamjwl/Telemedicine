package com.nsdl.auth.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsdl.auth.entity.AuditLoginUserEntity;
import com.nsdl.auth.entity.LoginUserEntity;
import com.nsdl.auth.repository.AuditLoginUserRepo;
import com.nsdl.auth.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

	@Autowired
	AuditLoginUserRepo auditRepo;
	
	@Override
	public boolean auditloginService(LoginUserEntity loginUserEntity, String userId) {
		AuditLoginUserEntity auditEntity = new AuditLoginUserEntity();
		BeanUtils.copyProperties(loginUserEntity,auditEntity);
		//auditEntity.setUserIdPk(loginUserEntity.getId());
		auditEntity.setAudUserId(userId);
		auditEntity.setRole(loginUserEntity.getRoleEntity().getRoleName());
		//System.out.println("audit entity is "+auditEntity);
		auditRepo.saveAndFlush(auditEntity);
		return true;
	}

}
