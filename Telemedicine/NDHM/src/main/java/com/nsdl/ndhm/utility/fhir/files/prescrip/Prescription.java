package com.nsdl.ndhm.utility.fhir.files.prescrip;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;
import com.nsdl.ndhm.dto.datareport.DoctorDetailsDto;
import com.nsdl.ndhm.dto.datareport.PatientRegDetailsDTO;
import com.nsdl.ndhm.dto.datareport.ReportResponseDtls;
import com.nsdl.ndhm.dto.prescription.BinaryDTO;
import com.nsdl.ndhm.dto.prescription.CodingDTO;
import com.nsdl.ndhm.dto.prescription.PatientDTO;
import com.nsdl.ndhm.dto.prescription.PractitionerDTO;
import org.apache.commons.io.FileUtils;
import org.hl7.fhir.common.hapi.validation.support.*;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Component
public class Prescription {
	private static final Logger logger = LoggerFactory.getLogger(Prescription.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

	@Autowired
	PrescriptionResource prescriptionResource;


	
	// The FHIR context is the central starting point for the use of the HAPI FHIR
	// API
	// It should be created once, and then used as a factory for various other types
	// of objects (parsers, clients, etc.)
	static FhirContext ctx = FhirContext.forR4();

	static FhirInstanceValidator instanceValidator;
	static FhirValidator validator;

	public String getFhirString(PatientRegDetailsDTO patientRegDTO, DoctorDetailsDto doctorDetailsDto,
			ReportResponseDtls reportDtls) throws DataFormatException, IOException {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(reportDtls.getDocCreationTimestamp(), ZoneId.of("UTC"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

		String lastUpdatedElement = zonedDateTime.format(formatter);
		String patientUniqueId = patientRegDTO.getAbhaId();
		String patientGender = patientRegDTO.getGender().trim();
		String doctorUniqueId = doctorDetailsDto.getMciNo();

		// Initialize validation support and loads all required profiles
		init();

		insertData(patientRegDTO, doctorDetailsDto, reportDtls);

		// Populate the resource
		Bundle prescriptionBundle = populatePrescriptionBundle(lastUpdatedElement, patientGender, patientUniqueId,
				doctorUniqueId);

		// Validate it. Validate method return result of validation in boolean
		// If validation result is true then parse, serialize operations are performed
		if (validate(prescriptionBundle)) {
			logger.info("Validated populated Prescription bundle successfully");

			// Instantiate a new parser
			IParser parser = ctx.newJsonParser();

			// Indent the output
			parser.setPrettyPrint(true);

			// Serialize populated bundle
			String serializeBundle = parser.encodeResourceToString(prescriptionBundle);

			return serializeBundle;
		} else {
			logger.info("Failed to validate populate Prescription bundle");
			return "";
		}
	}

	// Populate Composition for Prescription
	Composition populatePrescriptionCompositionResource(String lastUpdatedElement, String patientUniqueId,
			String doctorUniqueId) {
		Composition composition = new Composition();

		// Set logical id of this artifact
		composition.setId("Composition-01");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = composition.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType(lastUpdatedElement));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/PrescriptionRecord");

		// Set language of the resource content
		composition.setLanguage("en-IN");

		// Plain text representation of the concept
		Narrative text = composition.getText();
		text.setStatus((Narrative.NarrativeStatus.GENERATED));
		text.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">Prescription report</div>");

		// Set version-independent identifier for the Composition
		Identifier identifier = composition.getIdentifier();
		identifier.setSystem("https://ndhm.in/phr");
		identifier.setValue(UUID.randomUUID().toString());

		// Status can be preliminary | final | amended | entered-in-error
		composition.setStatus(Composition.CompositionStatus.FINAL);

		// Kind of composition ("Prescription record ")
		composition
				.setType(new CodeableConcept(new Coding("http://snomed.info/sct", "440545006", "Prescription record")));

		// Set subject - Who and/or what the composition/Prescription record is about
		composition.setSubject(new Reference().setReference("Patient/" + patientUniqueId + ""));

		// Set Timestamp
		composition.setDateElement(new DateTimeType(lastUpdatedElement));

		// Set author - Who and/or what authored the composition/Presciption record
		composition.addAuthor(new Reference().setReference("Practitioner/" + doctorUniqueId + ""));

		// Set a Human Readable name/title
		composition.setTitle("Prescription record");

		// Composition is broken into sections / Prescription record contains single
		// section to define the relevant medication requests
		// Entry is a reference to data that supports this section
		Reference reference3 = new Reference();
		reference3.setReference("Binary/Binary-01");
		reference3.setType("Binary");

		Composition.SectionComponent section = new Composition.SectionComponent();
		section.setTitle("Prescription record");
		section.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "440545006", "Prescription record")))
				.addEntry(reference3);
		composition.addSection(section);

		return composition;
	}

	// Populate Prescription Bundle
	Bundle populatePrescriptionBundle(String lastUpdatedElement, String patientGender, String patientUniqueId,
			String doctorUniqueId) {
		Bundle prescriptionBundle = new Bundle();

		// Set logical id of this artifact
		prescriptionBundle.setId("prescription-bundle-01");

		// Set metadata about the resource
		Meta meta = prescriptionBundle.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType(lastUpdatedElement));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle");

		// Set Confidentiality as defined by affinity domain
		meta.addSecurity(
				new Coding("http://terminology.hl7.org/CodeSystem/v3-Confidentiality", "V", "very restricted"));

		// Set version-independent identifier for the Bundle
		Identifier identifier = prescriptionBundle.getIdentifier();
		identifier.setValue(UUID.randomUUID().toString());
		identifier.setSystem("http://hip.in");

		// Set Bundle Type
		prescriptionBundle.setType(Bundle.BundleType.DOCUMENT);

		// Set Timestamp
		prescriptionBundle.setTimestampElement(new InstantType(lastUpdatedElement));

		// Add resources entries for bundle with Full URL
		List<Bundle.BundleEntryComponent> listBundleEntries = prescriptionBundle.getEntry();

		Bundle.BundleEntryComponent bundleEntry1 = new Bundle.BundleEntryComponent();
		bundleEntry1.setFullUrl("Composition/Composition-01");
		bundleEntry1.setResource(
				populatePrescriptionCompositionResource(lastUpdatedElement, patientUniqueId, doctorUniqueId));

		Bundle.BundleEntryComponent bundleEntry2 = new Bundle.BundleEntryComponent();
		bundleEntry2.setFullUrl("Patient/" + patientUniqueId + "");
		bundleEntry2.setResource(prescriptionResource.populatePatientResource(patientGender));

		Bundle.BundleEntryComponent bundleEntry3 = new Bundle.BundleEntryComponent();
		bundleEntry3.setFullUrl("Practitioner/" + doctorUniqueId + "");
		bundleEntry3.setResource(prescriptionResource.populatePractitionerResource());

		Bundle.BundleEntryComponent bundleEntry4 = new Bundle.BundleEntryComponent();
		bundleEntry4.setFullUrl("Binary/Binary-01");
		bundleEntry4.setResource(prescriptionResource.populateBinaryResource());

		listBundleEntries.add(bundleEntry1);
		listBundleEntries.add(bundleEntry2);
		listBundleEntries.add(bundleEntry3);
		listBundleEntries.add(bundleEntry4);

		return prescriptionBundle;
	}

	/**
	 * This method initiates loading of FHIR default profiles and NDHM profiles for
	 * validation
	 */
	void init() throws DataFormatException, FileNotFoundException {

		// Create xml parser object for reading profiles
		IParser parser = ctx.newXmlParser();

		// Create a chain that will hold our modules
		ValidationSupportChain supportChain = new ValidationSupportChain();

		// Add Default Profile Support
		// DefaultProfileValidationSupport supplies base FHIR definitions. This is
		// generally required
		// even if you are using custom profiles, since those profiles will derive from
		// the base
		// definitions.
		DefaultProfileValidationSupport defaultSupport = new DefaultProfileValidationSupport(ctx);

		// Create a PrePopulatedValidationSupport which can be used to load custom
		// definitions.
		// In this example we're loading all the custom Profile Structure Definitions,
		// in other scenario we might
		// load many StructureDefinitions, ValueSets, CodeSystems, etc.
		PrePopulatedValidationSupport prePopulatedSupport = new PrePopulatedValidationSupport(ctx);
		StructureDefinition sd;

		/** LOADING PROFILES **/
		// Read all Profile Structure Definitions
		/*
		 * String[] fileList = new
		 * File("\\NDHM_FHIR_PROFILES\\").list(new WildcardFileFilter("*.xml")); for
		 * (String file : fileList) { // Parse All Profiles and add to prepopulated
		 * support sd = parser.parseResource(StructureDefinition.class, new
		 * FileReader("\\NDHM_FHIR_PROFILES\\" + file));
		 * prePopulatedSupport.addStructureDefinition(sd); }
		 */

		/*
		 * for (File f :
		 * FileUtility.getResourceFolderFiles("classpath:NDHM_FHIR_PROFILES")) { sd =
		 * parser.parseResource(StructureDefinition.class, new FileReader(f));
		 * prePopulatedSupport.addStructureDefinition(sd); }
		 */

		ResourcePatternResolver resourcePatResolver = new PathMatchingResourcePatternResolver();
		Resource[] AllResources = null;
		try {
			AllResources = resourcePatResolver.getResources("classpath:NDHM_FHIR_PROFILES/*.xml");
			for(Resource resource: AllResources) {
			    InputStream inputStream = resource.getInputStream();
			    File tempFile = File.createTempFile( "myfile", ".xml" );
			    FileUtils.copyInputStreamToFile(inputStream, tempFile);
				sd = parser.parseResource(StructureDefinition.class, new FileReader(tempFile));
				prePopulatedSupport.addStructureDefinition(sd);
			 }
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
		 
		// Add Snapshot Generation Support
		SnapshotGeneratingValidationSupport snapshotGenerator = new SnapshotGeneratingValidationSupport(ctx);

		// Add prepopulated support consisting all structure definitions and Terminology
		// support
		supportChain.addValidationSupport(defaultSupport);
		supportChain.addValidationSupport(prePopulatedSupport);
		supportChain.addValidationSupport(snapshotGenerator);
		supportChain.addValidationSupport(new InMemoryTerminologyServerValidationSupport(ctx));
		supportChain.addValidationSupport(new CommonCodeSystemsTerminologyService(ctx));

		// Create a validator using the FhirInstanceValidator module and register.
		instanceValidator = new FhirInstanceValidator(supportChain);
		validator = ctx.newValidator().registerValidatorModule(instanceValidator);

	}

	/**
	 * This method validates the FHIR resources
	 */
	boolean validate(IBaseResource resource) {
		// Validate
		ValidationResult result = validator.validateWithResult(resource);

		// The result object now contains the validation results
		result.getMessages().stream()
				.map(next -> next.getSeverity().name() + " : " + next.getLocationString() + " " + next.getMessage())
				.forEach(System.out::println);

		return result.isSuccessful();
	}

	public void insertData(PatientRegDetailsDTO patientRegDTO, DoctorDetailsDto doctorDetailsDto,
			ReportResponseDtls reportDtls) {
		SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		String patientDivData = "" + patientRegDTO.getName() + ", " + calculateAge(patientRegDTO.getDob()) + ", "
				+ patientRegDTO.getGender() + "";
		String doctorDivData = "Dr. " + doctorDetailsDto.getDrName() + ", " + doctorDetailsDto.getSpeciality() + "";
		doctorDivData = doctorDivData.replace("&", "and").replaceAll("[^.,a-zA-Z0-9\\s]", "");
		logger.info(patientDivData);
		logger.info(doctorDivData);

		PatientDTO patientDTO = PatientDTO.builder().build();
		patientDTO.setId(patientRegDTO.getAbhaId());
		patientDTO.setVersionId("1");
		patientDTO.setLastUpdatedElement(format1.format(new Date()));
		patientDTO.setAddProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Patient");
		patientDTO.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">" + patientDivData + "</div>");
		patientDTO.setCodingDTO(
				new CodingDTO("http://terminology.hl7.org/CodeSystem/v2-0203", "MR", "Medical record number"));
		patientDTO.setIdentifierSystem("https://ndhm.in/SwasthID");
		patientDTO.setIdentifierValue(patientRegDTO.getAbhaId());
		patientDTO.setNameText(patientRegDTO.getName());
		patientDTO.setTelecomValue(patientRegDTO.getMob().toString());
		patientDTO.setBirthDateElement(patientRegDTO.getDob());

		prescriptionResource.setPatientDTO(patientDTO);

		PractitionerDTO practitionerDTO = PractitionerDTO.builder().build();
		practitionerDTO.setId(doctorDetailsDto.getMciNo());
		practitionerDTO.setVersionId("1");
		practitionerDTO.setLastUpdatedElement(format1.format(new Date()));
		practitionerDTO.setAddProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Practitioner");
		practitionerDTO.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">" + doctorDivData + "</div>");
		practitionerDTO.setCodingDTO(
				new CodingDTO("http://terminology.hl7.org/CodeSystem/v2-0203", "MD", "Medical License number"));
		practitionerDTO.setIdentifierSystem("https://ndhm.in/DigiDoc");
		practitionerDTO.setIdentifierValue(doctorDetailsDto.getMciNo());
		practitionerDTO.setNameText(doctorDetailsDto.getDrName());

		prescriptionResource.setPractitionerDTO(practitionerDTO);

		BinaryDTO binaryDTO = BinaryDTO.builder().build();
		binaryDTO.setId("Binary-01");
		binaryDTO.setAddProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Binary");
		binaryDTO.setContentType("application/pdf");
		binaryDTO.setDataElement(reportDtls.getReport_doc());

		prescriptionResource.setBinaryDTO(binaryDTO);
	}

	public String calculateAge(String dob) {
		Period p = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
			LocalDate currentDate = LocalDate.parse(dateFormat.format(dateFormat.parse(dob.trim())));
			p = Period.between(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()),
					LocalDate.now());
		} catch (ParseException e) {
			logger.error("error un calculate age: {}", e.getMessage());
		}
		return p.getYears() + " years";
	}
}
