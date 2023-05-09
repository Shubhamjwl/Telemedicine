package com.nsdl.payment.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;


/**
 * The persistent class for the payment_dtls database table.
 * 
 */
@Data
@Entity
@Table(name="payment_dtls",schema = "payment")
@NamedQuery(name="PaymentDtl.findAll", query="SELECT p FROM PaymentDtl p")
public class PaymentDtl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="pd_id_pk")
	private Integer pdId;

	@Column(name="pd_amount")
	private Integer amount;
	
	@Column(name="pd_convenience_charge")
	private Integer convenienceCharge;
	
	@Column(name="pd_discount")
	private Integer discount;

	@Column(name="pd_bank_ref_trans_id")
	private String bankRefTransId;

	@Column(name="pd_created_by")
	private String createdBy;

	@Column(name="pd_created_tmstmp")
	private LocalDateTime createdTmstmp;

	@Column(name="pd_modified_by")
	private String modifiedBy;

	@Column(name="pd_modified_tmstmp")
	private LocalDateTime modifiedTmstmp;

	@Version
	@Column(name="pd_opti_version")
	private Integer optiVersion;

	@Column(name="pd_payment_method")
	private String paymentMethod;

	
	@Column(name="pd_pmt_date")
	private LocalDateTime payDate;

	@Column(name="pd_pmt_mode")
	private String payMode;

	@Column(name="pd_pmt_status")
	private String payStatus;

	@Column(name="pd_pmt_trans_id")
	private String transId;

	@Column(name="pd_razorpay_order_id")
	private String razorpayOrderId;

	@Column(name="pd_razorpay_payment_id")
	private String razorpayPaymentId;

	@Column(name="pd_razorpay_signature")
	private String razorpaySignature;

	@Column(name="pd_refund_amount")
	private Integer refundAmount;

	
	@Column(name="pd_refund_date")
	private Date refundDate;
	private String bankName;
	@Column(name="pd_invoice_id")
	private String invoiceId;

	
}