CREATE TABLE payment.payment_dtls (
	pd_id_pk int4 NOT NULL DEFAULT nextval('payment.pmt_dtls_pd_id_pk_seq'::regclass),
	pd_pmt_trans_id varchar(50) NOT NULL,
	pd_pmt_mode varchar(50) NULL,
	pd_amount int4 NOT NULL,
	pd_bank_ref_trans_id varchar(50) NULL,
	pd_pmt_date date NOT NULL,
	pd_pmt_status varchar(120) NOT NULL,
	pd_refund_amount int4 NULL,
	pd_refund_date date NULL,
	pd_created_by varchar(25) NOT NULL,
	pd_created_tmstmp timestamp NOT NULL,
	pd_modified_tmstmp timestamp NULL,
	pd_modified_by varchar(25) NULL,
	pd_razorpay_payment_id varchar(50) NULL,
	pd_razorpay_order_id varchar(50) NULL,
	pd_razorpay_signature varchar(500) NULL,
	pd_payment_method varchar(30) NULL,
	pd_opti_version int4 NULL,
	pd_bank_name varchar(120) NULL,
	CONSTRAINT pmt_dtl_bank_ref_trans_id_key UNIQUE (pd_bank_ref_trans_id),
	CONSTRAINT pmt_dtl_pd_pmt_trans_id_key UNIQUE (pd_pmt_trans_id),
	CONSTRAINT pmt_dtl_pkey PRIMARY KEY (pd_id_pk)
)
WITH (
	OIDS=FALSE
);
