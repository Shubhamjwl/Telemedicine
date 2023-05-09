package com.nsdl.otpManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.otpManager.entity.UsrOtpEmailVerifyDtl;

public interface UsrEmailVerifyRepository extends JpaRepository<UsrOtpEmailVerifyDtl, Long>{
		UsrOtpEmailVerifyDtl  findByUserIdFk(String userIdFk);
		List<UsrOtpEmailVerifyDtl> findByuserIdFkOrderByCreatedTimestampDesc(String userName);
		
}
