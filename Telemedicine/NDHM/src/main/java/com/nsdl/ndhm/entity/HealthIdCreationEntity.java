package com.nsdl.ndhm.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
/*@Table(name = "healthId_dtls" , schema = "ndhm")*/
@Table(name = "healthId_dtls")
public class HealthIdCreationEntity {
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String healthId;	
	private String healthIdNumber;
	private String dayOfBirth;
	private String districtCode;
	private String districtName;
	private String email;
	private String firstName;
	private String gender;
	
	@Column(name = "kycPhoto", columnDefinition = "TEXT")
	private String kycPhoto;
	private String lastName;
	private String middleName;
	private String mobile;
	private String monthOfBirth;
	private String name;
	@Column(name = "isNew", columnDefinition = "boolean default false")
	private boolean New;
	@Column(name = "profilePhoto", columnDefinition = "TEXT")
	private String profilePhoto;
	private String stateCode;
	private String stateName;
	private String yearOfBirth;

	/**
	 * @ElementCollection private List<String> authMethods = new
	 *                    ArrayList<String>();
	 **/

	@Column(name = "token", columnDefinition = "TEXT")
	private String token;

	private String createdFrom;

	@Column(name = "created_on", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdOn;
	
	@Column(name = "refreshToken", columnDefinition = "TEXT")
	private String refreshToken;
	
	private String pincode;
}
