package com.nsdl.ndhm.utility.fhir.files.prescrip;

import com.nsdl.ndhm.dto.prescription.BinaryDTO;
import com.nsdl.ndhm.dto.prescription.PatientDTO;
import com.nsdl.ndhm.dto.prescription.PractitionerDTO;
import lombok.Builder;
import lombok.Data;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Narrative.NarrativeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The FhirResourcePopulator class populates all the FHIR resources
 */
@Data
@Builder
@Component
public class PrescriptionResource {
	private static final Logger logger = LoggerFactory.getLogger(PrescriptionResource.class);
	private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
	
	@Autowired
	private PatientDTO patientDTO;

	@Autowired
	private PractitionerDTO practitionerDTO;

	@Autowired
	private BinaryDTO binaryDTO;

	// Populate Patient Resource
	public Patient populatePatientResource(String patientGender) {

		AdministrativeGender gender = (patientGender.equalsIgnoreCase("male"))
				? AdministrativeGender.MALE : (patientGender.equalsIgnoreCase("female"))
				? AdministrativeGender.FEMALE : AdministrativeGender.OTHER;

		SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		
		String getBirthDateElement = null;
		try {
			getBirthDateElement = dateFormat.format(dateFormat.parse(patientDTO.getBirthDateElement()));
		} catch (ParseException e) {
			logger.error("error in date format: {}",e.getMessage());
		}

		Patient patient = new Patient();
		patient.setId(patientDTO.getId());
		patient.getMeta().setVersionId(patientDTO.getVersionId()).setLastUpdatedElement(new InstantType(
				patientDTO.getLastUpdatedElement()))
				.addProfile(patientDTO.getAddProfile());
		patient.getText().setStatus(NarrativeStatus.GENERATED)
				.setDivAsString(patientDTO.getDivAsString());
		patient.addIdentifier()
				.setType(new CodeableConcept(new Coding(
								   patientDTO.getCodingDTO().getTheSystem(),
								   patientDTO.getCodingDTO().getTheCode(),
								   patientDTO.getCodingDTO().getTheDisplay())))
				.setSystem(patientDTO.getIdentifierSystem()).setValue(patientDTO.getIdentifierValue());
		patient.addName().setText(patientDTO.getNameText());
		patient.addTelecom().setSystem(ContactPointSystem.PHONE).setValue(patientDTO.getTelecomValue()).setUse(ContactPointUse.HOME);
		patient.setGender(gender).setBirthDateElement(new DateType(getBirthDateElement));
		return patient;
	}

	// Populate Practitioner Resource
	public Practitioner populatePractitionerResource() {
		Practitioner practitioner = new Practitioner();
		practitioner.setId(practitionerDTO.getId());
		practitioner.getMeta().setVersionId(practitionerDTO.getVersionId()).setLastUpdatedElement(new InstantType(
				practitionerDTO.getLastUpdatedElement()))
				.addProfile(practitionerDTO.getAddProfile());
		practitioner.getText().setStatus(NarrativeStatus.GENERATED)
				.setDivAsString(practitionerDTO.getDivAsString());
		practitioner.addIdentifier()
				.setType(new CodeableConcept(new Coding(
						           practitionerDTO.getCodingDTO().getTheSystem(),
								   practitionerDTO.getCodingDTO().getTheCode(),
								   practitionerDTO.getCodingDTO().getTheDisplay())))
				.setSystem(practitionerDTO.getIdentifierSystem()).setValue(practitionerDTO.getIdentifierValue());
		practitioner.addName().setText(practitionerDTO.getNameText());
		return practitioner;
	}

	// Populate Binary Resource
	public Binary populateBinaryResource() {
		Binary binary = new Binary();
		binary.setId(binaryDTO.getId());
		binary.getMeta().addProfile(binaryDTO.getAddProfile());
		binary.setContentType(binaryDTO.getContentType());
		binary.setDataElement(new Base64BinaryType(
				binaryDTO.getDataElement()));
		return binary;
	}
}
