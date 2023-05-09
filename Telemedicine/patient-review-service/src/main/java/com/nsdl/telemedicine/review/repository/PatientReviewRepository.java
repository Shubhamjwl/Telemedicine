package com.nsdl.telemedicine.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.review.entity.PatientRevDtlsEntity;

@Repository
//@Transactional
public interface PatientReviewRepository extends JpaRepository<PatientRevDtlsEntity, Long>{

	@Query(value = "FROM PatientRevDtlsEntity where lower(prdDrUserIdFk) =:drUserId and lower(prdPtUserIdFk) =:ptUserId", nativeQuery = false)
	public PatientRevDtlsEntity findPatientReviews(@Param("drUserId") String drUserId, @Param("ptUserId") String ptUserId);

	@Query(value = "select count(*) from usrmgmt.pt_rev_dtls where upper(prd_dr_user_id_fk) =:doctorUserId and prd_rating >= 3", nativeQuery = true)
	public Long getNumberOfLikesToDoctor(@Param("doctorUserId") String doctorUserId);
	
	@Query(value = "select count(*) from usrmgmt.pt_rev_dtls where upper(prd_dr_user_id_fk) =:doctorUserId and prd_review is not null", nativeQuery = true)
	public Long getNumberOfCommentsToDoctor(@Param("doctorUserId") String doctorUserId);
}
