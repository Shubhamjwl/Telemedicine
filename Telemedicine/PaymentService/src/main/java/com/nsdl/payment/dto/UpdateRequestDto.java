package com.nsdl.payment.dto;

import lombok.Data;

@Data
public class UpdateRequestDto {
	private String transId;
	private String orderId;
	private String status;
	private String receiptId;  //Transaction Id of application
	private int amount;
	private int convenienceCharge;
	private String loggedinUser;
	private int totalWalletAmount;

}
