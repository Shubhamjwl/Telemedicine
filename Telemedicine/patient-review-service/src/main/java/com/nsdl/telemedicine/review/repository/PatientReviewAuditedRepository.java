/**
 * 
 */
package com.nsdl.telemedicine.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.review.entity.PatientRevDtlsEntityAudited;

/**
 * @author Pegasus_girishk
 *
 */
@Repository
//@Transactional
public interface PatientReviewAuditedRepository extends JpaRepository<PatientRevDtlsEntityAudited, Long>{

}
