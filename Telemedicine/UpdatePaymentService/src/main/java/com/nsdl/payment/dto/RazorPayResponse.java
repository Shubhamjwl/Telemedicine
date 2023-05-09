package com.nsdl.payment.dto;

import lombok.Data;

@Data
public class RazorPayResponse  {
	
	private String orderId;
	private String paymentId;
	private String signature;
	private String paymentMethod;
	private String paymentStatus;
	private String bankName;
	private String loggedinUser;
	private String transId;
	private String invoiceId;
	private int totalWalletAmount;
	private int usedWalletAmount;
	
	
	

}
