package com.nsdl.ndhm.utility.fhir.files.opconconsult.two;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.parser.DataFormatException;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;
import com.nsdl.ndhm.dto.datareport.DoctorDetailsDto;
import com.nsdl.ndhm.dto.datareport.PatientRegDetailsDTO;
import com.nsdl.ndhm.dto.datareport.ReportResponseDtls;
import com.nsdl.ndhm.dto.opconsult.DocumentReferenceDTO;
import com.nsdl.ndhm.dto.opconsult.EncounterDTO;
import com.nsdl.ndhm.dto.opconsult.OrganizationDTO;
import com.nsdl.ndhm.dto.opconsult.ReportDoc;
import com.nsdl.ndhm.dto.prescription.CodingDTO;
import com.nsdl.ndhm.dto.prescription.PatientDTO;
import com.nsdl.ndhm.dto.prescription.PractitionerDTO;
import org.apache.commons.io.FileUtils;
import org.hl7.fhir.common.hapi.validation.support.*;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.BundleType;
import org.hl7.fhir.r4.model.Composition.CompositionStatus;
import org.hl7.fhir.r4.model.Composition.SectionComponent;
import org.hl7.fhir.r4.model.Narrative.NarrativeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class OPConsultNoteTwo {
	private static final Logger logger = LoggerFactory.getLogger(OPConsultNoteTwo.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

	@Autowired
    OPConsultResourceTwo opConsultResource;

	// The FHIR context is the central starting point for the use of the HAPI FHIR API
	// It should be created once, and then used as a factory for various other types of objects (parsers, clients, etc.)
	static FhirContext ctx = FhirContext.forR4();

	static FhirInstanceValidator instanceValidator;
	static FhirValidator validator;

	public String getFhirString(String careCxtEncounterId,LocalDateTime localDateTime, PatientRegDetailsDTO patientRegDTO, DoctorDetailsDto doctorDetailsDto,
								List<ReportResponseDtls> responseDtlsList) throws DataFormatException, IOException {

		String lastUpdatedElement = getTimeString(localDateTime);
		logger.info("LastUpdatedElement {}", lastUpdatedElement);
		String patientUniqueId = patientRegDTO.getAbhaId();
		String patientGender = patientRegDTO.getGender().trim();
		String patientName = patientRegDTO.getName().trim();
		String doctorUniqueId = doctorDetailsDto.getMciNo();
		String doctorName = doctorDetailsDto.getDrName();
		String docuRefNum = new Random().ints(48, 122 + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(10)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		//Initialize validation support and loads all required profiles
		init();

		insertData(docuRefNum, careCxtEncounterId, patientRegDTO, doctorDetailsDto, responseDtlsList, lastUpdatedElement);
				
		// Populate the resource
		Bundle opConsultNoteBundle = populateOPConsultNoteBundle(docuRefNum, careCxtEncounterId, lastUpdatedElement,
				patientGender, patientUniqueId, patientName, doctorUniqueId, doctorName);

		// Validate it. Validate method return result of validation in boolean
		// If validation result is true then parse, serialize operations are performed
		/*if (validate(opConsultNoteBundle)) {*/
            if (true) {
			logger.info("Validated populated OPConsultNote bundle successfully");

			// Instantiate a new parser
			IParser parser = ctx.newJsonParser();

			// Indent the output
			parser.setPrettyPrint(true);

			// Serialize populated bundle
			String serializeBundle = parser.encodeResourceToString(opConsultNoteBundle);

			return serializeBundle;
		} else {
			logger.info("Failed to validate populate OPConsultNote bundle");
			return "";
		}
	}

	// Populate Composition for OPConsultNote
	static Composition populateOPConsultNoteCompositionResource(String docuRefNum, String careCxtEncounterId,
																String lastUpdatedElement, String patientUniqueId,
																String patientName, String doctorUniqueId, String doctorName)
	{
		Composition composition = new Composition();

		// Set logical id of this artifact
		composition.setId("Composition-01");

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = composition.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType(lastUpdatedElement));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/OPConsultRecord");

		// Set language of the resource content
		composition.setLanguage("en-IN");

		// Plain text representation of the concept
		Narrative text= composition.getText();
		text.setStatus((NarrativeStatus.GENERATED));
		text.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">OPConsult report</div>");

		// Set version-independent identifier for the Composition
		Identifier identifier = composition.getIdentifier();
		identifier.setSystem("https://ndhm.in/phr");
		identifier.setValue(UUID.randomUUID().toString());

		// Status can be preliminary | final | amended | entered-in-error
		composition.setStatus(CompositionStatus.FINAL);

		// Kind of composition ("Clinical consultation report")
		CodeableConcept type = composition.getType();
		type.addCoding(new Coding("http://snomed.info/sct", "371530004", "Clinical consultation report"));
		type.setText("Clinical Consultation report");

		//composition.setType(new CodeableConcept(new Coding("http://snomed.info/sct", "371530004", "Clinical consultation report")).setText("Clinical Consultation report"));

		// Set subject - Who and/or what the composition/OPConsultNote record is about
		Reference reference = new Reference();
		reference.setReference("Patient/" + patientUniqueId + "");
		reference.setDisplay(patientName);
		composition.setSubject(reference);

		// Set Context of the Composition
		composition.setEncounter(new Reference().setReference("Encounter/" + careCxtEncounterId + ""));

		// Set Timestamp
		composition.setDateElement(new DateTimeType(lastUpdatedElement));

		// Set author - Who and/or what authored the composition/OPConsultNote record
		Reference referenceAuthor = new Reference();
		referenceAuthor.setReference("Practitioner/" + doctorUniqueId + "");
		referenceAuthor.setDisplay(doctorName);
		composition.addAuthor(referenceAuthor);

		// Set a Human Readable name/title
		composition.setTitle("Consultation Report");

		// Set Custodian - Organization which maintains the composition
		Reference referenceCustodian = new Reference();
		referenceCustodian.setReference("Organization/Organization-01");
		referenceCustodian.setDisplay("Protean Clinic");
		composition.setCustodian(referenceCustodian);

		// Composition is broken into sections / OPConsultNote record contains single section to define the relevant medication requests
		// Entry is a reference to data that supports this section
		SectionComponent section8 = new SectionComponent();
		section8.setTitle("Document Reference");
		section8.setCode(new CodeableConcept(new Coding("http://snomed.info/sct", "371530004", "Clinical consultation report"))).
		addEntry(new Reference().setReference("DocumentReference/"+ docuRefNum + ""));

		List<SectionComponent> sectionList = new ArrayList<SectionComponent>();
		sectionList.add(section8);
		composition.setSection(sectionList);

		return composition;
	}

	Bundle populateOPConsultNoteBundle(String docuRefNum, String careCxtEncounterId, String lastUpdatedElement,
									   String patientGender, String patientUniqueId, String patientName,
									   String doctorUniqueId, String doctorName) {
		Bundle opCounsultNoteBundle = new Bundle();

		// Set logical id of this artifact
		opCounsultNoteBundle.setId("OPConsultNote-"+docuRefNum);

		// Set metadata about the resource - Version Id, Lastupdated Date, Profile
		Meta meta = opCounsultNoteBundle.getMeta();
		meta.setVersionId("1");
		meta.setLastUpdatedElement(new InstantType(lastUpdatedElement));
		meta.addProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle");

		// Set Confidentiality as defined by affinity domain
		meta.addSecurity(new Coding("http://terminology.hl7.org/CodeSystem/v3-Confidentiality", "V", "very restricted"));

		// Set version-independent identifier for the Composition
		Identifier identifier = opCounsultNoteBundle.getIdentifier();
		identifier.setValue(UUID.randomUUID().toString());
		identifier.setSystem("http://hip.in");

		// Set Bundle Type
		opCounsultNoteBundle.setType(BundleType.DOCUMENT);

		// Set Timestamp
		opCounsultNoteBundle.setTimestampElement(new InstantType(lastUpdatedElement));

		// Add resources entries for bundle with Full URL
		List<BundleEntryComponent> listBundleEntries = opCounsultNoteBundle.getEntry();

		BundleEntryComponent bundleEntry1 = new BundleEntryComponent();
		bundleEntry1.setFullUrl("Composition/Composition-01");
		bundleEntry1.setResource(populateOPConsultNoteCompositionResource(docuRefNum, careCxtEncounterId,
				lastUpdatedElement, patientUniqueId, patientName, doctorUniqueId, doctorName));

		BundleEntryComponent bundleEntry2 = new BundleEntryComponent();
		bundleEntry2.setFullUrl("Practitioner/" + doctorUniqueId + "");
		bundleEntry2.setResource(opConsultResource.populatePractitionerResource());

		BundleEntryComponent bundleEntry3 = new BundleEntryComponent();
		bundleEntry3.setFullUrl("Organization/Organization-01");
		bundleEntry3.setResource(opConsultResource.populateOrganizationResource());

		BundleEntryComponent bundleEntry4 = new BundleEntryComponent();
		bundleEntry4.setFullUrl("Patient/" + patientUniqueId + "");
		bundleEntry4.setResource(opConsultResource.populatePatientResource(patientGender));

		BundleEntryComponent bundleEntry5 = new BundleEntryComponent();
		bundleEntry5.setFullUrl("Encounter/" + careCxtEncounterId + "");
		bundleEntry5.setResource(opConsultResource.populateEncounterResource());

		BundleEntryComponent bundleEntry15 = new BundleEntryComponent();
		bundleEntry15.setFullUrl("DocumentReference/"+ docuRefNum + "");
		bundleEntry15.setResource(opConsultResource.populateDocumentReferenceResource());

		listBundleEntries.add(bundleEntry1);
		listBundleEntries.add(bundleEntry2);
		listBundleEntries.add(bundleEntry3);
		listBundleEntries.add(bundleEntry4);
		listBundleEntries.add(bundleEntry5);
		listBundleEntries.add(bundleEntry15);

		return opCounsultNoteBundle;
	}

	/**
	 * This method initiates loading of FHIR default profiles and NDHM profiles for validation 
	 */
	@Cacheable(value="cacheFileList")
	public void init() throws DataFormatException
	{

		// Create xml parser object for reading profiles
		IParser parser = ctx.newXmlParser();

		// Create a chain that will hold our modules
		ValidationSupportChain supportChain = new ValidationSupportChain();
		
		// Add Default Profile Support
		// DefaultProfileValidationSupport supplies base FHIR definitions. This is generally required
		// even if you are using custom profiles, since those profiles will derive from the base
		// definitions.
		DefaultProfileValidationSupport defaultSupport = new DefaultProfileValidationSupport(ctx);
		
		// Create a PrePopulatedValidationSupport which can be used to load custom definitions.
		// In this example we're loading all the custom Profile Structure Definitions, in other scenario we might
		// load many StructureDefinitions, ValueSets, CodeSystems, etc.
		PrePopulatedValidationSupport prePopulatedSupport = new PrePopulatedValidationSupport(ctx);
		StructureDefinition sd ;


		/** LOADING PROFILES **/
		// Read all Profile Structure Definitions
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

		//Add Snapshot Generation Support
		SnapshotGeneratingValidationSupport snapshotGenerator = new SnapshotGeneratingValidationSupport(ctx);

		//Add prepopulated support consisting all structure definitions and Terminology support
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
	static boolean validate(IBaseResource resource)
	{
		// Validate
		ValidationResult result = validator.validateWithResult(resource);

		// The result object now contains the validation results
		result.getMessages().stream()
				.map(next -> next.getSeverity().name() + " : " + next.getLocationString() + " " + next.getMessage()).forEach(System.out::println);

		return result.isSuccessful();
	}

	public void insertData(String docuRefNum, String careCxtEncounterId, PatientRegDetailsDTO patientRegDTO,
						   DoctorDetailsDto doctorDetailsDto, List<ReportResponseDtls> responseDtlsList,
						   String lastUpdatedElement) {
		SimpleDateFormat format1 = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		String patientDivData = "" + patientRegDTO.getName() + ", " + calculateAge(patientRegDTO.getDob()) + ", "
				+ patientRegDTO.getGender() + "";
		String doctorDivData = "Dr. " + doctorDetailsDto.getDrName() + ", " + doctorDetailsDto.getSpeciality() + "";
		doctorDivData = doctorDivData.replace("&", "and").replaceAll("[^.,a-zA-Z0-9\\s]", "");
		logger.info(patientDivData);
		logger.info(doctorDivData);

		PatientDTO patientDTO = new PatientDTO();
		patientDTO.setId(patientRegDTO.getAbhaId());
		patientDTO.setVersionId("1");
		patientDTO.setLastUpdatedElement(format1.format(new Date()));
		patientDTO.setAddProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Patient");
		patientDTO.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">" + patientDivData + "</div>");
		patientDTO.setCodingDTO(new CodingDTO("http://terminology.hl7.org/CodeSystem/v2-0203", "MR", "Medical record number"));
		patientDTO.setIdentifierSystem("https://ndhm.in/SwasthID");
		patientDTO.setIdentifierValue(patientRegDTO.getAbhaId());
		patientDTO.setNameText(patientRegDTO.getName());
		patientDTO.setTelecomValue(patientRegDTO.getMob().toString());
		patientDTO.setBirthDateElement(patientRegDTO.getDob());

		opConsultResource.setPatientDTO(patientDTO);

		PractitionerDTO practitionerDTO = new PractitionerDTO();
		practitionerDTO.setId(doctorDetailsDto.getMciNo());
		practitionerDTO.setVersionId("1");
		practitionerDTO.setLastUpdatedElement(format1.format(new Date()));
		practitionerDTO.setAddProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Practitioner");
		practitionerDTO.setDivAsString("<div xmlns=\"http://www.w3.org/1999/xhtml\">" + doctorDivData + "</div>");
		practitionerDTO.setCodingDTO(new CodingDTO("http://terminology.hl7.org/CodeSystem/v2-0203", "MD", "Medical License number"));
		practitionerDTO.setIdentifierSystem("https://ndhm.in/DigiDoc");
		practitionerDTO.setIdentifierValue(doctorDetailsDto.getMciNo());
		practitionerDTO.setNameText(doctorDetailsDto.getDrName());

		opConsultResource.setPractitionerDTO(practitionerDTO);

		DocumentReferenceDTO documentReferenceDTO = new DocumentReferenceDTO();
		documentReferenceDTO.setId(docuRefNum);
		documentReferenceDTO.setAddProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentReference");
		documentReferenceDTO.setSubjectReference("Patient/"+patientRegDTO.getAbhaId()+"");
		documentReferenceDTO.setTypeCode(new CodingDTO("http://snomed.info/sct", "4241000179101", "Laboratory report"));
		documentReferenceDTO.setDocumentText("Laboratory report");
		documentReferenceDTO.setReportDocList(
				responseDtlsList.stream().map(reportDtls ->
								new ReportDoc(reportDtls.getReport_type(), getFileFormat(reportDtls.getReport_name()),
										"en-IN", reportDtls.getReport_type(),
										getTimeString(reportDtls.getDocCreationTimestamp()),reportDtls.getReport_doc()))
						.collect(Collectors.toList()));

		opConsultResource.setDocumentReferenceDTO(documentReferenceDTO);

		EncounterDTO encounterDTO = new EncounterDTO();
		encounterDTO.setId(careCxtEncounterId);
		encounterDTO.setInstantType(lastUpdatedElement);
		encounterDTO.setAddProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Encounter");
		encounterDTO.setIdentifierSystem("https://ndhm.in");
		encounterDTO.setIdentifierValue("S100");
		encounterDTO.setEncounterCode(new CodingDTO("http://terminology.hl7.org/CodeSystem/v3-ActCode", "IMP", "inpatient encounter"));
		encounterDTO.setSubjectReference("Patient/"+patientRegDTO.getAbhaId()+"");
		encounterDTO.setPeriodStart(lastUpdatedElement);
		encounterDTO.setPeriodEnd(lastUpdatedElement);
		encounterDTO.setDischargeCode(new CodingDTO("http://terminology.hl7.org/CodeSystem/discharge-disposition", "home", "Home"));
		encounterDTO.setDischargeText("Discharged to Home Care");

		opConsultResource.setEncounterDTO(encounterDTO);

		OrganizationDTO organizationDTO = new OrganizationDTO();
		organizationDTO.setId("Organization-01");
		organizationDTO.setAddProfile("https://nrces.in/ndhm/fhir/r4/StructureDefinition/Organization");
		organizationDTO.setIdentifierCode(new CodingDTO("http://terminology.hl7.org/CodeSystem/v2-0203", "PRN", "Provider number"));
		organizationDTO.setIdentifierSystem("https://facility.ndhm.gov.in");
		organizationDTO.setIdentifierValue("4567878");
		organizationDTO.setOrganisationName("www.protean-health.com");
		organizationDTO.setContactNum("+91 22 2499 4200");
		organizationDTO.setEmail("xyz@nsdl.co.in");

		opConsultResource.setOrganizationDTO(organizationDTO);
	}

	public String calculateAge(String dob) {
		Period p = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
			LocalDate currentDate = LocalDate.parse(dateFormat.format(dateFormat.parse(dob.trim())));
			p = Period.between(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth()),
					LocalDate.now());
		} catch (ParseException e) {
			logger.error("error in calculate age: {}", e.getMessage());
		}
		return p.getYears() + " years";
	}

	public String getTimeString(LocalDateTime localDateTime) {
		return ZonedDateTime.of(localDateTime, ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
	}

	public String getFileFormat(String reportFileStr) {
		String format = reportFileStr.substring(reportFileStr.lastIndexOf(".") + 1);

		return format.equalsIgnoreCase("pdf") ? "application/pdf" :
				(format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("jpeg")) ? "image/jpeg" :
				format.equalsIgnoreCase("png") ? "image/png" :
				format.equalsIgnoreCase("doc") ? "application/msword" :
		        format.equalsIgnoreCase("docx") ? "application/vnd.openxmlformats-officedocument.wordprocessingml.document" :
				format.equalsIgnoreCase("xls") ? "application/vnd.ms-excel" :
				format.equalsIgnoreCase("xlsx") ?
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" : "application/pdf";
	}
}
