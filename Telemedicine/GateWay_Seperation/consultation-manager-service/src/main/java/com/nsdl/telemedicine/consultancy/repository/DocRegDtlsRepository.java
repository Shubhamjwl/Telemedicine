package com.nsdl.telemedicine.consultancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.DocRegDtlsEntity;

@Repository
public interface DocRegDtlsRepository extends JpaRepository<DocRegDtlsEntity, Long> {

	public DocRegDtlsEntity findByDrdUserId(String drdUserId);

}
