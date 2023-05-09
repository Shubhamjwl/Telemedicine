package com.nsdl.ndhm.utility.fhir.files.opconconsult;

import com.nsdl.ndhm.dto.opconsult.DocumentReferenceDTO;
import com.nsdl.ndhm.dto.opconsult.EncounterDTO;
import com.nsdl.ndhm.dto.opconsult.OrganizationDTO;
import com.nsdl.ndhm.dto.prescription.PatientDTO;
import com.nsdl.ndhm.dto.prescription.PractitionerDTO;
import lombok.Builder;
import lombok.Data;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointSystem;
import org.hl7.fhir.r4.model.ContactPoint.ContactPointUse;
import org.hl7.fhir.r4.model.DocumentReference.DocumentReferenceContentComponent;
import org.hl7.fhir.r4.model.DocumentReference.ReferredDocumentStatus;
import org.hl7.fhir.r4.model.Encounter.EncounterHospitalizationComponent;
import org.hl7.fhir.r4.model.Encounter.EncounterStatus;
import org.hl7.fhir.r4.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.r4.model.Enumerations.DocumentReferenceStatus;
import org.hl7.fhir.r4.model.Narrative.NarrativeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The FhirResourcePopulator class populates all the FHIR resources
 */
@Data
@Builder
@Component
public class OPConsultResource {
	private static final Logger logger = LoggerFactory.getLogger(OPConsultResource.class);
	private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

	@Autowired
	private PatientDTO patientDTO;

	@Autowired
	private PractitionerDTO practitionerDTO;

	@Autowired
	private DocumentReferenceDTO documentReferenceDTO;

	@Autowired
	private EncounterDTO encounterDTO;

	@Autowired
	private OrganizationDTO organizationDTO;

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
		practitioner.getMeta().setVersionId(practitionerDTO.getVersionId()).setLastUpdatedElement(new InstantType(practitionerDTO.getLastUpdatedElement()))
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

	// Populate Document Reference Resource
	public DocumentReference populateDocumentReferenceResource() {
		DocumentReference documentReference = new DocumentReference();
		documentReference.setId(documentReferenceDTO.getId());
		documentReference.getMeta().addProfile(documentReferenceDTO.getAddProfile());
		documentReference.setStatus(DocumentReferenceStatus.CURRENT);
		documentReference.setDocStatus(ReferredDocumentStatus.FINAL);
		documentReference.setSubject(new Reference().setReference(documentReferenceDTO.getSubjectReference()));
		documentReference
				.setType(new CodeableConcept(new Coding(
						documentReferenceDTO.getTypeCode().getTheSystem(),
						documentReferenceDTO.getTypeCode().getTheCode(),
						documentReferenceDTO.getTypeCode().getTheDisplay()))
						.setText(documentReferenceDTO.getDocumentText()));
		documentReference.getContent()
				.add(new DocumentReferenceContentComponent(new Attachment().setContentType(documentReferenceDTO.getDocContentType())
						.setLanguage(documentReferenceDTO.getDocLanguage()).setTitle(documentReferenceDTO.getDocTitle())
						.setCreationElement(new DateTimeType(documentReferenceDTO.getDocCreationDate()))
						.setDataElement(new Base64BinaryType(documentReferenceDTO.getDocData()))));
		return documentReference;
	}

	// Populate Encounter Resource
	public Encounter populateEncounterResource() {
		Encounter encounter = new Encounter();
		encounter.setId(encounterDTO.getId());
		encounter.setStatus(EncounterStatus.FINISHED);
		encounter.getMeta().setLastUpdatedElement(new InstantType(encounterDTO.getInstantType()))
				.addProfile(encounterDTO.getAddProfile());
		encounter.getIdentifier().add(new Identifier().setSystem(encounterDTO.getIdentifierSystem())
				.setValue(encounterDTO.getIdentifierValue()));
		encounter.setClass_(
				new Coding(
						encounterDTO.getEncounterCode().getTheSystem(),
						encounterDTO.getEncounterCode().getTheCode(),
						encounterDTO.getEncounterCode().getTheDisplay()));
		encounter.setSubject(new Reference().setReference(encounterDTO.getSubjectReference()));
		encounter.setPeriod(new Period().setStartElement(new DateTimeType(encounterDTO.getPeriodStart()))
				.setEndElement(new DateTimeType(encounterDTO.getPeriodEnd())));
		encounter
				.setHospitalization(new EncounterHospitalizationComponent().setDischargeDisposition(new CodeableConcept(
						new Coding(
								encounterDTO.getDischargeCode().getTheSystem(),
								encounterDTO.getDischargeCode().getTheCode(),
								encounterDTO.getDischargeCode().getTheDisplay()
						)).setText("Discharged to Home Care")));
		return encounter;
	}

	// Populate Organization Resource
	public Organization populateOrganizationResource() {
		Organization organization = new Organization();
		organization.setId(organizationDTO.getId());
		organization.getMeta().addProfile(organizationDTO.getAddProfile());
		organization.getIdentifier()
				.add(new Identifier()
						.setType(new CodeableConcept(
								new Coding(
										organizationDTO.getIdentifierCode().getTheSystem(),
										organizationDTO.getIdentifierCode().getTheCode(),
										organizationDTO.getIdentifierCode().getTheSystem()
								)))
						.setSystem(organizationDTO.getIdentifierSystem()).setValue(organizationDTO.getIdentifierValue()));
		organization.setName(organizationDTO.getOrganisationName());
		List<ContactPoint> list = new ArrayList<ContactPoint>();
		ContactPoint contact1 = new ContactPoint();
		contact1.setSystem(ContactPointSystem.PHONE).setValue(organizationDTO.getContactNum()).setUse(ContactPointUse.WORK);
		ContactPoint contact2 = new ContactPoint();
		contact2.setSystem(ContactPointSystem.EMAIL).setValue(organizationDTO.getEmail()).setUse(ContactPointUse.WORK);
		list.add(contact1);
		list.add(contact2);
		organization.setTelecom(list);
		return organization;
	}

}
