package com.nsdl.auth.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * @author SayaliA
 *
 */
@Data
@Builder
@EqualsAndHashCode
public class DoctorRegDtlsDTO {
	
	private String drMobNo;
	private String drEmail;
	private String drUserID;
	private String drSMCNo;
	private String drMCINo;
	//private int drConsultFee;
	private String drSpecilization;
	private String drFullName;
	private String currentStatus;
	
}
