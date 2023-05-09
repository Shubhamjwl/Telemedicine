package com.nsdl.ndhm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.ndhm.entity.PatientDiscoveryEntity;

public interface PatientDiscoveryRepository extends JpaRepository<PatientDiscoveryEntity, String>{

}
