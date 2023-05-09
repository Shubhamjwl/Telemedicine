package com.nsdl.telemedicine.doctor.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.DoctorAuditDtlsEntity;

@Transactional
@Repository
public interface DoctorAuditRepo extends JpaRepository<DoctorAuditDtlsEntity, String> {
	
	@Modifying
	@Query(value = "delete from audit.aud_dr_reg_dtls where drd_user_id=:drd_user_id",nativeQuery= true)
	public int deleteByUserId(@Param("drd_user_id") String drd_user_id);

}
