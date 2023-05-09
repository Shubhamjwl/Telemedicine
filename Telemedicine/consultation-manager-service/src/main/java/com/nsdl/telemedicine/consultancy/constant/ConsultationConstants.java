package com.nsdl.telemedicine.consultancy.constant;

public enum ConsultationConstants {

	SCHEDULED("Scheduled"),
	COMPLETED("Completed"),
	PATIENT_ROLE("PATIENT"),
	SCRIBE_ROLE("SCRIBE"),
	DOCTOR_ROLE("DOCTOR"),
	RECEPT_ROLE("RECEPTIONIST"),
	CALL_CENTRE_ROLE("CALLCENTRE"),
	CANCEL("Cancel"),
	RESHEDULED("Resheduled"),
	DIAGNOSISRESPONSEMSG("Diagnosis details saved successfully"),
	DRNAME("Doctor Name"),
	DRSPECIALIZATION("Specialization"),
	DRSMCNO("SMC no."),
	PTNAME("Patient Name"),
	AGE("Age"),
	GENDER("Gender"),
	PTADDRESS("PtAddress"),
	PTMOBILENO("Patient Mobile NO"),
	PTEMAILID("Patiet Email Id"),
	APPTID("Appontment Id"),
	APPTDATE("Date"),
	SYMPTOMS("Symptoms"),
	DIAGNOSIS("Provisional Diagnosis"),
	INVESTIGATION("Investigation"),
	MEDICATION("Medication"),
	ADVICE("Advice"),
	TODAY("Today"),
	TOMORROW("Tomorrow"),
	PATIENTREPORTTEMPDIRECTORY("report"),
	DRMCINO("MCI no."),
	DRMOBILENO("Mobile no."),
	SCHEDULEWP("ScheduleWP"),
	DREMAILID("Email ID.");
	
	private String value;

	ConsultationConstants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public final static String PatientRegistrationTempDirectory = "patientRegistration";
}
