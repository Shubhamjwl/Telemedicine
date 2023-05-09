package com.nsdl.payment.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class RazorPayResponse  {
	
	private String orderId;
	@NotEmpty(message="payment Id cannot be null or empty")
	private String paymentId;
	private String signature;
	private String paymentMethod;
	private String paymentStatus;
	private String bankName;
	private String loggedinUser;
	private String transId;
	private String invoiceId;
	private String ptUserID;
	private int totalWalletAmount;
	private int usedWalletAmount;
	
	

}
