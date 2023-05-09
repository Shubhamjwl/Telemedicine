package com.nsdl.telemedicine.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.master.entity.DocumentMasterEntity;



@Repository
public interface DocumentRepo extends CrudRepository<DocumentMasterEntity, Integer>{

	@Query(value="select * from master.document_type_mstr dtm where dtm.dtm_flag='true' order by dtm_id ASC",nativeQuery = true)
	public List<DocumentMasterEntity> findAllDocument();
	
}
