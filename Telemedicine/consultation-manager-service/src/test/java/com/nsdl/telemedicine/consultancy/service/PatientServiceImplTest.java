/*package com.nsdl.telemedicine.consultancy.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.junit4.SpringRunner;

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
import com.nsdl.telemedicine.consultancy.repository.AppointmentDtlsRepository;
import com.nsdl.telemedicine.consultancy.service.impl.PatientServiceImpl;

@RunWith(SpringRunner.class)
public class PatientServiceImplTest {

	@Rule
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@InjectMocks
	private PatientServiceImpl patientServiceImpl;

	@Mock
	private AppointmentDtlsRepository appointmentDtlsRepository;

	DateTimeFormatter sdfo = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Before
	public void setUp() throws ParseException {
	}

	@Test
	public void getPatientDetailsTest() throws Exception {

		String ptRegID = "pt2";
		String appointmentID = "123";
		String drRegID = "AMOL";

		PatientDtls ptDetails = new PatientDtls();
		ptDetails.setBloodgrp("A+");
		ptDetails.setApptId(appointmentID);
		ptDetails.setHeight("5.6");
		ptDetails.setWeight("60.0");
		AddressDTO address = AddressDTO.builder().address1("om shanti").address2("indra lok").address3("bhayander")
				.build();
		ptDetails.setAddress(address);
		PtLifeStyleDtls ptLifeStyleDtls = new PtLifeStyleDtls();
		ptLifeStyleDtls.setDrinking("no");
		ptDetails.setPtLifeStyleDtls(ptLifeStyleDtls);
		PtMedicalDtls ptMedicalDtls = new PtMedicalDtls();
		ptMedicalDtls.setAllergies(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));
		ptMedicalDtls.setChronicDiseases(new ArrayList<String>());
		ptDetails.setPtMedicalDtls(ptMedicalDtls);
		PtPersonalDetals ptPersonalDetals = new PtPersonalDetals();
		ptPersonalDetals.setDob("17 11 2020");
		ptDetails.setPtPersonalDetals(ptPersonalDetals);
		
		AppointmentDtlsEntity appointment = new AppointmentDtlsEntity();
		appointment.setAdApptDateFk(LocalDate.parse("2020-11-17", sdfo));
		appointment.setAdApptSlotFk("11.30");
		appointment.setAdApptId(appointmentID);
		appointment.setAdApptStatus("S");

		PatientRegDtlsEntity patientRegDtlsEntity = new PatientRegDtlsEntity();
		patientRegDtlsEntity.setPrdAddress1("om shanti");
		patientRegDtlsEntity.setPrdAddress2("indra lok");
		patientRegDtlsEntity.setPrdAddress3("bhayander");
		patientRegDtlsEntity.setPrdHeight(5.6f);
		patientRegDtlsEntity.setPrdWeight(60f);
		patientRegDtlsEntity.setPrdBloodGroup("A+");
		patientRegDtlsEntity.setPrdDOB(LocalDate.parse("2020-11-17", sdfo));

		List<PatientMediDtlsEntity> patientMediDtls = new ArrayList<>();
		PatientMediDtlsEntity patientMediDtlsEntity = new PatientMediDtlsEntity();
		patientMediDtlsEntity.setMedicalType("allergy");
		patientMediDtlsEntity.setMedicalTypeValue("Buenos Aires");
		patientMediDtls.add(patientMediDtlsEntity);

		PatientMediDtlsEntity patientMediDtlsEntity1 = new PatientMediDtlsEntity();
		patientMediDtlsEntity1.setMedicalType("allergy");
		patientMediDtlsEntity1.setMedicalTypeValue("Córdoba");
		patientMediDtls.add(patientMediDtlsEntity1);

		PatientMediDtlsEntity patientMediDtlsEntity2 = new PatientMediDtlsEntity();
		patientMediDtlsEntity2.setMedicalType("allergy");
		patientMediDtlsEntity2.setMedicalTypeValue("La Plata");
		patientMediDtls.add(patientMediDtlsEntity2);
		patientRegDtlsEntity.setPatientMediDtlsEntity(patientMediDtls);

		List<PatientLifestyDtlsEntity> patientLifestyDtls = new ArrayList<>();
		PatientLifestyDtlsEntity patientLifestyDtlsEntity = new PatientLifestyDtlsEntity();
		patientLifestyDtlsEntity.setPlsdLfstyType("alcohol");
		patientLifestyDtlsEntity.setPlsdLfstyTypeValue("no");
		patientLifestyDtls.add(patientLifestyDtlsEntity);
		patientRegDtlsEntity.setPatientLifestyDtlsEntity(patientLifestyDtls);

		appointment.setPatientRegDtlsEntity(patientRegDtlsEntity);

		Mockito.when(appointmentDtlsRepository.findByDmdUserIdAndPrdUserIdAndAdApptId(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString())).thenReturn(appointment);

		assertThat(ptDetails)
				.isEqualToComparingFieldByField(patientServiceImpl.getPatientDetails(appointmentID, drRegID, ptRegID));
	}

	@Test(expected = ConsultationServiceException.class)
	public void getPatientDetailsNegativeTest() throws Exception {

		String ptRegID = "pt2";
		String appointmentID = "123";
		String drRegID = "AMOL";

		Mockito.when(appointmentDtlsRepository.findByDmdUserIdAndPrdUserIdAndAdApptId(Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		
		patientServiceImpl.getPatientDetails(appointmentID, drRegID, ptRegID);
	}

}
*/