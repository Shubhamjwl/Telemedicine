package com.nsdl.telemedicine.review.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "pt_rev_dtls_aud", schema = "audit")
public class PatientRevDtlsEntityAudited implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
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
	
	@Column(name = "user_id", nullable = false)
	private String userId;

	@Column(name = "timestamp", nullable = false)
	private LocalDateTime timestamp;

	@PrePersist
	private void prePersist() {
		this.timestamp = LocalDateTime.now();
	}
	
}
