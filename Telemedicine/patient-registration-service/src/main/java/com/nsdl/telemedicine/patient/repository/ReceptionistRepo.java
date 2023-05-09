/**
 * 
 */
package com.nsdl.telemedicine.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.ReceptionistDtlsEntity;




/**
 * @author SayaliA
 *
 */

@Repository
public interface ReceptionistRepo extends JpaRepository<ReceptionistDtlsEntity, String>{
	

	@Query(value = "FROM ReceptionistDtlsEntity where rrdUserId =:rrdUserId", nativeQuery = false)
	public ReceptionistDtlsEntity findReceptionist(@Param("rrdUserId") String rrdUserId);

}
