/**
 * 
 */
package com.nsdl.telemedicine.doctor.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.telemedicine.doctor.entity.AuditScribeRegEntity;

/**
 * @author Pegasus_girishk
 *
 */
@Repository
@Transactional
public interface ScribeRegRepoAudited extends JpaRepository<AuditScribeRegEntity, Integer>{

}
