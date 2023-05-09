package com.nsdl.telemedicine.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDetailsDTO {

	private Long idPk;
	private String transtId;
	private String drEmail;
	private String ptNname;
	private String ptMobile;
	private String orderId;
	private Date orderDate;
	private String orderStatus;
	private String specialistDoctorName;
	private String productName;
	private Double productPrice;
	private String prescriptionFile;
	private String prescriptionFileName;
	private String prescriptionFileType;
	private String prescriptionText;

}
