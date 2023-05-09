package com.nsdl.auth.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetDoctorDetailsDTO {


	private String drdDrName;
	
	private String drdUserId;
	
	private Integer drdConsulFee;

	private String drdEmail;
	
	private String drdMobileNo;
	
	private String drdGender;

	private String drdMciNumber;
	
	private String drdSmcNumber;

	private String drdSpecialiazation;
	
	private String drdPhotoPath;

	private String drdAddress1;

	private String drdAddress2;

	private String drdAddress3;

	private String drdCity;

	private String drdState;
	
	private String drdIsRegByIpan;
	
	private String drdIsactive;
	
	private List<DoctorDocDtlsDTO> drDocsDtls;
}
