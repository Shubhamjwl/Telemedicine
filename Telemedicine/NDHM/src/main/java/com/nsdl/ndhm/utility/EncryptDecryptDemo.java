package com.nsdl.ndhm.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.nsdl.ndhm.utility.FhirEncryptDecryptUtil.*;

public class EncryptDecryptDemo {
    private static final Logger logger = LoggerFactory.getLogger(EncryptDecryptDemo.class);

    public static void main(String[] args) throws Exception {
        String endingDash = "<-------------------------------------------------------------------------->";

        /* HIU Receiver side operations */
        receiverPublicPrivateKeygen();
        String receiverPublicKey = getReceiverPublicKey();
        String randomKeyReceiver = receiverNonce();
        logger.info("ReceiverPublicKey: {}",receiverPublicKey);
        logger.info("RandomKeyReceiver: {}",randomKeyReceiver);
        logger.info(endingDash);


        /* HIP Sender side operations */
        senderPublicPrivateKeygen();
        String fhirJsonString = "{\"resourceType\":\"Bundle\",\"id\":\"prescription-bundle-01\",\"meta\":{\"versionId\":\"1\",\"lastUpdated\":\"2020-07-09T15:32:26.605+05:30\",\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle\"],\"security\":[{\"system\":\"http://terminology.hl7.org/CodeSystem/v3-Confidentiality\",\"code\":\"V\",\"display\":\"veryrestricted\"}]},\"identifier\":{\"system\":\"http://hip.in\",\"value\":\"bc3c6c57-2053-4d0e-ac40-139ccccff645\"},\"type\":\"document\",\"timestamp\":\"2020-07-09T15:32:26.605+05:30\",\"entry\":[{\"fullUrl\":\"Composition/Composition-01\",\"resource\":{\"resourceType\":\"Composition\",\"id\":\"Composition-01\",\"meta\":{\"versionId\":\"1\",\"lastUpdated\":\"2020-07-09T15:32:26.605+05:30\",\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/PrescriptionRecord\"]},\"language\":\"en-IN\",\"text\":{\"status\":\"generated\",\"div\":\"<divxmlns=\\\"http://www.w3.org/1999/xhtml\\\">Prescriptionreport</div>\"},\"identifier\":{\"system\":\"https://ndhm.in/phr\",\"value\":\"645bb0c3-ff7e-4123-bef5-3852a4784813\"},\"status\":\"final\",\"type\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"440545006\",\"display\":\"Prescriptionrecord\"}]},\"subject\":{\"reference\":\"Patient/Patient-01\"},\"date\":\"2017-05-27T11:46:09+05:30\",\"author\":[{\"reference\":\"Practitioner/Practitioner-01\"}],\"title\":\"Prescriptionrecord\",\"section\":[{\"title\":\"Prescriptionrecord\",\"code\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"440545006\",\"display\":\"Prescriptionrecord\"}]},\"entry\":[{\"reference\":\"MedicationRequest/MedicationRequest-01\",\"type\":\"MedicationRequest\"},{\"reference\":\"MedicationRequest/MedicationRequest-02\",\"type\":\"MedicationRequest\"},{\"reference\":\"Binary/Binary-01\",\"type\":\"Binary\"}]}]}},{\"fullUrl\":\"Patient/Patient-01\",\"resource\":{\"resourceType\":\"Patient\",\"id\":\"Patient-01\",\"meta\":{\"versionId\":\"1\",\"lastUpdated\":\"2020-07-09T14:58:58.181+05:30\",\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/Patient\"]},\"text\":{\"status\":\"generated\",\"div\":\"<divxmlns=\\\"http://www.w3.org/1999/xhtml\\\">ABC,41year,Male</div>\"},\"identifier\":[{\"type\":{\"coding\":[{\"system\":\"http://terminology.hl7.org/CodeSystem/v2-0203\",\"code\":\"MR\",\"display\":\"Medicalrecordnumber\"}]},\"system\":\"https://ndhm.in/SwasthID\",\"value\":\"1234\"}],\"name\":[{\"text\":\"ABC\"}],\"telecom\":[{\"system\":\"phone\",\"value\":\"+919818512600\",\"use\":\"home\"}],\"gender\":\"male\",\"birthDate\":\"1981-01-12\"}},{\"fullUrl\":\"Practitioner/Practitioner-01\",\"resource\":{\"resourceType\":\"Practitioner\",\"id\":\"Practitioner-01\",\"meta\":{\"versionId\":\"1\",\"lastUpdated\":\"2019-05-29T14:58:58.181+05:30\",\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/Practitioner\"]},\"text\":{\"status\":\"generated\",\"div\":\"<divxmlns=\\\"http://www.w3.org/1999/xhtml\\\">Dr.DEF,MD(Medicine)</div>\"},\"identifier\":[{\"type\":{\"coding\":[{\"system\":\"http://terminology.hl7.org/CodeSystem/v2-0203\",\"code\":\"MD\",\"display\":\"MedicalLicensenumber\"}]},\"system\":\"https://ndhm.in/DigiDoc\",\"value\":\"7601003178999\"}],\"name\":[{\"text\":\"Dr.DEF\"}]}},{\"fullUrl\":\"MedicationRequest/MedicationRequest-01\",\"resource\":{\"resourceType\":\"MedicationRequest\",\"id\":\"MedicationRequest-01\",\"meta\":{\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/MedicationRequest\"]},\"status\":\"active\",\"intent\":\"order\",\"medicationCodeableConcept\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"324252006\",\"display\":\"Azithromycin(asazithromycindihydrate)250mgoralcapsule\"}]},\"subject\":{\"reference\":\"Patient/Patient-01\",\"display\":\"ABC\"},\"authoredOn\":\"2020-07-09\",\"requester\":{\"reference\":\"Practitioner/Practitioner-01\",\"display\":\"DrDEF\"},\"reasonCode\":[{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"11840006\",\"display\":\"Traveller'sDiarrhea(disorder)\"}]}],\"reasonReference\":[{\"reference\":\"Condition/Condition-01\"}],\"dosageInstruction\":[{\"text\":\"Onetabletatonce\",\"additionalInstruction\":[{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"311504000\",\"display\":\"Withorafterfood\"}]}],\"timing\":{\"repeat\":{\"frequency\":1,\"period\":1,\"periodUnit\":\"d\"}},\"route\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"26643006\",\"display\":\"OralRoute\"}]},\"method\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"421521009\",\"display\":\"Swallow\"}]}}]}},{\"fullUrl\":\"MedicationRequest/MedicationRequest-02\",\"resource\":{\"resourceType\":\"MedicationRequest\",\"id\":\"MedicationRequest-02\",\"meta\":{\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/MedicationRequest\"]},\"status\":\"active\",\"intent\":\"order\",\"medicationCodeableConcept\":{\"text\":\"Paracetemol500mgOralTab\"},\"subject\":{\"reference\":\"Patient/Patient-01\",\"display\":\"ABC\"},\"authoredOn\":\"2020-07-09\",\"requester\":{\"reference\":\"Practitioner/Practitioner-01\",\"display\":\"DrDEF\"},\"reasonCode\":[{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"602001\",\"display\":\"Rossriverfever\"}]}],\"reasonReference\":[{\"reference\":\"Condition/Condition-01\"}],\"dosageInstruction\":[{\"text\":\"Taketwotabletsorallywithoraftermealonceaday\"}]}},{\"fullUrl\":\"Condition/Condition-01\",\"resource\":{\"resourceType\":\"Condition\",\"id\":\"Condition-01\",\"meta\":{\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition\"]},\"text\":{\"status\":\"generated\",\"div\":\"<divxmlns=\\\"http://www.w3.org/1999/xhtml\\\">Abdominalpainon09-July2020</div>\"},\"code\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"21522001\",\"display\":\"Abdominalpain\"}],\"text\":\"Abdominalpain\"},\"subject\":{\"reference\":\"Patient/Patient-01\"}}},{\"fullUrl\":\"Binary/Binary-01\",\"resource\":{\"resourceType\":\"Binary\",\"id\":\"Binary-01\",\"meta\":{\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/Binary\"]},\"contentType\":\"application/pdf\",\"data\":\"R0lGODlhfgCRAPcAAAAAAIAAAACAAICAAAAAgIAAoxrXyMY2uvGNcIyjHOeoxkXBh44OOZdn8Ggu+DiPjwtJ2CZyUomCTRGO\"}}]}";
        setStrToPerformActionOn(fhirJsonString);
        String senderPublicKey = getSenderPublicKey();
        String randomKeySender = senderNonce();
        String encryptedFhir = encrypt(randomKeyReceiver, receiverPublicKey);
        logger.info("SenderPublicKey: {}",senderPublicKey);
        logger.info("RandomKeySender: {}",randomKeySender);
        logger.info(endingDash);
        logger.info("EncryptedFHIR: {}",encryptedFhir);


        /* HIU Receiver side operations */
        String decryptedFhir = decrypt(randomKeySender, senderPublicKey, encryptedFhir);
        logger.info("DecryptedFhir: {}",decryptedFhir);
        logger.info(endingDash);
    }
}
