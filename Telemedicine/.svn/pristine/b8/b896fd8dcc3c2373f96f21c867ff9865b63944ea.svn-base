package com.nsdl.telemedicine.scribe.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pmt_dtls", schema = "payment")
public class PaymentDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "pd_id_pk")
	private Long pdIdPk;

	@Column(name = "pd_pmt_trans_id", nullable = false, unique = true)
	private String pdPmtTransId;

	@Column(name = "pd_pmt_mode", nullable = false)
	private String pdPmtMode;

	@Column(name = "pd_amount", nullable = false)
	private Integer pdAmount;

	@Column(name = "pd_bank_ref_trans_id", nullable = false, unique = true)
	private Integer pdBankRefTransId;

	@Column(name = "pd_pmt_date", nullable=false)
	private Date pdPmtDate;

	@Column(name = "pd_pmt_status", nullable = false)
	private String pdPmtStatus;

	@Column(name = "pd_refund_amount")
	private Integer pdRefundAmount;

	@Column(name = "pd_refund_date")
	private Date pdRefundDate;

	@Column(name = "pd_card_type", nullable = false)
	private String pdCardType;

	@Column(name = "pd_card_no", nullable = false)
	private Integer pdCardNo;

	@Column(name = "pd_name_on_card", nullable = false)
	private String pdNameOnCard;

	@Column(name = "pd_card_validity", nullable = false)
	private String pdCardValidity;

	@Column(name = "prd_opti_version")
	private String prdOptiVersion;

}
