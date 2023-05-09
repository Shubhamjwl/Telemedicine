package com.nsdl.telemedicine.doctor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.AssociationEntity;

@Repository
@Transactional
public interface AssociationRepository extends JpaRepository<AssociationEntity, Integer> {
	
	@Query(value = "Select upper(amName) FROM AssociationEntity where amIsActive = true", nativeQuery = false)
	public List<String> findAssociationNamesByActiveFlag();

}
