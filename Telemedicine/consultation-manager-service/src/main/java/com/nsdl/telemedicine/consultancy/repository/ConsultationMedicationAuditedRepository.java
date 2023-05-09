/**
 * 
 */
package com.nsdl.telemedicine.consultancy.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.ConsultMedicationDtlsAudited;

/**
 * @author Pegasus_girishk
 *
 */
@Repository
@Transactional
public interface ConsultationMedicationAuditedRepository extends JpaRepository<ConsultMedicationDtlsAudited, String>{

}
