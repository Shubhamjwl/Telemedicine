package com.nsdl.telemedicine.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.nsdl.telemedicine.master.entity.AssociationNameMasterEntity;

public interface AssociationNameRepo extends CrudRepository<AssociationNameMasterEntity, Integer> {
	
	@Query(value="select * from master.assoc_mstr am where am.am_is_active='true'",nativeQuery = true)
	public List<AssociationNameMasterEntity> findAllAssociationName();

}
