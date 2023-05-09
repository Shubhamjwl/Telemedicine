package com.nsdl.payment.dto;

import lombok.Data;

@Data
public class PaymentRequest {
	
	private int amount;
	private int convenienceCharge;
	private String apptId;
	private String transId;
	private String ptUserID;

}
