package com.nsdl.ndhm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nsdl.ndhm.entity.PatientLinkedCareContext;

public interface PatientLinkedCareContextsRepository extends JpaRepository<PatientLinkedCareContext, Integer> {
	PatientLinkedCareContext findByCareContextId(String careContextId);
}
