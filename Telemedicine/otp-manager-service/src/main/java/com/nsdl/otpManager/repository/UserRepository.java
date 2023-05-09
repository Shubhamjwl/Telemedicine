package com.nsdl.otpManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nsdl.otpManager.entity.LoginUserEntity;


public interface UserRepository extends JpaRepository<LoginUserEntity, Integer>{
	
		
	public LoginUserEntity findByUserIdIgnoreCase(String userId);
	
	
	@Query(value = "select count(ud_user_id) from (select ud_user_id  from usrmgmt.usr_dtls union all select prd_user_id from registration.pt_reg_dtls union all select srd_user_id from registration.scrb_reg_dtls union all select drd_user_id from registration.dr_reg_dtls ) a where lower(ud_user_id) = :userId ",nativeQuery= true) 
	public Integer getUniqueUserId(@Param("userId") String userId);
	
	@Query(value="select count(ud_email) from (select ud_email  from usrmgmt.usr_dtls union all select prd_email from registration.pt_reg_dtls union all select srd_email from registration.scrb_reg_dtls union all select drd_email from registration.dr_reg_dtls )a where lower(ud_email) = :emailId ",nativeQuery= true)
	public Integer getUniqueEmail(@Param("emailId") String emailId);
	
	@Query(value="select count(ud_mobile_no) from (select cast(ud_mobile_no  as varchar) from usrmgmt.usr_dtls union all select prd_mobile_no from registration.pt_reg_dtls union all select cast(srd_mobile_no as varchar(10)) from registration.scrb_reg_dtls union all select drd_mobile_no from registration.dr_reg_dtls ) a where ud_mobile_no = :mobileNo ",nativeQuery= true)
	public Integer getUniqueMobile(@Param("mobileNo") String mobileNo);
	
	@Query(value="select count(dmd_smc_number) from (select dmd_smc_number from registration.dr_mstr_dtls union all select drd_smc_number from registration.dr_reg_dtls ) a where lower(dmd_smc_number) =:smcNo",nativeQuery= true)
	public Integer getUniqueSmcNo(@Param("smcNo") String smcNo);
	
	@Query(value="select count(dmd_mci_number) from (select dmd_mci_number from registration.dr_mstr_dtls union all select drd_mci_number from registration.dr_reg_dtls) a where lower(dmd_mci_number)=:mciNo",nativeQuery= true)
	public Integer getUniqueMciNo(@Param("mciNo") String mciNo);
}
