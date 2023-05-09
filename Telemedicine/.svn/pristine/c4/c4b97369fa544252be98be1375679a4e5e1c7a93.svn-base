/**
 * 
 */
package com.nsdl.telemedicine.consultancy.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.consultancy.entity.PrescriptionTemplateDetails;

/**
 * @author Pegasus_girishk
 *
 */
@Repository
@Transactional
public interface PrescriptionTemplateRepo extends JpaRepository<PrescriptionTemplateDetails, String>{
	
	@Query(value = "select * from appointment.prescription_template_dtls where upper(ptd_doctor_id)=:doctorId", nativeQuery = true)
	public PrescriptionTemplateDetails checkIfPrescriptionTempAvailable(@Param("doctorId") String doctorId);
}
