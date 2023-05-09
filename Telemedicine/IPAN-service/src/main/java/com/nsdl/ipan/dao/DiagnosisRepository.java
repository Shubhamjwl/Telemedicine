package com.nsdl.ipan.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.ipan.model.Diagnosis;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer>{

	
}
