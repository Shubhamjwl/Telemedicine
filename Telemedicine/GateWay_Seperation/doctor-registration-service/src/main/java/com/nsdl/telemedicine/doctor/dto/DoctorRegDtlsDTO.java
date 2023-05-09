package com.nsdl.telemedicine.doctor.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
/**
 * @author SayaliA
 *
 */
public class DoctorRegDtlsDTO<T> {
	
	@NotNull(message = "drMobNo must not be null")
	@Valid
	private String drMobNo;
	
//	@NotNull(message = "drEmail must not be null")
//	@Valid
	private String drEmail;
	
	@NotNull(message = "drUserID must not be null")
	@Valid
	private String drUserID;
	
	@NotNull(message = "drPassword must not be null")
	@Valid
	private String drPassword;
	
	@NotNull(message = "drSMCNo must not be null")
	@Valid
	private String drSMCNo;
	
	@NotNull(message = "drMCINo must not be null")
	@Valid
	private String drMCINo;
	
	@NotNull(message = "drConsultFee must not be null")
	@Valid
	private int drConsultFee;
	
	@NotNull(message = "drSpecilization must not be null")
	@Valid
	private String drSpecilization;
	
	@NotNull(message = "drFullName must not be null")
	@Valid
	private String drFullName;
	
	@NotNull(message = "drProfilePhoto must not be null")
	@Valid
	private String drProfilePhoto;
	
	@NotNull(message = "drAddress1 must not be null")
	@Valid
	private String drAddress1;
	
	private String drAddress2;
	
	private String drAddress3;
	
	@NotNull(message = "drGender must not be null")
	@Valid
	private String drGender;
	
	@NotNull(message = "drCity must not be null")
	@Valid
	private String drCity;
	
	@NotNull(message = "drState must not be null")
	@Valid
	private String drState;
	
	@NotNull(message = "documents must not be null")
	@Valid
	private List<T> documents;
	
	@NotNull(message = "captchaCode must not be null")
	@Valid
	private String captchaCode;
	
	@NotNull(message = "sessionId must not be null")
	@Valid
	private String sessionId;
	
	public List<T> getDocuments() {
		return documents;
	}
	public void setDocuments(List<T> documents) {
		this.documents = documents;
	}
	public String getDrProfilePhoto() {
		return drProfilePhoto;
	}
	public void setDrProfilePhoto(String drProfilePhoto) {
		this.drProfilePhoto = drProfilePhoto;
	}
	public String getDrFullName() {
		return drFullName;
	}
	public void setDrFullName(String drFullName) {
		this.drFullName = drFullName;
	}
	public String getDrMobNo() {
		return drMobNo;
	}
	public void setDrMobNo(String drMobNo) {
		this.drMobNo = drMobNo;
	}
	public String getDrEmail() {
		return drEmail;
	}
	public void setDrEmail(String drEmail) {
		this.drEmail = drEmail;
	}
	public String getDrUserID() {
		return drUserID;
	}
	public void setDrUserID(String drUserID) {
		this.drUserID = drUserID;
	}
	public String getDrPassword() {
		return drPassword;
	}
	public void setDrPassword(String drPassword) {
		this.drPassword = drPassword;
	}
	public String getDrSMCNo() {
		return drSMCNo;
	}
	public void setDrSMCNo(String drSMCNo) {
		this.drSMCNo = drSMCNo;
	}
	public String getDrMCINo() {
		return drMCINo;
	}
	public void setDrMCINo(String drMCINo) {
		this.drMCINo = drMCINo;
	}
	public int getDrConsultFee() {
		return drConsultFee;
	}
	public void setDrConsultFee(int drConsultFee) {
		this.drConsultFee = drConsultFee;
	}
	public String getDrSpecilization() {
		return drSpecilization;
	}
	public void setDrSpecilization(String drSpecilization) {
		this.drSpecilization = drSpecilization;
	}
	public String getDrAddress1() {
		return drAddress1;
	}
	public void setDrAddress1(String drAddress1) {
		this.drAddress1 = drAddress1;
	}
	public String getDrAddress2() {
		return drAddress2;
	}
	public void setDrAddress2(String drAddress2) {
		this.drAddress2 = drAddress2;
	}
	public String getDrAddress3() {
		return drAddress3;
	}
	public void setDrAddress3(String drAddress3) {
		this.drAddress3 = drAddress3;
	}
	public String getDrGender() {
		return drGender;
	}
	public void setDrGender(String drGender) {
		this.drGender = drGender;
	}
	public String getDrCity() {
		return drCity;
	}
	public void setDrCity(String drCity) {
		this.drCity = drCity;
	}
	public String getDrState() {
		return drState;
	}
	public void setDrState(String drState) {
		this.drState = drState;
	}
	public String getCaptchaCode() {
		return captchaCode;
	}
	public void setCaptchaCode(String captchaCode) {
		this.captchaCode = captchaCode;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	

	
}
