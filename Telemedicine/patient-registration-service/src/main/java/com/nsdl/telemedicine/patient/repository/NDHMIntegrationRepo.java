package com.nsdl.telemedicine.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nsdl.telemedicine.patient.entity.NDHMDeatilsEntity;

@Repository
@Transactional
public interface NDHMIntegrationRepo extends JpaRepository<NDHMDeatilsEntity, Integer> {

}
