package com.nsdl.telemedicine.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.nsdl.telemedicine.entity.FeatureCategoryMapDtlsEntity;

public interface FeatureCategoryMapDtlsRepo extends CrudRepository<FeatureCategoryMapDtlsEntity, Integer> {

	
	@Query("FROM FeatureCategoryMapDtlsEntity where dfmdDrEmailId=?1")
	public List<FeatureCategoryMapDtlsEntity> findByDrEmailId(String drEmailId);
}
