package com.nsdl.telemedicine.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.nsdl.telemedicine.master.entity.FeatureCategoryMasterEntity;

public interface FeatureCategoryRepo extends CrudRepository<FeatureCategoryMasterEntity, Integer>{

	@Query(value="select * from master.feature_category_mstr fcm where fcm.fcm_flag='true' order by fcm_id ASC",nativeQuery = true)
	public List<FeatureCategoryMasterEntity> findAllCategoryName();
	
}
