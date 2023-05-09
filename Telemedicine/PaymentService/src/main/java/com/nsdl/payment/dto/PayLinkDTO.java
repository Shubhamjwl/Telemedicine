package com.nsdl.payment.dto;

import lombok.Data;

@Data
public class PayLinkDTO {
	private String custName;
	private String custEmail;
	private String custMobile;
	private Integer amount;
	private Integer convenienceCharge;
	private Integer discount;
	private String transId;
	private String doctorId;
	//private String consultType;
	

}
