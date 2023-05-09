package com.nsdl.telemedicine.doctor.dto;

public class CreateUserRequestDTO {
     
	 private String userFullName ;
	 private String userId ;
	 private String password ;
	 private Long mobileNumber ;
	 private String email ;
	 private String userType ;
	 private String smcNumber ;
	 private String mciNumber ;
	 public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	private String roleName ;
	
	 
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getSmcNumber() {
		return smcNumber;
	}
	public void setSmcNumber(String smcNumber) {
		this.smcNumber = smcNumber;
	}
	public String getMciNumber() {
		return mciNumber;
	}
	public void setMciNumber(String mciNumber) {
		this.mciNumber = mciNumber;
	}
                
}
