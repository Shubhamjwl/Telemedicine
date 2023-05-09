package com.nsdl.telemedicine.consultancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.DocMstrDtlsEntity;

@Repository
public interface DocMstrDtlsRepository extends JpaRepository<DocMstrDtlsEntity, Long>{

	public DocMstrDtlsEntity findByDmdUserId(String drRegID);
	
}
