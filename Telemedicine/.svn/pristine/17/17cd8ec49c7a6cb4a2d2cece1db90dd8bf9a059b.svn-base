package com.nsdl.telemedicine.consultancy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nsdl.telemedicine.consultancy.constant.ErrorConstants;
import com.nsdl.telemedicine.consultancy.dto.AddressDTO;
import com.nsdl.telemedicine.consultancy.dto.PtLifeStyleDtls;
import com.nsdl.telemedicine.consultancy.dto.PtMedicalDtls;
import com.nsdl.telemedicine.consultancy.dto.PtPersonalDetals;
import com.nsdl.telemedicine.consultancy.dto.PatientDtls;
import com.nsdl.telemedicine.consultancy.entity.AppointmentDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientLifestyDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientMediDtlsEntity;
import com.nsdl.telemedicine.consultancy.entity.PatientRegDtlsEntity;
import com.nsdl.telemedicine.consultancy.exception.ConsultationServiceException;
import com.nsdl.telemedicine.consultancy.logger.LoggingClientInfo;
import com.nsdl.telemedicine.consultancy.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.consultancy.service.PatientService;
import com.nsdl.telemedicine.consultancy.utility.DateTimeUtil;
import com.nsdl.telemedicine.consultancy.utility.EmptyCheckUtility;

@Service
@LoggingClientInfo
public class PatientServiceImpl implements PatientService {

	@Autowired
	private AppointmentDtlsRepository appointmentDtlsRepository;

	private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Override
	public PatientDtls getPatientDetails(String appointmentID, String drRegID, String ptRegID) {

		if (!EmptyCheckUtility.isNullEmpty(appointmentID) && !EmptyCheckUtility.isNullEmpty(drRegID)
				&& !EmptyCheckUtility.isNullEmpty(ptRegID)) {
			logger.info("Validated request parameters successfully");
			
			AppointmentDtlsEntity appointmentDtlsEntity = appointmentDtlsRepository
					.findByDmdUserIdAndPrdUserIdAndAdApptId(drRegID, ptRegID, appointmentID);

			if (appointmentDtlsEntity != null) {
				logger.info("Fetched appointment data from entity table");
				PatientRegDtlsEntity patientRegDtlsEntity = appointmentDtlsEntity.getPatientRegDtlsEntity();

				if (patientRegDtlsEntity != null) {

					// GET ADDRESS DETAILS
					AddressDTO address = AddressDTO.builder().address1(patientRegDtlsEntity.getPrdAddress1())
							.address2(patientRegDtlsEntity.getPrdAddress2())
							.address3(patientRegDtlsEntity.getPrdAddress3()).build();

					// GET MEDICAL DETAILS
					List<PatientMediDtlsEntity> patientMediDtls = patientRegDtlsEntity.getPatientMediDtlsEntity();
					PtMedicalDtls medicalDetails = new PtMedicalDtls();
					if (patientMediDtls != null && !patientMediDtls.isEmpty()) {
						medicalDetails.setAllergies(patientMediDtls.stream()
								.filter(dtls -> dtls.getMedicalType().equalsIgnoreCase("allergy"))
								.map(dtls -> dtls.getMedicalTypeValue()).collect(Collectors.toList()));
						medicalDetails.setChronicDiseases(patientMediDtls.stream()
								.filter(dtls -> dtls.getMedicalType().equalsIgnoreCase("chronicDisease"))
								.map(dtls -> dtls.getMedicalTypeValue()).collect(Collectors.toList()));
					}

					// GET LIFESTYLE DETAILS
					PtLifeStyleDtls lifeStyleDtls = new PtLifeStyleDtls();
					List<PatientLifestyDtlsEntity> patientLifestyDtls = patientRegDtlsEntity
							.getPatientLifestyDtlsEntity();
					if (patientLifestyDtls != null && !patientLifestyDtls.isEmpty()) {
						lifeStyleDtls.setSmoking(patientRegDtlsEntity.getPatientLifestyDtlsEntity().parallelStream()
								.filter(dtls -> dtls.getPlsdLfstyType().equalsIgnoreCase("smoking"))
								.map(dtls -> dtls.getPlsdLfstyTypeValue()).findFirst().orElseGet(() -> null));

						lifeStyleDtls.setDrinking(patientRegDtlsEntity.getPatientLifestyDtlsEntity().parallelStream()
								.filter(dtls -> dtls.getPlsdLfstyType().equalsIgnoreCase("alcohol"))
								.map(dtls -> dtls.getPlsdLfstyTypeValue()).findFirst().orElseGet(() -> null));
					}

					// Initialize personal data
					PtPersonalDetals personalDetals = new PtPersonalDetals();
					personalDetals.setDob(DateTimeUtil.formatDateToString(patientRegDtlsEntity.getPrdDOB()));
					personalDetals.setEmailId(patientRegDtlsEntity.getPrdEmail());
					personalDetals.setGender(patientRegDtlsEntity.getPrdGender());
					personalDetals.setMobileNo(patientRegDtlsEntity.getPrdMobileNo());
					personalDetals.setName(patientRegDtlsEntity.getPrdName());

					// Initialize response object
					PatientDtls response = new PatientDtls();
					response.setBloodgrp(patientRegDtlsEntity.getPrdBloodGroup());
					response.setApptId(appointmentDtlsEntity.getAdApptId());
					response.setHeight(
							patientRegDtlsEntity.getPrdHeight() != null ? patientRegDtlsEntity.getPrdHeight().toString()
									: null);
					response.setWeight(
							patientRegDtlsEntity.getPrdWeight() != null ? patientRegDtlsEntity.getPrdWeight().toString()
									: null);
					response.setAddress(address);
					response.setPtLifeStyleDtls(lifeStyleDtls);
					response.setPtMedicalDtls(medicalDetails);
					response.setPtPersonalDetals(personalDetals);
					logger.info("Mapped data to response");
					return response;
				} else {
					logger.error("No patient data found for provided patient userId in specified appointment entry");
					throw new ConsultationServiceException(ErrorConstants.PATIENT_DATA_NOT_FOUND.getCode(),
							ErrorConstants.PATIENT_DATA_NOT_FOUND.getMessage());
				}
			} else {
				logger.error("No result data found for provided appointmentId");
				throw new ConsultationServiceException(ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getCode(),
						ErrorConstants.NO_APPOINTMENT_DATA_FOUND.getMessage());
			}
		} else {
			logger.error("Invalid/Inappropriate request data");
			throw new ConsultationServiceException(ErrorConstants.INVALID_INPUT_DATA.getCode(),
					ErrorConstants.INVALID_INPUT_DATA.getMessage());
		}
	}

}
