package com.nsdl.telemedicine.consultancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.ReceptionistDtlsEntity;


@Repository
public interface ReceptionistDtlsRepository extends JpaRepository<ReceptionistDtlsEntity, Long> {
	
	@Query(value = "select * from registration.recept_reg_dtls where rrd_user_id=:recptUserId",nativeQuery= true)
	public ReceptionistDtlsEntity findByReceptUserId(@Param("recptUserId") String recptUserId);
	
}
