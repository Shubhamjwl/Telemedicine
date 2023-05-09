package com.nsdl.telemedicine.consultancy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.telemedicine.consultancy.entity.PatientCareContextEntity;

public interface PatientCareContextRepo extends JpaRepository<PatientCareContextEntity, String> {
	

}
