export class AppConstants {
  //Roles
  public static doctor = 'DOCTOR';
  public static patient = 'PATIENT';
  public static scribe = 'SCRIBE';
  public static callcentre = 'CALLCENTRE';
  public static receptionist = 'RECEPTIONIST';
  public static systemuser = 'SYSTEMUSER';
  public static admin = 'ADMIN';
  // PatientReview/review/v1/getNumberOfLikesToDoctor
  /**
   * Modules URLs
   */
  // User Management
  public static masterManagementModuleURL = 'masterManagement/v1/';
  public static marketPlaceModuleURL = 'tele/'; //marketplace/v1/
  public static usermanagementModuleURL = 'usermanagement';
  public static documentManagementModulleURL = 'documentManagement/'
  public static otpManagerModuleURL = 'OTPManager/';
  public static captchaModuleURL = 'captcha';
  public static bookModuleURL = 'book/v1/';
  public static consultationModuleURL = 'appointment/'; //consultation/appointment/
  public static iPANModuleURL = 'ipan/v1/';
  public static orderHistorySearchModuleURL = "getOrderHistoryDetails"
  // Doctor
  public static doctorRegistrationModuleURL = 'DoctorRegistration'; //doctor-registration/DoctorRegistration
  public static doctorDeRegistrationModuleURL = 'DoctorDeRegistration';
  public static doctorLikesModuleURL = 'review'; //PatientReview/review
  public static doctorByPatientId = 'DoctorRegistration/v1/'; // Check doctor tag with PatientId
  //Payment
  public static paymentModuleURL = 'Payment/';
  // Checker

  // Maker

  // Scribe
  public static scribeService = 'scribe-service';

  // Patient
  public static patientModuleURL = 'patient';
  public static getAppointmentListByPatientID = 'getAppointmentListByPatientID';
  public static downloadPatientReport = 'downloadPatientReport';

  // Patient book appointment
  public static bookAppointmentModuleURL = 'book/v1/';

  // Slot management
  public static slotManagement = 'slotManagement/v1/';

  //resource
  public static downloadAboutUsPdf = 'login/downloadAboutUsPdf';

  // ----END----------MODULE------URLS--------------------------

  /**
   * Application version
   */
  public static appVersion = '/v1/';

  /**
   * APIs URLs
   */
  // Login - Logout user
  public static signInURL = 'login/signin';
  public static createUserURL = 'login/createuser';
  public static signOutURL = 'login/signout';
  public static generateOtpURL = '/generateOtp';
  public static verifyOtpURL = '/verifyOTP';
  public static generateCaptchaURL = '/generateCaptcha';
  public static verifyCaptchaURL = '/verifyCaptcha';
  public static forgotPasswodURL = 'login/forgotPassword';
  public static resetPasswordURL = 'login/resetPassword';
  public static changePasswordURL = 'login/changePassword';
  public static getUserDetailsAndSendOTP = 'login/getUserDetailsAndSendOTP';
  public static signoutWithoutToken = 'login/signoutWithoutToken';

  //Menu
  public static menuList = 'roleFunction/getFunctions';

  // Doctor
  public static getConsultationListByDoctorID = 'getConsultationListByDoctorID';
  public static getScheduledAndRescheduledConsultationList = 'getScheduledAndRescheduledConsultationList';
  public static saveDoctorDetailsURL = 'saveDoctorDetailsTele';
  public static updateDoctorProfile = 'updateDoctorProfile';
  public static viewDoctorProfile = 'viewDoctorProfile';
  public static checkDoctorTagByPatientId = 'checkDoctorTagByPatientId';
  public static uploadPrescriptionTemplate = 'uploadPrescriptionTemplate';
  public static deRegisterDoctor = 'deRegisterDoctor';
  public static verifiedOTPForDocDeRegister = 'verifiedOTPForDocDeRegister';
  public static changeDefaultScribe = 'changeDefaultScribe';
  public static getNumberOfLikesToDoctor = 'getNumberOfLikesToDoctor';
  public static getNumberOfCommentsToDoctor = 'getNumberOfCommentsToDoctor';
  public static getListOfDoctorTodeRegister = 'getListOfDoctorTodeRegister';
  public static seachCompletedAppointmentsforDoctor =
    'searchCompletedAppointmentsForDoctor';
  public static listOfDoctorTodeRegister = 'listOfDoctorTodeRegister';
  public static getMappedPatientListByDrId =
    'patientModification/getMappedPatientListByDrId';
  public static getPatientDetailsByMobNoForDoc = 'patientModification/getPatientDetailsByMobNo';
  public static getPatientDetailsByMobNoForDocVerification = 'patientVerification/getPatientDetailsByMobNo';
  public static updateTermsAndCondForDoc = 'updateTermsAndConditionForDoctor';
  public static deleteSlotDetails = 'deleteSlotDetails';
  public static fetchCategoryDtlsByDrId = 'fetchCategoryDtlsByDrId'

  // Checker
  public static getDoctorListToVerify = 'verifyDoctor/getDoctorListToVerify'; //user/verifyDoctor/getDoctorListToVerify
  public static verifyDoctor = '/verifyDoctor/verifyDoctorByDocName'; //'user/verifyDoctor/verifyDoctorByDocName
  public static doctorDetails = '/verifyDoctor/getDoctorDetails'; //user/verifyDoctor/getDoctorDetails
  public static download = 'downloadDocument';
  public static bulkDoctorRegistration = 'bulkDoctorRegistration';
  public static getListOfDoctorsToAllowLogin = 'verifyDoctor/getListOfDoctorsToAllowLogin';
  public static allowDoctorsForLogin = 'verifyDoctor/allowDoctorsForLogin';
  public static getAllFeaturesCategoryList = 'getAllFeaturesCategoryList';
  // Maker

  // Scribe
  public static updateScribeProfile = 'updateScribeProfile';
  public static viewScribeList = 'viewScribeList';
  public static assignedApptListToScribe = 'assignedApptListToScribe';
  public static getListOfScribeByDoctor = 'getListOfScribeByDoctor';
  public static isScribeAssignedToAppt = 'isScribeAssignedToAppt';
  public static activeDeactiveScribe = 'activeDeactiveScribe';
  public static getScribeListByDoctorToActiveDeactive =
    'getScribeListByDoctorToActiveDeactive';
  public static getScribeDetails = 'getScribeDetails';
  public static assignScribeToApptByDoctor = 'assignScribeToApptByDoctor';


  // Patient
  public static patientRegistrationURL = 'patientRegistration/registration';
  public static patientSearchURL = 'patientVerification/searchPatientByHealthId';
  public static patientRegistartionByScribeOrDoctorWithHealthId =
    'patientVerification/patientRegistartionByScribeOrDoctorWithHealthId';
  public static updatePatientProfile =
    'patientModification/modifyPersonalDetails';
  public static getPatientDetails = 'patientRegistration/getpatientdetails';
  public static getPatientAllDetails =
    'patientModification/getPatientAllDetails';
  public static cancelAppt = 'cancelAppointment';
  public static getconsultationPatientDetails = 'getPatientDetails';
  public static uploadReport = 'saveConsultationPatientReports';
  public static savePatientReviewDtls = 'savePatientReviewDtls';
  public static searchCompltedAppointments =
    'patientRegistration/searchCompletedAppointmentsForPatient';
  public static listOfCompletedAppointmentsForPatient =
    'patientRegistration/listOfCompletedAppointmentsForPatient';
  public static getPatientDetailsByMobNo =
    'patientModification/getPatientDetailsByMobNo';
  public static patientRegistartionByScribeOrDoctor =
    'patientRegistration/patientRegistartionByScribeOrDoctor';
  public static bulkPatientRegistration =
    'patientRegistration/bulkPatientRegistration';
  public static getMappedDrListByPatientId = 'getMappedDrListByPatientId';
  public static unMappedPatientOrDrById =
    'patientModification/unMappedPatientOrDrById';
  public static patientRegistartionByExternalLink =
    'patientRegistration/patientRegistartionByExternalLink';
  public static patientRegistartionByLink =
    'patientRegistration/registerPatientLink';
  public static getNdhmFlag = 'patientRegistration/getNdhmFlag';
  public static getCancelAppointList = "getCancelAppointList";
  public static getCancelAppointListByFilter = "getCancelAppointListByFilter";
  public static getScheduledAndRescheduledConsultationListByPatientId = "getScheduledAndRescheduledConsultationListByPatientId";
  public static updateAbhaDetails = 'patientVerification/updateAbhaDetails';     //Use to Update the ABHA Details.
  public static getAllDocumentList = 'getAllDocumentList';
  public static uploadPatientDocument = 'uploadPatientDocument';
  public static getAllDocumentDtls = 'getAllDocumentDtls';
  public static deletePatientDocument = 'deletePatientDocument';
  public static downloadPatientDocument = 'downloadPatientDocument'


  // Book Appointment
  public static getListOfDoctorBySearchURL = 'getListOfDoctorBySearch';
  public static dummyPaymentApi = 'dummyPaymentApi';
  public static saveAppointmentDetails = 'saveAppointmentDetails';
  public static saveRescheduleApi = 'saveRescheduleApptDetails';
  public static bookAppointmentOnline = 'bookAppointmentOnline';
  public static updateStartTimeByAppointmentId = 'updateStartTimeByAppointmentId';
  public static dummyPaymentApIForExternalLink =
    'dummyPaymentApIForExternalLink';
  public static saveAppointmentDetailsForPatientByLink =
    'saveAppointmentDetailsForPatientByLink';

  //SlotBy Doctor
  public static getAvailableSlotListByDoctor = 'getAvailableSlotListByDoctor';

  //Mster Dropdownlist
  public static DetailsListByMasterName = 'getMasterDetailsListByMasterName';

  public static associationNameList = 'getAssociationNameList';

  //Consultation
  public static getConsultationDiagnosisDtls = 'getConsultationDiagnosisDtls';
  public static getDrRedflagMapURL = 'getDrRedflagMapURL';
  public static saveConsultationDiagnosis = 'saveConsultationDiagnosis';
  public static saveConsultationChiefComplaint =
    'saveConsultationChiefComplaint';
  public static getConsultationChiefComplaint = 'getConsultationChiefComplaint';
  public static getConsultationInvestigationDtls =
    'getConsultationInvestigationDtls';
  public static saveConsultationInvestigation = 'saveConsultationInvestigation';
  public static getPatientMedicationDtls = 'getPatientMedicationDtls';
  public static saveConsultationMedicationDtls =
    'saveConsultationMedicationDtls';
  public static getConsultationAdvice = 'getConsultationAdvice';
  public static saveConsultationAdvice = 'saveConsultationAdvice';
  public static getConsultationDetails = 'getConsultationDetails';
  public static saveConsultationDtls = 'saveConsultationDtls';
  public static updateConsultationStatus = 'updateConsultationStatus';
  public static getPrescriptionDetails = 'getPrescriptionDetails';
  public static getDetailsForPrescription = 'getDetailsForPrescription';
  public static getStateList = 'getStateList';
  public static getCityList = 'getCityList';
  public static checkUniqueValue = 'checkUniqueValue';
  public static startConsultation = 'videoconference/generateVCToken';
  public static createMeeting = 'bluevc/createMeeting';
  public static joinMeeting = 'bluevc/joinMeeting';
  public static generateKarzaMeetingUrl = 'videoconference/generateKarzaMeetingUrl';
  public static getKarzaMeetingUrl = 'videoconference/getKarzaMeetingUrl';
  public static getSymptomsDetails = 'symptoms';
  public static getAdviceDetails = 'advice';
  public static getMedicinesDetails = 'medicines';
  public static getDiagnosisDetails = 'diagnosis';
  public static getSearchEngineDetailsBySymtpoms = 'getSearchEngineDetailsBySymtpoms';
  public static getDrRedflagDetails = 'getDrRedflagDetails';
  public static saveOrderDetails = 'saveOrderDetails';
  public static saveMarketPlaceDetails = 'saveMarketPlaceDetails';
  public static getTransactionId = 'getTransactionId';

  // Home page
  public static getCountOfDoctors = 'getCountOfDoctors';
  public static getCountOfConsultation = 'getCountOfConsultation';
  public static getCountOfSpeciality = 'getCountOfSpeciality';

  // Slot management
  public static saveSlotDetails = 'saveSlotDetails';
  public static saveSlotDetailsForDoctorRegistration = 'saveSlotDetailsForDoctorRegistration';
  public static getSlotCreatedDays = 'getSlotCreatedDays';
  public static getAvailableSlotByMonth = 'getAvailableSlotByMonth';
  public static holidayManagement = 'holidayManagement';
  public static getApptHistory = 'getApptHistory';
  public static getHistoricalSlotDetailsByMonth =
    'getHistoricalSlotDetailsByMonth';

  //Payment

  public static payment = 'pay';
  public static orderPayment = 'payResponse';
  public static payLater = 'payLater';
  public static payUsingLink = 'payUsingLink';
  public static payResponseForBookingLink = 'payResponseForBookingLink';

  //ndhm
  public static searchHealthIdToLogin = 'ndhm/searchHealthIdToLogin';
  public static searchByHealthId = 'ndhm/searchByHealthId';
  public static authInit = 'ndhm/authInit';
  public static confirmAadhaarOtp = 'ndhm/confirm-aadhaar-otp';
  public static confirmMobileOtp = 'ndhm/confirm-mobile-otp';
  public static getAccountProfile = 'ndhm/getAccountProfile';



  // ----END----------API------URLS--------------------------


}
