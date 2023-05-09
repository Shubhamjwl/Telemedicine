package com.nsdl.telemedicine.scribe.dto;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Scribe Registration Data Tranfer Object")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScribeRegDTO {
	
	@ApiModelProperty(value = "scrbFullName", required = true, dataType = "java.lang.String")
	//@NotBlank(message = "Name is mandatory")
	private String scrbFullName;
	
	@ApiModelProperty(value = "scrbMobNo", required = true, dataType = "java.lang.String")
	//@NotBlank(message = "Mobile number is mandatory")
	private String scrbMobNo;
	
	@ApiModelProperty(value = "scrbEmail", dataType = "java.lang.String")
	//@Email(message = "Email should be valid")
	private String scrbEmail;
	
	@ApiModelProperty(value = "scrbUserID", required = true, dataType = "java.lang.String")
	//@NotBlank(message = "UserID is mandatory")
	private String scrbUserID;
	
	@ApiModelProperty(value = "dmdUserId", required = true, dataType = "java.lang.String")
	private String scrbdrUserIDfk;
	
	private String scrbDrName;
	
	@ApiModelProperty(value = "scrbPassword", required = true, dataType = "java.lang.String")
	//@NotBlank(message = "Password is mandatory")
	private String scrbPassword;
	
	@ApiModelProperty(value = "scrbAdd1", required = false, dataType = "java.lang.String")
	private String scrbAdd1;
	
	@ApiModelProperty(value = "scrbAdd2", required = false, dataType = "java.lang.String")
	private String scrbAdd2;
	
	@ApiModelProperty(value = "scrbAdd3", required = false, dataType = "java.lang.String")
	private String scrbAdd3;
	
	@ApiModelProperty(value = "scrbAdd4", required = false, dataType = "java.lang.String")
	private String scrbAdd4;
	
	@ApiModelProperty(value = "scribeProfilePhoto", required = true, dataType = "java.lang.String")
	//@NotEmpty(message = "Photo mandatory to be captured")
	private String scribeProfilePhoto;
	
	private String captchaCode;
	
	private String sessionId;
	
	private String isDefaultScribe;
	
	private String scrbGender;
	
	private String scrbState;
	
	private String scrbCity;
}
