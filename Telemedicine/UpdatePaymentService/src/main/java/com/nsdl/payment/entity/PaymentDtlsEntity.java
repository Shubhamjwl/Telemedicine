package com.nsdl.payment.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
@Table(name = "pmt_dtls", schema = "payment")
public class PaymentDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "pd_id_pk")
	private Long id;

	@Column(name = "pd_pmt_trans_id", nullable = false, unique = true)
	private String transId;

	@Column(name = "pd_pmt_mode", nullable = false)
	private String pmtMode;

	@Column(name = "pd_amount", nullable = false)
	private Integer amount;

	@Column(name = "pd_bank_ref_trans_id", nullable = false, unique = true)
	private Integer bankRefTransId;

	@Column(name = "pd_pmt_date", nullable=false)
	private Date pmtDate;

	@Column(name = "pd_pmt_status", nullable = false)
	private String pmtStatus;

	@Column(name = "pd_refund_amount")
	private Integer refundAmount;

	@Column(name = "pd_refund_date")
	private Date refundDate;

	@Column(name = "pd_card_type", nullable = false)
	private String cardType;

	@Column(name = "pd_card_no", nullable = false)
	private Integer cardNo;

	@Column(name = "pd_name_on_card", nullable = false)
	private String nameOnCard;

	@Column(name = "pd_card_validity", nullable = false)
	private String cardValidity;

	@Version
	@Column(name = "prd_opti_version")
	private Integer optiVersion;

	//Added for paylater option
	@Column(name = "pd_dr_user_id")
	private String docRegId;
	
	@Column(name = "pd_pt_user_id")
	private String ptRegId;
	
	@Column(name = "pd_appt_slot")
	private String apptSlot;

	@Column(name = "pd_appt_date")
	private Date apptDate;
	
	@Column(name = "pd_appt_booked_for")
	private String apptBookedFor;
	
	@Column(name = "pd_created_tmstmp")
	private LocalDateTime createdTimestmp;
	
	//Added for paylater option ends
	
	@Column(name = "pd_slot_type")
	private String pdslotType;

}
