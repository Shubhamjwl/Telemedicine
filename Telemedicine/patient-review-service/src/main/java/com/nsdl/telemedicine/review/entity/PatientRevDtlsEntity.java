package com.nsdl.telemedicine.review.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "pt_rev_dtls", schema = "usrmgmt", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "prd_dr_user_id_fk", " prd_Pt_user_id_fk" }) })
public class PatientRevDtlsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "prd_id_pk")
	private Long prdIdPk;

	@Column(name = "prd_dr_user_id_fk", nullable = false)
	private String prdDrUserIdFk;

	@Column(name = "prd_Pt_user_id_fk", nullable = false)
	private String prdPtUserIdFk;

	@Column(name = "prd_review", nullable = false)
	private String prdReview;

	@Column(name = "prd_review_date", nullable = false)
	private LocalDate prdReviewDate;

	@Column(name = "prd_rating", columnDefinition = "integer default 1", nullable = false)
	private Long prdRating;

	@Column(name = "prd_created_By", nullable = false)
	private String prdCreatedBy;

	@Column(name = "prd_created_tmstmp", nullable = false)
	private LocalDateTime prdCreatedTmstmp;

//	@Version
	@Column(name = "prd_opti_version")
	private String prdOptiVersion;

	@PrePersist
	private void prePersist() {
		prdCreatedTmstmp = LocalDateTime.now();
		prdReviewDate = LocalDate.now();
	}
	
}
