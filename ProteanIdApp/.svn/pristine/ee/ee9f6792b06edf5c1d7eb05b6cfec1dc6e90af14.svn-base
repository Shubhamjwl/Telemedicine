package com.nsdl.authenticate.uin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nsdl.authenticate.uin.entity.UinAuthenticateEntity;

public interface UinAuthenticateRepository extends JpaRepository<UinAuthenticateEntity, Integer> {
	
	@Query(value="select * from public.authenticate_uid_audit where auth_uid=?1 order by auth_otp_created_tmstmp desc",nativeQuery=true)
    List<UinAuthenticateEntity> findByAuthUinOrderByAuthOtpCreatedTmstmp(String authUin);

}
