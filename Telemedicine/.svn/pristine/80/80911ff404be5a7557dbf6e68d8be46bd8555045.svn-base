package com.nsdl.ndhm.utility;

import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyAgreement;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class FhirEncryptDecryptUtil {
    private static final Logger logger = LoggerFactory.getLogger(FhirEncryptDecryptUtil.class);

    public static final String ALGORITHM = "ECDH";
    public static final String CURVE = "curve25519";
    public static final String PROVIDER = BouncyCastleProvider.PROVIDER_NAME;
    private static String strToPerformActionOn = "{\"resourceType\":\"Bundle\",\"id\":\"prescription-bundle-01\",\"meta\":{\"versionId\":\"1\",\"lastUpdated\":\"2020-07-09T15:32:26.605+05:30\",\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/DocumentBundle\"],\"security\":[{\"system\":\"http://terminology.hl7.org/CodeSystem/v3-Confidentiality\",\"code\":\"V\",\"display\":\"veryrestricted\"}]},\"identifier\":{\"system\":\"http://hip.in\",\"value\":\"bc3c6c57-2053-4d0e-ac40-139ccccff645\"},\"type\":\"document\",\"timestamp\":\"2020-07-09T15:32:26.605+05:30\",\"entry\":[{\"fullUrl\":\"Composition/Composition-01\",\"resource\":{\"resourceType\":\"Composition\",\"id\":\"Composition-01\",\"meta\":{\"versionId\":\"1\",\"lastUpdated\":\"2020-07-09T15:32:26.605+05:30\",\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/PrescriptionRecord\"]},\"language\":\"en-IN\",\"text\":{\"status\":\"generated\",\"div\":\"<divxmlns=\\\"http://www.w3.org/1999/xhtml\\\">Prescriptionreport</div>\"},\"identifier\":{\"system\":\"https://ndhm.in/phr\",\"value\":\"645bb0c3-ff7e-4123-bef5-3852a4784813\"},\"status\":\"final\",\"type\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"440545006\",\"display\":\"Prescriptionrecord\"}]},\"subject\":{\"reference\":\"Patient/Patient-01\"},\"date\":\"2017-05-27T11:46:09+05:30\",\"author\":[{\"reference\":\"Practitioner/Practitioner-01\"}],\"title\":\"Prescriptionrecord\",\"section\":[{\"title\":\"Prescriptionrecord\",\"code\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"440545006\",\"display\":\"Prescriptionrecord\"}]},\"entry\":[{\"reference\":\"MedicationRequest/MedicationRequest-01\",\"type\":\"MedicationRequest\"},{\"reference\":\"MedicationRequest/MedicationRequest-02\",\"type\":\"MedicationRequest\"},{\"reference\":\"Binary/Binary-01\",\"type\":\"Binary\"}]}]}},{\"fullUrl\":\"Patient/Patient-01\",\"resource\":{\"resourceType\":\"Patient\",\"id\":\"Patient-01\",\"meta\":{\"versionId\":\"1\",\"lastUpdated\":\"2020-07-09T14:58:58.181+05:30\",\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/Patient\"]},\"text\":{\"status\":\"generated\",\"div\":\"<divxmlns=\\\"http://www.w3.org/1999/xhtml\\\">ABC,41year,Male</div>\"},\"identifier\":[{\"type\":{\"coding\":[{\"system\":\"http://terminology.hl7.org/CodeSystem/v2-0203\",\"code\":\"MR\",\"display\":\"Medicalrecordnumber\"}]},\"system\":\"https://ndhm.in/SwasthID\",\"value\":\"1234\"}],\"name\":[{\"text\":\"ABC\"}],\"telecom\":[{\"system\":\"phone\",\"value\":\"+919818512600\",\"use\":\"home\"}],\"gender\":\"male\",\"birthDate\":\"1981-01-12\"}},{\"fullUrl\":\"Practitioner/Practitioner-01\",\"resource\":{\"resourceType\":\"Practitioner\",\"id\":\"Practitioner-01\",\"meta\":{\"versionId\":\"1\",\"lastUpdated\":\"2019-05-29T14:58:58.181+05:30\",\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/Practitioner\"]},\"text\":{\"status\":\"generated\",\"div\":\"<divxmlns=\\\"http://www.w3.org/1999/xhtml\\\">Dr.DEF,MD(Medicine)</div>\"},\"identifier\":[{\"type\":{\"coding\":[{\"system\":\"http://terminology.hl7.org/CodeSystem/v2-0203\",\"code\":\"MD\",\"display\":\"MedicalLicensenumber\"}]},\"system\":\"https://ndhm.in/DigiDoc\",\"value\":\"7601003178999\"}],\"name\":[{\"text\":\"Dr.DEF\"}]}},{\"fullUrl\":\"MedicationRequest/MedicationRequest-01\",\"resource\":{\"resourceType\":\"MedicationRequest\",\"id\":\"MedicationRequest-01\",\"meta\":{\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/MedicationRequest\"]},\"status\":\"active\",\"intent\":\"order\",\"medicationCodeableConcept\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"324252006\",\"display\":\"Azithromycin(asazithromycindihydrate)250mgoralcapsule\"}]},\"subject\":{\"reference\":\"Patient/Patient-01\",\"display\":\"ABC\"},\"authoredOn\":\"2020-07-09\",\"requester\":{\"reference\":\"Practitioner/Practitioner-01\",\"display\":\"DrDEF\"},\"reasonCode\":[{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"11840006\",\"display\":\"Traveller'sDiarrhea(disorder)\"}]}],\"reasonReference\":[{\"reference\":\"Condition/Condition-01\"}],\"dosageInstruction\":[{\"text\":\"Onetabletatonce\",\"additionalInstruction\":[{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"311504000\",\"display\":\"Withorafterfood\"}]}],\"timing\":{\"repeat\":{\"frequency\":1,\"period\":1,\"periodUnit\":\"d\"}},\"route\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"26643006\",\"display\":\"OralRoute\"}]},\"method\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"421521009\",\"display\":\"Swallow\"}]}}]}},{\"fullUrl\":\"MedicationRequest/MedicationRequest-02\",\"resource\":{\"resourceType\":\"MedicationRequest\",\"id\":\"MedicationRequest-02\",\"meta\":{\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/MedicationRequest\"]},\"status\":\"active\",\"intent\":\"order\",\"medicationCodeableConcept\":{\"text\":\"Paracetemol500mgOralTab\"},\"subject\":{\"reference\":\"Patient/Patient-01\",\"display\":\"ABC\"},\"authoredOn\":\"2020-07-09\",\"requester\":{\"reference\":\"Practitioner/Practitioner-01\",\"display\":\"DrDEF\"},\"reasonCode\":[{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"602001\",\"display\":\"Rossriverfever\"}]}],\"reasonReference\":[{\"reference\":\"Condition/Condition-01\"}],\"dosageInstruction\":[{\"text\":\"Taketwotabletsorallywithoraftermealonceaday\"}]}},{\"fullUrl\":\"Condition/Condition-01\",\"resource\":{\"resourceType\":\"Condition\",\"id\":\"Condition-01\",\"meta\":{\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/Condition\"]},\"text\":{\"status\":\"generated\",\"div\":\"<divxmlns=\\\"http://www.w3.org/1999/xhtml\\\">Abdominalpainon09-July2020</div>\"},\"code\":{\"coding\":[{\"system\":\"http://snomed.info/sct\",\"code\":\"21522001\",\"display\":\"Abdominalpain\"}],\"text\":\"Abdominalpain\"},\"subject\":{\"reference\":\"Patient/Patient-01\"}}},{\"fullUrl\":\"Binary/Binary-01\",\"resource\":{\"resourceType\":\"Binary\",\"id\":\"Binary-01\",\"meta\":{\"profile\":[\"https://nrces.in/ndhm/fhir/r4/StructureDefinition/Binary\"]},\"contentType\":\"application/pdf\",\"data\":\"R0lGODlhfgCRAPcAAAAAAIAAAACAAICAAAAAgIAAoxrXyMY2uvGNcIyjHOeoxkXBh44OOZdn8Ggu+DiPjwtJ2CZyUomCTRGO\"}}]}";
    private static String receiverPublicKey;
    private static String receiverPrivateKey;
    private static String senderPrivateKey;
    private static String senderPublicKey;
    private static String randomSender;
    private static String randomReceiver;

    /* Generate the DH keys for receiver */
    public static void receiverPublicPrivateKeygen() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
                                                            NoSuchProviderException {
        KeyPair receiverKeyPair = generateKeyPair();
        receiverPublicKey = getBase64String(getEncodedPublicKey(receiverKeyPair.getPublic()));
        receiverPrivateKey = getBase64String(getEncodedPrivateKey(receiverKeyPair.getPrivate()));
    }

    /* Generate the DH keys for sender */
    public static void senderPublicPrivateKeygen() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException,
                                                          NoSuchProviderException {
        KeyPair senderKeyPair = generateKeyPair();
        senderPrivateKey = getBase64String(getEncodedPrivateKey(senderKeyPair.getPrivate()));
        senderPublicKey = getBase64String(getEncodedPublicKey(senderKeyPair.getPublic()));
    }

    public static String receiverNonce() {
        randomReceiver = generateRandomKey(); // Receiver random key or nonce
        return randomReceiver;
    }

    public static String senderNonce() {
        randomSender = generateRandomKey();  // Sender random key or nonce
        return randomSender;
    }

    public static byte[] xorOfRandom(String randomKeySender,String randomKeyReceiver) {
        byte[] randomSender = getBytesForBase64String(randomKeySender);
        byte[] randomReceiver = getBytesForBase64String(randomKeyReceiver);

        byte[] out = new byte[randomSender.length];
        for (int i = 0; i < randomSender.length; i++) {
            out[i] = (byte) (randomSender[i] ^ randomReceiver[i%randomReceiver.length]);
        }
        return out;
    }

    /* Method for encryption */
    public static String encrypt(String randomKeyReceiver, String receiverPublicKey) throws NoSuchAlgorithmException,
                                                InvalidKeySpecException, NoSuchProviderException, InvalidKeyException {

        byte[] xorOfRandom = xorOfRandom(randomSender, randomKeyReceiver);
        String stringToEncrypt = strToPerformActionOn;

    /* <------------------- ENCRYPTION --------------------> */
        /* Generating shared secret */
        String sharedKey = doECDH(getBytesForBase64String(senderPrivateKey), getBytesForBase64String(receiverPublicKey));

        /* Generating iv and HKDF-AES key */
        byte[] iv = Arrays.copyOfRange(xorOfRandom, xorOfRandom.length - 12, xorOfRandom.length);
        byte[] aesKey = generateAesKey(xorOfRandom, sharedKey);

        /* Perform Encryption */
        String encryptedData = "";
        try {
            byte[] stringBytes = stringToEncrypt.getBytes();

            GCMBlockCipher cipher = new GCMBlockCipher(new AESEngine());
            AEADParameters parameters =
                    new AEADParameters(new KeyParameter(aesKey), 128, iv, null);

            cipher.init(true, parameters);
            byte[] plainBytes = new byte[cipher.getOutputSize(stringBytes.length)];
            int retLen = cipher.processBytes
                    (stringBytes, 0, stringBytes.length, plainBytes, 0);
            cipher.doFinal(plainBytes, retLen);

            encryptedData = getBase64String(plainBytes);
        } catch (Exception e) {
            logger.error("error in encrypt data: {}",e.getLocalizedMessage());
        }

    /* <------------------- ENCRYPTION done --------------------> */
        return encryptedData;
    }

    /* Method for decryption */
    public static String decrypt(String randomKeySender, String senderPublicKey, String stringToDecrypt)
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException, InvalidKeyException {
        byte[] xorOfRandom = xorOfRandom(randomReceiver, randomKeySender);

    /* <------------------- DECRYPTION --------------------> */
        /* Generating shared secret */
        String sharedKey = doECDH(getBytesForBase64String(receiverPrivateKey),getBytesForBase64String(senderPublicKey));

        /* Generating iv and HKDF-AES key */
        byte[] iv = Arrays.copyOfRange(xorOfRandom, xorOfRandom.length - 12, xorOfRandom.length);
        byte[] aesKey = generateAesKey(xorOfRandom, sharedKey);

        /* Perform Decryption */
        String decryptedData = "";
        try {
            byte[] encryptedBytes = getBytesForBase64String(stringToDecrypt);

            GCMBlockCipher cipher = new GCMBlockCipher(new AESEngine());
            AEADParameters parameters =
                    new AEADParameters(new KeyParameter(aesKey), 128, iv, null);

            cipher.init(false, parameters);
            byte[] plainBytes = new byte[cipher.getOutputSize(encryptedBytes.length)];
            int retLen = cipher.processBytes
                    (encryptedBytes, 0, encryptedBytes.length, plainBytes, 0);
            cipher.doFinal(plainBytes, retLen);

            decryptedData = new String(plainBytes);
        } catch (Exception e) {
            logger.error("error in decrypt data: {}",e.getLocalizedMessage());
        }

    /* <------------------- DECRYPTION done --------------------> */
        return decryptedData;
    }

    public static byte[] getBytesForBase64String(String value){
        return org.bouncycastle.util.encoders.Base64.decode(value);
    }

    /* Method for generating DH Keys */
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException,
                                                   InvalidAlgorithmParameterException {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
        X9ECParameters ecParameters = CustomNamedCurves.getByName(CURVE);
        ECParameterSpec ecSpec=new ECParameterSpec(ecParameters.getCurve(), ecParameters.getG(),
                ecParameters.getN(), ecParameters.getH(), ecParameters.getSeed());

        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    public static String getBase64String(byte[] value){
        return new String(org.bouncycastle.util.encoders.Base64.encode(value));
    }

    public static byte [] getEncodedPublicKey(PublicKey key) {
        ECPublicKey ecKey = (ECPublicKey)key;
        return ecKey.getQ().getEncoded(true);
    }

    public static byte [] getEncodedPrivateKey(PrivateKey key) {
        ECPrivateKey ecKey = (ECPrivateKey)key;
        return ecKey.getD().toByteArray();
    }

    /* Method for generating random string */
    public static String generateRandomKey() {
        byte[] salt = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return getBase64String(salt);
    }

    /* Method for generating HKDF AES key */
    private static byte [] generateAesKey(byte[] xorOfRandoms, String sharedKey ){
        byte[] salt = Arrays.copyOfRange(xorOfRandoms, 0, 20);
        HKDFBytesGenerator hkdfBytesGenerator = new HKDFBytesGenerator(new SHA256Digest());
        HKDFParameters hkdfParameters = new HKDFParameters(getBytesForBase64String(sharedKey), salt, null);
        hkdfBytesGenerator.init(hkdfParameters);
        byte[] aesKey = new byte[32];
        hkdfBytesGenerator.generateBytes(aesKey, 0, 32);
        return aesKey;
    }

    /* Method for generating shared secret */
    private static String doECDH (byte[] dataPrv, byte[] dataPub) throws NoSuchAlgorithmException,
                                                NoSuchProviderException, InvalidKeySpecException, InvalidKeyException {
        KeyAgreement ka = KeyAgreement.getInstance(ALGORITHM, PROVIDER);
        ka.init(loadPrivateKey(dataPrv));
        ka.doPhase(loadPublicKey(dataPub), true);
        byte [] secret = ka.generateSecret();
        return getBase64String(secret);
    }

    private static PrivateKey loadPrivateKey (byte [] data) throws NoSuchAlgorithmException, NoSuchProviderException,
                                                                   InvalidKeySpecException {
        X9ECParameters ecP = CustomNamedCurves.getByName(CURVE);
        ECParameterSpec params=new ECParameterSpec(ecP.getCurve(), ecP.getG(),
                ecP.getN(), ecP.getH(), ecP.getSeed());
        ECPrivateKeySpec privateKeySpec = new ECPrivateKeySpec(new BigInteger(data), params);
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM, PROVIDER);
        return kf.generatePrivate(privateKeySpec);
    }

    private static PublicKey loadPublicKey (byte [] data) throws NoSuchAlgorithmException, NoSuchProviderException,
                                                                 InvalidKeySpecException {
        Security.addProvider(new BouncyCastleProvider());
        X9ECParameters ecP = CustomNamedCurves.getByName(CURVE);
        ECParameterSpec ecNamedCurveParameterSpec = new ECParameterSpec(ecP.getCurve(), ecP.getG(),
                ecP.getN(), ecP.getH(), ecP.getSeed());

        return KeyFactory.getInstance(ALGORITHM, PROVIDER)
                .generatePublic(new ECPublicKeySpec(ecNamedCurveParameterSpec.getCurve().decodePoint(data),
                        ecNamedCurveParameterSpec));
    }

    /* Getter Setter */
    public static String getReceiverPublicKey() {
        return receiverPublicKey;
    }

    public static String getSenderPublicKey() {
        return senderPublicKey;
    }

    public static String getStrToPerformActionOn() {
        return strToPerformActionOn;
    }

    public static void setStrToPerformActionOn(String strToPerformActionOn) {
        FhirEncryptDecryptUtil.strToPerformActionOn = strToPerformActionOn;
    }
}
