package com.nsdl.telemedicine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.entity.DocRegDtlsEntity;

@Repository
public interface DocRegDtlsRepository extends JpaRepository<DocRegDtlsEntity, Long> {

	
      @Query("FROM DocRegDtlsEntity where drdUserId=:userId") 	
      public DocRegDtlsEntity findByUserId(@Param("userId")String userId);   
}
