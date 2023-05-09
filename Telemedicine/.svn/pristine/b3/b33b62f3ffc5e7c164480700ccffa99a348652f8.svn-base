package com.nsdl.payment.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.nsdl.payment.entity.DocMstrDtlsEntity;

@Repository
@Transactional
public interface DocMstrDtlsRepository
		extends JpaRepository<DocMstrDtlsEntity, Long>, JpaSpecificationExecutor<DocMstrDtlsEntity> {

	public DocMstrDtlsEntity findByDmdUserId(String doctorUserId);

	
}
