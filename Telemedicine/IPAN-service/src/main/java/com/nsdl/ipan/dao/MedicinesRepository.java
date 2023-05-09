package com.nsdl.ipan.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nsdl.ipan.model.Medicines;

@Repository
public interface MedicinesRepository extends JpaRepository<Medicines, Integer>{

	
}
