package com.nsdl.ndhm.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountBenefitsRespDTO {
	public String address;
	public List<String> authMethods;
	public String dayOfBirth;
	public String districtCode;
	public String districtName;
	public String email;
	public boolean emailVerified;
	public String firstName;
	public String gender;
	public String healthId;
	public String healthIdNumber;
	public String kycPhoto;
	public boolean kycVerified;
	public String lastName;
	public String middleName;
	public String mobile;
	public String monthOfBirth;
	public String name;
	public boolean New;
	public String pincode;
	public String profilePhoto;
	public String stateCode;
	public String stateName;
	public String subDistrictCode;
	public String subdistrictName;
	public TagsDTO tags;
	public String townCode;
	public String townName;
	public String villageCode;
	public String villageName;
	public String wardCode;
	public String wardName;
	public String yearOfBirth;
}
