package com.nsdl.telemedicine.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderHistoryDetailsDTO {

	private String patientName;
	private String orderId;
	private Date orderDate;

}
