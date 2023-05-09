/**
 * 
 */
package com.nsdl.telemedicine.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.patient.entity.ScribeRegEntity;




/**
 * @author SayaliA
 *
 */

@Repository
public interface ScribeRegRepo extends JpaRepository<ScribeRegEntity, String>{
	

	@Query(value = "FROM ScribeRegEntity where lower(scrbUserID) =:srdUserId", nativeQuery = false)
	public ScribeRegEntity findScribe(@Param("srdUserId") String srdUserId);

}
