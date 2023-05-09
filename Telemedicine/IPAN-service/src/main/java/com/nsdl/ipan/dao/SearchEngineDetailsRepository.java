package com.nsdl.ipan.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nsdl.ipan.model.SearchEngineDetails;

@Repository
public interface SearchEngineDetailsRepository extends JpaRepository<SearchEngineDetails, Long> {

	@Query(value = "SELECT distinct diagnosis.id  As diagnosis_id, diagnosis.name  As diagnosis\r\n"
			+ "FROM master.consultation_ruling_engine\r\n"
			+ "INNER JOIN master.diagnosis ON consultation_ruling_engine.diagnosis_id = diagnosis.id\r\n"
			+ "INNER JOIN master.symptoms ON to_jsonb(consultation_ruling_engine.symptoms_id) @> to_jsonb(symptoms.id)\r\n"
			+ "WHERE symptoms.id in (:id) and symptoms.users_id = 0", nativeQuery = true)
	public List<Object[]> fetchDiagnosisDetails(@Param("id") List<Integer> id);

	@Query(value = "SELECT  distinct medicines.id As medicine_id, medicines.name As medicine\r\n"
			+ "FROM master.consultation_ruling_engine\r\n"
			+ "INNER JOIN master.symptoms ON to_jsonb(consultation_ruling_engine.symptoms_id) @> to_jsonb(symptoms.id)\r\n"
			+ "INNER JOIN master.medicines ON to_jsonb(consultation_ruling_engine.medicine_id) @> to_jsonb(medicines.id)\r\n"
			+ "WHERE symptoms.id in (:id) and symptoms.users_id = 0", nativeQuery = true)
	public List<Object[]> fetchMedicinesDetails(@Param("id") List<Integer> id);

	@Query(value = "SELECT distinct advice.id As advice_id, advice.name As advice\r\n"
			+ "FROM master.consultation_ruling_engine\r\n"
			+ "INNER JOIN master.symptoms ON to_jsonb(consultation_ruling_engine.symptoms_id) @> to_jsonb(symptoms.id)\r\n"
			+ "INNER JOIN master.advice ON to_jsonb(consultation_ruling_engine.advice_id) @> to_jsonb(advice.id)\r\n"
			+ "WHERE symptoms.id in (:id) and symptoms.users_id = 0", nativeQuery = true)
	public List<Object[]> fetchAdviceDetails(@Param("id") List<Integer> id);

//	@Query(value="SELECT diagnosis.id  As diagnosis_id, diagnosis.name  As diagnosis,symptoms.id As symptoms_id, symptoms.name As symptoms, medicines.id As medicine_id, medicines.name As medicine, advice.id As advice_id, advice.name As advice\r\n"
//	+ "FROM consultation_ruling_engine\r\n"
//	+ "INNER JOIN diagnosis ON consultation_ruling_engine.diagnosis_id = diagnosis.id\r\n"
//	+ "INNER JOIN symptoms ON JSON_CONTAINS(consultation_ruling_engine.symptoms_id, CAST(symptoms.id as JSON), '$')\r\n"
//	+ "INNER JOIN medicines ON JSON_CONTAINS(consultation_ruling_engine.medicine_id, CAST(medicines.id as JSON), '$')\r\n"
//	+ "INNER JOIN advice ON JSON_CONTAINS(consultation_ruling_engine.advice_id, CAST(advice.id as JSON), '$')\r\n"
//	+ "WHERE symptoms.id in (:id) and symptoms.users_id = 0", nativeQuery=true)
//public List<SearchEngineDetails>  fetchSearchEngineResults(@Param("id") List<String> id);

//@Query(value="SELECT diagnosis.id  As diagnosis_id, diagnosis.name  As diagnosis,symptoms.id As symptoms_id, symptoms.name As symptoms, medicines.id As medicine_id, medicines.name As medicine, advice.id As advice_id, advice.name As advice\r\n"
//	+ "FROM consultation_ruling_engine\r\n"
//	+ "INNER JOIN diagnosis ON consultation_ruling_engine.diagnosis_id = diagnosis.id\r\n"
//	+ "INNER JOIN symptoms ON JSON_CONTAINS(consultation_ruling_engine.symptoms_id, CAST(symptoms.id as JSON), '$')\r\n"
//	+ "INNER JOIN medicines ON JSON_CONTAINS(consultation_ruling_engine.medicine_id, CAST(medicines.id as JSON), '$')\r\n"
//	+ "INNER JOIN advice ON JSON_CONTAINS(consultation_ruling_engine.advice_id, CAST(advice.id as JSON), '$')\r\n"
//	+ "WHERE symptoms.id in (:id) and symptoms.users_id = 0", nativeQuery=true)
//public List<SearchEngineDetailsBySymptoms>  fetchSearchEngineResults(@Param("id") List<String> id);
	
}
