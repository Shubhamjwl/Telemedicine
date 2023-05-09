package com.nsdl.payment.dto;

import lombok.Data;

@Data
public class InvoiceDetail {
	private String paymentLinkId;
	private String paymentId;
	private String orderId;
	private String transId; //Application's transaction Id
	private Integer amount_due;
	private Integer convenienceCharge;
	private Integer discount;
	private String loggedinUser;
	private String invoiceId;
	private String invoiceStatus;

}
