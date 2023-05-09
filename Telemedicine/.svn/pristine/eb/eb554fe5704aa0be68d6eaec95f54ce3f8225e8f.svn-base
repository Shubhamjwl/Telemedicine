package com.nsdl.telemedicine.doctor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.DoctorDocsDtlEntity;

/**
 * @author SayaliA
 *
 */
@Repository
@Transactional
public interface DocumentRepo extends JpaRepository<DoctorDocsDtlEntity, String> {
	
	@Modifying
	@Query(value = "delete from registration.dr_docs_dtls where ddt_dr_user_id_fk=:drd_user_id",nativeQuery= true)
	public int deleteByUserId(@Param("drd_user_id") String drd_user_id);

	@Query(value = "select ddt_docs_path from registration.dr_docs_dtls where ddt_dr_user_id_fk=:drd_user_id", nativeQuery = true)
	public List<String> findDocumentPath(@Param("drd_user_id") String drd_user_id);



	
}
