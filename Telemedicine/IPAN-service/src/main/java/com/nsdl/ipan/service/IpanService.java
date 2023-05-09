package com.nsdl.ipan.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.nsdl.ipan.dao.AdviceRepository;
import com.nsdl.ipan.dao.DiagnosisRepository;
import com.nsdl.ipan.dao.MedicinesRepository;
import com.nsdl.ipan.dao.SearchEngineDetailsRepository;
import com.nsdl.ipan.dao.SymptomsRepository;
import com.nsdl.ipan.model.Advice;
import com.nsdl.ipan.model.AdviceDetails;
import com.nsdl.ipan.model.Diagnosis;
import com.nsdl.ipan.model.DiagnosisDetails;
import com.nsdl.ipan.model.Medicines;
import com.nsdl.ipan.model.MedicinesDetails;
import com.nsdl.ipan.model.Symptoms;
import com.nsdl.ipan.response.SymptomDetailsDTO;

@Service
public class IpanService {

	@Autowired
	SymptomsRepository symptomsDao;

	@Autowired
	AdviceRepository advicRepo;

	@Autowired
	DiagnosisRepository diagnosisRepo;

	@Autowired
	SearchEngineDetailsRepository searchEngRepo;

	@Autowired
	MedicinesRepository medicinesRepo;

	@Cacheable("symptomList")
	public List<Symptoms> fetchSymptoms() {
		List<Symptoms> findAllSymptoms = symptomsDao.findAll();
		return findAllSymptoms;
	}

	@Cacheable("adviceList")
	public List<Advice> fetchAdvices() {
		List<Advice> findAllAdvice = advicRepo.findAll();
		return findAllAdvice;
	}

	@Cacheable("diagnosisList")
	public List<Diagnosis> fetchDiagnosis() {
		List<Diagnosis> findAllDiagnosis = diagnosisRepo.findAll();
		return findAllDiagnosis;
	}

	@Cacheable("medicinesList")
	public List<Medicines> fetchMedicines() {
		List<Medicines> findAllMedicines = medicinesRepo.findAll();
		return findAllMedicines;
	}

	public List<DiagnosisDetails> fetchDiagnosisDetails(List<Integer> idList) {
		List<Object[]> fetchDiagnosisDetailsList = searchEngRepo.fetchDiagnosisDetails(idList);

		List<DiagnosisDetails> fetchDiagnosisDetails = new ArrayList<DiagnosisDetails>();
		if (fetchDiagnosisDetailsList != null && !fetchDiagnosisDetailsList.isEmpty()) {

			for (Object[] obj : fetchDiagnosisDetailsList) {

				DiagnosisDetails diagnosisDetails = new DiagnosisDetails();
				for (Object obj1 : obj) {

					if (obj1 instanceof Integer) {
						Integer daignosisId = (Integer) obj1;
						diagnosisDetails.setId(daignosisId);
					}

					if (obj1 instanceof String) {
						String diagnosisName = (String) obj1;
						diagnosisDetails.setName(diagnosisName);
					}
				}
				fetchDiagnosisDetails.add(diagnosisDetails);
			}
		}

		return fetchDiagnosisDetails;
	}

	public List<MedicinesDetails> fetchMedicinesDetails(List<Integer> idList) {
		List<Object[]> fetchMedicinesDetailsList = searchEngRepo.fetchMedicinesDetails(idList);

		List<MedicinesDetails> fetchMedicinesDetails = new ArrayList<MedicinesDetails>();
		if (fetchMedicinesDetailsList != null && !fetchMedicinesDetailsList.isEmpty()) {

			for (Object[] obj : fetchMedicinesDetailsList) {

				MedicinesDetails medicinesDetails = new MedicinesDetails();
				for (Object obj1 : obj) {

					if (obj1 instanceof Integer) {
						Integer daignosisId = (Integer) obj1;
						medicinesDetails.setId(daignosisId);
					}
					if (obj1 instanceof String) {
						String diagnosisName = (String) obj1;
						medicinesDetails.setName(diagnosisName);
					}
				}
				fetchMedicinesDetails.add(medicinesDetails);
			}
		}

		return fetchMedicinesDetails;
	}

	public List<AdviceDetails> fetchAdviceDetails(List<Integer> idList) {
		List<Object[]> fetchAdviceDetailsList = searchEngRepo.fetchAdviceDetails(idList);

		List<AdviceDetails> fetchAdviceDetails = new ArrayList<AdviceDetails>();
		if (fetchAdviceDetailsList != null && !fetchAdviceDetailsList.isEmpty()) {

			for (Object[] obj : fetchAdviceDetailsList) {

				AdviceDetails adviceDetails = new AdviceDetails();
				for (Object obj1 : obj) {

					if (obj1 instanceof Integer) {
						Integer daignosisId = (Integer) obj1;
						adviceDetails.setId(daignosisId);
					}

					if (obj1 instanceof String) {
						String diagnosisName = (String) obj1;
						adviceDetails.setName(diagnosisName);
					}
				}
				fetchAdviceDetails.add(adviceDetails);
			}
		}
		return fetchAdviceDetails;
	}

	@Cacheable(cacheNames = "ruleEngineList", key = "#idList")
	public SymptomDetailsDTO fetchSymptomsById(List<Integer> idList) {

		List<DiagnosisDetails> fetchDiagnosisDetails = this.fetchDiagnosisDetails(idList);
		List<AdviceDetails> fetchAdvicesDetails = this.fetchAdviceDetails(idList);
		List<MedicinesDetails> fetchMedicinesDetails = this.fetchMedicinesDetails(idList);

		SymptomDetailsDTO symptomInfo = new SymptomDetailsDTO();
		symptomInfo.setDiagnosisDetails(fetchDiagnosisDetails);
		symptomInfo.setMedicinesDetails(fetchMedicinesDetails);
		symptomInfo.setAdvicesDetails(fetchAdvicesDetails);

		return symptomInfo;
	}

}
