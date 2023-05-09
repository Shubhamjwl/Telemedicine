package com.nsdl.telemedicine.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.entity.DoctorRedFlagMarketEntity;

@Repository
@Transactional
public interface RedFlagMarketRepo extends JpaRepository<DoctorRedFlagMarketEntity, Long> {

	@Query("select distinct u.drmDate from DoctorRedFlagMarketEntity u where u.drmDrEmailid =:drEmailID AND u.drmPtMobile =:ptMobileNo order by u.drmDate desc")
	List<String> findLatestDatesByDrmDrEmailidAndDrmPtMobile(@Param("drEmailID") String drEmailID,
			@Param("ptMobileNo") String ptMobileNo, Pageable pageable);

	@Query("select u from DoctorRedFlagMarketEntity u where u.drmDrEmailid =:drEmailID AND u.drmPtMobile =:ptMobileNo AND u.drmDate IN :dates order by u.drmDate desc")
	List<DoctorRedFlagMarketEntity> findByDrmDrEmailidAndDrmPtMobile(@Param("drEmailID") String drEmailID,
			@Param("ptMobileNo") String ptMobileNo, @Param("dates") List<String> dates);
	
	@Query("From DoctorRedFlagMarketEntity r where r.drmIdentifier in(:identifierList)")
	List<DoctorRedFlagMarketEntity> findByNotExistIndentifier(@Param("identifierList")List<String> identifierList);
}
