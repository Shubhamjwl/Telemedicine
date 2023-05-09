package com.nsdl.telemedicine.doctor.dto;

import java.util.List;

import lombok.Data;

@Data
public class DoctorMasterDTO {


	private Integer dmdConsulFee;
	
	private Integer dmdConvenienceCharge;

	private String dmdDrName;

	private String dmdEmail;

	private String dmdGender;

	private String dmdIsRegByIpan;

	private Boolean dmdIsverified;

	private String dmdMciNumber;

	private Long dmdMobileNo;

	private String dmdSmcNumber;

	private String dmdSpecialiazation;
	
	private String dmdUserId;

	private List<DoctorDocDTO> drDocsDtls;
	
	private List<ScribeRegDtlsDTO> drScribeDtls;
	
	private String profilePhoto;
	
	private String dmdAddress1;
	
	private String dmdAddress2;
	
	private String dmdAddress3;
	
	private String dmdState;
	
	private String dmdCity; 
	
	private String dmdDrLink;
	
	private String dmdDrProfileLink;
	
	private String dmdPreassessmentLink;
	
	private boolean dmdPreassessmentFlag;
	
	private String dmdPatientRegistrationLink;
	
	private Boolean dmdTcFlag;
	
	private String dmdAssociationName;
	
	private String dmdAssociationNumber;

}
